package backend.academy.singlethreading;

import backend.academy.data.image.Coordinates;
import backend.academy.data.image.Fractal;
import backend.academy.data.image.ImageSettings;
import backend.academy.data.image.Pixel;
import backend.academy.data.image.Point;
import backend.academy.data.image.RGB;
import backend.academy.data.postprocessing.BlurCorrection;
import backend.academy.data.postprocessing.GammaCorrection;
import backend.academy.data.postprocessing.HeatMap;
import backend.academy.service.ImageGenerator;
import backend.academy.service.Renderer;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import static backend.academy.data.image.Coordinates.scale;
import static backend.academy.singlethreading.Application.RANDOM;

@Log4j2
@RequiredArgsConstructor
public class SingleThreadGenerator implements ImageGenerator {
    private final Renderer renderer;

    private static Point getRandomPoint(Fractal fractal) {
        double newX = RANDOM.nextDouble(fractal.xMin(), fractal.xMax());
        double newY = RANDOM.nextDouble(fractal.yMin(), fractal.yMax());

        return new Point(newX, newY);
    }

    @Override
    public Fractal generate(ImageSettings settings) {
        Fractal fractal = Fractal.of(settings.heightRes(), settings.widthRes(), 1.3);

        for (int i = 0; i < settings.startingPoints(); i++) {
            Point current = getRandomPoint(fractal);
            for (int step = -20; step < settings.iterationsForPoint(); step++) {
                var transformation = settings.getRandomTransformation();
                current = transformation.apply(current);
                applyTransformedPoint(current, settings.symmetry(), transformation.rgb(), fractal);
            }
            if (i % 500 == 0) {
                log.info("Point {} processed", i);
//                renderer.update(fractal);
            }
        }
        new GammaCorrection().accept(fractal);
//        new HeatMap().accept(fractal);
        new BlurCorrection().accept(fractal);
        return fractal;
    }

    private void hitPixel(Point position, RGB rgb, Fractal fractal) {
        Coordinates scaled = scale(position, fractal, 1.0);
        if (!fractal.contains(scaled)) {
            return;
        }

        Pixel hitPixel = fractal.getPixel(scaled);
        if (hitPixel.hitCount() > 0) {
            rgb = hitPixel.rgb().blend(rgb);
        }
        fractal.setPixel(
            scaled,
            new Pixel(rgb, hitPixel.hitCount() + 1, hitPixel.normal())
        );
    }

    private void applyTransformedPoint(Point transformed, int symmetryCount, RGB rgb, Fractal fractal) {
        double theta2 = 0.0;
        for (int sym = 0; sym < symmetryCount; theta2 += Math.PI * 2 / symmetryCount, sym++) {
            Point rotated = transformed.rotate(theta2);
            if (!fractal.containsUnscaled(transformed)) {
                continue;
            }
            hitPixel(rotated, rgb, fractal);
        }
    }
}

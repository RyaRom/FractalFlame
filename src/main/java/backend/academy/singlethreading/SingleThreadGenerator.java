package backend.academy.singlethreading;

import backend.academy.data.image.Coordinates;
import backend.academy.data.image.Frame;
import backend.academy.data.image.ImageSettings;
import backend.academy.data.image.Pixel;
import backend.academy.data.image.Point;
import backend.academy.data.image.RGB;
import backend.academy.data.postprocessing.GammaCorrection;
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

    private static Point getRandomPoint(Frame frame) {
        double newX = RANDOM.nextDouble(frame.xMin(), frame.xMax());
        double newY = RANDOM.nextDouble(frame.yMin(), frame.yMax());

        return new Point(newX, newY);
    }

    @Override
    public Frame generate(ImageSettings settings) {
        Frame frame = new Frame(settings.widthRes(), settings.heightRes());

        for (int i = 0; i < settings.startingPoints(); i++) {
            Point current = getRandomPoint(frame);
            for (int step = 0; step < settings.iterationsForPoint(); step++) {
                var transformation = settings.getRandomTransformation();
                Point transformed = transformation.apply(current);
                applyTransformedPoint(transformed, settings.symmetry(), transformation.rgb(), frame);
            }
            if (i % 500 == 0) {
                log.info("Point {} processed", i);
//                renderer.update(frame);
            }
        }
        new GammaCorrection().accept(frame);
        return frame;
    }

    private void hitPixel(Point position, RGB rgb, Frame frame) {
        Coordinates scaled = scale(position, frame, 0.7);
        if (!frame.contains(scaled)) {
            return;
        }

        Pixel hitPixel = frame.getPixel(scaled);
        if (hitPixel.hitCount() > 0) {
            rgb = hitPixel.rgb().blend(rgb);
        }
        frame.setPixel(
            scaled,
            new Pixel(rgb, hitPixel.hitCount() + 1, hitPixel.normal())
        );
    }

    private void applyTransformedPoint(Point transformed, int symmetryCount, RGB rgb, Frame frame) {
        double theta2 = 0.0;
        for (int sym = 0; sym < symmetryCount; theta2 += Math.PI * 2 / symmetryCount, sym++) {
            Point rotated = transformed.rotate(theta2);
            if (!frame.containsUnscaled(transformed)) {
                continue;
            }
            hitPixel(rotated, rgb, frame);
        }
    }
}

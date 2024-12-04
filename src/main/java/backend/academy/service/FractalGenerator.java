package backend.academy.service;

import backend.academy.data.image.Coordinates;
import backend.academy.data.image.Fractal;
import backend.academy.data.image.ImageSettings;
import backend.academy.data.image.Pixel;
import backend.academy.data.image.Point;
import backend.academy.data.image.RGB;
import static backend.academy.data.image.Coordinates.scale;
import static backend.academy.singlethreading.Application.RANDOM;

public interface FractalGenerator {
    Fractal generate(ImageSettings settings);

    default void hitPixel(Point position, RGB rgb, Fractal fractal) {
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

    default void drawTransformedPoint(Point transformed, int symmetryCount, RGB rgb, Fractal fractal) {
        double theta2 = 0.0;
        for (int sym = 0; sym < symmetryCount; theta2 += Math.PI * 2 / symmetryCount, sym++) {
            Point rotated = transformed.rotate(theta2);
            if (!fractal.containsUnscaled(transformed)) {
                continue;
            }
            hitPixel(rotated, rgb, fractal);
        }
    }

    default void processPointTransformations(Fractal fractal, ImageSettings settings){
        Point current = getRandomPoint(fractal);
        for (int step = -20; step < settings.iterationsForPoint(); step++) {
            var transformation = settings.getRandomTransformation();
            current = transformation.apply(current);
            drawTransformedPoint(current, settings.symmetry(), transformation.rgb(), fractal);
        }
    }

    default Point getRandomPoint(Fractal fractal) {
        double newX = RANDOM.nextDouble(fractal.xMin(), fractal.xMax());
        double newY = RANDOM.nextDouble(fractal.yMin(), fractal.yMax());

        return new Point(newX, newY);
    }
}

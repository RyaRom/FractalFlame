package backend.academy.service.fractals;

import backend.academy.data.image.Coordinates;
import backend.academy.data.image.Fractal;
import backend.academy.data.image.Pixel;
import backend.academy.data.image.Point;
import backend.academy.data.image.RGB;
import backend.academy.data.webDTO.GenerationProcess;
import static backend.academy.data.image.Coordinates.scale;
import static backend.academy.service.fractals.FractalUtil.RANDOM;

public interface FractalGenerator {

    /**
     * takes a reference to the fractal and modifies it
     *
     * @param fractal the reference to fractal
     * @param id      the id of the fractal and current generation process
     *                <p>
     *                {@link GenerationProcess}
     *                </p>
     */
    void generate(Fractal fractal, String id);

    default void hitPixel(Point position, RGB rgb, Fractal fractal) {
        Coordinates scaled = scale(position, fractal, 1.0);
        if (!fractal.contains(scaled)) {
            return;
        }

        Pixel hitPixel = fractal.getPixel(scaled);
        RGB newColor = rgb;
        if (hitPixel.hitCount() > 0) {
            newColor = hitPixel.rgb().blend(rgb);
        }
        fractal.setPixel(
            scaled,
            new Pixel(newColor, hitPixel.hitCount() + 1, hitPixel.normal())
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

    default Point getRandomPoint(Fractal fractal) {
        double newX = RANDOM.nextDouble(fractal.xMin(), fractal.xMax());
        double newY = RANDOM.nextDouble(fractal.yMin(), fractal.yMax());

        return new Point(newX, newY);
    }
}

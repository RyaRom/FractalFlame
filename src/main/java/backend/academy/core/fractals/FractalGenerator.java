package backend.academy.core.fractals;

import backend.academy.core.data.image.Coordinates;
import backend.academy.core.data.image.Fractal;
import backend.academy.core.data.image.Pixel;
import backend.academy.core.data.image.Point;
import backend.academy.core.data.image.RGB;
import backend.academy.web.data.webDTO.GenerationProcess;
import static backend.academy.core.data.image.Coordinates.scale;
import static backend.academy.core.fractals.FractalUtil.RANDOM;

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

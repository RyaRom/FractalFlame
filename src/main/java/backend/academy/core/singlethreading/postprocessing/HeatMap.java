package backend.academy.core.singlethreading.postprocessing;

import backend.academy.core.data.image.Fractal;
import backend.academy.core.data.image.Pixel;
import backend.academy.core.data.image.RGB;
import backend.academy.core.data.transformations.PostProcessing;

@SuppressWarnings("MagicNumber")
public class HeatMap implements PostProcessing {
    public static RGB interpolateColor(double normalized) {
        double[][] gradient = {
            {0.0, 0, 0, 255},
            {1.0, 255, 0, 0}
        };

        double[] start = gradient[0];
        double[] end = gradient[1];

        int r = (int) (start[1] + normalized * (end[1] - start[1]));
        int g = (int) (start[2] + normalized * (end[2] - start[2]));
        int b = (int) (start[3] + normalized * (end[3] - start[3]));

        return new RGB(r, g, b);
    }

    @Override
    public void process(Fractal fractal) {
        for (int x = 0; x < fractal.width(); x++) {
            for (int y = 0; y < fractal.height(); y++) {
                processPoint(fractal, x, y);
            }
        }
    }

    public static void processPoint(Fractal fractal, int x, int y) {
        Pixel current = fractal.getPixel(x, y);
        RGB newColor = current.hitCount() == 0
            ? new RGB(0, 0, 0) : interpolateColor(current.normal());
        Pixel correctPixel = new Pixel(
            newColor,
            current.hitCount(),
            current.normal()
        );
        fractal.setPixel(x, y, correctPixel);
    }
}

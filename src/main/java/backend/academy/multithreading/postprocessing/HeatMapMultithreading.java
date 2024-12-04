package backend.academy.multithreading.postprocessing;

import backend.academy.data.image.Fractal;
import backend.academy.data.image.Pixel;
import backend.academy.data.image.RGB;
import backend.academy.data.transformations.PostProcessing;
import java.util.stream.IntStream;

public class HeatMapMultithreading implements PostProcessing {
    @Override
    public void process(Fractal fractal) {
        IntStream.range(0, fractal.height() * fractal.width())
            .parallel()
            .forEach(i -> {
                int x = i / fractal.height();
                int y = i % fractal.height();
                Pixel current = fractal.getPixel(x, y);
                RGB newColor = current.hitCount() == 0
                    ? new RGB(0, 0, 0) : interpolateColor(current.normal());
                Pixel correctPixel = new Pixel(
                    newColor,
                    current.hitCount(),
                    current.normal()
                );
                fractal.setPixel(x, y, correctPixel);
            });
    }

    private RGB interpolateColor(double normalized) {
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
}

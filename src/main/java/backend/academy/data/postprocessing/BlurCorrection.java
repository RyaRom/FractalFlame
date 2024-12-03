package backend.academy.data.postprocessing;

import backend.academy.data.image.Fractal;
import backend.academy.data.image.Pixel;
import backend.academy.data.image.RGB;

public class BlurCorrection implements PostProcessing {
    @Override
    public void accept(Fractal fractal) {
        for (int x = 0; x < fractal.width(); x++) {
            for (int y = 0; y < fractal.height(); y++) {
                fractal.setPixel(x, y, blurPixel(x, y, fractal));
            }
        }
    }

    private Pixel blurPixel(int x, int y, Fractal fractal) {
        int[][] kernel = {
            {1, 2, 1},
            {2, 4, 2},
            {1, 2, 1}
        };
        int kernelSum = 16;

        int width = fractal.width();
        int height = fractal.height();
        int rSum = 0;
        int gSum = 0;
        int bSum = 0;

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                int nx = x + dx;
                int ny = y + dy;

                if (nx >= 0 && nx < width && ny >= 0 && ny < height) {
                    RGB neighborRGB = fractal.getPixel(nx, ny).rgb();
                    int weight = kernel[dx + 1][dy + 1];

                    rSum += neighborRGB.red() * weight;
                    gSum += neighborRGB.green() * weight;
                    bSum += neighborRGB.blue() * weight;
                }
            }
        }

        int r = rSum / kernelSum;
        int g = gSum / kernelSum;
        int b = bSum / kernelSum;
        return new Pixel(new RGB(r, g, b), 0, 0);
    }

}

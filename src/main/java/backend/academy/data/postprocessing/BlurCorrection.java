package backend.academy.data.postprocessing;

import backend.academy.data.image.Frame;
import backend.academy.data.image.Pixel;
import backend.academy.data.image.RGB;

public class BlurCorrection implements PostProcessing {
    @Override
    public void accept(Frame frame) {
        for (int x = 0; x < frame.width(); x++) {
            for (int y = 0; y < frame.height(); y++) {
                frame.setPixel(x, y, blurPixel(x, y, frame));
            }
        }
    }

    private Pixel blurPixel(int x, int y, Frame frame) {
        int[][] kernel = {
            {1, 2, 1},
            {2, 4, 2},
            {1, 2, 1}
        };
        int kernelSum = 16;

        int width = frame.width();
        int height = frame.height();
        int rSum = 0;
        int gSum = 0;
        int bSum = 0;

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                int nx = x + dx;
                int ny = y + dy;

                if (nx >= 0 && nx < width && ny >= 0 && ny < height) {
                    RGB neighborRGB = frame.getPixel(nx, ny).rgb();
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

package backend.academy.data.image;

import java.awt.image.BufferedImage;

public record Frame(
    Pixel[][] pixels,
    int width,
    int height,
    double xMin,
    double xMax,
    double yMin,
    double yMax
) {
    public Frame(int width, int height) {
        this(
            initPixels(width, height),
            width,
            height,
            -2.0 * ((double) width / height) / 2,
            2.0 * ((double) width / height) / 2,
            -1.0,
            1.0
        );
    }

    private static Pixel[][] initPixels(int width, int height) {
        Pixel[][] pixels = new Pixel[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                pixels[i][j] = new Pixel(new RGB(0, 0, 0), 0, 0.0);
            }
        }
        return pixels;
    }

    public Pixel getPixel(Coordinates coordinates) {
        return pixels[coordinates.y()][coordinates.x()];
    }

    public Pixel getPixel(int x, int y) {
        return pixels[y][x];
    }

    public void setPixel(int x, int y, Pixel pixel) {
        pixels[y][x] = pixel;
    }

    public void setPixel(Coordinates coordinates, Pixel pixel) {
        pixels[coordinates.y()][coordinates.x()] = pixel;
    }

    public boolean containsUnscaled(Point unscaled) {
        return unscaled.x() < xMax && unscaled.x() >= xMin
            && unscaled.y() < yMax && unscaled.y() >= yMin;
    }

    public boolean contains(Coordinates scaled) {
        return scaled.x() < width && scaled.x() >= 0
            && scaled.y() < height && scaled.y() >= 0;
    }

    public BufferedImage toBufferedImage() {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Pixel current = getPixel(x, y);
                image.setRGB(x, y, current.rgb().toRGBInt());
            }
        }
        return image;
    }
}

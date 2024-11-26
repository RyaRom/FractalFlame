package backend.academy.data.image;

import static backend.academy.data.image.Coordinates.scale;

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
            generatePixels(width, height),
            width,
            height,
            -2.0 * ((double) width / height) / 2,
            2.0 * ((double) width / height) / 2,
            -1.0,
            1.0
        );
    }

    private static Pixel[][] generatePixels(int width, int height) {
        Pixel[][] pixels = new Pixel[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                pixels[i][j] = new Pixel(new RGB(0, 0, 0), 0);
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

    public void setPixel(Coordinates coordinates, Pixel pixel) {
        pixels[coordinates.y()][coordinates.x()] = pixel;
    }

    private void hitPixel(Point position, RGB rgb) {
        Coordinates scaled = scale(position, this);
        if (!contains(scaled)) {
            return;
        }

        Pixel hitPixel = getPixel(scaled);
        if (hitPixel.hitCount() > 0) {
            rgb = hitPixel.rgb().blend(rgb);
        }
        setPixel(
            scaled,
            new Pixel(rgb, hitPixel.hitCount() + 1)
        );
    }

    public void applyTransformedPoint(Point transformed, int symmetryCount, RGB rgb) {
        double theta2 = 0.0;
        for (int sym = 0; sym < symmetryCount; theta2 += Math.PI * 2 / symmetryCount, sym++) {
            Point rotated = transformed.rotate(theta2);
            if (!containsUnscaled(transformed)) {
                continue;
            }
            hitPixel(rotated, rgb);
        }
    }

    private boolean containsUnscaled(Point unscaled) {
        return unscaled.x() < xMax && unscaled.x() >= xMin
            && unscaled.y() < yMax && unscaled.y() >= yMin;
    }

    private boolean contains(Coordinates scaled) {
        return scaled.x() < width && scaled.x() >= 0
            && scaled.y() < height && scaled.y() >= 0;
    }
}

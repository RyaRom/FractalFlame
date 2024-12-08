package backend.academy.core.data.image;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import javax.imageio.ImageIO;

public record Fractal(
    Pixel[][] pixels,
    int width,
    int height,
    double xMin,
    double xMax,
    double yMin,
    double yMax
) {

    public static Fractal of(int height, int width, double depth) {
        double aspectRatio = (double) width / height;

        double xMin;
        double xMax;
        double yMin;
        double yMax;
        if (aspectRatio >= 1.0) {
            xMin = -depth * aspectRatio;
            xMax = depth * aspectRatio;
            yMin = -depth;
            yMax = depth;
        } else {
            xMin = -depth;
            xMax = depth;
            yMin = -depth / aspectRatio;
            yMax = depth / aspectRatio;
        }
        Pixel[][] pixels = new Pixel[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                pixels[i][j] = new Pixel(new RGB(0, 0, 0), 0, 0.0);
            }
        }

        return new Fractal(pixels, width, height, xMin, xMax, yMin, yMax);
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

    public String encode() {
        BufferedImage image = image();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            ImageIO.write(image, "png", outputStream);
            byte[] imageBytes = outputStream.toByteArray();
            return Base64.getEncoder().encodeToString(imageBytes);
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    public BufferedImage image() {
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

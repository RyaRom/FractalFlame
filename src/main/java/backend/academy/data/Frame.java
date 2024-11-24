package backend.academy.data;

public record Frame(Pixel[][] pixels, int width, int height) {
    public Frame(int width, int height) {
        this(generatePixels(width, height), width, height);
    }

    private static Pixel[][] generatePixels(int width, int height) {
        Pixel[][] pixels = new Pixel[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                pixels[i][j] = new Pixel(new ColorRGB(0, 0, 0), 0);
            }
        }
        return pixels;
    }

    public Pixel getPixel(int x, int y) {
        return pixels[y][x];
    }

    public void setPixel(int x, int y, Pixel pixel) {
        pixels[y][x] = pixel;
    }
}

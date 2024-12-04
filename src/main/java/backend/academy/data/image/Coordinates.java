package backend.academy.data.image;

public record Coordinates(int x, int y) {
    public static Coordinates scale(Point unscaled, Fractal fractal, double scaleFactor) {
        int xScaled =
            (int) (((unscaled.x() - fractal.xMin()) / (fractal.xMax() - fractal.xMin())) * fractal.width() *
                scaleFactor)
                + (int) ((1.0 - scaleFactor) * fractal.width() / 2);
        int yScaled =
            (int) (((unscaled.y() - fractal.yMin()) / (fractal.yMax() - fractal.yMin())) * fractal.height() *
                scaleFactor)
                + (int) ((1.0 - scaleFactor) * fractal.height() / 2);
        return new Coordinates(xScaled, yScaled);
    }
}

package backend.academy.core.data.image;

public record Coordinates(int x, int y) {
    /**
     * Scales a Point from fractal coordinates to screen coordinates using the specified scale factor.
     *
     * @param unscaled    the point in fractal coordinates to be scaled
     * @param fractal     the fractal object containing the dimensions and bounds for scaling
     * @param scaleFactor the factor by which the coordinates are scaled
     * @return a Coordinates object representing the scaled point in screen coordinates
     */

    public static Coordinates scale(Point unscaled, Fractal fractal, double scaleFactor) {
        int xScaled =
            (int) (((unscaled.x() - fractal.xMin()) / (fractal.xMax() - fractal.xMin()))
                * fractal.width() * scaleFactor)
                + (int) ((1.0 - scaleFactor) * fractal.width() / 2);
        int yScaled =
            (int) (((unscaled.y() - fractal.yMin()) / (fractal.yMax() - fractal.yMin()))
                * fractal.height() * scaleFactor)
                + (int) ((1.0 - scaleFactor) * fractal.height() / 2);
        return new Coordinates(xScaled, yScaled);
    }
}

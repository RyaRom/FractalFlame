package backend.academy.data.image;

public record Coordinates(int x, int y) {
    public static Coordinates scale(Point unscaled, Frame frame, double scaleFactor) {
        int xScaled =
            (int) (((unscaled.x() - frame.xMin()) / (frame.xMax() - frame.xMin())) * frame.width() * scaleFactor)
                + (int) ((1.0 - scaleFactor) * frame.width() / 2);
        int yScaled =
            (int) (((unscaled.y() - frame.yMin()) / (frame.yMax() - frame.yMin())) * frame.height() * scaleFactor)
                + (int) ((1.0 - scaleFactor) * frame.height() / 2);
        return new Coordinates(xScaled, yScaled);
    }
}

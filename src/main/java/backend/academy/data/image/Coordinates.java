package backend.academy.data.image;

public record Coordinates(int x, int y) {
    public static Coordinates scale(Point unscaled, Frame frame) {
        int xScaled = (int) ((unscaled.x() - frame.xMin()) / (frame.xMax() - frame.xMin()) * frame.width());
        int yScaled = (int) ((unscaled.y() - frame.yMin()) / (frame.yMax() - frame.yMin()) * frame.height());
        return new Coordinates(xScaled, yScaled);
    }
}

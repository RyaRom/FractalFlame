package backend.academy.data.image;

/**
 * Point unscaled to the frame, for representing coordinates in fractal world
 */
public record Point(double x, double y) {
    public Point rotate(double theta) {
        double newX = x * Math.cos(theta) - y * Math.sin(theta);
        double newY = x * Math.sin(theta) + y * Math.cos(theta);
        return new Point(newX, newY);
    }

    public Point sum(Point other) {
        return new Point(x + other.x(), y + other.y());
    }
}

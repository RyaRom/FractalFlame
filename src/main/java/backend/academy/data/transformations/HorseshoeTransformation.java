package backend.academy.data.transformations;

import backend.academy.data.image.Point;

public final class HorseshoeTransformation extends WeightedTransformation {
    public HorseshoeTransformation(double weight) {
        super(weight);
    }

    @Override
    public Point transform(Point point) {
        double r = 1.0 / Math.sqrt(point.x() * point.x() + point.y() * point.y());
        double newX = r * (point.x() - point.y()) * (point.x() + point.y());
        double newY = r * 2.0 * point.x() * point.y();
        return new Point(weight() * newX, weight() * newY);
    }
}

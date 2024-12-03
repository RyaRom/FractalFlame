package backend.academy.data.transformations;

import backend.academy.data.image.Point;

public final class PolarTransformation extends WeightedTransformation {
    public PolarTransformation(double weight) {
        super(weight);
    }

    @Override
    public Point transform(Point point) {
        double newX = Math.atan2(point.y(), point.x()) / Math.PI;
        double newY = Math.sqrt(point.x() * point.x() + point.y() * point.y()) - 1.0;
        return new Point(weight() * newX, weight() * newY);
    }
}

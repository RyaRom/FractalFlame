package backend.academy.data.transformations;

import backend.academy.data.image.Point;

public final class SwirlTransformation extends WeightedTransformation {
    public SwirlTransformation(double weight) {
        super(weight);
    }

    @Override
    public Point transform(Point point) {
        double r = point.x() * point.x() + point.y() * point.y();
        double newX = point.x() * Math.sin(r) - point.y() * Math.cos(r);
        double newY = point.x() * Math.cos(r) + point.y() * Math.sin(r);
        return new Point(weight() * newX, weight() * newY);
    }
}

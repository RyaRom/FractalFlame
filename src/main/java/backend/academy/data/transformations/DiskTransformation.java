package backend.academy.data.transformations;

import backend.academy.data.image.Point;

public final class DiskTransformation extends WeightedTransformation {
    public DiskTransformation(double weight) {
        super(weight);
    }

    @Override
    public Point transform(Point point) {
        double r = Math.sqrt(point.x() * point.x() + point.y() * point.y()) * Math.PI;
        double theta = Math.atan2(point.y(), point.x()) / Math.PI;
        double newX = theta * Math.sin(r);
        double newY = theta * Math.cos(r);
        return new Point(weight() * newX, weight() * newY);
    }
}

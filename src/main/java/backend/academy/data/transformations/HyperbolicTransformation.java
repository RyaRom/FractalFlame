package backend.academy.data.transformations;

import backend.academy.data.image.Point;

public final class HyperbolicTransformation implements AbstractTransformation {
    @Override
    public Point transform(Point point) {
        double r = Math.sqrt(point.x() * point.x() + point.y() * point.y());
        double theta = Math.atan2(point.y(), point.x());
        double newX = Math.sin(theta) / r;
        double newY = r * Math.cos(theta);
        return new Point(newX, newY);
    }
}

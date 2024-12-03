package backend.academy.data.transformations;

import backend.academy.data.image.Point;

public final class PolarTransformation implements AbstractTransformation {
    @Override
    public Point transform(Point point) {
        double newX = Math.atan2(point.y(), point.x()) / Math.PI;
        double newY = Math.sqrt(point.x() * point.x() + point.y() * point.y()) - 1.0;
        return new Point(newX, newY);
    }
}

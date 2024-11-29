package backend.academy.data.transformations;

import backend.academy.data.image.Point;

public final class SphereTransformation implements AbstractTransformation {
    @Override
    public Point transform(Point point) {
        double newX = point.x() / (Math.pow(point.x(), 2) + Math.pow(point.y(), 2));
        double newY = point.y() / (Math.pow(point.x(), 2) + Math.pow(point.y(), 2));
        return new Point(newX, newY);
    }
}

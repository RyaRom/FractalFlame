package backend.academy.data.transformations;

import backend.academy.data.image.Point;

public final class SwirlTransformation implements AbstractTransformation {
    @Override
    public Point transform(Point point) {
        double r = point.x() * point.x() + point.y() * point.y();
        double newX = point.x() * Math.sin(r) - point.y() * Math.cos(r);
        double newY = point.x() * Math.cos(r) + point.y() * Math.sin(r);
        return new Point(newX, newY);
    }
}

package backend.academy.data.transformations;

import backend.academy.data.image.Point;

public final class FisheyeTransformation implements AbstractTransformation {
    @Override
    public Point transform(Point point) {
        double r = 2.0 / (1.0 + Math.sqrt(point.x() * point.x() + point.y() * point.y()));
        double newX = r * point.y();
        double newY = r * point.x();
        return new Point(newX, newY);
    }
}

package backend.academy.data.transformations;

import backend.academy.data.image.Point;

public final class BubbleTransformation implements AbstractTransformation {
    @Override
    public Point transform(Point point) {
        double r = 4 + point.x() * point.x() + point.y() * point.y();
        double newX = (4.0 * point.x()) / r;
        double newY = (4.0 * point.y()) / r;
        return new Point(newX, newY);
    }
}

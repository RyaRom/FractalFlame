package backend.academy.data.transformations;

import backend.academy.data.image.Point;

public final class PolarTransformation implements AbstractTransformation {
    @Override
    public Point transform(Point point) {
        double newX = Math.atan(point.y() / point.x()) / Math.PI;
        double newY = Math.sqrt(Math.pow(point.x(), 2) + Math.pow(point.y(), 2)) - 1;
        return new Point(newX, newY);
    }
}

package backend.academy.data.variations;

import backend.academy.data.image.Point;
import backend.academy.data.transformations.WeightedTransformation;

public final class HandkerchiefTransformation extends WeightedTransformation {
    public HandkerchiefTransformation(double weight) {
        super(weight);
    }

    @Override
    public Point transform(Point point) {
        double r = Math.sqrt(point.x() * point.x() + point.y() * point.y());
        double theta = Math.atan2(point.y(), point.x());
        double newX = r * Math.sin(theta + r);
        double newY = r * Math.cos(theta - r);
        return new Point(weight() * newX, weight() * newY);
    }
}

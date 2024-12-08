package backend.academy.core.data.variations;

import backend.academy.core.data.image.Point;
import backend.academy.core.data.transformations.WeightedTransformation;

public final class HeartTransformation extends WeightedTransformation {
    public HeartTransformation(double weight) {
        super(weight);
    }

    @Override
    public Point transform(Point point) {
        double r = Math.sqrt(point.x() * point.x() + point.y() * point.y());
        double theta = Math.atan2(point.y(), point.x());
        double newX = r * Math.sin(theta * r);
        double newY = -r * Math.cos(theta * r);
        return new Point(weight() * newX, weight() * newY);
    }
}

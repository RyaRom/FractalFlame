package backend.academy.core.data.variations;

import backend.academy.core.data.image.Point;
import backend.academy.core.data.transformations.WeightedTransformation;

public final class HyperbolicTransformation extends WeightedTransformation {
    public HyperbolicTransformation(double weight) {
        super(weight);
    }

    @Override
    public Point transform(Point point) {
        double r = Math.sqrt(point.x() * point.x() + point.y() * point.y());
        double theta = Math.atan2(point.y(), point.x());
        double newX = Math.sin(theta) / r;
        double newY = r * Math.cos(theta);
        return new Point(weight() * newX, weight() * newY);
    }
}

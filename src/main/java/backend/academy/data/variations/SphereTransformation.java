package backend.academy.data.variations;

import backend.academy.data.image.Point;
import backend.academy.data.transformations.WeightedTransformation;

public final class SphereTransformation extends WeightedTransformation {
    public SphereTransformation(double weight) {
        super(weight);
    }

    @Override
    public Point transform(Point point) {
        double newX = point.x() / (Math.pow(point.x(), 2) + Math.pow(point.y(), 2));
        double newY = point.y() / (Math.pow(point.x(), 2) + Math.pow(point.y(), 2));
        return new Point(weight() * newX, weight() * newY);
    }
}

package backend.academy.data.transformations;

import backend.academy.data.image.Point;

public final class BubbleTransformation extends WeightedTransformation {
    public BubbleTransformation(double weight) {
        super(weight);
    }

    @Override
    public Point transform(Point point) {
        double r = 4 + point.x() * point.x() + point.y() * point.y();
        double newX = (4.0 * point.x()) / r;
        double newY = (4.0 * point.y()) / r;
        return new Point(weight() * newX, weight() * newY);
    }
}

package backend.academy.core.data.variations;

import backend.academy.core.data.image.Point;
import backend.academy.core.data.transformations.WeightedTransformation;

public final class BubbleTransformation extends WeightedTransformation {
    public BubbleTransformation(double weight) {
        super(weight);
    }

    @Override
    @SuppressWarnings("MagicNumber")
    public Point transform(Point point) {
        double r = 4 + point.x() * point.x() + point.y() * point.y();
        double newX = (4.0 * point.x()) / r;
        double newY = (4.0 * point.y()) / r;
        return new Point(weight() * newX, weight() * newY);
    }
}

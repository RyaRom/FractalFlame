package backend.academy.core.data.variations;

import backend.academy.core.data.image.Point;
import backend.academy.core.data.transformations.WeightedTransformation;

public final class LinearTransformation extends WeightedTransformation {
    public LinearTransformation(double weight) {
        super(weight);
    }

    @Override
    public Point transform(Point point) {
        return new Point(weight() * point.x(), weight() * point.y());
    }
}

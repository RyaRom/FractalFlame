package backend.academy.data.variations;

import backend.academy.data.image.Point;
import backend.academy.data.transformations.WeightedTransformation;

public final class LinearTransformation extends WeightedTransformation {
    public LinearTransformation(double weight) {
        super(weight);
    }

    @Override
    public Point transform(Point point) {
        return new Point(weight() * point.x(), weight() * point.y());
    }
}

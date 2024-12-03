package backend.academy.data.transformations;

import backend.academy.data.image.Point;

public final class LinearTransformation extends WeightedTransformation {
    public LinearTransformation(double weight) {
        super(weight);
    }

    @Override
    public Point transform(Point point) {
        return new Point(weight() * point.x(), weight() * point.y());
    }
}

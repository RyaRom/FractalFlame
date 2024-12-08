package backend.academy.core.data.variations;

import backend.academy.core.data.image.Point;
import backend.academy.core.data.transformations.WeightedTransformation;
import static java.lang.Math.sin;

public final class SinusoidalTransformation extends WeightedTransformation {

    public SinusoidalTransformation(double weight) {
        super(weight);
    }

    @Override
    public Point transform(Point point) {
        double newX = sin(point.x());
        double newY = sin(point.y());
        return new Point(weight() * newX, weight() * newY);
    }
}

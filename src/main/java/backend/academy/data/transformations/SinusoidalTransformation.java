package backend.academy.data.transformations;

import backend.academy.data.image.Point;
import static java.lang.Math.sin;

public final class SinusoidalTransformation implements AbstractTransformation {

    @Override
    public Point transform(Point point) {
        return new Point(sin(point.x()), sin(point.y()));
    }
}

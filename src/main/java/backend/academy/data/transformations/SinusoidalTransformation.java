package backend.academy.data.transformations;

import backend.academy.data.image.Point;
import backend.academy.data.image.RGB;
import static java.lang.Math.sin;

public final class SinusoidalTransformation extends Transformation {
    public SinusoidalTransformation(RGB rgb, AffineTransformation affineTransformation) {
        super(rgb, affineTransformation);
    }

    @Override
    public Point apply(Point point) {
        point = affineTransformation.apply(point);
        return new Point(sin(point.x()), sin(point.y()));
    }
}

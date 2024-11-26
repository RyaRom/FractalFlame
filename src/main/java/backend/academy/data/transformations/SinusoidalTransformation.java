package backend.academy.data.transformations;

import backend.academy.data.image.Point;
import backend.academy.data.image.RGB;
import lombok.RequiredArgsConstructor;
import static java.lang.Math.sin;

@RequiredArgsConstructor
public class SinusoidalTransformation implements Transformation {
    private final RGB rgb;

    @Override
    public RGB getColor() {
        return rgb;
    }

    @Override
    public Point apply(Point point) {
        return new Point(sin(point.x()), sin(point.y()));
    }
}

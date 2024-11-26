package backend.academy.data.transformations;

import backend.academy.data.image.RGB;
import backend.academy.data.image.Point;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AffineTransformation implements Transformation {
    private final double a;

    private final double b;

    private final double c;

    private final double d;

    private final double e;

    private final double f;

    private final RGB rgb;

    @Override
    public RGB getColor() {
        return rgb;
    }

    @Override
    public Point apply(Point point) {
        double newX = a * point.x() + b * point.y() + c;
        double newY = d * point.x() + e * point.y() + f;
        return new Point(newX, newY);
    }
}

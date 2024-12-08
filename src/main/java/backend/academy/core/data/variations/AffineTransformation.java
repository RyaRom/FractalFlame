package backend.academy.core.data.variations;

import backend.academy.core.data.image.Point;
import backend.academy.core.data.transformations.AbstractTransformation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class AffineTransformation implements AbstractTransformation {
    private final double a;

    private final double b;

    private final double c;

    private final double d;

    private final double e;

    private final double f;

    @Override
    public Point transform(Point point) {
        double newX = a * point.x() + b * point.y() + c;
        double newY = d * point.x() + e * point.y() + f;
        return new Point(newX, newY);
    }
}

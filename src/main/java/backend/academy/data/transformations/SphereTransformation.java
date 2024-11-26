package backend.academy.data.transformations;

import backend.academy.data.image.Point;
import backend.academy.data.image.RGB;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SphereTransformation implements Transformation {
    private final RGB rgb;

    @Override
    public RGB getColor() {
        return rgb;
    }

    @Override
    public Point apply(Point point) {
        double newX = point.x() / (Math.pow(point.x(), 2) + Math.pow(point.y(), 2));
        double newY = point.y() / (Math.pow(point.x(), 2) + Math.pow(point.y(), 2));
        return new Point(newX, newY);
    }
}

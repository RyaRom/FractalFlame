package backend.academy.data.transformations;

import backend.academy.data.image.Point;
import backend.academy.data.image.RGB;

public final class SphereTransformation extends Transformation {
    public SphereTransformation(RGB rgb, AffineTransformation affineTransformation) {
        super(rgb, affineTransformation);
    }

    @Override
    public Point apply(Point point) {
        point = affineTransformation.apply(point);
        double newX = point.x() / (Math.pow(point.x(), 2) + Math.pow(point.y(), 2));
        double newY = point.y() / (Math.pow(point.x(), 2) + Math.pow(point.y(), 2));
        return new Point(newX, newY);
    }
}

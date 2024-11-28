package backend.academy.data.transformations;

import backend.academy.data.image.Point;
import backend.academy.data.image.RGB;

public final class PolarTransformation extends Transformation {
    public PolarTransformation(RGB rgb, AffineTransformation affineTransformation) {
        super(rgb, affineTransformation);
    }

    @Override
    public Point apply(Point point) {
        point = affineTransformation.apply(point);
        double newX = Math.atan(point.y() / point.x()) / Math.PI;
        double newY = Math.sqrt(Math.pow(point.x(), 2) + Math.pow(point.y(), 2)) - 1;
        return new Point(newX, newY);
    }
}

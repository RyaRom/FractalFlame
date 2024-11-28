package backend.academy.data.transformations;

import backend.academy.data.image.Point;
import backend.academy.data.image.RGB;

public final class LinearTransformation extends Transformation {
    public LinearTransformation(RGB rgb, AffineTransformation affineTransformation) {
        super(rgb, affineTransformation);
    }

    @Override
    public Point apply(Point point) {
        return affineTransformation.apply(point);
    }
}

package backend.academy.data.transformations;

import backend.academy.data.image.RGB;
import backend.academy.data.image.Point;
import java.util.function.Function;

public interface Transformation extends Function<Point, Point> {
    RGB getColor();
}

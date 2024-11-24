package backend.academy.data.transformations;

import backend.academy.data.ColorRGB;
import backend.academy.data.Point;
import java.util.function.Function;

public interface Transformation extends Function<Point, Point> {
    ColorRGB getColor();
}

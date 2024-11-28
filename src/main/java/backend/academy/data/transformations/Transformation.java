package backend.academy.data.transformations;

import backend.academy.data.image.Point;
import backend.academy.data.image.RGB;
import java.util.function.UnaryOperator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class Transformation implements UnaryOperator<Point> {
    @Getter
    private final RGB rgb;

    protected final AffineTransformation affineTransformation;
}

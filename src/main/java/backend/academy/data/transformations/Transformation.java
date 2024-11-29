package backend.academy.data.transformations;

import backend.academy.data.image.Point;
import backend.academy.data.image.RGB;
import java.util.function.UnaryOperator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Transformation implements UnaryOperator<Point> {
    @Getter
    private final RGB rgb;

    private final AffineTransformation affineTransformation;

    private final AbstractTransformation nonLinearTransformations;

    @Getter
    private final Double weight;

    public Transformation(
        RGB rgb,
        AffineTransformation affineTransformation,
        Double weight,
        AbstractTransformation... transformations
    ) {
        this.rgb = rgb;
        this.affineTransformation = affineTransformation;
        this.weight = weight;
        this.nonLinearTransformations = compose(transformations);
    }

    private static AbstractTransformation compose(AbstractTransformation... transformations) {
        if (transformations.length == 0) {
            return (point -> point);
        }
        AbstractTransformation result = transformations[0];
        for (int i = 1; i < transformations.length; i++) {
            result = result.compose(transformations[i]);
        }
        return result;
    }

    @Override
    public Point apply(Point point) {
        return nonLinearTransformations.transform(affineTransformation.transform(point));
    }
}

package backend.academy.data.transformations;

import backend.academy.data.image.Point;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public abstract class WeightedTransformation implements AbstractTransformation {
    private final double weight;

    public static AbstractTransformation compose(
        AffineTransformation affineFunc,
        WeightedTransformation... transformations
    ) {
        if (transformations.length == 0) {
            return affineFunc;
        }

        return point -> {
            Point result = new Point(0, 0);
            for (AbstractTransformation transformation : transformations) {
                Point transformed = affineFunc.compose(transformation).transform(point);
                result = result.sum(transformed);
            }
            return result;
        };
    }
}

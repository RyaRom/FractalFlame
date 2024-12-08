package backend.academy.core.data.transformations;

import backend.academy.core.data.image.Point;

@FunctionalInterface
public interface AbstractTransformation {
    Point transform(Point point);

    default AbstractTransformation compose(AbstractTransformation transformation) {
        return (Point point) -> transformation.transform(transform(point));
    }
}

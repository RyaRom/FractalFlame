package backend.academy.data.transformations;

import backend.academy.data.image.Point;

@FunctionalInterface
public interface AbstractTransformation {
    Point transform(Point point);

    default AbstractTransformation compose(AbstractTransformation transformation) {
        return (Point point) -> transformation.transform(transform(point));
    }
}

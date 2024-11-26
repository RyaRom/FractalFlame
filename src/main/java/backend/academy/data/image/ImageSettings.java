package backend.academy.data.image;

import backend.academy.data.transformations.Transformation;
import java.util.List;
import static backend.academy.singlethreading.Application.RANDOM;

public record ImageSettings(
    int widthRes,
    int heightRes,
    int startingPoints,
    int iterationsForPoint,
    int symmetry,
    List<Transformation> transformations
) {
    public Transformation getRandomTransformation() {
        int index = RANDOM.nextInt(transformations.size());
        return transformations.get(index);
    }
}

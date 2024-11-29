package backend.academy.data.image;

import backend.academy.data.transformations.Transformation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static backend.academy.singlethreading.Application.RANDOM;

public record ImageSettings(
    int widthRes,
    int heightRes,
    int startingPoints,
    int iterationsForPoint,
    int symmetry,
    List<Transformation> transformations,
    List<Double> prefixSumTransformations
) {
    public ImageSettings(
        int heightRes,
        int widthRes,
        int startingPoints,
        int iterationsForPoint,
        int symmetry,
        List<Transformation> transformations
    ) {
        this(widthRes, heightRes, startingPoints, iterationsForPoint, symmetry, transformations, calculatePrefixSum(transformations));
    }

    private static List<Double> calculatePrefixSum(List<Transformation> transformations) {
        double sum = transformations.stream().mapToDouble(Transformation::weight).sum();
        List<Double> normalizedWeights = new ArrayList<>();
        for (Transformation transformation : transformations) {
            normalizedWeights.add(transformation.weight() / sum);
        }

        List<Double> cumulativeWeights = new ArrayList<>();
        double cumulativeSum = 0;
        for (double weight : normalizedWeights) {
            cumulativeSum += weight;
            cumulativeWeights.add(cumulativeSum);
        }

        return cumulativeWeights;
    }

    public Transformation getRandomTransformation() {
        double r = RANDOM.nextDouble();
        int index = Collections.binarySearch(prefixSumTransformations, r);
        if (index < 0) {
            index = -index - 1;
        }

        return transformations.get(index);
    }
}

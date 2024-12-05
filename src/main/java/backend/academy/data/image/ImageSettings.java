package backend.academy.data.image;

import backend.academy.data.transformations.IterativeFunction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static backend.academy.service.FractalUtil.RANDOM;

public record ImageSettings(
    int widthRes,
    int heightRes,
    int startingPoints,
    int iterationsForPoint,
    int symmetry,
    List<IterativeFunction> iterativeFunctions,
    List<Double> prefixSumTransformations,
    double depth,
    double gamma
) {
    public ImageSettings(
        int heightRes,
        int widthRes,
        int startingPoints,
        int iterationsForPoint,
        int symmetry,
        List<IterativeFunction> iterativeFunctions,
        double depth,
        double gamma
    ) {
        this(widthRes, heightRes, startingPoints, iterationsForPoint, symmetry,
            iterativeFunctions, calculatePrefixSum(iterativeFunctions), depth, gamma);
    }

    private static List<Double> calculatePrefixSum(List<IterativeFunction> iterativeFunctions) {
        double sum = iterativeFunctions.stream().mapToDouble(IterativeFunction::weight).sum();
        List<Double> normalizedWeights = new ArrayList<>();
        for (IterativeFunction iterativeFunction : iterativeFunctions) {
            normalizedWeights.add(iterativeFunction.weight() / sum);
        }

        List<Double> cumulativeWeights = new ArrayList<>();
        double cumulativeSum = 0;
        for (double weight : normalizedWeights) {
            cumulativeSum += weight;
            cumulativeWeights.add(cumulativeSum);
        }

        return cumulativeWeights;
    }

    public IterativeFunction getRandomTransformation() {
        double r = RANDOM.nextDouble();
        int index = Collections.binarySearch(prefixSumTransformations, r);
        if (index < 0) {
            index = -index - 1;
        }

        return iterativeFunctions.get(index);
    }
}

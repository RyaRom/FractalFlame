package backend.academy.core.data.image;

import backend.academy.core.data.transformations.IterativeFunction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static backend.academy.core.fractals.FractalUtil.RANDOM;

@SuppressWarnings({"RecordComponentNumber", "ParameterNumber"})
public record ImageSettings(
    int widthRes,
    int heightRes,
    int startingPoints,
    int iterationsForPoint,
    int symmetry,
    List<IterativeFunction> iterativeFunctions,
    List<Double> prefixSumTransformations,
    double depth,
    double gamma,
    boolean gammaEnabled,
    boolean isBlur,
    boolean isHeatMap,
    boolean isConcurrent
) {
    public ImageSettings(
        int heightRes,
        int widthRes,
        int startingPoints,
        int iterationsForPoint,
        int symmetry,
        List<IterativeFunction> iterativeFunctions,
        double depth,
        double gamma,
        boolean gammaEnabled,
        boolean isBlur,
        boolean isHeatMap,
        boolean isConcurrent
    ) {
        this(widthRes, heightRes, startingPoints, iterationsForPoint, symmetry,
            iterativeFunctions, calculatePrefixSum(iterativeFunctions), depth, gamma,
            gammaEnabled, isBlur, isHeatMap, isConcurrent);
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

package backend.academy.service.fractals;

import backend.academy.data.image.RGB;
import backend.academy.data.transformations.AbstractTransformation;
import backend.academy.data.transformations.IterativeFunction;
import backend.academy.data.variations.AffineTransformation;
import backend.academy.data.variations.Variations;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import org.jspecify.annotations.Nullable;
import static backend.academy.data.variations.VariationFactory.getVariation;

@SuppressWarnings("MagicNumber")
public class FractalUtil {
    public static final Random RANDOM = new Random();

    private FractalUtil() {
    }

    public static IterativeFunction getRandomTransformation(Variations... variations) {
        double funcWeight = Math.random();
        AffineTransformation affine = getRandomAffineTransformation();
        double sum = .0;
        RGB color = RGB.getRandomRGB();
        if (variations.length == 0) {
            return new IterativeFunction(color, affine, funcWeight);
        }
        List<AbstractTransformation> transformations = new ArrayList<>();

        while (sum < 1.0) {
            double variationWeight = Math.random();
            int varIndex = RANDOM.nextInt(0, variations.length - 1);
            if (sum + variationWeight > 1.0) {
                variationWeight = 1.0 - sum;
            }
            transformations.add(getVariation(variations[varIndex], variationWeight));
            sum += variationWeight;
        }

        return new IterativeFunction(
            color, affine, funcWeight, transformations.toArray(new AbstractTransformation[0])
        );
    }

    public static List<IterativeFunction> getRandomTransformationList(Variations... variations) {
        int amount = RANDOM.nextInt(1, 6);
        List<IterativeFunction> transformations = new ArrayList<>(amount);
        for (int i = 0; i < amount; i++) {
            transformations.add(getRandomTransformation(variations));
        }
        return transformations;
    }

    public static AffineTransformation getRandomAffineTransformation() {
        return new AffineTransformation(
            RANDOM.nextDouble(-1.5, 1.5),
            RANDOM.nextDouble(-1.5, 1.5),
            RANDOM.nextDouble(-1.5, 1.5),
            RANDOM.nextDouble(-1.5, 1.5),
            RANDOM.nextDouble(-1.5, 1.5),
            RANDOM.nextDouble(-1.5, 1.5)
        );
    }

    public static <T> Long profileTime(Supplier<T> supplier, @Nullable AtomicReference<T> reference) {
        long start = System.currentTimeMillis();
        if (reference == null) {
            supplier.get();
        } else {
            reference.set(supplier.get());
        }
        return System.currentTimeMillis() - start;
    }
}

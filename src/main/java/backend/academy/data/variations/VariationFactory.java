package backend.academy.data.variations;

import backend.academy.data.transformations.AbstractTransformation;

public class VariationFactory {
    private VariationFactory() {
    }

    public static AbstractTransformation getVariation(Variations variation, double weight) {
        return switch (variation) {
            case BUBBLE -> new BubbleTransformation(weight);
            case DISK -> new DiskTransformation(weight);
            case HANDKERCHIEF -> new HandkerchiefTransformation(weight);
            case HEART -> new HeartTransformation(weight);
            case HORSESHOE -> new HorseshoeTransformation(weight);
            case HYPERBOLIC -> new HyperbolicTransformation(weight);
            case LINEAR -> new LinearTransformation(weight);
            case POLAR -> new PolarTransformation(weight);
            case SINUSOIDAL -> new SinusoidalTransformation(weight);
            case SPHERE -> new SphereTransformation(weight);
            case SPIRAL -> new SpiralTransformation(weight);
            case SWIRL -> new SwirlTransformation(weight);
        };
    }
}

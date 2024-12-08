package backend.academy.core.data.transformations;

import backend.academy.core.data.image.Fractal;

@FunctionalInterface
public interface PostProcessing {
    void process(Fractal fractal);
}

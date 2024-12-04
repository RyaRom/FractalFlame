package backend.academy.data.transformations;

import backend.academy.data.image.Fractal;

@FunctionalInterface
public interface PostProcessing {
    void process(Fractal fractal);
}

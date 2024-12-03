package backend.academy.data.postprocessing;

import backend.academy.data.image.Fractal;

@FunctionalInterface
public interface PostProcessing {
    void process(Fractal fractal);

    default PostProcessing compose(PostProcessing postProcessing) {
        return (Fractal fractal) -> {
            process(fractal);
            postProcessing.process(fractal);
        };
    }
}

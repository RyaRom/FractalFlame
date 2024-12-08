package backend.academy.core.fractals;

import backend.academy.core.data.image.ImageSettings;
import backend.academy.core.multithreading.MultithreadingGenerator;
import backend.academy.core.singlethreading.SingleThreadGenerator;
import backend.academy.web.data.FractalCache;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FractalFactory {
    private final FractalCache fractalCache;

    public FractalGenerator generator(ImageSettings settings) {
        if (settings.isConcurrent()) {
            return new MultithreadingGenerator(settings, fractalCache);
        } else {
            return new SingleThreadGenerator(settings, fractalCache);
        }
    }

    public FractalRenderer renderer(ImageSettings settings) {
        return new FractalRendererImpl(settings);
    }
}

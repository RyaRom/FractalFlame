package backend.academy.service.fractals;

import backend.academy.data.FractalCache;
import backend.academy.data.image.ImageSettings;
import backend.academy.multithreading.MultithreadingGenerator;
import backend.academy.singlethreading.SingleThreadGenerator;
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

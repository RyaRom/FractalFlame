package backend.academy.service.fractals;

import backend.academy.data.FractalCache;
import backend.academy.data.image.ImageSettings;
import backend.academy.multithreading.MultithreadingGenerator;
import backend.academy.singlethreading.SingleThreadGenerator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FractalFactory {
    private final ImageSettings settings;
    private final FractalCache fractalCache;

    public FractalGenerator generator() {
        if (settings.isConcurrent()) {
            return new MultithreadingGenerator(settings, fractalCache);
        } else {
            return new SingleThreadGenerator(settings, fractalCache);
        }
    }
}

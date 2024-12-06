package backend.academy.service;

import backend.academy.data.image.ImageSettings;
import backend.academy.multithreading.MultithreadingGenerator;
import backend.academy.singlethreading.SingleThreadGenerator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FractalFactory {
    private final ImageSettings settings;

    public FractalGenerator generator() {
        if (settings.isConcurrent()) {
            return new MultithreadingGenerator(settings);
        } else {
            return new SingleThreadGenerator(settings);
        }
    }
}

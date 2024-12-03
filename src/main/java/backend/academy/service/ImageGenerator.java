package backend.academy.service;

import backend.academy.data.image.Fractal;
import backend.academy.data.image.ImageSettings;

public interface ImageGenerator {
    Fractal generate(ImageSettings settings);
}

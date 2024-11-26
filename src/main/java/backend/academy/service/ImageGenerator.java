package backend.academy.service;

import backend.academy.data.image.Frame;
import backend.academy.data.image.ImageSettings;

public interface ImageGenerator {
    Frame generate(ImageSettings settings);
}

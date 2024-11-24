package backend.academy.service;

import backend.academy.data.Frame;
import backend.academy.data.ImageSettings;

public interface ImageGenerator {
    Frame generate(ImageSettings settings);
}

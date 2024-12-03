package backend.academy.service;

import backend.academy.data.image.Format;
import backend.academy.data.image.Fractal;

public interface Renderer {
    void update(Fractal fractal);
    void saveAs(Fractal fractal, String path, String name, Format format);
}

package backend.academy.service;

import backend.academy.data.image.Format;
import backend.academy.data.image.Fractal;
import backend.academy.data.transformations.PostProcessing;

public interface FractalRenderer {
    void postProcess(Fractal fractal, PostProcessing... functions);

    void saveAs(Fractal fractal, String path, String name, Format format);
}

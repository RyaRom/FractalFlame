package backend.academy.service.fractals;

import backend.academy.data.FractalCache;
import backend.academy.data.image.Format;
import backend.academy.data.image.Fractal;
import backend.academy.data.image.ImageSettings;
import backend.academy.data.transformations.PostProcessing;

public interface FractalRenderer {
    void postProcess(Fractal fractal, String id, FractalCache cache);

    void saveAs(Fractal fractal, String path, String name, Format format);
}
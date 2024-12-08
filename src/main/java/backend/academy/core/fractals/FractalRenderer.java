package backend.academy.core.fractals;

import backend.academy.core.data.image.Fractal;
import backend.academy.web.data.FractalCache;

public interface FractalRenderer {
    void postProcess(Fractal fractal, String id, FractalCache cache);

}

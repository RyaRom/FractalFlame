package backend.academy.service;

import backend.academy.data.image.Format;
import backend.academy.data.image.Fractal;
import backend.academy.singlethreading.postprocessing.BlurCorrection;
import backend.academy.singlethreading.postprocessing.GammaCorrection;
import backend.academy.singlethreading.postprocessing.HeatMap;
import java.io.File;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FractalRendererImplTest {

    @Test
    void saveAs() {
        String path = "./";
        String name = "test";
        Fractal fractal = Fractal.of(100, 100, 1.0);
        FractalRendererImpl renderer = new FractalRendererImpl();
        for (Format format : Format.values()) {
            renderer.saveAs(fractal, path, name, format);
            File created = new File(
                path + "/" + name + "." + format.toString().toLowerCase());
            assertTrue(created.exists());
            created.delete();
        }
    }

    @Test
    void process() {
        Fractal fractal = Fractal.of(100, 100, 1.0);
        FractalRendererImpl renderer = new FractalRendererImpl();
        assertDoesNotThrow(() ->
            renderer.postProcess(fractal, new GammaCorrection(), new BlurCorrection(), new HeatMap()));
    }
}

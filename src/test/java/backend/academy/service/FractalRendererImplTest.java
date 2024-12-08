package backend.academy.service;

import backend.academy.core.data.image.Format;
import backend.academy.core.data.image.Fractal;
import backend.academy.core.fractals.FractalUtil;
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
        for (Format format : Format.values()) {
            FractalUtil.saveAs(fractal, path, name, format);
            File created = new File(
                path + "/" + name + "." + format.toString().toLowerCase());
            assertTrue(created.exists());
            created.delete();
        }
    }
}

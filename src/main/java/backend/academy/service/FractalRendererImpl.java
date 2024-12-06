package backend.academy.service;

import backend.academy.data.image.Format;
import backend.academy.data.image.Fractal;
import backend.academy.data.transformations.PostProcessing;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.imageio.ImageIO;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class FractalRendererImpl implements FractalRenderer {

    @Override
    public void postProcess(Fractal fractal, PostProcessing... functions) {
        for (var function : functions) {
            function.process(fractal);
        }
    }

    @Override
    public void saveAs(Fractal fractal, String path, String name, Format format) {
        BufferedImage image = fractal.toBufferedImage();
        String fullPath = path + "/" + name + "." + format.toString().toLowerCase();
        try {
            Files.createDirectories(Path.of(path));
            File output = new File(fullPath);
            if (!ImageIO.write(image, format.toString(), output)) {
                log.error("Error saving fractal image {}", fullPath);
            }
        } catch (IOException e) {
            log.error("Error saving fractal image {} {}", fullPath, e);
        }
    }
}

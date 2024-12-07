package backend.academy.service.fractals;

import backend.academy.data.FractalCache;
import backend.academy.data.image.Format;
import backend.academy.data.image.Fractal;
import backend.academy.data.image.ImageSettings;
import backend.academy.data.transformations.RenderFactory;
import backend.academy.data.webDTO.GenerationProcess;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.imageio.ImageIO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
public class FractalRendererImpl implements FractalRenderer {
    private final ImageSettings settings;

    @Override
    public void postProcess(Fractal fractal, String id, FractalCache cache) {
        GenerationProcess process = cache.getProcess(id);
        process.startTimeRender(System.currentTimeMillis());
        var functions = new RenderFactory(settings).renderFunctions();
        for (var function : functions) {
            if (Thread.currentThread().isInterrupted()) {
                break;
            }
            function.process(fractal);
        }
        process.shutdownTimeRender(System.currentTimeMillis());
    }

    @Override
    public void saveAs(Fractal fractal, String path, String name, Format format) {
        BufferedImage image = fractal.image();
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

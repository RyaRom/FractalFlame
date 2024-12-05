package backend.academy.multithreading;

import backend.academy.data.image.Fractal;
import backend.academy.data.image.ImageSettings;
import backend.academy.service.FractalGenerator;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
public class MultithreadingGenerator implements FractalGenerator {

    @Override
    public Fractal generate(ImageSettings settings) {
        Fractal fractal = Fractal.of(settings.heightRes(), settings.widthRes(), settings.depth());
        IntStream.range(0, settings.startingPoints())
            .parallel()
            .forEach(i -> processPointTransformations(fractal, settings));
        log.info("Fractal generated");
        return fractal;
    }
}

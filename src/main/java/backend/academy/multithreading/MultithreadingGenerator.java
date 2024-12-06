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
    private final ImageSettings settings;

    @Override
    public Fractal generate(Fractal fractal) {
        IntStream.range(0, settings.startingPoints())
            .parallel()
            .forEach(i -> processPointTransformations(fractal, settings));
        log.info("Fractal generated");
        return fractal;
    }
}

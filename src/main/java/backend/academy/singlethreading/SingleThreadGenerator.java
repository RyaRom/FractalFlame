package backend.academy.singlethreading;

import backend.academy.data.image.Fractal;
import backend.academy.data.image.ImageSettings;
import backend.academy.service.FractalGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@SuppressWarnings("MagicNumber")
public class SingleThreadGenerator implements FractalGenerator {
    private final ImageSettings settings;

    @Override
    public Fractal generate(Fractal fractal) {
        for (int i = 0; i < settings.startingPoints(); i++) {
            if (i % 500 == 0) {
                log.info("Point {} processed", i);
            }
            processPointTransformations(fractal, settings);
        }
        log.info("Fractal generated");
        return fractal;
    }
}

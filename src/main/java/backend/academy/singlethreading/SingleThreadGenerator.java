package backend.academy.singlethreading;

import backend.academy.data.image.Fractal;
import backend.academy.data.image.ImageSettings;
import backend.academy.service.FractalGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
public class SingleThreadGenerator implements FractalGenerator {

    @Override
    public Fractal generate(ImageSettings settings) {
        Fractal fractal = Fractal.of(settings.heightRes(), settings.widthRes(), settings.zoom());

        for (int i = 0; i < settings.startingPoints(); i++) {
            if (i % 500 == 0) {
                log.info("Point {} processed", i);
            }
            processPointTransformations(fractal, settings);
        }
        return fractal;
    }
}

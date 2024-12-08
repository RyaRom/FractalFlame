package backend.academy.core.singlethreading;

import backend.academy.core.data.image.Fractal;
import backend.academy.core.data.image.ImageSettings;
import backend.academy.core.data.image.Point;
import backend.academy.core.fractals.FractalGenerator;
import backend.academy.web.data.FractalCache;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@SuppressWarnings("MagicNumber")
public class SingleThreadGenerator implements FractalGenerator {
    private final ImageSettings settings;

    private final FractalCache fractalCache;

    @Override
    public void generate(Fractal fractal, String id) {
        for (int i = 0; i < settings.startingPoints(); i++) {
            if (i % 500 == 0) {
                log.info("Point {} processed", i);
            }
            if (Thread.currentThread().isInterrupted()) {
                log.info("Generation process interrupted in single thread mode");
                break;
            }
            processPointTransformations(fractal, settings);
        }
        log.info("Fractal generated");
        complete(id);
    }

    private void processPointTransformations(Fractal fractal, ImageSettings settings) {
        Point current = getRandomPoint(fractal);
        for (int step = -20; step < settings.iterationsForPoint(); step++) {
            if (Thread.currentThread().isInterrupted()) {
                return;
            }

            var transformation = settings.getRandomTransformation();
            current = transformation.apply(current);
            drawTransformedPoint(current, settings.symmetry(), transformation.rgb(), fractal);
        }
    }

    private void complete(String id) {
        var process = fractalCache.getProcess(id);
        process.shutdownTimeGen(System.currentTimeMillis());
        process.threadsCount(1L);
    }
}

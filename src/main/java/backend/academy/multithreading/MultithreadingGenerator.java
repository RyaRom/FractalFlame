package backend.academy.multithreading;

import backend.academy.data.image.Fractal;
import backend.academy.data.image.ImageSettings;
import backend.academy.service.FractalGenerator;
import java.util.concurrent.CountDownLatch;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import static backend.academy.Main.EXECUTOR;

@Log4j2
@RequiredArgsConstructor
public class MultithreadingGenerator implements FractalGenerator {

    @Override
    public Fractal generate(ImageSettings settings) {
        Fractal fractal = Fractal.of(settings.heightRes(), settings.widthRes(), settings.zoom());
        CountDownLatch countDownLatch = new CountDownLatch(settings.startingPoints());

        for (int i = 0; i < settings.startingPoints(); i++) {
            if (i % 500 == 0) {
                log.info("Point {} started processing", i);
            }

            EXECUTOR.submit(() -> {
                try {
                    processPointTransformations(fractal, settings);
                } finally {
                    countDownLatch.countDown();
                }
            });
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            log.warn("Fractal generator interrupted");
            return fractal;
        }
        log.info("Fractal generated");
        return fractal;
    }
}

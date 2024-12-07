package backend.academy.multithreading;

import backend.academy.data.FractalCache;
import backend.academy.data.image.Fractal;
import backend.academy.data.image.ImageSettings;
import backend.academy.data.image.Point;
import backend.academy.service.fractals.FractalGenerator;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
public class MultithreadingGenerator implements FractalGenerator {
    private final ImageSettings settings;

    private final FractalCache fractalCache;

    AtomicBoolean interrupted = new AtomicBoolean(false);

    @Override
    public void generate(Fractal fractal, String id) {
        log.info("Starting main thread: {}", Thread.currentThread().threadId());
        interrupted.set(false);
        Set<String> threadIds = new HashSet<>();

        IntStream.range(0, settings.startingPoints())
            .parallel()
            .forEach(i -> {
                String threadId = Thread.currentThread().getName();
                threadIds.add(threadId);
                if (interrupted.get()) {
//                    log.info("Generation process interrupted in multi thread mode. Shutting down...");
                    return;
                }
                if (Thread.currentThread().isInterrupted()) {
//                    log.info("Generation process interrupted in multi-thread mode. Shutting down...");
                    interrupted.set(true);
                    return;
                }

                processPointTransformations(fractal, settings);
            });

        if (Thread.currentThread().isInterrupted()) {
            log.info("Generation process finally interrupted in multi thread mode. Shutting down...");
        }
        log.info("Threads: {}", threadIds);
        log.info("Fractal generated");

        complete(id, threadIds.size());
    }

    private void processPointTransformations(Fractal fractal, ImageSettings settings) {
        Point current = getRandomPoint(fractal);
        for (int step = -20; step < settings.iterationsForPoint(); step++) {
            if (Thread.currentThread().isInterrupted() || interrupted.get())  {
//                log.info("Shutting down...");
                return;
            }

            var transformation = settings.getRandomTransformation();
            current = transformation.apply(current);
            drawTransformedPoint(current, settings.symmetry(), transformation.rgb(), fractal);
        }
    }

    private void complete(String id, int threadCount) {
        var process = fractalCache.getProcess(id);
        process.shutdownTimeGen(System.currentTimeMillis());
        process.threadsCount((long) threadCount);
    }
}

package backend.academy.multithreading;

import backend.academy.data.FractalCache;
import backend.academy.data.image.Fractal;
import backend.academy.data.image.ImageSettings;
import backend.academy.service.fractals.FractalGenerator;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
public class MultithreadingGenerator implements FractalGenerator {
    private final ImageSettings settings;

    private final FractalCache fractalCache;

    @Override
    public void generate(Fractal fractal, String id) {
        log.info("Starting main thread: {}", Thread.currentThread().threadId());
        Set<String> threadIds = new HashSet<>();

        IntStream.range(0, settings.startingPoints())
            .parallel()
            .forEach(i -> {
                String threadId = Thread.currentThread().getName();
                threadIds.add(threadId);
                if (Thread.currentThread().isInterrupted()) {
                    return;
                }

                processPointTransformations(fractal, settings);
            });
        log.info("Threads: {}", threadIds);
        log.info("Fractal generated");

        complete(id, threadIds.size());
    }

    private void complete(String id, int threadCount) {
        var process = fractalCache.getProcess(id);
        process.shutdownTimeGen(System.currentTimeMillis());
        process.threadsCount((long) threadCount);
    }
}

package backend.academy.service;

import backend.academy.data.FractalCache;
import backend.academy.data.Mapper;
import backend.academy.data.image.Fractal;
import backend.academy.data.image.ImageSettings;
import backend.academy.data.webDTO.GenerationProcess;
import backend.academy.data.webDTO.ImageSettingsDTO;
import backend.academy.data.webDTO.ResponseDTO;
import backend.academy.service.fractals.FractalFactory;
import backend.academy.service.fractals.FractalGenerator;
import backend.academy.service.fractals.FractalRenderer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import static backend.academy.data.Mapper.toResponse;

@Service
@RequiredArgsConstructor
@Log4j2
public class WebService {
    private final FractalCache fractalCache;

    private final ExecutorService executorService;

    private final FractalFactory fractalFactory;

    private final ObjectMapper objectMapper;

    public String startGeneration(ImageSettingsDTO imageSettingsDTO) {
        ImageSettings settings = Mapper.mapToImageSettings(imageSettingsDTO);
        FractalGenerator generator = fractalFactory.generator(settings);
        FractalRenderer renderer = fractalFactory.renderer(settings);
        Fractal fractal = Fractal.of(settings.heightRes(), settings.widthRes(), settings.depth());
        String id = fractalCache.generateId();
        fractalCache.cacheFractal(id, fractal);
        startGenerating(fractal, generator, renderer, id);

        return id;
    }

    public void startGenerating(Fractal fractal, FractalGenerator generator, FractalRenderer renderer, String id) {
        GenerationProcess process = GenerationProcess.empty();
        fractalCache.cacheProcess(id, process);
        CountDownLatch countDownLatch = new CountDownLatch(1);

        Future<?> gen = executorService.submit(() -> {
            log.info("Generation process started");
            try {
                generator.generate(fractal, id);
            } finally {
                log.info("Generation process finished");
                countDownLatch.countDown();
            }
        });
        process.genTask(gen);

        Future<?> render = executorService.submit(() -> {
            try {
                //after generation is finished or interrupted
                log.info("Waiting for generation process to finish");
                countDownLatch.await();
                log.info("Rendering process started");
                renderer.postProcess(fractal, id, fractalCache);
            } catch (Exception e) {
                log.info("Rendering process interrupted");
                //if render stops before it finishes
            }
            log.info("Rendering process finished");
        });
        process.renderTask(render);
    }

    public void stopGeneration(String id) {
        if (!fractalCache.containsFractal(id)) {
            return;
        }
        GenerationProcess process = fractalCache.getProcess(id);
        process.genTask().cancel(true);
    }

    public void terminateFractalProcessCompletely(String id) {
        if (!fractalCache.containsProcess(id)) {
            return;
        }
        GenerationProcess process = fractalCache.getProcess(id);
        process.genTask().cancel(true);
        process.renderTask().cancel(true);
        fractalCache.deleteProcess(id);
        fractalCache.deleteFractal(id);
        log.info("Fractal deleted");
    }

    public String renderFractal(String id) {
        if (!fractalCache.containsFractal(id)) {
            throw new IllegalArgumentException();
        }
        GenerationProcess process = fractalCache.getProcess(id);
        Fractal fractal = fractalCache.getFractal(id);
        try {
            var generation = process.genTask();
            log.info("isDone: {}", generation.isDone());
            log.info("isCancelled: {}", generation.isCancelled());
            if (!generation.isDone() && !generation.isCancelled()) {
                generation.cancel(true);
            }
            process.renderTask().get();
        } catch (InterruptedException | ExecutionException | CancellationException e) {
            //new image is creating
            return null;
        }

        //weird deserialization error
        ResponseDTO responseDTO = toResponse(process, fractal.encode());
        try {
            return objectMapper.writeValueAsString(responseDTO);
        } catch (JsonProcessingException e) {
            log.error("json error {}", e.getMessage());
        }
        log.info("{}", responseDTO);
        return "";
    }

    public String getProgress(String id) {
        if (!fractalCache.containsProcess(id)) {
            throw new IllegalArgumentException();
        }
        Fractal fractal = fractalCache.getFractal(id);
        return fractal.encode();
    }
}

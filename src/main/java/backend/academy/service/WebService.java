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
import backend.academy.service.fractals.FractalRendererImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
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

    private final ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    public String startGeneration(ImageSettingsDTO imageSettingsDTO) {
        ImageSettings settings = Mapper.mapToImageSettings(imageSettingsDTO);
        FractalGenerator generator = new FractalFactory(settings, fractalCache).generator();
        FractalRenderer renderer = new FractalRendererImpl(settings);
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
            try {
                generator.generate(fractal, id);
            } finally {
                countDownLatch.countDown();
            }
        });
        process.genTask(gen);

        Future<?> render = executorService.submit(() -> {
            try {
                //after generation is finished or interrupted
                countDownLatch.await();
                renderer.postProcess(fractal, id, fractalCache);
            } catch (InterruptedException e) {
                //if render stops before it finishes
            }
        });
        process.renderTask(render);
    }

    public void stopGeneration(String id) {
        GenerationProcess process = fractalCache.getProcess(id);
        process.genTask().cancel(true);
    }

    public void terminateFractalProcessCompletely(String id) {
        GenerationProcess process = fractalCache.getProcess(id);
        process.genTask().cancel(true);
        process.renderTask().cancel(true);
        fractalCache.deleteProcess(id);
        fractalCache.deleteFractal(id);
    }

    public String renderFractal(String id) {
        GenerationProcess process = fractalCache.getProcess(id);
        Fractal fractal = fractalCache.getFractal(id);
        try {
            process.renderTask().get();
        } catch (InterruptedException | ExecutionException e) {
            //new image is creating
            return null;
        }

        //weird deserialization error
        ResponseDTO responseDTO = toResponse(process, fractal.encode());
        try {
            return mapper.writeValueAsString(responseDTO);
        } catch (JsonProcessingException e) {
            log.error("json error {}", e.getMessage());
        }
        log.info("{}", responseDTO);
        return "";
    }

    public String getProgress(String id) {
        Fractal fractal = fractalCache.getFractal(id);
        return fractal.encode();
    }
}

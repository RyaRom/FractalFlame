package backend.academy.data;

import backend.academy.data.image.Fractal;
import backend.academy.data.webDTO.GenerationProcess;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
@SuppressWarnings("MagicNumber")
public class FractalCache {
    private final Cache<String, Fractal> fractals;

    private final Cache<String, GenerationProcess> processes;

    public FractalCache() {
        this.fractals = Caffeine.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(15, TimeUnit.MINUTES)
            .build();

        this.processes = Caffeine.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(15, TimeUnit.MINUTES)
            .build();
    }

    public String generateId() {
        return UUID.randomUUID().toString();
    }

    public void cacheFractal(String id, Fractal fractal) {
        this.fractals.put(id, fractal);
    }

    public Fractal getFractal(String id) {
        return fractals.getIfPresent(id);
    }

    public void deleteFractal(String id) {
        fractals.invalidate(id);
    }

    public boolean containsFractal(String id) {
        return fractals.asMap().containsKey(id);
    }

    public void cacheProcess(String id, GenerationProcess generationProcess) {
        this.processes.put(id, generationProcess);
    }

    public GenerationProcess getProcess(String id) {
        return processes.getIfPresent(id);
    }

    public void deleteProcess(String id) {
        processes.invalidate(id);
    }

    public boolean containsProcess(String id) {
        return processes.asMap().containsKey(id);
    }
}

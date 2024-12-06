package backend.academy.data;

import backend.academy.data.image.Fractal;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class FractalCache {
    private final Cache<String, Fractal> cache;

    public FractalCache() {
        this.cache = Caffeine.newBuilder()
            .maximumSize(1_000_00)
            .expireAfterWrite(30, TimeUnit.MINUTES)
            .build();
    }

    public String generateId() {
        return UUID.randomUUID().toString();
    }

    public void cacheFractal(String id, Fractal fractal) {
        cache.put(id, fractal);
    }

    public Fractal getFractal(String id) {
        return cache.getIfPresent(id);
    }

    public void deleteFractal(String id) {
        cache.invalidate(id);
    }

    public boolean containsFractal(String id) {
        return cache.asMap().containsKey(id);
    }
}

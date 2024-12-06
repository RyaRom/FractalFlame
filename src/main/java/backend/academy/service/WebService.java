package backend.academy.service;

import backend.academy.data.FractalCache;
import backend.academy.data.Mapper;
import backend.academy.data.image.Fractal;
import backend.academy.data.image.ImageSettings;
import backend.academy.data.webDTO.ImageSettingsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebService {
    private final FractalCache fractalCache;

    public String startGeneration(ImageSettingsDTO imageSettingsDTO) {
        ImageSettings settings = Mapper.mapToImageSettings(imageSettingsDTO);
        FractalGenerator generator = new FractalFactory(settings).generator();
        Fractal fractal = Fractal.of(settings.heightRes(), settings.widthRes(), settings.depth());
        String id = fractalCache.generateId();
        fractalCache.cacheFractal(id, fractal);
//        generator.generate(fractal);
        startGenerating(fractal, generator);

        return id;
    }

    @Async
    public void startGenerating(Fractal fractal, FractalGenerator generator) {
//        generator.generate(fractal);
        while (true){
            try {
                Thread.sleep(1000);
                System.out.println("generating...");
            } catch (InterruptedException e) {
                System.out.println("interrupted");
            }
        }
    }
}

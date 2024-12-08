package backend.academy.core.fractals;

import backend.academy.core.data.image.Fractal;
import backend.academy.core.data.image.ImageSettings;
import backend.academy.core.data.transformations.RenderFactory;
import backend.academy.web.data.FractalCache;
import backend.academy.web.data.webDTO.GenerationProcess;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
public class FractalRendererImpl implements FractalRenderer {
    private final ImageSettings settings;

    @Override
    public void postProcess(Fractal fractal, String id, FractalCache cache) {
        GenerationProcess process = cache.getProcess(id);
        process.startTimeRender(System.currentTimeMillis());
        var functions = new RenderFactory(settings).renderFunctions();
        for (var function : functions) {
            if (Thread.currentThread().isInterrupted()) {
                break;
            }
            function.process(fractal);
        }
        process.shutdownTimeRender(System.currentTimeMillis());
    }
}

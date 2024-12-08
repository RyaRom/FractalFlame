package backend.academy.core.multithreading.postprocessing;

import backend.academy.core.data.image.Fractal;
import backend.academy.core.data.transformations.PostProcessing;
import backend.academy.core.singlethreading.postprocessing.BlurCorrection;
import java.util.stream.IntStream;
import lombok.extern.log4j.Log4j2;

@Log4j2
@SuppressWarnings("MagicNumber")
public class BlurCorrectionConcurrent implements PostProcessing {
    @Override
    public void process(Fractal fractal) {
        log.info("Start blur correction");
        IntStream.range(0, fractal.width() * fractal.height())
            .parallel()
            .forEach(index -> {
                int x = index / fractal.height();
                int y = index % fractal.height();
                fractal.setPixel(x, y, BlurCorrection.blurForPixel(x, y, fractal));
            });

        log.info("End blur correction");
    }
}

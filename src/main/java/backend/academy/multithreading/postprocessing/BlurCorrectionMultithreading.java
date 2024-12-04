package backend.academy.multithreading.postprocessing;

import backend.academy.data.image.Fractal;
import backend.academy.data.transformations.PostProcessing;
import java.util.stream.IntStream;
import lombok.extern.log4j.Log4j2;
import static backend.academy.singlethreading.postprocessing.BlurCorrection.blurForPixel;

@Log4j2
@SuppressWarnings("MagicNumber")
public class BlurCorrectionMultithreading implements PostProcessing {
    @Override
    public void process(Fractal fractal) {
        log.info("Start blur correction");
        IntStream.range(0, fractal.width() * fractal.height())
            .parallel()
            .forEach(index -> {
                int x = index / fractal.height();
                int y = index % fractal.height();
                fractal.setPixel(x, y, blurForPixel(x, y, fractal));
            });

        log.info("End blur correction");
    }
}

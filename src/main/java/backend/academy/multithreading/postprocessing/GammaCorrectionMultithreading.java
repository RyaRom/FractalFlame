package backend.academy.multithreading.postprocessing;

import backend.academy.data.image.Fractal;
import backend.academy.data.image.Pixel;
import backend.academy.data.transformations.PostProcessing;
import com.google.common.util.concurrent.AtomicDouble;
import java.util.stream.IntStream;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class GammaCorrectionMultithreading implements PostProcessing {
    @Override
    public void process(Fractal fractal) {
        log.info("Start gamma correction");

        AtomicDouble max = new AtomicDouble(0.0);
        double gamma = 2.2;
        IntStream.range(0, fractal.height() * fractal.width())
            .parallel()
            .forEach(i -> {
                int x = i / fractal.width();
                int y = i % fractal.width();

                Pixel current = fractal.getPixel(x, y);
                if (current.hitCount() != 0) {
                    Pixel normalized = new Pixel(
                        current.rgb(),
                        current.hitCount(),
                        Math.log10(fractal.getPixel(x, y).hitCount())
                    );
                    fractal.setPixel(x, y, normalized);
                    max.set(Math.max(max.get(), normalized.normal()));
                }
            });

        if (max.get() == 0.0) {
            return;
        }

        IntStream.range(0, fractal.width() * fractal.height())
            .parallel()
            .forEach(index -> {
                int x = index / fractal.height();
                int y = index % fractal.height();
                Pixel current = fractal.getPixel(x, y);
                double normalized = current.normal() / max.get();
                Pixel correctPixel = new Pixel(
                    current.rgb().gammaCorrected(gamma, normalized),
                    current.hitCount(),
                    normalized
                );
                fractal.setPixel(x, y, correctPixel);
            });
        log.info("End gamma correction");
    }
}

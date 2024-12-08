package backend.academy.core.singlethreading.postprocessing;

import backend.academy.core.data.image.Fractal;
import backend.academy.core.data.image.Pixel;
import backend.academy.core.data.transformations.PostProcessing;
import lombok.extern.log4j.Log4j2;

@Log4j2
@SuppressWarnings("MagicNumber")
public class GammaCorrection implements PostProcessing {
    @Override
    public void process(Fractal fractal) {
        log.info("Start gamma correction");

        double max = 0.0;
        double gamma = 2.2;
        for (int x = 0; x < fractal.width(); x++) {
            for (int y = 0; y < fractal.height(); y++) {
                Pixel current = fractal.getPixel(x, y);
                if (current.hitCount() == 0) {
                    continue;
                }
                Pixel normalized = new Pixel(
                    current.rgb(),
                    current.hitCount(),
                    Math.log10(fractal.getPixel(x, y).hitCount())
                );
                fractal.setPixel(x, y, normalized);
                max = Math.max(max, normalized.normal());
            }
        }
        if (max == 0.0) {
            return;
        }

        for (int x = 0; x < fractal.width(); x++) {
            for (int y = 0; y < fractal.height(); y++) {
                Pixel current = fractal.getPixel(x, y);
                double normalized = current.normal() / max;
                Pixel correctPixel = new Pixel(
                    current.rgb().gammaCorrected(gamma, normalized),
                    current.hitCount(),
                    normalized
                );
                fractal.setPixel(x, y, correctPixel);
            }
        }
        log.info("End gamma correction");
    }
}

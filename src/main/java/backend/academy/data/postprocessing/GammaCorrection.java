package backend.academy.data.postprocessing;

import backend.academy.data.image.Frame;
import backend.academy.data.image.Pixel;

public class GammaCorrection implements PostProcessing {
    @Override
    public void accept(Frame frame) {
        double max = 0.0;
        double gamma = 2.2;
        for (int x = 0; x < frame.width(); x++) {
            for (int y = 0; y < frame.height(); y++) {
                Pixel current = frame.getPixel(x, y);
                if (current.hitCount() == 0) {
                    continue;
                }
                Pixel normalized = new Pixel(
                    current.rgb(),
                    current.hitCount(),
                    Math.log10(frame.getPixel(x, y).hitCount())
                );
                frame.setPixel(x, y, normalized);
                max = Math.max(max, normalized.normal());
            }
        }
        if (max == 0.0) {
            return;
        }

        for (int x = 0; x < frame.width(); x++) {
            for (int y = 0; y < frame.height(); y++) {
                Pixel current = frame.getPixel(x, y);
                double normalized = current.normal() / max;
                Pixel correctPixel = new Pixel(
                    current.rgb().gammaCorrected(gamma, normalized),
                    current.hitCount(),
                    normalized
                );
                frame.setPixel(x, y, correctPixel);
            }
        }
    }
}

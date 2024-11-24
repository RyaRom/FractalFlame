package backend.academy.singlethreading;

import backend.academy.data.ColorRGB;
import backend.academy.data.Frame;
import backend.academy.data.ImageSettings;
import backend.academy.data.Pixel;
import backend.academy.data.Point;
import backend.academy.service.ImageGenerator;
import backend.academy.service.Renderer;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import static backend.academy.singlethreading.Application.RANDOM;

@Log4j2
@RequiredArgsConstructor
public class SingleThreadGenerator implements ImageGenerator {
    private final Renderer renderer;

    @Override
    public Frame generate(ImageSettings settings) {
        Frame frame = new Frame(settings.widthRes(), settings.heightRes());

        for (int i = 0; i < settings.startingPoints(); i++) {
            double xMin = -1.777, xMax = 1.777, yMin = -1.0, yMax = 1.0;
            double newX = RANDOM.nextDouble(xMin, xMax);
            double newY = RANDOM.nextDouble(yMin, yMax);

            Point current = new Point(newX, newY);
            log.info("point created {} {}", newX, newY);
            for (int step = -20; step < settings.iterationsForPoint(); step++) {
                var transformation = settings.getRandomTransformation();
                Point transformed = transformation.apply(current);
                if (step > 0 && transformed.x() > xMin && transformed.x() < xMax && transformed.y() > yMin &&
                    transformed.y() < yMax) {
                    int x1 = (int) ((transformed.x() - xMin) / (xMax - xMin) * settings.widthRes());
                    int y1 = (int) ((transformed.y() - yMin) / (yMax - yMin) * settings.heightRes());
                    if (x1 >= 0 && x1 < settings.widthRes() && y1 >= 0 && y1 < settings.heightRes()) {
                        Pixel hitPixel = frame.getPixel(x1, y1);
                        ColorRGB newColorRGB;
                        if (hitPixel.hitCount() == 0) {
                            newColorRGB = transformation.getColor();
                        } else {
                            newColorRGB = hitPixel.colorRGB().blend(transformation.getColor());
                        }
                        frame.setPixel(
                            x1, y1,
                            new Pixel(newColorRGB, hitPixel.hitCount() + 1)
                        );
                    }
                }
            }
            if (i % 100 == 0) {
                renderer.update(frame);
            }
        }
        return frame;
    }
}

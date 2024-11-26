package backend.academy.singlethreading;

import backend.academy.data.image.Frame;
import backend.academy.data.image.ImageSettings;
import backend.academy.data.image.Point;
import backend.academy.service.ImageGenerator;
import backend.academy.service.Renderer;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import static backend.academy.singlethreading.Application.RANDOM;

@Log4j2
@RequiredArgsConstructor
public class SingleThreadGenerator implements ImageGenerator {
    private final Renderer renderer;

    private static Point getRandomPoint(Frame frame) {
        double newX = RANDOM.nextDouble(frame.xMin(), frame.xMax());
        double newY = RANDOM.nextDouble(frame.yMin(), frame.yMax());

        return new Point(newX, newY);
    }

    @Override
    public Frame generate(ImageSettings settings) {
        Frame frame = new Frame(settings.widthRes(), settings.heightRes());

        for (int i = 0; i < settings.startingPoints(); i++) {
            Point current = getRandomPoint(frame);
            for (int step = 0; step < settings.iterationsForPoint(); step++) {
                var transformation = settings.getRandomTransformation();
                Point transformed = transformation.apply(current);
                frame.applyTransformedPoint(transformed, settings.symmetry(), transformation.getColor());
            }
            if (i % 50 == 0) {
                renderer.update(frame);
            }
        }
        return frame;
    }
}

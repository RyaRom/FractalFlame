package backend.academy.singlethreading;

import backend.academy.data.image.ImageSettings;
import backend.academy.data.image.RGB;
import backend.academy.data.transformations.AffineTransformation;
import backend.academy.data.transformations.SinusoidalTransformation;
import backend.academy.data.transformations.SphereTransformation;
import backend.academy.data.transformations.Transformation;
import java.security.SecureRandom;
import java.util.List;

public class Application {
    public static final SecureRandom RANDOM = new SecureRandom();

    public void start() {
        List<Transformation> transformations = List.of(
            new AffineTransformation(0.7, 0.3, -0.5, -0.3, 0.7, 0.2, new RGB(255, 0, 0)),
            new SinusoidalTransformation(new RGB(100, 100, 100)),
            new SphereTransformation(new RGB(100, 100, 100))
        );

        ImageSettings settings = new ImageSettings(2000, 2000, 2000, 20000, 5, transformations);
        SingleThreadRenderer renderer = new SingleThreadRenderer(settings);
        SingleThreadGenerator generator = new SingleThreadGenerator(renderer);
        generator.generate(settings);
    }
}

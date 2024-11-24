package backend.academy.singlethreading;

import backend.academy.data.ColorRGB;
import backend.academy.data.ImageSettings;
import backend.academy.data.transformations.AffineTransformation;
import backend.academy.data.transformations.Transformation;
import java.security.SecureRandom;
import java.util.List;

public class Application {
    public static final SecureRandom RANDOM = new SecureRandom();

    public void start() {
        List<Transformation> transformations = List.of(
            new AffineTransformation(0.5, 0, 0, 0, 0.5, 0, new ColorRGB(255, 0, 0)),
            new AffineTransformation(0.5, 0, 1, 0, 0.5, 0, new ColorRGB(0, 255, 0)),
            new AffineTransformation(0.5, 0, -1, 0, 0.5, 0, new ColorRGB(0, 0, 255))
        );

        ImageSettings settings = new ImageSettings(600, 600, 10000, 5000, transformations);
        SingleThreadRenderer renderer = new SingleThreadRenderer(600, 600);
        SingleThreadGenerator generator = new SingleThreadGenerator(renderer);
        generator.generate(settings);
    }
}

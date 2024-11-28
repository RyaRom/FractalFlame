package backend.academy.singlethreading;

import backend.academy.data.image.Format;
import backend.academy.data.image.Frame;
import backend.academy.data.image.ImageSettings;
import backend.academy.data.image.RGB;
import backend.academy.data.transformations.SinusoidalTransformation;
import backend.academy.data.transformations.SphereTransformation;
import backend.academy.data.transformations.Transformation;
import java.util.List;
import java.util.Random;

public class Application {
    public static final Random RANDOM = new Random();

    public void start() {
        List<Transformation> transformations = List.of(
//            new AffineTransformation(0.7, 0.3, -0.5, -0.3, 0.7, 0.2, new RGB(255, 0, 0)),
            new SinusoidalTransformation(new RGB(100, 255, 0)),
            new SphereTransformation(new RGB(0, 100, 255))
        );

        ImageSettings settings = new ImageSettings(500, 500, 10000, 10000, 5, transformations);
        SingleThreadRenderer renderer = new SingleThreadRenderer(settings);
        SingleThreadGenerator generator = new SingleThreadGenerator(renderer);
        Frame frame = generator.generate(settings);
        renderer.saveAs(frame, "src/main/resources", "fractal", Format.PNG);
    }
}

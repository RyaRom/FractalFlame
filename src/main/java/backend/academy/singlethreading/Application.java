package backend.academy.singlethreading;

import backend.academy.data.image.Format;
import backend.academy.data.image.Frame;
import backend.academy.data.image.ImageSettings;
import backend.academy.data.image.RGB;
import backend.academy.data.transformations.AffineTransformation;
import backend.academy.data.transformations.SinusoidalTransformation;
import backend.academy.data.transformations.Transformation;
import java.util.List;
import java.util.Random;

public class Application {
    public static final Random RANDOM = new Random();

    public void start() {
        List<Transformation> transformations = List.of(
            new SinusoidalTransformation(
                new RGB(43, 163, 217),
                new AffineTransformation(
                    -1.184, -0.738, -0.247, 0.128, 0.521, 0.212
                )
            ),

            new SinusoidalTransformation(
                new RGB(56, 99, 186),
                new AffineTransformation(
                    -0.719, -0.377, 0.606, 1.250, 0.068, -1.225
                )
            ),

            new SinusoidalTransformation(
                new RGB(56, 99, 186),
                new AffineTransformation(
                    1.245, 0.841, 0.798, 0.329, 1.496, 1.111
                )
            )
        );

        ImageSettings settings = new ImageSettings(500, 500, 200000, 100, 5, transformations);
        SingleThreadRenderer renderer = new SingleThreadRenderer(settings);
        SingleThreadGenerator generator = new SingleThreadGenerator(renderer);
        Frame frame = generator.generate(settings);
        renderer.saveAs(frame, "src/main/resources", "fractal", Format.PNG);
    }
}

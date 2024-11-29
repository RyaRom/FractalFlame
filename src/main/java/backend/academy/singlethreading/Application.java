package backend.academy.singlethreading;

import backend.academy.data.image.Format;
import backend.academy.data.image.Frame;
import backend.academy.data.image.ImageSettings;
import backend.academy.data.image.RGB;
import backend.academy.data.transformations.AffineTransformation;
import backend.academy.data.transformations.PolarTransformation;
import backend.academy.data.transformations.SinusoidalTransformation;
import backend.academy.data.transformations.SphereTransformation;
import backend.academy.data.transformations.Transformation;
import java.util.List;
import java.util.Random;

public class Application {
    public static final Random RANDOM = new Random();

    public void start() {
        List<Transformation> transformations = List.of(
            new Transformation(new RGB(255, 0, 0),
                new AffineTransformation(
                    1.114,-0.735,-0.093,-0.662,-0.947,0.202
                ),
                0.234375,
                new SinusoidalTransformation()
            ),new Transformation(new RGB(255, 0, 0),
                new AffineTransformation(
                    -1.426,-0.166,1.379,-0.065,1.307,0.500
                ),
                0.7578125,
                new SinusoidalTransformation()
            )
        );

        ImageSettings settings = new ImageSettings(700, 700, 400000, 1000, 3, transformations);
        SingleThreadRenderer renderer = new SingleThreadRenderer(settings);
        SingleThreadGenerator generator = new SingleThreadGenerator(renderer);
        Frame frame = generator.generate(settings);
        renderer.saveAs(frame, "src/main/resources", "fractal", Format.PNG);
    }
}

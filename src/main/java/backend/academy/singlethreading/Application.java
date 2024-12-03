package backend.academy.singlethreading;

import backend.academy.data.image.Format;
import backend.academy.data.image.Fractal;
import backend.academy.data.image.ImageSettings;
import backend.academy.data.image.RGB;
import backend.academy.data.transformations.AffineTransformation;
import backend.academy.data.transformations.HorseshoeTransformation;
import backend.academy.data.transformations.IterativeFunction;
import backend.academy.data.transformations.SinusoidalTransformation;
import backend.academy.data.transformations.SwirlTransformation;
import java.util.List;
import java.util.Random;

public class Application {
    public static final Random RANDOM = new Random();

    public void start() {

        List<IterativeFunction> iterativeFunctions = List.of(
            new IterativeFunction(
                new RGB(0.64, 0.44, 0.48),
                new AffineTransformation(
                    0.625, -0.538, -0.077, 1.409, 0.775, -1.452
                ),
                0.5,
                new SinusoidalTransformation(0.2),
                new HorseshoeTransformation(0.2)
            ), new IterativeFunction(
                new RGB(0.46, 0.55, 0.98),
                new AffineTransformation(
                    -1.095, -0.129, -0.316, -0.428, -0.738, -0.231
                ),
                0.0625,
                new SinusoidalTransformation(1.0)
            ), new IterativeFunction(
                new RGB(0.93, 0.06, 0.69),
                new AffineTransformation(
                    1.430, 0.354, -1.084, 0.903, -1.019, 0.958
                ),
                0.015625,
                new SinusoidalTransformation(1.0)
            ), new IterativeFunction(
                new RGB(0.89, 0.63, 0.62),
                new AffineTransformation(
                    -0.281, 0.590, -0.228, -0.449, 1.202, 0.188
                ),
                0.03125,
                new SinusoidalTransformation(1.0)
            ), new IterativeFunction(
                new RGB(0.28, 0.74, 0.52),
                new AffineTransformation(
                    0.963, 0.240, 0.419, -0.370, -1.125, 1.081
                ),
                0.125,
                new SinusoidalTransformation(1.0)
            ), new IterativeFunction(
                new RGB(0.96, 0.38, 0.97),
                new AffineTransformation(
                    1.113, -1.254, 1.258, 0.411, -0.494, 1.098
                ),
                0.25,
                new SinusoidalTransformation(1.0)
            )
            );

        ImageSettings settings = new ImageSettings(1000, 1000, 20000, 1000, 1, iterativeFunctions);
        SingleThreadRenderer renderer = new SingleThreadRenderer(settings);
        SingleThreadGenerator generator = new SingleThreadGenerator(renderer);
        Fractal fractal = generator.generate(settings);
        renderer.saveAs(fractal, "src/main/resources", "fractal", Format.PNG);
    }
}

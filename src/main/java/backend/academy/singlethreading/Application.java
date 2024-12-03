package backend.academy.singlethreading;

import backend.academy.data.image.Format;
import backend.academy.data.image.Fractal;
import backend.academy.data.image.ImageSettings;
import backend.academy.data.image.RGB;
import backend.academy.data.postprocessing.BlurCorrection;
import backend.academy.data.postprocessing.GammaCorrection;
import backend.academy.data.postprocessing.HeatMap;
import backend.academy.data.transformations.AffineTransformation;
import backend.academy.data.transformations.DiskTransformation;
import backend.academy.data.transformations.HeartTransformation;
import backend.academy.data.transformations.IterativeFunction;
import java.util.List;
import java.util.Random;

public class Application {
    public static final Random RANDOM = new Random();

    public void start() {

        List<IterativeFunction> iterativeFunctions = List.of(
            new IterativeFunction(
                RGB.EMERALD,
                new AffineTransformation(-0.198, 1.230, -1.291, -0.720, -0.326, -0.268),
                0.0703125,
                new HeartTransformation(1.0)
            ), new IterativeFunction(
                RGB.AQUA,
                new AffineTransformation(0.848, 0.616, -1.218, -0.472, -1.173, 0.110),
                0.125,
                new DiskTransformation(1.0)
            )
        );

        ImageSettings settings = new ImageSettings(1000, 1000, 3000, 1000, 5, iterativeFunctions, 2.0);
        SingleThreadImageRenderer renderer = new SingleThreadImageRenderer();
        SingleThreadGenerator generator = new SingleThreadGenerator();
        Fractal fractal = generator.generate(settings);
        renderer.postProcess(fractal, new GammaCorrection(), new HeatMap(), new BlurCorrection());
        renderer.saveAs(fractal, "src/main/resources", "fractal", Format.PNG);
    }
}

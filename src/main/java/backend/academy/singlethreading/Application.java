package backend.academy.singlethreading;

import backend.academy.data.image.Format;
import backend.academy.data.image.Fractal;
import backend.academy.data.image.ImageSettings;
import backend.academy.data.image.RGB;
import backend.academy.data.postprocessing.BlurCorrection;
import backend.academy.data.postprocessing.GammaCorrection;
import backend.academy.data.transformations.AffineTransformation;
import backend.academy.data.transformations.IterativeFunction;
import backend.academy.data.transformations.SphereTransformation;
import backend.academy.data.transformations.SwirlTransformation;
import backend.academy.multithreading.MultithreadingGenerator;
import java.util.List;
import java.util.Random;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Application {
    public static final Random RANDOM = new Random();

    public void start() {

        List<IterativeFunction> iterativeFunctions = List.of(
            new IterativeFunction(
                new RGB(0.40, 0.59, 0.27),
                new AffineTransformation(-1.276, -1.440, 0.489, -0.979, 0.897, 1.116),
                0.0859375,
                new SwirlTransformation(0.4),
                new SphereTransformation(0.6)
            ), new IterativeFunction(
                new RGB(0.77, 0.25, 0.76),
                new AffineTransformation(-0.560, -0.951, -0.914, 0.523, -0.426, -0.494),
                0.75,
                new SwirlTransformation(0.3),
                new SphereTransformation(0.7)
            ), new IterativeFunction(
                new RGB(0.64, 0.02, 0.50),
                new AffineTransformation(-1.086, -0.013, 0.195, 0.067, 1.380, -0.188),
                0.03125,
                new SwirlTransformation(0.7),
                new SphereTransformation(0.3)
            ), new IterativeFunction(
                new RGB(0.48, 0.05, 0.21),
                new AffineTransformation(-0.301, -0.813, -1.223, -1.246, -0.104, -0.208),
                0.03125,
                new SwirlTransformation(0.4),
                new SphereTransformation(0.6)
            )
        );

        long start = 0L;
        long end = 0L;
        Fractal fractal;
        Fractal fractal2;
        long single = 0L;
        long multi = 0L;
        ImageSettings settings = new ImageSettings(1716, 4096, 10000, 5000, 2, iterativeFunctions, 1.77);
        SingleThreadFractalRenderer renderer = new SingleThreadFractalRenderer();

        start = System.currentTimeMillis();
        fractal = new SingleThreadGenerator().generate(settings);
        end = System.currentTimeMillis();
        single = end - start;

        start = System.currentTimeMillis();
        fractal2 = new MultithreadingGenerator().generate(settings);
        end = System.currentTimeMillis();
        multi = end - start;

        renderer.postProcess(fractal, new GammaCorrection(), new BlurCorrection());
        renderer.postProcess(fractal2, new GammaCorrection(), new BlurCorrection());
        renderer.saveAs(fractal, "src/main/resources", "fractalSingle", Format.PNG);
        renderer.saveAs(fractal2, "src/main/resources", "fractalMulti", Format.PNG);
        log.info("Single thread ms: {}      Multithreading ms: {}", single, multi);
    }
}

package backend.academy.singlethreading;

import backend.academy.data.image.Format;
import backend.academy.data.image.Fractal;
import backend.academy.data.image.ImageSettings;
import backend.academy.data.image.RGB;
import backend.academy.data.transformations.AffineTransformation;
import backend.academy.data.transformations.HeartTransformation;
import backend.academy.data.transformations.SwirlTransformation;
import backend.academy.data.transformations.Transformation;
import java.util.List;
import java.util.Random;

public class Application {
    public static final Random RANDOM = new Random();

    public void start() {

        List<Transformation> transformations = List.of(
            new Transformation(
                RGB.RED,
                new AffineTransformation(0.429,0.661,-0.041,0.929,0.247,-0.839),
                0.8,
                new HeartTransformation()),
            new Transformation(
                RGB.BLUE,
                new AffineTransformation(-0.541,1.271,-0.676,-1.180,0.184,0.400),
                0.3,
                new HeartTransformation())
        );

        ImageSettings settings = new ImageSettings(1000, 1000, 10000, 5000, 1, transformations);
        SingleThreadRenderer renderer = new SingleThreadRenderer(settings);
        SingleThreadGenerator generator = new SingleThreadGenerator(renderer);
        Fractal fractal = generator.generate(settings);
        renderer.saveAs(fractal, "src/main/resources", "fractal", Format.PNG);
    }
}

package backend.academy;

import backend.academy.data.image.Fractal;
import backend.academy.data.image.ImageSettings;
import backend.academy.data.variations.Variations;
import backend.academy.multithreading.MultithreadingGenerator;
import backend.academy.multithreading.postprocessing.BlurCorrectionConcurrent;
import backend.academy.multithreading.postprocessing.GammaCorrectionConcurrent;
import backend.academy.multithreading.postprocessing.HeatMapConcurrent;
import backend.academy.service.fractals.FractalRenderer;
import backend.academy.service.fractals.FractalRendererImpl;
import backend.academy.service.fractals.FractalUtil;
import backend.academy.singlethreading.SingleThreadGenerator;
import backend.academy.singlethreading.postprocessing.BlurCorrection;
import backend.academy.singlethreading.postprocessing.GammaCorrection;
import backend.academy.singlethreading.postprocessing.HeatMap;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static backend.academy.service.fractals.FractalUtil.getRandomTransformationList;
import static backend.academy.service.fractals.FractalUtil.profileTime;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GeneratorsTest {

    private static final int repetitions = 16;

    private static int concurrentGenIsFasterTimes = 0;

    private static int concurrentFuncIsFasterTimes = 0;

    @AfterAll
    static void afterAll() {
        System.out.println("Concurrent is faster " + concurrentGenIsFasterTimes + " times");
        assertTrue(concurrentGenIsFasterTimes > repetitions / 2);

        System.out.println("Concurrent function is faster " + concurrentFuncIsFasterTimes + " times");
        assertTrue(concurrentFuncIsFasterTimes > repetitions / 3);
    }

    @ParameterizedTest
    @CsvSource({
        "100, 100, 100, 100",
        "500, 500, 500, 500",
        "1000, 1000, 2000, 500",
        "2000, 2000, 4000, 1000",
        "50, 50, 50, 50",
        "250, 250, 250, 250",
        "750, 750, 1500, 750",
        "1200, 1200, 2400, 1200",
        "10, 10, 10, 10",
        "200, 200, 400, 200",
        "800, 800, 1600, 800",
        "3000, 3000, 6000, 3000",
        "25, 25, 25, 25",
        "125, 125, 125, 125",
        "625, 625, 1250, 625",
        "3125, 3125, 6250, 3125"
    })
    void speedTest(int height, int width, int startingPoints, int iterations) {
        ImageSettings settings =
            new ImageSettings(height, width,
                startingPoints, iterations, 1,
                getRandomTransformationList(Variations.values()),
                1.77, 2.2);
        SingleThreadGenerator singleGen = new SingleThreadGenerator();
        MultithreadingGenerator concurrentGen = new MultithreadingGenerator();
        FractalRenderer renderer = new FractalRendererImpl();
        AtomicReference<Fractal> pointer = new AtomicReference<>();

        Long timeForSingle = FractalUtil.profileTime(() -> singleGen.generate(settings), null);
        Long timeForConcurrent = FractalUtil.profileTime(() -> concurrentGen.generate(settings), pointer);
        long difference = timeForSingle - timeForConcurrent;

        System.out.println(settings);
        System.out.println("Difference: " + difference + "ms");
        System.out.println("\n\n");
        if (difference > 0) {
            concurrentGenIsFasterTimes++;
        }

        Fractal fractal = pointer.get();
        Long single = profileTime(() -> {
            renderer.postProcess(fractal,
                new GammaCorrection(), new HeatMap(), new BlurCorrection());
            return null;
        }, null);
        Long concurrent = profileTime(() -> {
            renderer.postProcess(fractal,
                new GammaCorrectionConcurrent(), new HeatMapConcurrent(), new BlurCorrectionConcurrent()
            );
            return null;
        }, null);
        long differenceForPostProcessing = single - concurrent;

        System.out.println(settings);
        System.out.println("Difference: " + differenceForPostProcessing + "ms");
        System.out.println("\n\n");
        if (differenceForPostProcessing > 0) {
            concurrentFuncIsFasterTimes++;
        }
    }
}

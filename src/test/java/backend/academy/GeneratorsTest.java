package backend.academy;

import backend.academy.data.image.Fractal;
import backend.academy.data.image.ImageSettings;
import backend.academy.data.variations.Variations;
import backend.academy.multithreading.MultithreadingGenerator;
import backend.academy.multithreading.postprocessing.BlurCorrectionConcurrent;
import backend.academy.multithreading.postprocessing.GammaCorrectionConcurrent;
import backend.academy.multithreading.postprocessing.HeatMapConcurrent;
import backend.academy.service.FractalRenderer;
import backend.academy.service.FractalRendererImpl;
import backend.academy.service.FractalUtil;
import backend.academy.singlethreading.SingleThreadGenerator;
import backend.academy.singlethreading.postprocessing.BlurCorrection;
import backend.academy.singlethreading.postprocessing.GammaCorrection;
import backend.academy.singlethreading.postprocessing.HeatMap;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.RepeatedTest;
import static backend.academy.service.FractalUtil.RANDOM;
import static backend.academy.service.FractalUtil.getRandomTransformationList;
import static backend.academy.service.FractalUtil.profileTime;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GeneratorsTest {

    private static final int repetitions = 50;

    private static final int repetitionsFunc = 50;

    private static int concurrentGenIsFasterTimes = 0;

    private static int concurrentFuncIsFasterTimes = 0;

    @AfterAll
    static void afterAll() {
        System.out.println("Concurrent is faster " + concurrentGenIsFasterTimes + " times");
        assertTrue(concurrentGenIsFasterTimes > repetitions / 2);

        System.out.println("Concurrent function is faster " + concurrentFuncIsFasterTimes + " times");
        assertTrue(concurrentFuncIsFasterTimes > repetitionsFunc / 3);
    }

    @RepeatedTest(repetitions)
    void speedTest() {
        int height = RANDOM.nextInt(100, 1000);
        int width = RANDOM.nextInt(100, 1000);
        int startingPoints = RANDOM.nextInt(100, 2000);
        int iterations = RANDOM.nextInt(100, 500);
        SingleThreadGenerator singleGen = new SingleThreadGenerator();
        MultithreadingGenerator concurrentGen = new MultithreadingGenerator();
        ImageSettings settings =
            new ImageSettings(height, width,
                startingPoints, iterations, 1,
                getRandomTransformationList(Variations.values()),
                1.77);

        Long timeForSingle = FractalUtil.profileTime(() -> singleGen.generate(settings), null);
        Long timeForConcurrent = FractalUtil.profileTime(() -> concurrentGen.generate(settings), null);
        long difference = timeForSingle - timeForConcurrent;

        System.out.println(settings);
        System.out.println("Difference: " + difference + "ms");
        System.out.println("\n\n");
        if (difference > 0) {
            concurrentGenIsFasterTimes++;
        }
    }

    @RepeatedTest(repetitionsFunc)
    void speedTestForOthers() {
        int height = RANDOM.nextInt(0, 500);
        int width = RANDOM.nextInt(0, 500);
        int startingPoints = RANDOM.nextInt(0, 100);
        int iterations = RANDOM.nextInt(0, 100);
        ImageSettings settings =
            new ImageSettings(height, width,
                startingPoints, iterations, 1,
                getRandomTransformationList(Variations.values()),
                1.77);
        FractalRenderer renderer = new FractalRendererImpl();
        Fractal fractal = new MultithreadingGenerator().generate(settings);

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
        long difference = single - concurrent;

        System.out.println(settings);
        System.out.println("Difference: " + difference + "ms");
        System.out.println("\n\n");
        if (difference > 0) {
            concurrentFuncIsFasterTimes++;
        }
    }
}

package backend.academy;

import backend.academy.data.image.ImageSettings;
import backend.academy.data.variations.Variations;
import backend.academy.multithreading.MultithreadingGenerator;
import backend.academy.service.FractalUtil;
import backend.academy.singlethreading.SingleThreadGenerator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.RepeatedTest;
import static backend.academy.service.FractalUtil.RANDOM;
import static backend.academy.service.FractalUtil.getRandomTransformationList;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GeneratorsTest {

    private static final int repetitions = 50;

    private static int concurrentIsFasterTimes = 0;

    @AfterAll
    static void afterAll() {
        System.out.println("Concurrent is faster " + concurrentIsFasterTimes + " times");
        assertTrue(concurrentIsFasterTimes > repetitions / 2);
    }

    @RepeatedTest(repetitions)
    void shouldGenerateFractal() {
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
            concurrentIsFasterTimes++;
        }
    }
}

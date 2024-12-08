package backend.academy;

import backend.academy.core.data.image.Fractal;
import backend.academy.core.data.image.ImageSettings;
import backend.academy.core.data.variations.Variations;
import backend.academy.core.fractals.FractalRenderer;
import backend.academy.core.fractals.FractalRendererImpl;
import backend.academy.core.fractals.FractalUtil;
import backend.academy.core.multithreading.MultithreadingGenerator;
import backend.academy.core.singlethreading.SingleThreadGenerator;
import backend.academy.web.data.FractalCache;
import backend.academy.web.data.webDTO.GenerationProcess;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static backend.academy.core.fractals.FractalUtil.getRandomTransformationList;
import static backend.academy.core.fractals.FractalUtil.profileTime;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GeneratorsTest {

    @Mock
    private FractalCache fractalCache;

    @BeforeEach
    void setUp() {
        when(fractalCache.getProcess(any())).thenReturn(GenerationProcess.empty());
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
    void genTest(int height, int width, int startingPoints, int iterations) {
        ImageSettings settingsAsync =
            new ImageSettings(height, width,
                startingPoints, iterations, 1,
                getRandomTransformationList(Variations.values()),
                1.77, 2.2, true, true, true, true);
        ImageSettings settingsSlow =
            new ImageSettings(height, width,
                startingPoints, iterations, 1,
                getRandomTransformationList(Variations.values()),
                1.77, 2.2, true, true, true, false);
        SingleThreadGenerator singleGen = new SingleThreadGenerator(settingsSlow, fractalCache);
        MultithreadingGenerator concurrentGen = new MultithreadingGenerator(settingsAsync, fractalCache);
        FractalRenderer rendererSlow = new FractalRendererImpl(settingsSlow);
        FractalRenderer rendererAsync = new FractalRendererImpl(settingsAsync);
        Fractal fractalSlow = Fractal.of(settingsSlow.heightRes(), settingsSlow.widthRes(), 1.77);
        Fractal fractalAsync = Fractal.of(settingsAsync.heightRes(), settingsAsync.widthRes(), 1.77);

        Assertions.assertDoesNotThrow(() -> {
            Long timeForSingle = FractalUtil.profileTime(() -> {
                singleGen.generate(fractalSlow, "");
                return null;
            }, null);
            Long timeForConcurrent = FractalUtil.profileTime(() -> {
                concurrentGen.generate(fractalAsync, "");
                return null;
            }, null);
            long difference = timeForSingle - timeForConcurrent;

            System.out.println(settingsSlow);
            System.out.println("Difference: " + difference + "ms");
            System.out.println("\n\n");
        });

        Assertions.assertDoesNotThrow(() -> {
            Long single = profileTime(() -> {
                rendererSlow.postProcess(fractalSlow, "", fractalCache);
                return null;
            }, null);
            Long concurrent = profileTime(() -> {
                rendererAsync.postProcess(fractalAsync, "", fractalCache);
                return null;
            }, null);
            long differenceForPostProcessing = single - concurrent;

            System.out.println("Difference: " + differenceForPostProcessing + "ms");
            System.out.println("\n\n");
        });
    }
}

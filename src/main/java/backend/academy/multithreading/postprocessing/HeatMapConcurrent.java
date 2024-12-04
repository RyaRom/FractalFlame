package backend.academy.multithreading.postprocessing;

import backend.academy.data.image.Fractal;
import backend.academy.data.transformations.PostProcessing;
import backend.academy.singlethreading.postprocessing.HeatMap;
import java.util.stream.IntStream;

@SuppressWarnings("MagicNumber")
public class HeatMapConcurrent implements PostProcessing {
    @Override
    public void process(Fractal fractal) {
        IntStream.range(0, fractal.height() * fractal.width())
            .parallel()
            .forEach(i -> {
                int x = i / fractal.height();
                int y = i % fractal.height();
                HeatMap.processPoint(fractal, x, y);
            });
    }
}

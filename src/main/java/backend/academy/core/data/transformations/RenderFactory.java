package backend.academy.core.data.transformations;

import backend.academy.core.data.image.ImageSettings;
import backend.academy.core.multithreading.postprocessing.BlurCorrectionConcurrent;
import backend.academy.core.multithreading.postprocessing.GammaCorrectionConcurrent;
import backend.academy.core.multithreading.postprocessing.HeatMapConcurrent;
import backend.academy.core.singlethreading.postprocessing.BlurCorrection;
import backend.academy.core.singlethreading.postprocessing.GammaCorrection;
import backend.academy.core.singlethreading.postprocessing.HeatMap;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RenderFactory {
    private final ImageSettings imageSettings;

    public List<PostProcessing> renderFunctions(double gamma) {
        List<PostProcessing> functions = new ArrayList<>();
        if (imageSettings.gammaEnabled()) {
            if (imageSettings.isConcurrent()) {
                functions.add(new GammaCorrectionConcurrent(gamma));
            } else {
                functions.add(new GammaCorrection(gamma));
            }
        }


        if (imageSettings.isHeatMap()) {
            if (imageSettings.isConcurrent()) {
                functions.add(new HeatMapConcurrent());
            } else {
                functions.add(new HeatMap());
            }
        }

        if (imageSettings.isBlur()) {
            if (imageSettings.isConcurrent()) {
                functions.add(new BlurCorrectionConcurrent());
            } else {
                functions.add(new BlurCorrection());
            }
        }
        return functions;
    }
}

package backend.academy.data.transformations;

import backend.academy.data.image.ImageSettings;
import backend.academy.multithreading.postprocessing.BlurCorrectionConcurrent;
import backend.academy.multithreading.postprocessing.GammaCorrectionConcurrent;
import backend.academy.multithreading.postprocessing.HeatMapConcurrent;
import backend.academy.singlethreading.postprocessing.BlurCorrection;
import backend.academy.singlethreading.postprocessing.GammaCorrection;
import backend.academy.singlethreading.postprocessing.HeatMap;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RenderFactory {
    private final ImageSettings imageSettings;

    public List<PostProcessing> renderFunctions() {
        List<PostProcessing> functions = new ArrayList<>();
        if (imageSettings.gammaEnabled()) {
            if (imageSettings.isConcurrent()) {
                functions.add(new GammaCorrectionConcurrent());
            } else {
                functions.add(new GammaCorrection());
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

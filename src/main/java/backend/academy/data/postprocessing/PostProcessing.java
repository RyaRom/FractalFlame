package backend.academy.data.postprocessing;

import backend.academy.data.image.Frame;
import java.util.function.Consumer;

@FunctionalInterface
public interface PostProcessing extends Consumer<Frame> {
}

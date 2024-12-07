package backend.academy.data.webDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@AllArgsConstructor
@Builder
@Jacksonized
public class ImageSettingsDTO {
    private int width;

    private int height;

    private int points;

    private int iterations;

    private int symmetry;

    private double depth;

    private double gamma;

    // isGamma name produces weird lombok error (java: cannot find symbol: method isGamma())
    @JsonProperty("isGamma")
    private boolean gammaEnabled;

    private boolean isBlur;

    private boolean isConcurrent;

    private boolean isHeatMap;

    private List<IterativeFunctionDTO> functions;
}

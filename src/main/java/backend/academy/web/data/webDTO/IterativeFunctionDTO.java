package backend.academy.web.data.webDTO;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@AllArgsConstructor
@Builder
@Jacksonized
public class IterativeFunctionDTO {
    private double weight;

    private int[] rgb;

    private double[] affine;

    private List<VariationDTO> variations;
}

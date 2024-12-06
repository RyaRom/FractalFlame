package backend.academy.data;

import backend.academy.data.image.ImageSettings;
import backend.academy.data.image.RGB;
import backend.academy.data.transformations.AbstractTransformation;
import backend.academy.data.transformations.IterativeFunction;
import backend.academy.data.variations.AffineTransformation;
import backend.academy.data.variations.VariationFactory;
import backend.academy.data.variations.Variations;
import backend.academy.data.webDTO.GenerationProcess;
import backend.academy.data.webDTO.ImageSettingsDTO;
import backend.academy.data.webDTO.IterativeFunctionDTO;
import backend.academy.data.webDTO.ResponseDTO;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Mapper {
    public static ImageSettings mapToImageSettings(ImageSettingsDTO dto) {
        List<IterativeFunction> iterativeFunctions = dto.functions().stream()
            .map(func -> {
                try {
                    return Mapper.mapToIterativeFunction(func);
                } catch (Exception e) {
                    return new IterativeFunction(
                        new RGB(0, 0, 0),
                        new AffineTransformation(0, 0, 0, 0, 0, 0),
                        0.0);
                }
            })
            .collect(Collectors.toList());

        return new ImageSettings(
            dto.width(),
            dto.height(),
            dto.points(),
            dto.iterations(),
            dto.symmetry(),
            iterativeFunctions,
            dto.depth(),
            dto.gamma(),
            dto.gammaEnabled(),
            dto.isBlur(),
            dto.isHeatMap(),
            dto.isConcurrent()
        );
    }

    private static IterativeFunction mapToIterativeFunction(IterativeFunctionDTO dto) {
        RGB rgb = new RGB(dto.rgb()[0], dto.rgb()[1], dto.rgb()[2]);
        AffineTransformation affineTransformation = new AffineTransformation(
            dto.affine()[0], dto.affine()[1], dto.affine()[2],
            dto.affine()[3], dto.affine()[4], dto.affine()[5]
        );

        AbstractTransformation[] transformations = dto.variations().stream()
            .map(variation -> VariationFactory.getVariation(Variations.valueOf(variation.name().toUpperCase()),
                variation.weight()))
            .toArray(AbstractTransformation[]::new);

        return new IterativeFunction(rgb, affineTransformation, dto.weight(), transformations);
    }

    public static ResponseDTO toResponse(GenerationProcess generationProcess, String base64Image) {
        log.info("process: {}", generationProcess);
        return new ResponseDTO(
            generationProcess.shutdownTimeGen() - generationProcess.startTimeGen(),
            generationProcess.shutdownTimeRender() - generationProcess.startTimeRender(),
            generationProcess.threadsCount(),
            generationProcess.config(),
            base64Image
        );
    }
}

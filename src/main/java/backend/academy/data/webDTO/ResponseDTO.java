package backend.academy.data.webDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ResponseDTO {
    @JsonProperty("timeForGeneration")
    private Long timeForGeneration;

    @JsonProperty("timeForRender")
    private Long timeForRender;

    @JsonProperty("threadsCount")
    private Long threadsCount;

    @JsonProperty("config")
    private String config;

    @JsonProperty("base64Image")
    private String base64Image;
}

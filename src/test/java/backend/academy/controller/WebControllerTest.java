package backend.academy.controller;

import backend.academy.web.data.webDTO.ImageSettingsDTO;
import backend.academy.web.data.webDTO.IterativeFunctionDTO;
import backend.academy.web.data.webDTO.VariationDTO;
import backend.academy.core.fractals.FractalFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class WebControllerTest {

    private final String json = """
        {
          "functions": [
            {
              "weight": 0.42,
              "rgb": [112, 4, 113],
              "affine": [1.25, 0.7, 0.38, -0.29, 0.8, -0.93],
              "variations": [
                {"weight": 0.48326187062053594, "name": "SINUSOIDAL"},
                {"weight": 0.29236407247039636, "name": "SWIRL"},
                {"weight": 0.011136930173334407, "name": "HYPERBOLIC"},
                {"weight": 0.21323712673573345, "name": "SINUSOIDAL"}
              ]
            }
          ],
          "isGamma": true,
          "isBlur": true,
          "isConcurrent": false,
          "isHeatMap": false,
          "points": 1000,
          "iterations": 1000,
          "symmetry": 12,
          "height": 1500,
          "width": 1500,
          "depth": 1.77,
          "gamma": 2.2
        }
        """;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FractalFactory fractalFactory;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testJsonDeserialization() throws JsonProcessingException {
        ImageSettingsDTO imageSettings = objectMapper.readValue(json, ImageSettingsDTO.class);

        assertNotNull(imageSettings);
        assertEquals(1500, imageSettings.width());
        assertEquals(1500, imageSettings.height());
        assertEquals(1000, imageSettings.points());
        assertTrue(imageSettings.gammaEnabled());
        assertEquals(12, imageSettings.symmetry());
        assertEquals(1.77, imageSettings.depth());
        assertEquals(2.2, imageSettings.gamma());
        assertNotNull(imageSettings.functions());
        assertEquals(1, imageSettings.functions().size());
        IterativeFunctionDTO function = imageSettings.functions().getFirst();
        assertEquals(0.42, function.weight());
        assertArrayEquals(new int[] {112, 4, 113}, function.rgb());
        assertArrayEquals(new double[] {1.25, 0.7, 0.38, -0.29, 0.8, -0.93}, function.affine());
        assertNotNull(function.variations());
        assertEquals(4, function.variations().size());
        VariationDTO variation1 = function.variations().getFirst();
        assertEquals("SINUSOIDAL", variation1.name());
        assertEquals(0.48326187062053594, variation1.weight());
        VariationDTO variation2 = function.variations().get(1);
        assertEquals("SWIRL", variation2.name());
        assertEquals(0.29236407247039636, variation2.weight());
    }

    @Test
    void generateFractal() throws Exception {
        AtomicBoolean isStopped = new AtomicBoolean(false);

        when(fractalFactory.generator(any())).thenReturn(
            (fractal, id) -> {
                for (int i = 0; i < 10; i++) {
                    if (Thread.currentThread().isInterrupted()) {
                        isStopped.set(true);
                    }
                    System.out.println("Generating...  " + i);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        isStopped.set(true);
                        return;
                    }
                }
            }
        );

        when(fractalFactory.renderer(any())).thenReturn(
            (fractal, id, cache) -> {
                for (int i = 0; i < 10; i++) {
                    System.out.println("Rendering...  " + i);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        return;
                    }
                }
            }
        );

        var id = mockMvc.perform(
                post("/api/generate")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json)
            ).andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

        Thread.sleep(1000);

        mockMvc.perform(get("/api/progress/" + id))
            .andExpect(status().isOk());

        mockMvc.perform(delete("/api/stop/" + id))
            .andExpect(status().isOk());

        mockMvc.perform(get("/api/progress/" + id))
            .andExpect(status().is4xxClientError());
        mockMvc.perform(post("/api/render/" + id))
            .andExpect(status().is4xxClientError());

        isStopped.set(false);
        var idNew = mockMvc.perform(
                post("/api/generate")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json)
            ).andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

        Thread.sleep(2000);

        mockMvc.perform(get("/api/progress/" + idNew))
            .andExpect(status().isOk());

        mockMvc.perform(post("/api/render/" + idNew))
            .andExpect(status().isOk());
        assertTrue(isStopped.get());
    }

}

package backend.academy.controller;

import backend.academy.data.webDTO.ImageSettingsDTO;
import backend.academy.service.WebService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api")
public class WebController {
    private final WebService webService;

    @PostMapping("/generate")
    public ResponseEntity<String> test(@RequestBody ImageSettingsDTO imageSettingsDTO) {
        return ResponseEntity.ok(webService.startGeneration(imageSettingsDTO));
    }
}

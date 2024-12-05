package backend.academy.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequestMapping("/api")
public class TestController {
    @GetMapping("/test/{var}")
    public void test(@PathVariable String var) {
        log.info(var);
        log.warn(var);
        log.error(var);
    }
}

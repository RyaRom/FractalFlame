package backend.academy.data.webDTO;

import java.util.concurrent.Future;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * a dto class used for logging this
 *
 * <p>Опубликованы сравнительные результаты времени работы</p>
 * <p>Конфигурация системы</p>
 * <p> Время работы</p>
 * <p>Количество используемых потоков</p>
 */
@Data
@AllArgsConstructor
public class GenerationProcess {

    private Long startTimeRender;

    private Long shutdownTimeRender;

    private Long startTimeGen;

    private Long shutdownTimeGen;

    private Long threadsCount;

    private String config;

    private Future<?> genTask;

    private Future<?> renderTask;

    @SuppressWarnings("MagicNumber")
    public static GenerationProcess empty() {
        String config = "CPUs: "
            + Runtime.getRuntime().availableProcessors()
            + ", Free memory: "
            + Runtime.getRuntime().freeMemory() / (1024 * 1024) + "MB";

        return new GenerationProcess(
            0L, 0L,
            System.currentTimeMillis(),
            0L, 0L,
            config, null, null);
    }
}

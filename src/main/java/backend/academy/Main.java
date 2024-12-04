package backend.academy;

import backend.academy.singlethreading.Application;
import lombok.experimental.UtilityClass;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@UtilityClass
public class Main {
    public static void main(String[] args) {
        Application application = new Application();
        application.start();
    }
}

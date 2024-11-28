package backend.academy;

import backend.academy.singlethreading.Application;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {
    public static void main(String[] args) {
        Application application = new Application();
        application.start();
        System.exit(0);
    }
}

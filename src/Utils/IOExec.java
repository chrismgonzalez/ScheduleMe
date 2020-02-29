package Utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class IOExec {

    private static class IOExecSingleton {
        private static final ExecutorService INSTANCE = Executors.newSingleThreadScheduledExecutor();
    }

    private IOExec() {}

    public static ExecutorService getInstance() {
        return IOExecSingleton.INSTANCE;
    }
}


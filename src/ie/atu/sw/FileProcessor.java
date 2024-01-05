package ie.atu.sw;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Executors;



public class FileProcessor {

    public void processFile(String filename, LineProcessor processor) throws Exception {
        String file = filename + ".txt";
        try (var thread = Executors.newVirtualThreadPerTaskExecutor()) {
            Files.lines(Paths.get(file)).forEach(line -> thread.execute(() -> processor.process(line)));
        }
    }

    interface LineProcessor {
        void process(String line);
    }
}

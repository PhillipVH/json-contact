package za.ac.sun.cs.grammarworkshop;

import com.google.gson.Gson;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static za.ac.sun.cs.grammarworkshop.JsonContact.*;

public class JsonContactTest {
    @Test
    public void parseJSONContractTest() throws IOException {

        System.out.println();

        // Read the contract file
        File dir = new File("src/main/resources/rule/pos/rule");
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                System.out.println("Processing " + child.getAbsolutePath());
                List<String> content = Files.readAllLines(Paths.get(child.getAbsolutePath()));

                StringBuilder jsonSourceBuilder = new StringBuilder();
                for (String line : content) {
                    jsonSourceBuilder.append(line).append("\n");
                }

                JsonContact.parseJSONContact(jsonSourceBuilder.toString());

            }
        }

    }

}

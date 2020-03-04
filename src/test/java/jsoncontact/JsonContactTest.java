package jsoncontact;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class JsonContactTest {
    @Test
    public void parseJSONContractTest() throws IOException {

        // Read the contact file
        File dir = new File("src/main/resources/contacts");
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

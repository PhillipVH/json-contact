package jsoncontact;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;

@RunWith(Parameterized.class)
public class JsonContactTest {

    @Parameters()
    public static Object[] testFiles() {
        File testsDirectory = new File("src/main/resources/contacts");
        return testsDirectory.listFiles();
    }

    @Parameter
    public File testFile;

    @Test
    public void parseJSONContractTest() throws IOException {

        // Read the contact file
        System.out.println("Processing " + testFile.getName());
        List<String> content = Files.readAllLines(Paths.get(testFile.getAbsolutePath()));

        StringBuilder jsonSourceBuilder = new StringBuilder();
        for (String line : content) {
            jsonSourceBuilder.append(line).append("\n");
        }

        JsonContact.parseJSONContact(jsonSourceBuilder.toString());
    }
}

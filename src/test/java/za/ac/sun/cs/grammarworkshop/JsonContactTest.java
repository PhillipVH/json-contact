package za.ac.sun.cs.grammarworkshop;

import com.google.gson.Gson;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static za.ac.sun.cs.grammarworkshop.JsonContact.validateContact;

public class JsonContactTest {
    @Test
    public void parseJSONContract() throws IOException {

        System.out.println();
        Gson gson = new Gson();

        // Read the contract file
         List<String> content = Files.readAllLines(Paths.get("src/main/resources/entry.json"));

         StringBuilder jsonSourceBuilder = new StringBuilder();

         for (String line : content) {
             jsonSourceBuilder.append(line).append("\n");
         }

        // Parse the contact file
        Contact contact = gson.fromJson(jsonSourceBuilder.toString(), Contact.class);
        Address address = contact.address;
        Phone[] phoneNumbers = contact.phoneNumbers;

        // Validate the fields
        validateContact(contact);

        // Save to CSV
        String header = "FirstName,LastName,DoB,Email,Address.StreetName,Address.StreetNum,PhoneNumbers\n";
        StringBuilder csvBuilder = new StringBuilder(header);

        csvBuilder.append(contact.firstName)
                .append(',')
                .append(contact.lastName)
                .append(',')
                .append(contact.age)
                .append(',')
                .append(contact.dob)
                .append(',')
                .append(contact.email)
                .append(',')
                .append(address.streetName)
                .append(',')
                .append(address.streetNum)
                .append(',');

        for (Phone phone : phoneNumbers) {
            csvBuilder.append(phone.label).append(":").append(phone.number).append(",");
        }

        System.out.println(csvBuilder.toString());
    }

}

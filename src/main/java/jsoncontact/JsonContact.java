package jsoncontact;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Address {
    @SerializedName("street-name")
    public String streetName;
    @SerializedName("street-num")
    public int streetNum;
}

class Phone {
    public String label;
    public String number;

    @Override
    public String toString() {
        return label + ":" + number;
    }
}

class Contact {
    @SerializedName("first-name")
    public String firstName;
    @SerializedName("last-name")
    public String lastName;
    @SerializedName("phone-numbers")
    public Phone[] phoneNumbers;

    public int age;
    public String dob;
    public String email;
    public Address address;
}

public class JsonContact {

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static void validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        if (!matcher.find()) {
            throw new RuntimeException("Invalid email address.");
        }
    }

    private static void validatePhone(Phone phone) {
        if (phone.number.length() != 10) {
            throw new RuntimeException("Phone number must be 10 digits long.");
        }

        for (char digit : phone.number.toCharArray()) {
            if (!Character.isDigit(digit)) {
                throw new RuntimeException("Phone number can only contain numbers.");
            }
        }

    }

    public static void validateDateOfBirth(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date testDate;
        try {
            testDate = sdf.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException("Invalid date");
        }
        if (!sdf.format(testDate).equals(date)) {
            throw new RuntimeException("Invalid date");
        }
    }

    static void validateContact(Contact contact) {

        // Validate the age
        if (contact.age < 0) {
            throw new RuntimeException("Age is negative");
        }

        // Validate the length of phone numbers
        for (Phone phone : contact.phoneNumbers) {
            validatePhone(phone);
        }

        // Validate the email
        validateEmail(contact.email);

        // Validate the date of birth
        validateDateOfBirth(contact.dob);

    }

    public static String parseJSONContact(String content) {
        // Parse the contact file
        Gson gson = new Gson();
        Contact contact = gson.fromJson(content, Contact.class);
        Address address = contact.address;
        Phone[] phoneNumbers = contact.phoneNumbers;

        // Validate the fields
        validateContact(contact);

        // Save to CSV
        String header = "FirstName,LastName,DoB,Email,jsoncontact.Address.StreetName,jsoncontact.Address.StreetNum,PhoneNumbers\n";
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

        return csvBuilder.toString();
    }
}

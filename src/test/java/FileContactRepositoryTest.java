import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileContactRepositoryTest {

    @Test
    void testSaveAndLoadContacts() throws IOException {
        File tempFile = File.createTempFile("temp", ".txt");

        List<String> linesBefore = Files.readAllLines(tempFile.toPath());
        assertEquals(0, linesBefore.size());

        ContactRepository repository = new FileContactRepository(tempFile.getAbsolutePath());
        repository.addContact(new Contact("John Doe", "1234567890"));

        List<String> linesAfter = Files.readAllLines(tempFile.toPath());
        assertEquals(1, linesAfter.size());
        assertEquals("John Doe,1234567890", linesAfter.get(0));

        repository.loadContacts();
        ArrayList<Contact> contacts = repository.getContacts();

        assertEquals(1, contacts.size());
        assertEquals("John Doe", contacts.get(0).getName());
        assertEquals("1234567890", contacts.get(0).getPhone());

        tempFile.delete();
    }
}

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ContactModelTest {

    private ContactModel contactModel;

    @BeforeEach
    void setUp() {
        contactModel = new ContactModel(new MockContactRepository());
    }

    @Test
    void testAddContact() {
        contactModel.addContact(new Contact("John Doe", "1234567890"));
        assertEquals(1, contactModel.getContacts().size());
    }

    @Test
    void testRemoveContact() {
        contactModel.addContact(new Contact("John Doe", "1234567890"));
        assertEquals(1, contactModel.getContacts().size());

        contactModel.removeContact(0);
        assertEquals(0, contactModel.getContacts().size());
    }

    @Test
    void testEditContact() {
        contactModel.addContact(new Contact("John Doe", "1234567890"));
        assertEquals("John Doe", contactModel.getContacts().get(0).getName());

        contactModel.editContact(0, "Jane Doe", "9876543210");
        assertEquals("Jane Doe", contactModel.getContacts().get(0).getName());
        assertEquals("9876543210", contactModel.getContacts().get(0).getPhone());
    }

    static class MockContactRepository implements ContactRepository {
        private final ArrayList<Contact> contacts = new ArrayList<>();

        @Override
        public void addContact(Contact contact) {
            contacts.add(contact);
        }

        @Override
        public void removeContact(int index) {
            contacts.remove(index);
        }

        @Override
        public void editContact(int index, String newName, String newPhone) {
            contacts.get(index).setName(newName);
            contacts.get(index).setPhone(newPhone);
        }

        @Override
        public ArrayList<Contact> getContacts() {
            return contacts;
        }

        @Override
        public void saveContacts() {
        }

        @Override
        public void loadContacts() {

        }
    }
}
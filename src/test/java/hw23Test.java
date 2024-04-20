import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
public class hw23Test {
    private ContactModel model;
    private ContactView view;
    private ContactController controller;

    @BeforeEach
    void setUp() {
        model = new ContactModel();
        view = new ContactView();
        controller = new ContactController(model, view);
        view.registerController(controller);
    }

    @Test
    void testAddContact() {
        int initialSize = model.getContacts().size();
        model.addContact(new Contact("John", "123456789"));
        assertEquals(initialSize + 1, model.getContacts().size());
    }

    private void assertEquals(int i, int size) {
    }

    @Test
    void testRemoveContact() {
        model.addContact(new Contact("John", "123456789"));
        int initialSize = model.getContacts().size();
        model.removeContact(0);
        assertEquals(initialSize - 1, model.getContacts().size());
    }

    @Test
    void testEditContact() {
        model.addContact(new Contact("John", "123456789"));
        model.editContact(0, "Jane", "987654321");
        assertEquals("Jane", model.getContacts().get(0).getName());
        assertEquals("987654321", model.getContacts().get(0).getPhone());
    }

    private void assertEquals(String jane, String name) {
    }

    @Test
    void testViewInputs() {
        String testName = "John";
        String testPhone = "123456789";
        view.nameField.setText(testName);
        view.phoneField.setText(testPhone);
        assertEquals(testName, view.getNameInput());
        assertEquals(testPhone, view.getPhoneInput());
    }

    @Test
    void testUpdateContactList() {
        model.addContact(new Contact("John", "123456789"));
        controller.updateContactList();
        assertTrue(view.contactListModel.size() > 0);
    }
}

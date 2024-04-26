import java.util.ArrayList;

interface ContactRepository {
    void addContact(Contact contact);
    void removeContact(int index);
    void editContact(int index, String newName, String newPhone);
    ArrayList<Contact> getContacts();
    void saveContacts();
    void loadContacts();
}
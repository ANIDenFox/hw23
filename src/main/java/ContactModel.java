import java.util.ArrayList;

class ContactModel {
    private ContactRepository repository;

    public ContactModel(ContactRepository repository) {
        this.repository = repository;
        loadContacts();
    }

    public void addContact(Contact contact) {
        repository.addContact(contact);
    }

    public void removeContact(int index) {
        repository.removeContact(index);
    }

    public void editContact(int index, String newName, String newPhone) {
        repository.editContact(index, newName, newPhone);
    }

    public ArrayList<Contact> getContacts() {
        return repository.getContacts();
    }

    private void saveContacts() {
        repository.saveContacts();
    }

    private void loadContacts() {
        repository.loadContacts();
    }
}
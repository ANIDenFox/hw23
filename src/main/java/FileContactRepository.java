import java.io.*;
import java.util.ArrayList;

class FileContactRepository implements ContactRepository {
    private ArrayList<Contact> contacts;
    private final String fileName;

    public FileContactRepository(String fileName) {
        this.contacts = new ArrayList<>();
        this.fileName = fileName;
        if (!fileExists(fileName)) {
            saveContacts(); 
        } else {
            loadContacts();
        }
    }

    public void addContact(Contact contact) {
        if (!contacts.contains(contact)) {
            contacts.add(contact);
            saveContacts();
        }
    }

    public void removeContact(int index) {
        contacts.remove(index);
        saveContacts();
    }

    public void editContact(int index, String newName, String newPhone) {
        Contact contact = contacts.get(index);
        contact.setName(newName);
        contact.setPhone(newPhone);
        saveContacts();
    }

    public ArrayList<Contact> getContacts() {
        return contacts;
    }

    public void saveContacts() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for (Contact contact : contacts) {
                writer.println(contact.getName() + "," + contact.getPhone());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadContacts() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    Contact contact = new Contact(parts[0], parts[1]);
                    if (!contacts.contains(contact) && !contactExists(contact)) {
                        contacts.add(contact);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean contactExists(Contact contactToCheck) {
        for (Contact existingContact : contacts) {
            if (existingContact.getName().equals(contactToCheck.getName())
                    && existingContact.getPhone().equals(contactToCheck.getPhone())) {
                return true;
            }
        }
        return false;
    }


    private boolean fileExists(String fileName) {
        File file = new File(fileName);
        return file.exists();
    }
}

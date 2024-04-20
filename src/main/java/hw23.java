import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class hw23 {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ContactModel model = new ContactModel();
            ContactView view = new ContactView();
            ContactController controller = new ContactController(model, view);
            view.registerController(controller);
        });
    }
}

class ContactModel {
    private ArrayList<Contact> contacts;
    private static final String FILENAME = "contacts.txt";

    public ContactModel() {
        contacts = new ArrayList<>();
        loadContacts();
    }

    public void addContact(Contact contact) {
        contacts.add(contact);
        saveContacts();
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

    private void saveContacts() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILENAME))) {
            for (Contact contact : contacts) {
                writer.println(contact.getName() + "," + contact.getPhone());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadContacts() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    addContact(new Contact(parts[0], parts[1]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ContactView extends JFrame {
    JTextField nameField;
    JTextField phoneField;
    DefaultListModel<String> contactListModel;
    private JList<String> contactList;
    JButton addButton;
    JButton deleteButton;
    JButton editButton;

    public ContactView() {
        setTitle("Contact Manager");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(4, 2));
        nameField = new JTextField();
        phoneField = new JTextField();
        addButton = new JButton("Add Contact");
        deleteButton = new JButton("Delete Contact");
        editButton = new JButton("Edit Contact");
        contactListModel = new DefaultListModel<>();
        contactList = new JList<>(contactListModel);

        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Phone:"));
        panel.add(phoneField);
        panel.add(addButton);
        panel.add(deleteButton);
        panel.add(editButton);

        JScrollPane scrollPane = new JScrollPane(contactList);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panel, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    public void registerController(ContactController controller) {
        addButton.addActionListener(controller);
        deleteButton.addActionListener(controller);
        editButton.addActionListener(controller);
        contactList.addListSelectionListener(controller);
    }

    public String getNameInput() {
        return nameField.getText();
    }

    public String getPhoneInput() {
        return phoneField.getText();
    }

    public void setContactListModel(DefaultListModel<String> model) {
        contactListModel = model;
        contactList.setModel(contactListModel);
    }

    public int getSelectedContactIndex() {
        return contactList.getSelectedIndex();
    }
}

class ContactController implements ActionListener, ListSelectionListener {
    private ContactModel model;
    private ContactView view;

    public ContactController(ContactModel model, ContactView view) {
        this.model = model;
        this.view = view;
        updateContactList();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.addButton) {
            String name = view.getNameInput();
            String phone = view.getPhoneInput();
            model.addContact(new Contact(name, phone));
            updateContactList();
            clearFields();
        } else if (e.getSource() == view.deleteButton) {
            int selectedIndex = view.getSelectedContactIndex();
            if (selectedIndex != -1) {
                model.removeContact(selectedIndex);
                updateContactList();
            }
        } else if (e.getSource() == view.editButton) {
            int selectedIndex = view.getSelectedContactIndex();
            if (selectedIndex != -1) {
                String newName = view.getNameInput();
                String newPhone = view.getPhoneInput();
                Contact selectedContact = model.getContacts().get(selectedIndex);
                model.editContact(selectedIndex, newName, newPhone);
                updateContactList();
            }
        }
    }

    void updateContactList() {
        DefaultListModel<String> model = new DefaultListModel<>();
        for (Contact contact : this.model.getContacts()) {
            model.addElement(contact.getName() + " - " + contact.getPhone());
        }
        view.setContactListModel(model);
    }

    private void clearFields() {
        view.nameField.setText("");
        view.phoneField.setText("");
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

    }
}

class Contact {
    private String name;
    private String phone;

    public Contact(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
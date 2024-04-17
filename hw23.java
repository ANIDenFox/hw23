import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class hw23 extends JFrame {
    private JTextField nameField, phoneField;
    private DefaultListModel<String> contactListModel;
    private JList<String> contactList;
    private static ContactModel model;

    public hw23() {
        model = new ContactModel();

        setTitle("Contact Manager");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(3, 2));
        nameField = new JTextField();
        phoneField = new JTextField();
        JButton addButton = new JButton("Add Contact");
        JButton deleteButton = new JButton("Delete Contact");
        JButton editButton = new JButton("Edit Contact"); // Добавляем кнопку для редактирования контакта
        contactListModel = new DefaultListModel<>();
        contactList = new JList<>(contactListModel);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String phone = phoneField.getText();
                Contact newContact = new Contact(name, phone);
                model.addContact(newContact);
                updateContactList();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = contactList.getSelectedIndex();
                if (selectedIndex != -1) {
                    model.removeContact(selectedIndex);
                    updateContactList();
                }
            }
        });

        editButton.addActionListener(new ActionListener() { // Обработчик для кнопки "Edit Contact"
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = contactList.getSelectedIndex();
                if (selectedIndex != -1) {
                    String newName = nameField.getText();
                    String newPhone = phoneField.getText();
                    Contact editedContact = new Contact(newName, newPhone);
                    model.editContact(selectedIndex, editedContact);
                    updateContactList();
                }
            }
        });

        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Phone:"));
        panel.add(phoneField);
        panel.add(addButton);
        panel.add(deleteButton);
        panel.add(editButton); // Добавляем кнопку "Edit Contact" на панель

        JScrollPane scrollPane = new JScrollPane(contactList);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panel, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    public void updateContactList() {
        contactListModel.clear();
        for (Contact contact : model.getContacts()) {
            contactListModel.addElement(contact.getName() + " - " + contact.getPhone());
        }
    }

    private static void saveContacts() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("contacts.txt"))) {
            for (Contact contact : model.getContacts()) {
                writer.println(contact.getName() + "," + contact.getPhone());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadContacts() {
        try (BufferedReader reader = new BufferedReader(new FileReader("contacts.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    model.addContact(new Contact(parts[0], parts[1]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new hw23();
            }
        });
    }

    private static class ContactModel {
        private ArrayList<Contact> contacts;

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

        public void editContact(int index, Contact editedContact) { // Метод для редактирования контакта
            contacts.set(index, editedContact);
            saveContacts();
        }

        public ArrayList<Contact> getContacts() {
            return contacts;
        }
    }

    private static class Contact {
        private String name;
        private String phone;

        public Contact(String name, String phone) {
            this.name = name;
            this.phone = phone;
        }

        public String getName() {
            return name;
        }

        public String getPhone() {
            return phone;
        }
    }

    @Test
    void testContactModel() {
        ContactModel model = new ContactModel();
        Contact contact = new Contact("John Doe", "1234567890");

        model.addContact(contact);
        assertTrue(model.getContacts().contains(contact));

        model.removeContact(0);
        assertFalse(model.getContacts().contains(contact));
    }

    @Test
    void testContactView() {
        ContactModel model = new ContactModel();
        hw23 view = new hw23();

        view.nameField.setText("Jane Smith");
        view.phoneField.setText("9876543210");
        view.updateContactList();

        boolean containsJaneSmith = false;
        for (int i = 0; i < view.contactListModel.getSize(); i++) {
            if (view.contactListModel.getElementAt(i).equals("Jane Smith - 9876543210")) {
                containsJaneSmith = true;
                break;
            }
        }
        assertTrue(containsJaneSmith);

        model.addContact(new Contact("John Doe", "1234567890"));
        view.updateContactList();

        boolean containsJohnDoe = false;
        for (int i = 0; i < view.contactListModel.getSize(); i++) {
            if (view.contactListModel.getElementAt(i).equals("John Doe - 1234567890")) {
                containsJohnDoe = true;
                break;
            }
        }
        assertTrue(containsJohnDoe);
    }
}

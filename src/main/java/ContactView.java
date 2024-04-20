import javax.swing.*;
import java.awt.*;

class ContactView extends JFrame {
    JTextField nameField;
    JTextField phoneField;
    DefaultListModel<String> contactListModel;
    JList<String> contactList;
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

    public void showView() {
        setVisible(true);
    }
}
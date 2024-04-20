import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
                model.editContact(selectedIndex, newName, newPhone);
                updateContactList();
            }
        }
    }

    public void updateContactList() {
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

import javax.swing.*;
public class hw23 {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ContactRepository repository = new FileContactRepository("contacts.txt");
            ContactModel model = new ContactModel(repository);
            ContactView view = new ContactView();
            ContactController controller = new ContactController(model, view);
            view.registerController(controller);
            view.showView();
        });
    }
}
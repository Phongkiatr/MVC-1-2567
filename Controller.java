import java.awt.event.*;

public class Controller {
    private View view;
    private Model model;

    Controller(View view, Model model) {
        this.view = view;
        this.model = model;

        // Add ActionListener For somethingButton
        this.view.addButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /* Do Something */
            }
        });
        // Add ActionListener For ComboBox
        this.view.addComboBoxSelectionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /* Do Something */
            }
        });
    }
    public void doSomething01() {

    }
    public void doSomething02() {

    }

}

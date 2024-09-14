import java.awt.event.*;

public class Controller {
    private View view;
    private Model model;

    Controller(View view, Model model) {
        this.view = view;
        this.model = model;

        // Add ActionListener For StartButton
        this.view.addButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateProcess();
            }
        });
    }

    public void updateProcess() {
        model.startMilkingProcess();
        for (int i = 0; i < 10; i++) {
            view.setMachineLabelText(i, String.format(model.getMachineStatus(i)));
        }
        view.setAngryCowCountLabelText(String.format("%d", model.getAngryCowCount()));
        view.setCompleteCowCountLabelText(String.format("%d", model.getCompleteCowCount()));
        view.setMilkQuantityLabelText(String.format("%.2f", model.getMilkQuantity()));
        view.setReleaseCowCountLabelText(String.format("%d", model.getReleaseProblemCowCount()));
        view.setInterruptCountLabelText(String.format("%d", model.getInterruptCount()));

    }

}

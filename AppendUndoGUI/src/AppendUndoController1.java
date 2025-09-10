/**
 * Controller class.
 *
 *
 * @author Jeng Zhuang
 */
public final class AppendUndoController1 implements AppendUndoController {

    /**
     * Model object.
     */
    private final AppendUndoModel model;

    /**
     * View object.
     */
    private final AppendUndoView view;

    /**
     * Updates view to display model.
     *
     * @param model
     *            the model
     * @param view
     *            the view
     */
    private static void updateViewToMatchModel(AppendUndoModel model,
            AppendUndoView view) {
        /*
         * Get model info
         */
        String input = model.input();
        String output = model.output().top();
        /*
         * Update view to reflect changes in model
         */
        view.updateInputDisplay(input);
        view.updateOutputDisplay(output);
        view.updateUndoAllowed(model.output().length() > 1);
    }

    /**
     * Constructor; connects {@code this} to the model and view it coordinates.
     *
     * @param model
     *            model to connect to
     * @param view
     *            view to connect to
     */
    public AppendUndoController1(AppendUndoModel model, AppendUndoView view) {
        this.model = model;
        this.view = view;
        /*
         * Update view to reflect initial value of model
         */
        updateViewToMatchModel(this.model, this.view);
    }

    /**
     * Processes reset event.
     */
    @Override
    public void processResetEvent() {
        /*
         * Update model in response to this event
         */
        this.model.setInput("");
        this.model.output().clear();
        this.model.output().push(""); // Reset output stack

        /*
         * Update view to reflect changes in model
         */
        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processAppendEvent(String input) {
        this.model.setInput(input);
        String currentOutput = this.model.output().top();
        this.model.output().push(currentOutput + input);
        this.model.setInput("");
        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processUndoEvent() {
        if (this.model.output().length() > 1) { // Check if undo is possible
            String currentOutput = this.model.output().pop();
            String previousOutput = this.model.output().top();
            this.model.setInput(currentOutput.substring(previousOutput.length()));
            updateViewToMatchModel(this.model, this.view);
        }
    }

}

import components.stack.Stack;
import components.stack.Stack1L;

/**
 * Model class.
 *
 *
 * @author Jeng Zhuang
 */
public final class AppendUndoModel1 implements AppendUndoModel {

    /**
     * Model variables.
     */
    private String input;

    /**
     * Stack output.
     */
    private final Stack<String> output;

    /**
     * Default constructor.
     */
    public AppendUndoModel1() {
        /*
         * Initialize model; both variables start as empty strings
         */
        this.input = "";
        this.output = new Stack1L<>();
        this.output.push(""); // Initialize with empty output
    }

    @Override
    public String input() {
        return this.input;
    }

    @Override
    public void setInput(String input) {
        this.input = input;
    }

    @Override
    public Stack<String> output() {
        return this.output;
    }

}

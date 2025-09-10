import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;

/**
 * Controller class.
 *
 * @author Jeng Zhuang
 */
public final class NNCalcController1 implements NNCalcController {

    /**
     * Model object.
     */
    private final NNCalcModel model;

    /**
     * View object.
     */
    private final NNCalcView view;

    /**
     * Useful constants.
     */
    private static final NaturalNumber TWO = new NaturalNumber2(2),
            INT_LIMIT = new NaturalNumber2(Integer.MAX_VALUE);

    /**
     * Updates this.view to display this.model, and to allow only operations
     * that are legal given this.model.
     *
     * @param model
     *            the model
     * @param view
     *            the view
     * @ensures [view has been updated to be consistent with model]
     */
    private static void updateViewToMatchModel(NNCalcModel model, NNCalcView view) {

        // Update the natural values in the top display area
        view.updateTopDisplay(model.top());
        // Update the natural values in the bottom display area
        view.updateBottomDisplay(model.bottom());
        // Set the subtraction button status
        // (available when the bottom value is <= the top value)
        view.updateSubtractAllowed(model.bottom().compareTo(model.top()) <= 0);
        // Set the status of the division button
        // (available when the bottom value is not 0)
        view.updateDivideAllowed(!model.bottom().isZero());
        // Set the status of the exponentiation button
        // (available when the bottom value is <= the maximum integer value)
        view.updatePowerAllowed(model.bottom().compareTo(INT_LIMIT) <= 0);
        // Set the status of the root operation button
        // (available when the bottom value is >= 2 and <= the maximum integer value)
        view.updateRootAllowed(model.bottom().compareTo(TWO) >= 0
                && model.bottom().compareTo(INT_LIMIT) <= 0);

    }

    /**
     * Constructor.
     *
     * @param model
     *            model to connect to
     * @param view
     *            view to connect to
     */
    public NNCalcController1(NNCalcModel model, NNCalcView view) {
        this.model = model;
        this.view = view;
        updateViewToMatchModel(model, view);
    }

    @Override
    public void processClearEvent() {
        /*
         * Get alias to bottom from model
         */
        NaturalNumber bottom = this.model.bottom();
        /*
         * Update model in response to this event
         */
        bottom.clear();
        /*
         * Update view to reflect changes in model
         */
        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processSwapEvent() {
        /*
         * Get aliases to top and bottom from model
         */
        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();
        /*
         * Update model in response to this event
         */
        NaturalNumber temp = top.newInstance();
        temp.transferFrom(top);
        top.transferFrom(bottom);
        bottom.transferFrom(temp);
        /*
         * Update view to reflect changes in model
         */
        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processEnterEvent() {

        /*
         * Copy the bottom value to the top
         */
        this.model.top().copyFrom(this.model.bottom());
        /*
         * Update view to reflect changes in model
         */
        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processAddEvent() {

        /*
         * Perform addition operation: bottom = top + bottom
         */
        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();
        bottom.add(top); // Add the top value to the bottom
        top.clear(); // Clear the top
        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processSubtractEvent() {

        /*
         * Perform subtraction operation: bottom = top - bottom
         */
        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();
        top.subtract(bottom); // Subtract the top value to the bottom
        bottom.transferFrom(top);
        top.clear(); // Clear the top
        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processMultiplyEvent() {

        /*
         * Perform multiply operation: bottom = top * bottom
         */
        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();
        top.multiply(bottom); // multiply the top value to the bottom
        bottom.transferFrom(top);
        top.clear(); // Clear the top
        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processDivideEvent() {

        /*
         * Perform division operations：bottom = top / bottom，top = top % bottom
         */
        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();

        // Calculate the remainder
        NaturalNumber remainder = top.divide(bottom);

        // Exchange result: bottom stores the quotient, top stores the remainder
        NaturalNumber quotient = top.newInstance();
        quotient.transferFrom(top);
        bottom.transferFrom(quotient);
        top.transferFrom(remainder);

        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processPowerEvent() {

        /*
         * Perform exponentiation operations：bottom = top ^ bottom
         */
        NaturalNumber base = this.model.top();
        NaturalNumber exponent = this.model.bottom();

        // Raise base to the power of exponent
        base.power(exponent.toInt());

        // The result is stored in the bottom and the top is cleared
        this.model.bottom().transferFrom(base);
        base.clear();

        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processRootEvent() {

        /*
         * Perform root operations
         */
        NaturalNumber radicand = this.model.top();
        NaturalNumber degree = this.model.bottom();

        // Perform the root operation
        radicand.root(degree.toInt());

        // The result is stored in the bottom and the top is cleared
        this.model.bottom().transferFrom(radicand);
        radicand.clear();

        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processAddNewDigitEvent(int digit) {

        /*
         * Add a new number to the end (equivalent to multiplying by 10 and then
         * adding digit)
         */
        NaturalNumber bottom = this.model.bottom();
        bottom.multiplyBy10(1); // Shift one place to the left (decimal)
        bottom.add(new NaturalNumber2(digit)); // Add new numbers
        updateViewToMatchModel(this.model, this.view);

    }

}

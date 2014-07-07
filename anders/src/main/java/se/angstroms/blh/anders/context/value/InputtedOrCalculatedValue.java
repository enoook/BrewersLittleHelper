package se.angstroms.blh.anders.context.value;

import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import org.blh.core.unit.Unit;
import se.angstroms.blh.anders.formulas.ObservableFormula;

/**
 *
 * @author Thinner
 * @param <T>
 */
public class InputtedOrCalculatedValue<T extends Unit<?>> implements Value<T> {

    public static enum STATE {
        INVALID, INPUTTED, CALCULATED;
    }

    private final ReadOnlyObjectWrapper<STATE> state = new ReadOnlyObjectWrapper<>();
    private final ReadOnlyObjectWrapper<T> value = new ReadOnlyObjectWrapper<>();
    private final CalculatedValue<T> calculatedValue;
    private final InputtedValue<T> inputtedValue;

    public InputtedOrCalculatedValue() {
        this.state.set(STATE.INVALID);
        calculatedValue = new CalculatedValue<>();
        inputtedValue = new InputtedValue<>();
    }

	public InputtedOrCalculatedValue(ObservableFormula<T> formula) {
        inputtedValue = new InputtedValue<>();
		calculatedValue = new CalculatedValue<>(formula);

        this.state.set(STATE.CALCULATED);
        this.calculatedValue.addFormulaListener(this::calculateAndSetValue);
	}

	public InputtedOrCalculatedValue(T value, ObservableFormula<T> formula) {
        inputtedValue = new InputtedValue<>(value);
        calculatedValue = new CalculatedValue<>(formula);

        set(value);
	}

    public ReadOnlyObjectProperty<STATE> stateProperty() {
        return state.getReadOnlyProperty();
    }

    @Override
	public T get() {
		if (value.get() == null && this.state.get() == STATE.CALCULATED) {
			calculateAndSetValue(null);
		}
		return value.get();
	}

    private void calculateAndSetValue(Observable lol) {
        if (value.isBound()) {
            throw new RuntimeException(this + "'s value was bound.");
        }

        T newValue = this.calculatedValue.formulaProperty().get().calc();
        value.set(newValue);
    }

    public ReadOnlyObjectProperty<T> valueProperty() {
        return value.getReadOnlyProperty();
    }

    public final void set(T value) {
		assertValueNotNull(value);

        if (state.get() != STATE.INPUTTED) {
            this.calculatedValue.removeFormulaListener(this::calculateAndSetValue);
            this.inputtedValue.set(value);
            this.state.set(STATE.INPUTTED);
            this.value.bind(this.inputtedValue);
            System.out.println("Bound " + this);
        } else {
            this.inputtedValue.set(value);
        }
    }

	private void assertValueNotNull(T value) throws NullPointerException {
		if (value == null) {
			throw new NullPointerException("Cannot set value to null");
		}
	}

    public void enterCalculatedState() {
        if (this.calculatedValue.formulaProperty().get() == null) {
            throw new IllegalStateException("Cannot enter calculated state without a formula");
        }

        doEnterCalculatedState();
        calculateAndSetValue(null);
    }

    private void doEnterCalculatedState() {
        this.value.unbind();
        this.state.set(STATE.CALCULATED);
        this.calculatedValue.addFormulaListener(this::calculateAndSetValue);
    }

    /**
     * Changes the formula that is used to calculate the value. This method
     * puts the object in the CALCULATED state, but doesn't update the value.
     * @param formula
     */
    public void setFormula(ObservableFormula<T> formula) {
        this.calculatedValue.formulaProperty().set(formula);
        doEnterCalculatedState();
    }

    public ReadOnlyObjectProperty<ObservableFormula<T>> formulaProperty() {
        return this.calculatedValue.formulaProperty();
    }
}
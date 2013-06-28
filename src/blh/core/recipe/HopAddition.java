package blh.core.recipe;

import blh.core.ingredients.Hop;
import blh.core.units.time.Minutes;
import blh.core.units.weight.Grams;

/**
 *
 * @author thinner
 */
public class HopAddition {

    private Hop hop;
    private Minutes timeInBoil;
    private Grams amount;

    public HopAddition(Hop hop, Minutes timeInBoil, Grams amount) {
        this.hop = hop;
        this.timeInBoil = timeInBoil;
        this.amount = amount;
    }

    public Hop getHop() {
        return hop;
    }

    public Minutes getTimeInBoil() {
        return timeInBoil;
    }

    public Grams getAmount() {
        return amount;
    }
}
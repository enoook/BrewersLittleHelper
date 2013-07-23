package org.blh.core.units.color;

import org.blh.core.units.Unit;
import org.blh.core.units.weight.Lbs;

/**
 *
 * @author thinner
 */
public class ColorPotential extends Unit<Double> {

    public ColorPotential() {
        super(0d);
    }

    public ColorPotential(Lovibond color, Lbs amount) {
        super(color.value() * amount.value());
    }

    public void add(Lovibond color, Lbs amount) {
        value += color.value() * amount.value();
    }
}
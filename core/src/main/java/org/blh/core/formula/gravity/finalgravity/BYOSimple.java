package org.blh.core.formula.gravity.finalgravity;

import org.blh.core.unit.Factor;
import org.blh.core.unit.gravity.GravityPoints;
import org.blh.core.unit.gravity.SpecificGravity;

/**
 * Taken from http://byo.com/component/k2/item/1658-write-your-own-brewing-spreadsheet?Itemid=398
 *
 * FG = OG (1 - AA)
 *   where AA is the yeasts' apparent attenuation
 *   OG and FG are in GP
 * 
 * @author thinner
 */
public class BYOSimple  {

    public SpecificGravity calc(SpecificGravity og, Factor yeastApparentAttenuation) {
        return calc(new GravityPoints(og), yeastApparentAttenuation);
    }

    public SpecificGravity calc(GravityPoints og, Factor yeastApparentAttenuation) {
        return new GravityPoints(og.value() * (1 - yeastApparentAttenuation.value())).toSpecificGravity();
    }
    
}

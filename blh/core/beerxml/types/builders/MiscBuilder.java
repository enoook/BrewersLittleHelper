package blh.core.beerxml.types.builders;

import blh.core.beerxml.types.Misc;
import blh.core.beerxml.types.Misc.TYPE;
import blh.core.beerxml.types.Misc.USE;
import blh.core.units.time.Minutes;

public class MiscBuilder implements Builder<Misc> {

    private String name;
    private TYPE type;
    private USE use;
    private Minutes time;
    private double amount;
    private boolean amountIsWeight;
    private String useFor;
    private String notes;

    public MiscBuilder() {
    }

    public MiscBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public MiscBuilder setType(TYPE type) {
        this.type = type;
        return this;
    }

    public MiscBuilder setUse(USE use) {
        this.use = use;
        return this;
    }

    public MiscBuilder setTime(Minutes time) {
        this.time = time;
        return this;
    }

    public MiscBuilder setAmount(double amount) {
        this.amount = amount;
        return this;
    }

    public MiscBuilder setAmountIsWeight(boolean amountIsWeight) {
        this.amountIsWeight = amountIsWeight;
        return this;
    }

    public MiscBuilder setUseFor(String useFor) {
        this.useFor = useFor;
        return this;
    }

    public MiscBuilder setNotes(String notes) {
        this.notes = notes;
        return this;
    }

    @Override
    public MiscBuilder set(String tagName, String value) {
        switch (tagName.toUpperCase()) {
            case "NAME":
                name = value;
                break;
            case "TYPE":
                type = Misc.TYPE.valueOf(value.toUpperCase());
                break;
            case "USE":
                use = Misc.USE.valueOf(value.toUpperCase());
                break;
            case "TIME":
                time = new Minutes(Integer.parseInt(value));
                break;
            case "AMOUNT":
                amount = Double.parseDouble(value);
                break;
            case "AMOUNT_IS_WEIGHT":
                amountIsWeight = Boolean.parseBoolean(value);
                break;
            case "USE_FOR":
                useFor = value;
                break;
            case "NOTES":
                notes = value;
                break;
            default:
                System.out.println("Unknown misc value: " + tagName);
                break;
        }

        return this;
    }

    @Override
    public Misc create() {
        return new Misc(name, type, use, time, amount, amountIsWeight, useFor, notes);
    }
}

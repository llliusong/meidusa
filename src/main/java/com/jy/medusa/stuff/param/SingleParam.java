package com.jy.medusa.stuff.param;

public class SingleParam extends BaseComplexParam {

    SingleParam(String column, Object value) {
        this.setColumn(column);
        this.setValue(value);
    }

    Object value;

    public Object getValue() {
        return value;
    }

/*    public SingleParam setValue(String value) {
        this.value = value;
        return this;
    }*/

    public void setValue(Object value) {
        this.value = value;
    }
}
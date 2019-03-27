package com.nikulitsa.springsandbox.modules.inheritance.entities;

/**
 * @author Sergey Nikulitsa
 */
public enum FigureType {

    BEGIN(Values.BEGIN),
    ACTION(Values.ACTION),
    DATA(Values.DATA),
    CONDITION(Values.CONDITION),
    LINK(Values.LINK);

    private final String value;

    FigureType(String value) {
        this.value = value;
    }

    /**
     * Строковые представления типов.
     */
    public static class Values {
        public static final String BEGIN = "BEGIN";
        public static final String ACTION = "ACTION";
        public static final String DATA = "DATA";
        public static final String CONDITION = "CONDITION";
        public static final String LINK = "LINK";
    }
}

package com.example.abclogic;

/**
 * Created by Chris on 09/06/2016.
 */
public enum CellStates {
    A("A"),
    B("B"),
    C("C"),
    BLANK("*"),
    EMPTY("");

    private CellStates n;

    private String text;

    static {
        A.n = B;
        B.n = C;
        C.n = BLANK;
        BLANK.n = EMPTY;
        EMPTY.n = A;
    }

    CellStates(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    public CellStates next() {
        return this.n;
    }

    public static CellStates getEnum(String value) {
        for (CellStates cs : values()) {
            if (cs.toString().equals(value)) {
                return cs;
            }
        }
        return null;
    }
}

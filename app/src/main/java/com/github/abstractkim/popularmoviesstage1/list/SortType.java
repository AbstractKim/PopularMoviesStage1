package com.github.abstractkim.popularmoviesstage1.list;

public enum SortType {
    MOST_POPULAR(0), HIGST_RATED(2);
    private final int value;
    SortType(int value)
    {
        this.value = value;
    }
    public int getValue()
    {
        return value;
    }
}

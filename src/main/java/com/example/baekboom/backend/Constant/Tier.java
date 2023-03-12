package com.example.baekboom.backend.Constant;

public enum Tier {
    Bronze5(0),
    Bronze4(1),
    Bronze3(2),
    Bronze2(3),
    Bronze1(4),
    Silver5(5),
    Silver4(6),
    Silver3(7),
    Silver2(8),
    Silver1(9),
    Gold5(10),
    Gold4(11),
    Gold3(12),
    Gold2(13),
    Gold1(14);

    private final int index;

    private Tier(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}

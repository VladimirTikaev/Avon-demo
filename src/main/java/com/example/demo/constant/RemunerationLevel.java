package com.example.demo.constant;

public enum RemunerationLevel {
    K1(1),
    K2(2),
    K3(3),
    K4(4),
    L1(5),
    L2(6),
    L3(7),
    L4(8),
    D1(9),
    D2(10),
    D3(11),
    D4(12);

    public final int intValue;

    RemunerationLevel(int intValue) {
        this.intValue = intValue;
    }

    public boolean isCoordinator(){
        return name().equals(K1.name()) || name().equals(K2.name()) || name().equals(K3.name()) || name().equals(K4.name());
    };

    public boolean isLeader(){
        return name().equals(L1.name()) || name().equals(L2.name()) || name().equals(L3.name()) || name().equals(L4.name());
    };

    public boolean isDirector(){
        return name().equals(D1.name()) || name().equals(D2.name()) || name().equals(D3.name()) || name().equals(D4.name());
    };
}

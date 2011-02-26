package com.cianmcgovern.simpleprotect;

public class PlayerInfo {
    private int[] c1 = {0,0,0};
    private int[] c2 = {0,0,0};
    private String name;
    
    public PlayerInfo(String name) {
        this.name = name;
    }

    public int[] getC1() {
        return c1;
    }

    public void setC1(int[] c1) {
        this.c1 = c1;
    }

    public int[] getC2() {
        return c2;
    }

    public void setC2(int[] c2) {
        this.c2 = c2;
    }
    
    public String getName() {
        return name;
    }


}

package com.cianmcgovern.simpleprotect;

import java.io.Serializable;
import java.util.ArrayList;

public class ProtectedArea implements Serializable {
    private ArrayList<String> allowedPlayers = new ArrayList<String>();
    private int[] c1;
    private int[] c2;
    
    public ProtectedArea(int[] c1, int[] c2, String initialPlayer) {
        allowedPlayers.add(initialPlayer);
        this.c1 = c1;
        this.c2 = c2;
    }
    
    public void addPlayer(String playername) {
        allowedPlayers.add(playername);
    }
    
    public boolean removePlayer(String playername) {
        if (allowedPlayers.contains(playername)) {
            allowedPlayers.remove(playername);
            return true;
        }
        return false;
    }
    
    public ArrayList<String> getAllowedPlayers() {
        return allowedPlayers;
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
    
    public boolean contains(int[] c) {
        return cOff(c,0);
    }
    
    public boolean cOff(int[] c, int offset) {
        int[] hc = {c1[0] > c2[0] ? c1[0]+offset : c2[0],c1[1] > c2[1] ? c1[1]+offset : c2[1]+offset,c1[2] > c2[2] ? c1[2]+offset : c2[2]+offset};
        int[] lc = {c1[0] < c2[0] ? c1[0]-offset : c2[0]-offset,c1[1] < c2[1] ? c1[1]-offset : c2[1]-offset,c1[2] < c2[2] ? c1[2]-offset : c2[2]-offset};
        if (c[0] <= hc[0] && c[0] >= lc[0]) //Three ifs looks cleaner than combining em.
            if (c[1] <= hc[1] && c[1] >= lc[1])
                if (c[2] <= hc[2] && c[2] >= lc[2])
                    return true;
        return false;        
    }
    
}

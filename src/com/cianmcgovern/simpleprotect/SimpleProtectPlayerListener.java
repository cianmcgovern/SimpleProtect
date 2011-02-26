package com.cianmcgovern.simpleprotect;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import org.bukkit.event.player.PlayerListener;

/**
 * Handle events for all Player related events
 * @author Philip Daian
 */

public class SimpleProtectPlayerListener extends PlayerListener {
    private final SimpleProtect plugin;
    public final static int[] ZERO = {0,0,0};
    public static ArrayList<PlayerInfo> pMode = new ArrayList<PlayerInfo>();
    public static ArrayList<ProtectedArea> areas = new ArrayList<ProtectedArea>();

    public SimpleProtectPlayerListener(SimpleProtect instance) {
        plugin = instance;
        load();
    }
    

    static PlayerInfo findProtecting(String name) {
        for (PlayerInfo p : pMode) {
            if (name.equals(p.getName())) {
                return p;
            }
        }
        return null;
    }

    static String arrayToString(int[] arr) {
        String out = "";
        for (int i = 0; i < arr.length; i++) {
            out = out + (i == arr.length - 1 ? arr[i] : arr[i] + ", ");
        }
        return out;
    }

    static String arrayToString(ArrayList<String> arr) {
        String out = "";
        for (int i = 0; i < arr.size(); i++) {
            out = out + (i == arr.size() - 1 ? arr.get(i) : arr.get(i) + ", ");
        }
        return out;
    }

    public static ArrayList<String> getProtectionOffset(int[] coords, int offset) {
        for (int i = 0; i < areas.size(); i++) {
            if (areas.get(i).cOff(coords, offset)) {
                return areas.get(i).getAllowedPlayers();
            }
        }
        return null;
    }
    public static ArrayList<String> getProtection(int[] coords) {
        return getProtectionOffset(coords,0);
    }
       
    
    

    private void load() {
        try {
            FileInputStream fin = new FileInputStream("protect.dat");
            ObjectInputStream ois = new ObjectInputStream(fin);
            this.areas = (ArrayList<ProtectedArea>) ois.readObject();
            ois.close();
        } catch (Exception e) {
        }
    }

    public static void save() {
        try {
            FileOutputStream fout = new FileOutputStream("protect.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(areas);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
   
}

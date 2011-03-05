package com.cianmcgovern.simpleprotect;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import org.bukkit.event.player.PlayerListener;

/**
 * Handle events for all Player related events
 * @author Philip Daian, Cian Mc Govern
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
       
    
    

    public static void load() {
    	
    	String dir = "plugins/SimpleProtect";
    	boolean success = (new File(dir)).exists();
    		if (success==false) {
    			new File(dir).mkdir();
    			
    	}
    	
    	File in = new File("plugins/SimpleProtect/protect.dat");
    	if(in.exists()!=true){
    		try {
    		System.out.println("SimpleProtect: No protect.dat file found, creating blank default now!!");
    		in.createNewFile();
    		}
    		catch (IOException e){
    			System.out.println("SimpleProtect: Error creating protect.dat file!!");
    			e.printStackTrace();
    		}
    	}
        try {
            FileInputStream fin = new FileInputStream("plugins/SimpleProtect/protect.dat");
            if(in.length()>0){
            	ObjectInputStream ois = new ObjectInputStream(fin);
            	areas = (ArrayList<ProtectedArea>) ois.readObject();
            	ois.close();
            }
        } catch (Exception e) {
        	System.out.println("SimpleProtect: Error loading protect.dat!");
        	e.printStackTrace();
        }
    }

    public static void save() {
        try {
            FileOutputStream fout = new FileOutputStream("plugins/SimpleProtect/protect.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(areas);
            oos.close();
        } catch (Exception e) {
            System.out.println("SimpleProtect: Error saving to protect.dat!");
        }
    }
    
   
}

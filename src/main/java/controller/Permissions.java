package controller;

import java.util.ArrayList;
import java.util.HashMap;

public class Permissions {

    public static int ADD_ACTIVITY = 1;
    public static int DELETE_ACTIVITY = 2;
    public static int UPDATE_ACTIVITY = 3;

    private static Permissions instance = null;
    public HashMap<String,ArrayList<Integer>> permissions = new HashMap<>();

    public Permissions(){
        ArrayList<Integer> admin = new ArrayList<>();
        admin.add(ADD_ACTIVITY);
        admin.add(DELETE_ACTIVITY);
        admin.add(UPDATE_ACTIVITY);

        ArrayList<Integer> monitor = new ArrayList<>();
        ArrayList<Integer> user = new ArrayList<>();

        permissions.put("ADMIN",admin);
        permissions.put("MONITOR",monitor);
        permissions.put("USER",user);
    }

    public static Permissions getInstance(){
        if(instance == null) {
            instance = new Permissions();
        }
        return instance;
    }

    public boolean hasPermision(String rol, int permision){

        ArrayList<Integer> perm =permissions.get(rol);

        System.out.println("======PERMISIONS: ROLE "+ rol);

        if(perm == null) {
            System.out.println("======PERMISIONS: FALSE");
            return false;
        }


        for(Integer p:perm){
            System.out.println("======PERMISIONS: " + p);
        }

        return perm.contains(permision);

    }



}

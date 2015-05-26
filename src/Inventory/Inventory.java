package Inventory;

import Items.Item;

import java.util.LinkedList;

/**
 * Created by Kevin on 5/25/2015.
 * Inventory class
 */
public class Inventory {

    LinkedList<Item> list;
    StarterTemplate starter;

    public Inventory(String role){
        switch(role.toLowerCase()){
            case "mage":
                starter = new MageStarter();
                list = starter.getItems();
        }
    }

    public LinkedList<Item> getList(){
        return list;
    }
}

package Inventory;

import Items.Item;

import java.util.LinkedList;

/**
 * Created by Kevin on 5/25/2015.
 * template for starter item set classes
 */
public abstract class StarterTemplate {

    LinkedList<Item> list;

    public StarterTemplate(){
        list = new LinkedList<>();
    }

    public LinkedList<Item> getItems(){
        return list;
    }
}

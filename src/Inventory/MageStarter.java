package Inventory;

import Items.Cloak;
import Items.Ring;
import Items.Wand;

/**
 * Created by Kevin on 5/25/2015.
 * Mage Starter Items
 */
public class MageStarter extends StarterTemplate {

    public MageStarter(){
        super();
        list.add(new Wand("White Birch Wand", "A simple magic birch wand, deals minor damage.", 5));
        list.add(new Cloak("Brown Cloak", "A rough cloth cloak.", 2));
        list.add(new Ring("Silver Ring", "A silver ring with mystic runes writ upon its surface"));
    }

}

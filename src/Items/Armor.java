package Items;

/**
 * Created by Kevin on 5/25/2015.
 * generic armor class
 */
public class Armor extends Item{
    int armor;

    public Armor(String name, String desc, int a){
        super(name, desc);
        armor = a;
    }

    public int getArmor(){
        return armor;
    }
}

package Items;

/**
 * Created by Kevin on 5/25/2015.
 * generic weapon class
 */
public class Weapon extends Item {
    int damage;

    public Weapon(String name, String desc, int d){
        super(name, desc);
        damage = d;
    }

    public int getDamage(){
        return damage;
    }
}

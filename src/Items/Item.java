package Items;

/**
 * Created by Kevin on 5/25/2015.
 * generic item class from which all items are formed
 */
public abstract class Item {
    String name;
    String description;

    public Item(String n, String desc){
        name = n;
        description = desc;
    }

    public String getDesc(){
        return description;
    }

    public String getName(){
        return name;
    }

    public void setDesc(String desc){
        description = desc;
    }
}

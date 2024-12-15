// create a java class
package src.GameObjects;
import src.Mechanics.Direction;
import src.Mechanics.PositionPoint;


public class Hero extends Entity {
    int lives; 
    Inventory inventory;  //(Bag containing Enchantments)
    PositionPoint position;  //(Grid coordinates)
    boolean  ProtectionStatus;  //(Indicates if thecloak of protection is active)
    public Hero(){
        super(new PositionPoint(0,0), null);
        this.lives = 3;
        this.inventory = new Inventory();
        this.position = new PositionPoint(0,0);
        this.ProtectionStatus = false;
    }
    public Hero(PositionPoint position, Hall hall) {
        super(position, hall);
        // TODO: should have input as PositionPoint position to set the initial position of the player
        this.lives = 3;
        this.inventory = new Inventory();
        //this.position = position;
        this.ProtectionStatus = false;
    }

    public boolean checkProtection(){
        return this.ProtectionStatus;
    }
    public void activateProtection(){
        this.ProtectionStatus = true;
    }
    public void deactivateProtection(){
        this.ProtectionStatus = false;
    }
    public void updateLifeCount(int life){
        this.lives += life;
    }
    public void setLifeCount(int life){
        this.lives = life;
    }
    public int getLives() {
        return this.lives;
    }
    //public void calculateDamage(){
        
    //}
    public void movePlayer(Direction.DirectionEnum direction){
        this.position = this.position.move(direction);
    }
    public void collectEnchantment(Enchantment enchantment){
        if (enchantment.isStorable){
            this.inventory.add(enchantment);
        }
        else{
            enchantment.applyEffect();
        }
    }
    public boolean throwEnchantment(Direction direction) {
        // Throw luring gem enchantment
        // for (Enchantment enchantment : this.inventory.getItems()) {
        //     if (enchantment instanceof LuringGemEnchantment luringGem) { // Pattern Matching for instanceof (Java 16+)
        //         this.inventory.remove(enchantment);
        //         luringGem.applyEffect(direction); // Call the specific method for LuringGemEnchantment
        //         return true;
        //     }
        return false;
    }
    // get position of player
    public PositionPoint getPosition(){
        return this.position;
    }
}

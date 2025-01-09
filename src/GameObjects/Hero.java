// create a java class
package src.GameObjects;
import src.GameController.GameModeController;
import src.Mechanics.Direction;
import src.Mechanics.PositionPoint;


public class Hero extends Entity {
    int lives; 
    Inventory inventory;  //(Bag containing Enchantments)
    boolean  ProtectionStatus;  //(Indicates if thecloak of protection is active)
    boolean FoolingStatus;
    private Direction lureDirection;
    transient private LifeCountListener listener;

    public Hero(PositionPoint heroPosition, Hall hall) {
        super(heroPosition, hall);
        String mode = GameModeController.getInstance().getGameMode();
        if (mode.equals("easy")) {
            this.lives = 5;
        } else if (mode.equals("hard")) {
            this.lives = 3;
        }
        this.inventory = new Inventory();
        this.ProtectionStatus = false;
        this.FoolingStatus = false;
        lureDirection = null;
    }

    public Direction getLureDirection() {
        return lureDirection;
    }

    public void setLureDirection(Direction lureDirection) {
        this.lureDirection = lureDirection;
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

    public boolean checkFooling(){
        return this.FoolingStatus;
    }
    public void activateFooling(){
        this.FoolingStatus = true;
    }
    public void deactivateFooling(){
        this.FoolingStatus = false;
    }

    public void increaseLifeCount(){
        this.lives += 1;
        notifyLifeChange();
    }
    public void decreaseLifeCount(){
        this.lives -= 1;
        notifyLifeChange();
        System.out.println("Life count decreased t "+this.lives);
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
    public void setPositon(PositionPoint position){
        this.position = position;
    }

    public Inventory getInventory() {
return this.inventory;    }


    public interface LifeCountListener {
        void onLifeChanged(int lives);
    }
    
    public synchronized void setLifeCountListener(LifeCountListener listener) {
        this.listener = listener;
    }

    private void notifyLifeChange() {
        if (listener != null) {
            listener.onLifeChanged(lives);
        }
    }
}

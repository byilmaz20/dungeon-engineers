package src.GameObjects;
import java.io.Serializable;
import java.util.Random;
import src.GameController.EnchantmentTimeController;
import src.GameController.RevealTimeController;
import src.Mechanics.GridEnvironment;
import src.Mechanics.PositionPoint;
import src.Mechanics.Timer;
import src.UI.PlayModeScreen;

public class RevealEnchantment extends Enchantment implements Serializable{
    private EnchantmentTimeController enchantmentTimeController;
    private RevealTimeController revealTimeController;
    transient private Timer revealUseTimer;
    transient private Timer enchantmentTimer;
    private GridEnvironment grid;


    public RevealEnchantment(PositionPoint position, Hall hall, GridEnvironment grid) {
        super(position, hall);
        this.type = EnchantmentTypes.REVEAL_ENCHANTMENT;
        this.isStorable = false;
        this.enchantmentTimeController = new EnchantmentTimeController(grid, this);
        this.grid = grid;
        enchantmentTimeController.startTimeController();
        this.revealTimeController = new RevealTimeController(grid, null, null);
        enchantmentTimer = enchantmentTimeController.getTimer();
    } 


    public void applyEffect(PlayModeScreen playModeScreen) {
        // Get the top-left corner of the 4x4 square
        PositionPoint topLeft = getSquareTopLeft(grid);

        // Call PlayModeScreen to apply the red tint
        playModeScreen.applyRedTint(topLeft, true);
        this.revealTimeController = new RevealTimeController(grid, playModeScreen, topLeft);
        revealTimeController.startTimeController();
        revealUseTimer = revealTimeController.getTimer();

        // Optionally, you can log this action for debugging
        System.out.println("Applied red tint to square starting at: (" + topLeft.x + ", " + topLeft.y + ")");
    }

    
    public static PositionPoint getRandomIndex() {
        Random random = new Random();
        int row = random.nextInt(4); // Generate a random number between 0 and 3 for the row
        int col = random.nextInt(4); // Generate a random number between 0 and 3 for the column

        return new PositionPoint(row, col); // Return as a PositionPoint object
    }
    
    public static PositionPoint getSquareTopLeft(GridEnvironment grid) {
    // Get the rune's position
    PositionPoint runePosition = grid.getRune().getPosition();

    // Get the random index using the provided getRandomIndex() method
    PositionPoint randomIndex = getRandomIndex();

    // Calculate the initial top-left corner of the 4x4 square
    int topLeftX = runePosition.x - randomIndex.x;
    int topLeftY = runePosition.y - randomIndex.y;

    // Ensure all 16 squares of the 4x4 matrix fit within the grid boundaries
    // Adjust for top-left boundary overflow (left or top edges)
    if (topLeftX < 0) {
        topLeftX = 0; // Shift to the right
    }
    if (topLeftY < 0) {
        topLeftY = 0; // Shift downward
    }

    // Adjust for bottom-right boundary overflow (right or bottom edges)
    if (topLeftX + 3 > 24) { // Maximum X index is 24
        int overflowX = (topLeftX + 3) - 24; // Calculate how much it overflows
        topLeftX -= overflowX; // Shift the square left by the overflow amount
    }
    if (topLeftY + 3 > 24) { // Maximum Y index is 24
        int overflowY = (topLeftY + 3) - 24; // Calculate how much it overflows
        topLeftY -= overflowY; // Shift the square up by the overflow amount
    }

    // Return the adjusted top-left corner of the 4x4 square
    return new PositionPoint(topLeftX, topLeftY);
}
}
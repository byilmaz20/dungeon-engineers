package src.GameController;

import src.GameObjects.Hall;
import src.GameObjects.HallTypes;
import src.GameObjects.Monster;
import src.Mechanics.GridEnvironment;
import src.Mechanics.PositionPoint;

public class SpawnMonsterController {
    private GridEnvironment grid;

    public SpawnMonsterController(GridEnvironment grid) {
        this.grid = grid; // Initialize with the grid
    }

    // Spawn a monster at a random empty position
    public void spawnMonster() {
        PositionPoint randomLocation = grid.selectRandomLocation(); // Get a random empty position
        if (randomLocation != null) {
            // Create a random monster
            Monster monster = new Monster(randomLocation, grid.getHall());

            // Place the monster on the grid
            if (grid.moveEntity(monster)) {
                System.out.println("Spawned " + monster.getType() + " at position: " + randomLocation);
            } else {
                System.out.println("Failed to place the monster at position: " + randomLocation);
            }
        }
    }
    public static void main(String[] args) {
        // Step 1: Create a Hall instance (e.g., EARTH hall)
        Hall hall = new Hall(HallTypes.EARTH);

        // Step 2: Initialize the GridEnvironment with the Hall
        GridEnvironment grid = new GridEnvironment(new PositionPoint(0, 0), new PositionPoint(5, 5), hall);

        // Step 3: Create a SpawnMonsterController to spawn monsters
        SpawnMonsterController spawner = new SpawnMonsterController(grid);

        // Step 4: Spawn multiple monsters
        System.out.println("Spawning monsters...");
        for (int i = 0; i < 5; i++) { // Spawn 5 monsters
            spawner.spawnMonster();
        }

        // Step 5: Print the grid to check monster positions
        System.out.println("\nFinal grid after spawning monsters:");
        printGrid(grid);

        // Step 6: Print the list of monsters in the hall
        System.out.println("\nMonsters in the hall:");
        for (Monster monster : hall.getMonsters()) {
            System.out.println(monster.getType() + " at position: " + monster.position);
        }
    }

    // Helper method to print the grid
    public static void printGrid(GridEnvironment grid) {
        for (int x = 0; x < 25; x++) {
            for (int y = 0; y <25; y++) {
                if (grid.getmap()[x][y] instanceof Monster) {
                    System.out.print("M "); // Monster
                } else if (grid.getmap()[x][y] != null) {
                    System.out.print("E "); // Other Entity
                } else {
                    System.out.print(". "); // Empty position
                }
            }
            System.out.println();
        }
    }
    
}

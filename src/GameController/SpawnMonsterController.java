package src.GameController;

import src.GameObjects.ArcherMonster;
import src.GameObjects.FighterMonster;
import src.GameObjects.Hall;
import src.GameObjects.HallTypes;
import src.GameObjects.Hero;
import src.GameObjects.Monster;
import src.GameObjects.MonsterTypes;
import src.GameObjects.Rune;
import src.GameObjects.WizardMonster;
import src.Mechanics.GridEnvironment;
import src.Mechanics.PositionPoint;

public class SpawnMonsterController {
    private GridEnvironment grid;

    public SpawnMonsterController(GridEnvironment grid) { 
        this.grid = grid; // Initialize with the grid
    }

    // Spawn a monster at a random empty position
    public Monster spawnMonster() {
        PositionPoint randomLocation = grid.selectRandomLocation(); // Get a random empty position
        if (randomLocation != null) {
            MonsterTypes type = Monster.selectRandomMonster();
            String mode = GameModeController.getInstance().getGameMode();
            if (mode.equals("easy")) {
                do {
                    if (type == MonsterTypes.WizardMonster && grid.isWizardMonsterSpawned) {
                        type = Monster.selectRandomMonster();
                        //System.out.println("Wizard Monster is already spawned!" + "for Hall: "+ grid.getHall().hallType+" now trying to spawn " + type);
                    }
                    if (type == MonsterTypes.ArcherMonster && grid.isArcherMonsterSpawned) {
                        type = Monster.selectRandomMonster(); 
                        //System.out.println("Archer Monster is already spawned!" + "for Hall: "+ grid.getHall().hallType+" now trying to spawn " + type);
                    }
                    if ((type != MonsterTypes.WizardMonster || !grid.isWizardMonsterSpawned) &&
                        (type != MonsterTypes.ArcherMonster || !grid.isArcherMonsterSpawned)) {
                        //System.out.println("Exit loop");
                        break;
                    }
                } while (true);
                if (type == MonsterTypes.WizardMonster) {
                    //System.out.println("Wizard Monster is spawned for the first time" + "for Hall: "+ grid.getHall().hallType);
                    grid.isWizardMonsterSpawned = true;
                } else if (type == MonsterTypes.ArcherMonster) {
                    //System.out.println("Archer Monster is spawned for the first time" + "for Hall: "+ grid.getHall().hallType);
                    grid.isArcherMonsterSpawned = true;
                } else {
                    //System.out.println("Fighter Monster is spawned" + "for Hall: "+ grid.getHall().hallType);
                }
            }

            // Create a random monster
            Monster monster;
            switch (type) {
                case FighterMonster:
                    monster = new FighterMonster(randomLocation, grid.getHall(), grid);
                    break;
                case ArcherMonster:
                    monster = new ArcherMonster(randomLocation, grid.getHall());
                    break;
                case WizardMonster:
                    monster = new WizardMonster(randomLocation, grid.getHall(), grid);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + type);
            }
            

            // Place the monster on the grid
            if (grid.moveEntity(monster)) {
                //System.out.println("Spawned " + monster.getType() + " at position: " + randomLocation);
                    return monster;

            } else {
                //System.out.println("Failed to place the monster at position: " + randomLocation);
                return null;
            }
        }
        return null;
    }
    
    
    public static void main(String[] args) {
        // Step 1: Create a Hall instance (e.g., EARTH hall)
        Hall hall = new Hall(HallTypes.EARTH);

        // Step 2: Initialize the GridEnvironment with the Hall
        GridEnvironment grid = new GridEnvironment(hall);

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
                if (grid.getMap()[x][y] instanceof Monster) {
                    System.out.print("M "); // Monster
                } else if (grid.getMap()[x][y] instanceof Hero) {
                    System.out.print("H "); // Monster
                } else if (grid.getMap()[x][y] instanceof Rune) {
                    System.out.print("R "); // Other Entity
                } else if (grid.getMap()[x][y] != null) {
                    System.out.print("E "); // Other Entity
                } else {
                    System.out.print(". "); // Empty position
                }
            }
            System.out.println();
        }
    }
    
}

package src.GameController;

import src.GameObjects.Hall;
import src.GameObjects.HallTypes;
import src.GameObjects.Hero;
import src.GameObjects.Monster;
import src.GameObjects.Rune;
import src.Mechanics.Direction;
import src.Mechanics.Direction.DirectionEnum;
import src.Mechanics.GridEnvironment;
import src.Mechanics.PositionPoint;
import src.UI.PlayModeScreen;

public class Main {
    
    public static void main(String[] args) {
        Hall hall = new Hall(HallTypes.EARTH);
        PositionPoint position = new PositionPoint(2, 4);
        PositionPoint position2  =new PositionPoint(5, 21);
        PositionPoint position3  =new PositionPoint(21, 14);

        Monster monster = new Monster(position2, hall);
        Hero hero  = new Hero(position3, hall);

        Rune rune = new Rune(position, hall);
        hall.placeEntity(hero);
        hall.placeEntity(monster);
        hall.setRune(rune);
        GridEnvironment grid = new GridEnvironment(position, position, hall);

        PlayModeScreen pms = new PlayModeScreen(HallTypes.FIRE,grid); // Test with FIRE hall type
        SpawnMonsterController spawn = new SpawnMonsterController(grid);
        spawn.spawnMonster();
        spawn.spawnMonster();
        spawn.spawnMonster();
        spawn.spawnMonster();
        spawn.spawnMonster();
        Direction direcdown = new Direction(DirectionEnum.DOWN); 
                Direction direcRight = new Direction(DirectionEnum.RIGHT); 

        Direction direcleft = new Direction(DirectionEnum.LEFT); 
        Direction direcup = new Direction(DirectionEnum.UP); 
        // Assuming you have access to the Hero instance and directions like `direcdown`, `direcRight`, `direcup`
Thread movementThread = new Thread(() -> {
    try {
        // Move hero down multiple times
        for (int i = 0; i < 7; i++) {
            grid.moveEntity(hero, direcdown);
            Thread.sleep(1000); // Wait for 1 second
        }

        // Move hero right multiple times
        for (int i = 0; i < 8; i++) {
            grid.moveEntity(hero, direcRight);
            Thread.sleep(1000); // Wait for 1 second
        }

        // Move hero down multiple times again
        for (int i = 0; i < 8; i++) {
            grid.moveEntity(hero, direcdown);
            Thread.sleep(1000); // Wait for 1 second
        }

        // Move hero up multiple times
        for (int i = 0; i < 10; i++) {
            grid.moveEntity(hero, direcup);
            Thread.sleep(1000); // Wait for 1 second
        }
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
});

// Start the movement thread
movementThread.start();




        
    }
}

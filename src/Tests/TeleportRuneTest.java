package src.Tests;

import src.GameObjects.Hero;
import src.GameObjects.Obstacles;
import src.GameObjects.Obstacles.ObstacleType;
import src.GameObjects.Rune;
import src.GameObjects.WizardMonster;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import src.Mechanics.GridEnvironment;
import src.Mechanics.PositionPoint;
import src.GameController.GameModeController;
import src.GameObjects.WizardMonster;
import src.GameObjects.Hero;
import src.GameObjects.Hall;
import src.GameObjects.HallTypes;

public class TeleportRuneTest {
    private WizardMonster wizardMonster;
    private Hero hero;
    private GridEnvironment grid;
    private Hall hall;
    private Rune rune;
    private Obstacles obstacle1;
    private Obstacles obstacle2;
    private Obstacles obstacle3;

    @BeforeEach
    void setUp() {
        GameModeController gmc = new GameModeController() ;
        gmc.setGameMode("easy"); 
        GameModeController.getInstance().setGameMode("easy");
        PositionPoint wizardPosition = new PositionPoint(0, 0);
        PositionPoint heroPosition = new PositionPoint(3, 0); 
        PositionPoint runePosition = new PositionPoint(5, 21);
        PositionPoint obstaclePosition1 = new PositionPoint(5, 21);
        PositionPoint obstaclePosition2 = new PositionPoint(21, 14);
        PositionPoint obstaclePosition3 = new PositionPoint(21, 4);
        hall = new Hall(HallTypes.AIR); // Hall type doesn't matter
        grid = new GridEnvironment(hall);
        wizardMonster = new WizardMonster(wizardPosition, hall, grid);
        rune = new Rune(runePosition, hall);
        hero = new Hero(heroPosition, hall);
        obstacle1 = new Obstacles(obstaclePosition1, hall, ObstacleType.CHEST);
        obstacle2 = new Obstacles(obstaclePosition2, hall, ObstacleType.CHEST);
        obstacle3 = new Obstacles(obstaclePosition3, hall, ObstacleType.CHEST);
        hall.placeEntity(rune);
        hall.placeEntity(hero);
        hall.placeEntity(obstacle1);
        hall.placeEntity(obstacle2);
        hall.placeEntity(obstacle3);
        hall.placeEntity(wizardMonster);
    }

    @Test
    void testTeleportRune() {
        wizardMonster.teleportRune(grid);
        assertTrue(grid.rune.position != null);
    }

    @Test
    void testTeleportRuneTwice() {
        wizardMonster.teleportRune(grid);
        PositionPoint firstRunePosition = grid.rune.position;
        wizardMonster.teleportRune(grid);
        assertTrue(grid.rune.position != firstRunePosition);
    }

    @Test
    void testTeleportRuneToObstacle() {
        wizardMonster.teleportRune(grid);
        assertTrue(grid.getHall().getObstacles().contains(grid.rune));
    }
}

/*
Hall hall = new Hall(HallTypes.EARTH);
        PositionPoint position = new PositionPoint(2, 4);
        PositionPoint position2  =new PositionPoint(5, 21);
        PositionPoint position3  =new PositionPoint(21, 14);
        PositionPoint position4  =new PositionPoint(21, 14);
        PositionPoint position5  =new PositionPoint(21, 14);

        Obstacles obstacle = new Obstacles(position4, hall, ObstacleType.CHEST);
        Obstacles obstacle2 = new Obstacles(position5, hall, ObstacleType.CHEST);
        hall.placeEntity(obstacle);
        hall.placeEntity(obstacle2);

        GridEnvironment grid = new GridEnvironment(hall);


        Monster monster = new Monster(position2, hall);
        WizardMonster wizard = new WizardMonster(position3, hall, grid);
        Hero hero  = new Hero(position3, hall);
        Rune rune = new Rune(position, hall);
        hall.placeEntity(rune);
        hall.placeEntity(hero);
        hall.placeEntity(monster);
        hall.placeEntity(wizard);
        
        System.out.println(hall);

        TimeController controller = new TimeController(grid);
        controller.startTimeController();
        controller.startTimeController();
 */
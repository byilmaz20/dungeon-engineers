package src.Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import src.GameController.GameModeController;
import src.GameObjects.*;
import src.Mechanics.PositionPoint;

public class HallTest {

    private Hall hall;

    @BeforeEach
    public void setUp() {
        GameModeController GameModeController = new GameModeController() ;
        GameModeController.setGameMode("easy"); 
        GameModeController.getInstance().setGameMode("easy"); // Set the game mode for tests
        hall = new Hall(HallTypes.EARTH);
        Hero hero = new Hero(new PositionPoint(3, 3), hall);

    }

    @Test
    public void testRepOkOnFreshHall() {
        assertTrue(hall.repOk(), "repOk() should be true for a fresh Hall instance");
    }

    @Test
    public void testPlaceEntity() {
        Obstacles obstacle = new Obstacles(new PositionPoint(0, 0), hall, Obstacles.ObstacleType.BARREL);
        hall.placeEntity(obstacle);

        assertTrue(hall.getEntitys().contains(obstacle));
        assertTrue(hall.getObstacles().contains(obstacle));
        assertTrue(hall.repOk());

    }
    @Test
public void testOverlappingEntities() {
    Monster monster = new Monster(new PositionPoint(1, 1), hall);
    hall.placeEntity(monster);

    assertTrue(hall.getMonsters().contains(monster));
    assertTrue(hall.getEntitys().contains(monster));
    assertTrue(hall.repOk(), "repOk() should hold when entities overlap correctly.");
}

    
    @Test
    public void testRemoveEntity() {
        Obstacles obstacle1 = new Obstacles(new PositionPoint(2, 2), hall, Obstacles.ObstacleType.CHEST);
        hall.placeEntity(obstacle1);

        hall.removeEntity(obstacle1);

        assertFalse(hall.getEntitys().contains(obstacle1), "Obstacle should be removed from the main entity list");
    assertFalse(hall.getObstacles().contains(obstacle1), "Obstacle should be removed from the obstacles sublist");
    assertTrue(hall.repOk(), "repOk() should still be true after removal");
    }

    @Test
    public void testCheckObjectRequirements() {
        for (int i = 0; i < 5; i++) {
            hall.placeEntity(new Obstacles(new PositionPoint(i, 0), hall, Obstacles.ObstacleType.BARREL));
        }
        assertFalse(hall.checkObjectRequirements());

        hall.placeEntity(new Obstacles(new PositionPoint(5, 0), hall, Obstacles.ObstacleType.BARREL));
        assertTrue(hall.checkObjectRequirements());
        assertTrue(hall.repOk());
    }
    

    @Test
    public void testGetHero() {
        assertNull(hall.getHero());

        Hero hero = new Hero(new PositionPoint(3, 3), hall);
        hall.placeEntity(hero);

        assertNotNull(hall.getHero());
        assertEquals(hero, hall.getHero());
        assertTrue(hall.repOk());
    }
    
    
}

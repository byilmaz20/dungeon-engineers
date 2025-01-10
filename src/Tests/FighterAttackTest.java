package src.Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import src.GameController.GameModeController;
import src.GameObjects.FighterMonster;
import src.GameObjects.Hall;
import src.GameObjects.HallTypes;
import src.GameObjects.Hero;
import src.GameObjects.Obstacles;
import src.GameObjects.Obstacles.ObstacleType;
import src.Mechanics.GridEnvironment;
import src.Mechanics.PositionPoint;

/**
 * Tests the fighterAttack(Hero hero) method in the FighterMonster class.
 *
 * Specifications:
 * - Requires: A FighterMonster, a Hero, a valid Hall, and a grid (optional if not strictly needed).
 * - Modifies: The Hero's life count.
 * - Effects: Decreases the Hero's life count by 1 and returns true if the Hero is within a 
 *            3x3 area around the FighterMonster's position (including diagonals). 
 *            Otherwise, returns false and does not change Hero's life.
 */
public class FighterAttackTest {

    private FighterMonster fighterMonster;
    private Hero hero;
    private Hall hall;

    @BeforeEach
    public void setUp() {
    // Set the game mode
    GameModeController.getInstance().setGameMode("easy");

    // 1) Create the Hall
    hall = new Hall(HallTypes.EARTH);

    // 2) Place a dummy obstacle so that hall.getObstacles().size() >= 1
    Obstacles dummyObstacle = new Obstacles(new PositionPoint(0, 0), hall, ObstacleType.BARREL);
    hall.placeEntity(dummyObstacle);

    // 3) Now create the GridEnvironment (it uses hall.getObstacles() inside)
    GridEnvironment grid = new GridEnvironment(hall);

    // 4) Create your FighterMonster and Hero
    PositionPoint fighterPosition = new PositionPoint(5, 5);
    fighterMonster = new FighterMonster(fighterPosition, hall, grid);

    PositionPoint heroPosition = new PositionPoint(5, 6);
    hero = new Hero(heroPosition, hall);
    hero.setLifeCount(3);
}


    @Test
    public void testHeroInRange() {
        /**
         * Requires: Hero within the 3x3 range around the FighterMonster.
         * Modifies: Hero's life.
         * Effects: Should return true and reduce the Hero's life by 1.
         */
        boolean result = fighterMonster.fighterAttack(hero);
        assertTrue(result, "Expected fighterAttack to return true when hero is in range");
        assertEquals(2, hero.getLives(), "Hero's life should decrease by 1");
    }

    @Test
    public void testHeroOutOfRange() {
        /**
         * Requires: Hero positioned outside the 3x3 area around the FighterMonster.
         * Modifies: None (Hero's life remains the same).
         * Effects: Should return false and not affect Hero's life.
         */
        // Move hero far away, e.g., (10, 10)
        hero.setPositon(new PositionPoint(10, 10));

        boolean result = fighterMonster.fighterAttack(hero);
        assertFalse(result, "Expected fighterAttack to return false when hero is out of range");
        assertEquals(3, hero.getLives(), "Hero's life should remain unchanged");
    }

    @Test
    public void testHeroOnDiagonalEdgeOfRange() {
        /**
         * Requires: Hero exactly diagonal to the FighterMonster by 1 tile (which is within the 3x3 area).
         * Modifies: Hero's life.
         * Effects: Should return true and decrease Hero's life by 1 if diagonally within range.
         */
        // FighterMonster is at (5, 5). Diagonal would be (4, 4) or (6, 6), etc.
        hero.setPositon(new PositionPoint(4, 4));

        boolean result = fighterMonster.fighterAttack(hero);
        assertTrue(result, "Expected fighterAttack to return true for diagonal adjacency");
        assertEquals(2, hero.getLives(), "Hero's life should decrease by 1 for diagonal adjacency");
    }
}

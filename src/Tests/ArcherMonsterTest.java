package src.Tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.Mechanics.PositionPoint;
import src.GameController.GameModeController;
import src.GameObjects.ArcherMonster;
import src.GameObjects.Hero;
import src.GameObjects.Hall;
import src.GameObjects.HallTypes;

public class ArcherMonsterTest {

    private ArcherMonster archerMonster;
    private Hero hero;

    @BeforeEach
    void setUp() {
        GameModeController GameModeController = new GameModeController() ;
        GameModeController.setGameMode("easy"); 
        GameModeController.getInstance().setGameMode("easy"); // Set the game mode for tests
        PositionPoint archerPosition = new PositionPoint(0, 0);
        PositionPoint heroPosition = new PositionPoint(3, 0); // Within range
        Hall hall = new Hall(HallTypes.AIR); // Hall type doesn't matter

        archerMonster = new ArcherMonster(archerPosition, hall);
        hero = new Hero(heroPosition, hall);
        hero.setLifeCount(3); // Initial life count
    }

    @Test
    void testShootArrowSuccessful() {
        // Hero is within range and not protected
        hero.deactivateProtection();
        assertTrue(archerMonster.shootArrow(hero));
        assertEquals(2, hero.getLives());
    }

    @Test
    void testShootArrowProtectedHero() {
        // Hero is within range but protected
        hero.activateProtection();
        assertFalse(archerMonster.shootArrow(hero));
        assertEquals(3, hero.getLives());
    }

    @Test
    void testShootArrowOutOfRange() {
        // Move hero out of range
        hero.setPositon(new PositionPoint(5, 0));
        assertFalse(archerMonster.shootArrow(hero));
        assertEquals(3, hero.getLives());
    }

    @Test
    void testShootArrowAlreadyAttacked() {
        // Ensure ArcherMonster doesn't attack twice
        hero.deactivateProtection();
        assertTrue(archerMonster.shootArrow(hero));
        assertFalse(archerMonster.shootArrow(hero)); // Second attack should fail
        assertEquals(2, hero.getLives());
    }
}
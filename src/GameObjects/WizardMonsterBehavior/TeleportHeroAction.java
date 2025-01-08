package src.GameObjects.WizardMonsterBehavior;

import java.io.Serializable;
import java.util.Random;

import src.GameController.WizardTimeController;
import src.Mechanics.Direction;
import src.Mechanics.Direction.DirectionEnum;
import src.Mechanics.GridEnvironment;
import src.Mechanics.PositionPoint;

public class TeleportHeroAction implements IWizardBehavior, Serializable {
    public void takeAction(WizardTimeController wizardTimeController) {
        GridEnvironment grid = wizardTimeController.getGrid();
        wizardTimeController.getTimer().pauseTimer();


        PositionPoint heroPosition = grid.getHero().getPosition();
        PositionPoint newHeroPosition = heroPosition;
        while (heroPosition == newHeroPosition ) { 
            newHeroPosition = grid.selectRandomLocation();
        }
        grid.moveEntityToNewPosition(grid.getHero(), newHeroPosition);
        //wait for 1 second
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //then dissapear
        wizardTimeController.getGrid().removeEntity(wizardTimeController.getWizard());
    }
    
}

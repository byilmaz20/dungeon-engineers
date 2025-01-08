package src.GameObjects.WizardMonsterBehavior;

import java.io.Serializable;

import src.GameController.WizardTimeController;
import src.GameObjects.WizardMonster;
import src.Mechanics.GridEnvironment;

public class TeleportRuneAction implements IWizardBehavior, Serializable {
    
    public void takeAction(WizardTimeController wizardTimeController) {
        double elapsedTime = wizardTimeController.getElapsedTime();
        double lastRuneSpawnTime = wizardTimeController.getLastRuneSpawnTime();
        double RuneStartDelay = wizardTimeController.getRuneStartDelay();
        WizardMonster wizard = wizardTimeController.getWizard();
        GridEnvironment grid = wizardTimeController.getGrid();
        if (elapsedTime >= RuneStartDelay && elapsedTime - lastRuneSpawnTime >= 6.0) {
            wizard.teleportRune(grid);
            wizardTimeController.setLastRuneSpawnTime(elapsedTime);
            //System.out.println("Rune has been spawned!");
        }
    }
    
}

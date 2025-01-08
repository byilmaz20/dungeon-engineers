package src.GameObjects.WizardMonsterBehavior;

import java.io.Serializable;

import src.GameController.WizardTimeController;

public class DisappearAction implements IWizardBehavior, Serializable{
    public void takeAction(WizardTimeController wizardTimeController) {
        //it will stay in the place in which it appears, then disappear after 2 seconds without doing anything.
        wizardTimeController.getTimer().pauseTimer();
        //wait for 2 seconds
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        wizardTimeController.getGrid().removeEntity(wizardTimeController.getWizard());
    }
    
}

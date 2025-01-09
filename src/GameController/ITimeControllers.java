package src.GameController;

import src.Mechanics.Timer;

public interface ITimeControllers {
    public void pressPauseButton();
    public void applyTimeEchantment();
    public Timer getTimer();
    public double disposeTimer();

    
}

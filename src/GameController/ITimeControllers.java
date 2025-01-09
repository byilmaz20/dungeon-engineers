package src.GameController;

import java.io.Serializable;

import src.Mechanics.Timer;

public interface ITimeControllers extends Serializable{
    public void pressPauseButton();
    public void applyTimeEchantment();
    public Timer getTimer();
    public double disposeTimer();
    public void startTimeController();
    public double getRemainingTimeLoaded();
    public void startTimeController(double remainingTimeLoaded);

    
}
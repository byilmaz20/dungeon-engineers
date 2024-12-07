package GameObjects;

public class PlayerObject{
    public PlayerObject() {
        currentHall: Hall
        isPaused: boolean
        timeRemaining: int
        isGameOver: boolean
        
        public bool containsRune(){
            return true;
        }
        public void freezeGameActions(){
            isPaused = true;
        }
        public void unfreezeGameActions(){
            isPaused = false;
        }
        public void setGameRunning(boolean running){
            isGameOver = !running;
        }

        
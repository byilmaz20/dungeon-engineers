package GameObjects;

public class PlayerObject{
    public PlayerObject() {
        Hall currentHall;
        boolean isPaused; 
        int timeRemaining;
        boolean isGameOver;
        
        public bool containsRune(){
            return true;
        }
        public void freezeGameActions(){
            this.isPaused = true;
        }
        public void unfreezeGameActions(){
            this.isPaused = false;
        }
        public void setGameRunning(boolean running){
            this.isGameOver = !running;
        }

        
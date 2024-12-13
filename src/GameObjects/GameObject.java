package src.GameObjects;

public class GameObject{
    Hall currentHall;
    boolean isPaused; 
    int timeRemaining;
    boolean  isGameOver;

    public GameObject() {
        currentHall = new Hall();
        isPaused = false;
        timeRemaining = 0;
        isGameOver = false;
    }
        public boolean containsRune(){
            return true;
        }
        public void freezeGameActions(){
            this.isPaused = true;
        }
        public void unfreezeGameActions(){
            this.isPaused = false;
        }
        public void setGameRunning(boolean  running){
            this.isGameOver = !running;
        }
    }

        
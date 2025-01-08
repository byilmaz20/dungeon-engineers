package src.GameObjects;

import java.io.Serializable;

public class GameObject implements Serializable{
    Hall currentHall;
    boolean isPaused; 
    int timeRemaining;
    boolean  isGameOver;

    public GameObject() {
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

        
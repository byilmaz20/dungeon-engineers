package src.GameObjects;

import java.io.Serializable;

import src.Mechanics.PositionPoint;

public abstract class Entity implements Serializable{
    public PositionPoint position;
    public Hall hall;
    public Entity(PositionPoint position, Hall hall){
        this.position = position;
        this.hall = hall;
    }
    
}

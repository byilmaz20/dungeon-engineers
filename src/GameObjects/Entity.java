package src.GameObjects;

import src.Mechanics.PositionPoint;

public abstract class Entity {
    public PositionPoint position;
    public Hall hall;
    public Entity(PositionPoint position, Hall hall){
        this.position = position;
        this.hall = hall;
    }
    
}

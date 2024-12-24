package src.GameController;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import src.GameObjects.Hall;
import src.GameObjects.HallTypes;
import src.GameObjects.Obstacles;
import src.GameObjects.Obstacles.ObstacleType;
import src.Mechanics.PositionPoint;
import src.UI.BuildModeScreen;

public class BuildModeController {
    public static final Map<String, Hall> Halls = new HashMap<>();

    public BuildModeController(){


        for (Entry<String, Map<Point, String>> outerEntry : BuildModeScreen.hallObjectPlacements.entrySet()) {
            String outerKey = outerEntry.getKey();
            Map<Point, String> innerMap = outerEntry.getValue();

            HallTypes halltyp = null;
            if (outerKey == "Hall of EARTH"){ halltyp = HallTypes.EARTH;}
            if (outerKey == "Hall of AIR"){ halltyp = HallTypes.AIR;}
            if (outerKey == "Hall of WATER"){ halltyp = HallTypes.WATER;}
            if (outerKey == "Hall of FIRE"){ halltyp = HallTypes.FIRE;}

            Hall hall = new Hall(halltyp);

            for (Map.Entry<Point, String> innerEntry : innerMap.entrySet()) {
                Point point = innerEntry.getKey();
                String value = innerEntry.getValue();

                PositionPoint pp = new PositionPoint(point.y, point.x);

                ObstacleType obst = null;
                if (value == "Skull"){ obst = ObstacleType.SKULL;}
                if (value == "Stair"){ obst = ObstacleType.STAIR;}
                if (value == "Rectangle"){ obst = ObstacleType.RECTANGLE;}
                if (value == "1Box"){ obst = ObstacleType.ONE_BOX;}
                if (value == "2Box"){ obst = ObstacleType.TWO_BOX;}
                if (value == "Barrel"){ obst = ObstacleType.BARREL;}
                if (value == "Chest"){ obst = ObstacleType.CHEST;}
                if (value == "Potion"){ obst = ObstacleType.POTION;}



                Obstacles ent  = new Obstacles(pp, hall, obst);
                hall.placeEntity(ent);


            }
            Halls.put(outerKey, hall);
        }
        

    }
    
    
}

package GameObjects;
class Hall {
    String name; //(Earth, Air, Water, Fire) //TODO: Enum
    List<GameObject> objects;
    List<Monster> monsters;
    List<Enchantment> enchantments;
    Rune rune;
    int minimumObjectsRequired;

    void placeObject(GameObject object) {
        // Place object in hall
    }

    void checkObjectRequirements() {
        // Check if all objects are placed
    }

    void selectRandomLocation() {
        // Select random location in hall
    }

    void addMonsterToHall() {
        // Add monster to hall
    }
}
package rpg.game;

interface Management {
    void add_character(Character new_character);
    void removeCharacter(int characterID);
    int doesCharacterExist(int characterID);
    void display_list();
}

interface Stats {
    String getClassName();
    String getCharacterName();
    int getHealthPoints();
    int getAttackDamages();
    int getInitiative();

    int getShield();
    int getEnhancedDamages();
    float getAgility();
    float getCriticalChance();
    boolean isCritical();
    void disableCritical();
    void enableCritical();
}

interface Fighting {
    void takeDamages(int damages);
    Character getFighter(int index);
}

interface Save {
    String IndexType();
    String convertToJSON();
    String toString();
}
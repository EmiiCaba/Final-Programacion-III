public class Character {
    private String name;
    private String race;
    private String nickname;
    private int velocity;
    private int dexterity;
    private int strength;
    private int level;
    private int armor;
    private int health;

    public String getName() {
        return name;
    }

    public String getNickname() {
        return nickname;
    }

    public int getVelocity() {

        return velocity;
    }


    public int getDexterity() {

        return dexterity;
    }


    public int getStrength() {

        return strength;
    }

    public int getArmor() {

        return armor;
    }


    // Constructor
    public Character(String name, String race, String nickname, int velocity, int dexterity, int strength,
                     int level, int armor, int health) {
        this.name = name;
        this.race = race;
        this.nickname = nickname;
        this.velocity = velocity;
        this.dexterity = dexterity;
        this.strength = strength;
        this.level = level;
        this.armor = armor;
        this.health = health;
    }

    public int getLevel() {
        return level;
    }

    public String getRace() {
        return race;
    }

    public int getHealth() {
        return health;
    }

    public int calculateAttackPower() {
        return dexterity * strength * level;
    }

    public int calculateDefensePower() {
        return armor * velocity;
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health < 0) {
            health = 0;
        }
    }
    public String getName(int level) {
        if (this.getRace().equalsIgnoreCase("human")) {
            return "Humano";
        } else if (this.getRace().equalsIgnoreCase("elfo")) {
            return "Elfo";
        } else {
            return "Orco";
        }

    }


    public boolean isAlive() {
        return health > 0;
    }

    public void setHealth(int i) {
    }


}



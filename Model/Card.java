package Model;

/**
 * Abstract base class representing a card in the game.
 * Every card has a name, rank, attack, defense, and HP.
 * Subclasses must implement the special skill behavior.
 */
public abstract class Card {
    private String name;
    private String rank;
    private int attack;
    private int defense;
    private int hp;
    private int maxHp;

    /**
     * Constructor for a card.
     * @param name Card's name
     * @param rank Rank (S/A/B)
     * @param attack Base attack value
     * @param defense Base defense value
     * @param hp Base HP value
     */
    public Card(String name, String rank, int attack, int defense, int hp) {
        this.name = name;
        this.rank = rank;
        this.attack = attack;
        this.defense = defense;
        this.hp = hp;
        this.maxHp = hp;
    }

    // Getters
    public String getName() { return name; }
    public String getRank() { return rank; }
    public int getAttack() { return attack; }
    public int getDefense() { return defense; }
    public int getHp() { return hp; }
    public int getMaxHp() { return maxHp; }

    /**
     * Sets HP with bounds [0, maxHp].
     */
    public void setHp(int hp) {
        this.hp = Math.max(0, Math.min(hp, maxHp));
    }

    /**
     * Reduces HP by damage after applying defense reduction.
     * Minimum damage is 1.
     * @param damage Raw incoming damage
     */
    public void takeDamage(int damage) {
        int actualDamage = Math.max(1, damage - defense);
        this.hp = Math.max(0, this.hp - actualDamage);
        System.out.println(name + " takes " + actualDamage + " damage! HP left: " + hp);
    }

    /**
     * @return true if HP > 0
     */
    public boolean isAlive() {
        return hp > 0;
    }

    /**
     * Abstract method to be implemented by subclasses.
     * Prints a skill description and the damage multiplier logic is handled in battle.
     */
    public abstract void useSpecialSkill();

    /**
     * Displays card information in a formatted way.
     */
    public void displayInfo() {
        System.out.printf("[%s] %s | ATK:%d DEF:%d HP:%d/%d\n",
                rank, name, attack, defense, hp, maxHp);
    }

    @Override
    public String toString() {
        return String.format("[%s] %s (ATK:%d DEF:%d HP:%d/%d)",
                rank, name, attack, defense, hp, maxHp);
    }
}
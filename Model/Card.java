package Model;

public abstract class Card {
    private String name;
    private String rank;
    private int attack;
    private int defense;
    private int hp;
    private int maxHp;

    public Card(String name, String rank, int attack, int defense, int hp) {
        this.name = name;
        this.rank = rank;
        this.attack = attack;
        this.defense = defense;
        this.hp = hp;
        this.maxHp = hp;
    }

    public String getName() { return name; }
    public String getRank() { return rank; }
    public int getAttack() { return attack; }
    public int getDefense() { return defense; }
    public int getHp() { return hp; }
    public int getMaxHp() { return maxHp; }

    public void setHp(int hp) {
        this.hp = Math.max(0, Math.min(hp, maxHp));
    }

    public void takeDamage(int damage) {
        int actualDamage = Math.max(1, damage - defense);
        this.hp = Math.max(0, this.hp - actualDamage);
        System.out.println(name + " takes " + actualDamage + " damage! HP left: " + hp);
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public abstract void useSpecialSkill();

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
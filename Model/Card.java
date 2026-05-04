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
    
    // Getters and Setters
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
        System.out.println(name + " 受到 " + actualDamage + " 点伤害！剩余血量: " + hp);
    }
    
    public boolean isAlive() {
        return hp > 0;
    }
    
    // 多态方法：不同等级卡片技能不同
    public abstract void useSpecialSkill();
    
    // 展示卡片信息
    public void displayInfo() {
        System.out.printf("【%s级】%s | 攻击:%d 防御:%d 生命:%d/%d\n", 
            rank, name, attack, defense, hp, maxHp);
    }
    
    @Override
    public String toString() {
        return String.format("[%s] %s (攻:%d 防:%d 血:%d/%d)", 
            rank, name, attack, defense, hp, maxHp);
    }
}
package Model;

public class BCard extends Card {
    public BCard(String name, int baseAttack, int baseDefense, int baseHp) {
        super(name, "B", baseAttack, baseDefense, baseHp);
    }
    
    @Override
    public void useSpecialSkill() {
        System.out.println("💪 " + getName() + " 发动B级技能：重击！造成1.2倍伤害！💪");
    }
}
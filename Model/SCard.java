package Model;

public class SCard extends Card {
    private static final int BONUS_ATTACK = 50;
    private static final int BONUS_DEFENSE = 30;
    private static final int BONUS_HP = 100;
    
    public SCard(String name, int baseAttack, int baseDefense, int baseHp) {
        super(name, "S", 
            baseAttack + BONUS_ATTACK, 
            baseDefense + BONUS_DEFENSE, 
            baseHp + BONUS_HP);
    }
    
    @Override
    public void useSpecialSkill() {
        System.out.println("✨ " + getName() + " 发动S级必杀技：天罚！造成双倍伤害！✨");
        // 技能效果在战斗逻辑中实现
    }
}

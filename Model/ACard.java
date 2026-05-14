package Model;

// A-rank card with bonus stats and 1.5x special skill multiplier

public class ACard extends Card {
    private static final int BONUS_ATTACK = 20;
    private static final int BONUS_DEFENSE = 15;
    private static final int BONUS_HP = 50;

    public ACard(String name, int baseAttack, int baseDefense, int baseHp) {
        super(name, "A", baseAttack + BONUS_ATTACK, baseDefense + BONUS_DEFENSE, baseHp + BONUS_HP);
    }

    @Override
    public void useSpecialSkill() {
        System.out.println("[A] " + getName() + " uses A-rank skill: Combo! 1.5x damage!");
    }
}
package Model;

/**
 * S-rank card with bonus stats and 2.0x special skill multiplier.
 */
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
        System.out.println("[S] " + getName() + " uses S-rank skill: Divine Punishment! Double damage!");
    }
}
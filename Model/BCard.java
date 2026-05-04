package Model;

public class BCard extends Card {
    public BCard(String name, int baseAttack, int baseDefense, int baseHp) {
        super(name, "B", baseAttack, baseDefense, baseHp);
    }

    @Override
    public void useSpecialSkill() {
        System.out.println("💪 " + getName() + " uses B-rank skill: Heavy Strike! 1.2x damage! 💪");
    }
}
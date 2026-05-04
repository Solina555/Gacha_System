package Service;

import Model.*;
import java.util.*;

public class BattleService {
    private Player player;
    private Random random;

    private static final EnemyConfig[] STAGES = {
        new EnemyConfig("Goblin Chief", "B", 35, 20, 80),
        new EnemyConfig("Shadow Assassin", "B", 45, 15, 100),
        new EnemyConfig("Flame Mage", "A", 60, 25, 120),
        new EnemyConfig("Frost Dragon", "A", 70, 30, 150),
        new EnemyConfig("Demon Lord", "S", 100, 40, 200)
    };

    public BattleService(Player player) {
        this.player = player;
        this.random = new Random();
    }

    public boolean startBattle(int stage) {
        if (stage < 1 || stage > Player.getTotalStages()) {
            System.out.println("❌ Invalid stage number!");
            return false;
        }
        if (stage > player.getMaxUnlockedStage()) {
            System.out.println("⚠️ Stage " + stage + " is not unlocked yet! Clear previous stages first.");
            return false;
        }

        EnemyConfig config = STAGES[stage - 1];
        Enemy enemy = new Enemy(config);

        boolean isFirst = !player.isFirstClear(stage);
        System.out.println("\n⚔️⚔️⚔️ STAGE " + stage + " ⚔️⚔️⚔️");
        System.out.println("Enemy: " + enemy);
        if (isFirst) {
            System.out.println("🎁 First clear reward: " + Player.getFullReward(stage) + " coins");
        } else {
            System.out.println("🔄 Repeat clear reward: " + Player.getHalfReward(stage) + " coins (half of first clear)");
        }
        System.out.println("---------------------------------------------");

        List<Card> team = player.getTeam();
        if (team.isEmpty()) {
            System.out.println("⚠️ No cards to fight! Go draw some first.");
            return false;
        }

        boolean victory = battleLoop(team, enemy);

        if (victory) {
            int reward = isFirst ? Player.getFullReward(stage) : Player.getHalfReward(stage);
            player.addCoins(reward);
            System.out.println("💰 You received " + reward + " coins!");

            if (isFirst) {
                player.setFirstClear(stage);
                player.unlockNextStage(stage);
                System.out.println("🎉 First clear! Repeating this stage will give half reward.");
            } else {
                System.out.println("🔄 Stage cleared again. Half reward granted.");
            }
        }

        return victory;
    }

    private boolean battleLoop(List<Card> team, Enemy enemy) {
        Scanner scanner = new Scanner(System.in);
        int currentCardIndex = 0;

        while (!team.isEmpty() && enemy.isAlive()) {
            Card currentCard = team.get(currentCardIndex % team.size());

            System.out.println("\nCurrent card: " + currentCard.getName() + " [" + currentCard.getRank() + "]");
            System.out.println("Enemy status: " + enemy.getStatus());
            System.out.println("\nChoose action:");
            System.out.println("1. Normal attack");
            System.out.println("2. Special skill");
            System.out.println("3. Switch card");
            System.out.print("> ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    playerAttack(currentCard, enemy);
                    break;
                case 2:
                    useSpecialSkill(currentCard, enemy);
                    break;
                case 3:
                    currentCardIndex++;
                    System.out.println("Switched to next card!");
                    continue;
                default:
                    System.out.println("Invalid choice!");
                    continue;
            }

            if (enemy.isAlive()) {
                enemyAttack(team, enemy);
                team.removeIf(card -> !card.isAlive());
                if (team.isEmpty()) {
                    System.out.println("\n💀 All cards defeated! You lose... 💀");
                    return false;
                }
            }
        }

        System.out.println("\n🎉 Victory! You defeated " + enemy.getName() + "! 🎉");
        return true;
    }

    private void playerAttack(Card card, Enemy enemy) {
        int damage = card.getAttack();
        System.out.println("\n" + card.getName() + " attacks!");
        enemy.takeDamage(damage);
    }

    private void useSpecialSkill(Card card, Enemy enemy) {
        card.useSpecialSkill();
        double multiplier;
        switch (card.getRank()) {
            case "S": multiplier = 2.0; break;
            case "A": multiplier = 1.5; break;
            default:  multiplier = 1.2; break;
        }
        int damage = (int)(card.getAttack() * multiplier);
        System.out.println("Deals " + damage + " damage!");
        enemy.takeDamage(damage);
    }

    private void enemyAttack(List<Card> team, Enemy enemy) {
        Card target = team.get(random.nextInt(team.size()));
        System.out.println("\n" + enemy.getName() + " attacks " + target.getName() + "!");
        target.takeDamage(enemy.getAttack());
    }

    private static class EnemyConfig {
        String name;
        String rank;
        int attack;
        int defense;
        int hp;

        EnemyConfig(String name, String rank, int attack, int defense, int hp) {
            this.name = name;
            this.rank = rank;
            this.attack = attack;
            this.defense = defense;
            this.hp = hp;
        }
    }

    private static class Enemy {
        private String name;
        private String rank;
        private int attack;
        private int defense;
        private int hp;
        private int maxHp;

        Enemy(EnemyConfig config) {
            this.name = config.name;
            this.rank = config.rank;
            this.attack = config.attack;
            this.defense = config.defense;
            this.hp = config.hp;
            this.maxHp = config.hp;
        }

        void takeDamage(int damage) {
            int actualDamage = Math.max(1, damage - defense);
            hp = Math.max(0, hp - actualDamage);
            System.out.println(name + " takes " + actualDamage + " damage! HP left: " + hp);
        }

        boolean isAlive() { return hp > 0; }
        int getAttack() { return attack; }
        String getName() { return name; }
        String getStatus() { return name + " [HP:" + hp + "/" + maxHp + "]"; }

        @Override
        public String toString() {
            return String.format("[%s] %s (ATK:%d DEF:%d HP:%d)", rank, name, attack, defense, hp);
        }
    }
}
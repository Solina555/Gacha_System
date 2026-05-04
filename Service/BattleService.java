package Service;

import Model.*;
import java.util.*;

public class BattleService {
    private Player player;
    private Random random;
    
    // 关卡敌人配置
    private static final EnemyConfig[] STAGES = {
        new EnemyConfig("哥布林首领", "B", 35, 20, 80, 50),
        new EnemyConfig("暗影刺客", "B", 45, 15, 100, 80),
        new EnemyConfig("火焰魔导师", "A", 60, 25, 120, 100),
        new EnemyConfig("冰霜巨龙", "A", 70, 30, 150, 120),
        new EnemyConfig("魔王·毁灭者", "S", 100, 40, 200, 200)
    };
    
    public BattleService(Player player) {
        this.player = player;
        this.random = new Random();
    }
    
    public boolean startBattle() {
        int stage = player.getCurrentStage();
        if (stage > STAGES.length) {
            System.out.println("🎉 恭喜你已通关所有关卡！ 🎉");
            return true;
        }
        
        EnemyConfig config = STAGES[stage - 1];
        Enemy enemy = new Enemy(config);
        
        System.out.println("\n⚔️⚔️⚔️ 第 " + stage + " 关 ⚔️⚔️⚔️");
        System.out.println("敌人: " + enemy);
        System.out.println("----------------------------------------");
        
        List<Card> team = player.getTeam();
        if (team.isEmpty()) {
            System.out.println("⚠️ 没有可出战的卡片！请先抽卡！");
            return false;
        }
        
        return battleLoop(team, enemy);
    }
    
    private boolean battleLoop(List<Card> team, Enemy enemy) {
        Scanner scanner = new Scanner(System.in);
        int currentCardIndex = 0;
        
        while (!team.isEmpty() && enemy.isAlive()) {
            Card currentCard = team.get(currentCardIndex % team.size());
            
            System.out.println("\n当前出战: " + currentCard.getName() + " [" + currentCard.getRank() + "级]");
            System.out.println("敌人状态: " + enemy.getStatus());
            System.out.println("\n请选择：");
            System.out.println("1. 普通攻击");
            System.out.println("2. 特殊技能");
            System.out.println("3. 切换卡片");
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
                    System.out.println("切换到下一张卡片！");
                    continue;
                default:
                    System.out.println("无效选择！");
                    continue;
            }
            
            // 敌人反击（如果还活着）
            if (enemy.isAlive()) {
                enemyAttack(team, enemy);
                // 移除死亡卡片
                team.removeIf(card -> !card.isAlive());
                if (team.isEmpty()) {
                    System.out.println("\n💀 全体卡片阵亡，战斗失败！ 💀");
                    return false;
                }
            }
        }
        
        System.out.println("\n🎉 胜利！击败了 " + enemy.getName() + "！ 🎉");
        int reward = STAGES[player.getCurrentStage() - 1].reward;
        player.addCoins(reward);
        System.out.println("💰 获得 " + reward + " 游戏币奖励！");
        player.setCurrentStage(player.getCurrentStage() + 1);
        return true;
    }
    
    private void playerAttack(Card card, Enemy enemy) {
        int damage = card.getAttack();
        System.out.println("\n" + card.getName() + " 发动攻击！");
        enemy.takeDamage(damage);
    }
    
    private void useSpecialSkill(Card card, Enemy enemy) {
        card.useSpecialSkill();
        int damage = (int)(card.getAttack() * getSkillMultiplier(card.getRank()));
        System.out.println("造成 " + damage + " 点伤害！");
        enemy.takeDamage(damage);
    }
    
    private double getSkillMultiplier(String rank) {
        switch(rank) {
            case "S": return 2.0;
            case "A": return 1.5;
            default: return 1.2;
        }
    }
    
    private void enemyAttack(List<Card> team, Enemy enemy) {
        Card target = team.get(new Random().nextInt(team.size()));
        System.out.println("\n" + enemy.getName() + " 攻击 " + target.getName() + "！");
        target.takeDamage(enemy.getAttack());
    }
    
    // 内部类：敌人配置
    private static class EnemyConfig {
        String name;
        String rank;
        int attack;
        int defense;
        int hp;
        int reward;
        
        EnemyConfig(String name, String rank, int attack, int defense, int hp, int reward) {
            this.name = name;
            this.rank = rank;
            this.attack = attack;
            this.defense = defense;
            this.hp = hp;
            this.reward = reward;
        }
    }
    
    // 敌人实体类
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
            System.out.println(name + " 受到 " + actualDamage + " 点伤害！剩余血量: " + hp);
        }
        
        boolean isAlive() { return hp > 0; }
        int getAttack() { return attack; }
        String getName() { return name; }
        String getStatus() { return name + " [HP:" + hp + "/" + maxHp + "]"; }
        
        @Override
        public String toString() {
            return String.format("【%s级】%s (攻:%d 防:%d 血:%d)", rank, name, attack, defense, hp);
        }
    }
}
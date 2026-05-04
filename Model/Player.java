package Model;

import java.util.*;

public class Player {
    private String name;
    private List<Card> inventory;  // 背包：存储所有抽到的卡
    private int currentStage;       // 当前关卡（1-5）
    private int coins;              // 游戏币（用于抽卡）
    
    public Player(String name) {
        this.name = name;
        this.inventory = new ArrayList<>();
        this.currentStage = 1;
        this.coins = 1000;  // 初始1000游戏币
    }
    
    public void addCard(Card card) {
        inventory.add(card);
        System.out.println("🎉 获得新卡片：" + card);
    }
    
    public void showInventory() {
        if (inventory.isEmpty()) {
            System.out.println("📦 背包空空如也，快去抽卡吧！");
            return;
        }
        
        System.out.println("\n========== 我的背包 ==========");
        System.out.printf("共 %d 张卡片\n", inventory.size());
        System.out.println("------------------------------");
        
        // 按等级分组显示
        Map<String, List<Card>> grouped = new HashMap<>();
        for (Card card : inventory) {
            grouped.computeIfAbsent(card.getRank(), k -> new ArrayList<>()).add(card);
        }
        
        for (String rank : Arrays.asList("S", "A", "B")) {
            if (grouped.containsKey(rank)) {
                System.out.println("\n【" + rank + "级卡片】");
                for (int i = 0; i < grouped.get(rank).size(); i++) {
                    System.out.printf("  %d. ", i + 1);
                    grouped.get(rank).get(i).displayInfo();
                }
            }
        }
        System.out.println("==============================\n");
    }
    
    public List<Card> getTeam() {
        // 返回前3张卡作为出战队伍（简化版）
        int teamSize = Math.min(3, inventory.size());
        if (teamSize == 0) return new ArrayList<>();
        return new ArrayList<>(inventory.subList(0, teamSize));
    }
    
    // Getters and Setters
    public String getName() { return name; }
    public int getCurrentStage() { return currentStage; }
    public void setCurrentStage(int stage) { this.currentStage = stage; }
    public int getCoins() { return coins; }
    public void addCoins(int amount) { this.coins += amount; }
    public boolean spendCoins(int amount) {
        if (coins >= amount) {
            coins -= amount;
            return true;
        }
        return false;
    }
    public int getInventorySize() { return inventory.size(); }
}
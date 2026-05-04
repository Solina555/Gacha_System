package Model;

import java.util.*;

public class Player {
    private String name;
    private List<Card> inventory;
    private int coins;
    private int maxUnlockedStage;
    private boolean[] firstClear;
    private static final int TOTAL_STAGES = 5;

    public Player(String name) {
        this.name = name;
        this.inventory = new ArrayList<>();
        this.coins = 1000;
        this.maxUnlockedStage = 1;
        this.firstClear = new boolean[TOTAL_STAGES + 1];
        Arrays.fill(firstClear, false);
    }

    public boolean isFirstClear(int stage) {
        if (stage < 1 || stage > TOTAL_STAGES) return false;
        return firstClear[stage];
    }

    public void setFirstClear(int stage) {
        if (stage >= 1 && stage <= TOTAL_STAGES) {
            firstClear[stage] = true;
        }
    }

    public void unlockNextStage(int stage) {
        if (stage == maxUnlockedStage && stage < TOTAL_STAGES) {
            maxUnlockedStage = stage + 1;
            System.out.println("🔓 New stage unlocked! You can now challenge stage " + maxUnlockedStage + ".");
        }
    }

    public int getMaxUnlockedStage() { return maxUnlockedStage; }
    public static int getTotalStages() { return TOTAL_STAGES; }

    public static int getFullReward(int stage) { return stage * 100; }
    public static int getHalfReward(int stage) { return getFullReward(stage) / 2; }

    public void addCard(Card card) {
        inventory.add(card);
        System.out.println("🎉 New card obtained: " + card);
    }

    public void showInventory() {
        if (inventory.isEmpty()) {
            System.out.println("📦 Inventory is empty! Go draw some cards.");
            return;
        }

        System.out.println("\n========== MY INVENTORY ==========");
        System.out.println("Total cards: " + inventory.size());
        System.out.println("-----------------------------------");

        Map<String, List<Card>> grouped = new HashMap<>();
        for (Card card : inventory) {
            grouped.computeIfAbsent(card.getRank(), k -> new ArrayList<>()).add(card);
        }

        for (String rank : Arrays.asList("S", "A", "B")) {
            if (grouped.containsKey(rank)) {
                System.out.println("\n[" + rank + "-rank cards]");
                for (int i = 0; i < grouped.get(rank).size(); i++) {
                    System.out.printf("  %d. ", i + 1);
                    grouped.get(rank).get(i).displayInfo();
                }
            }
        }
        System.out.println("==================================\n");
    }

    public List<Card> getTeam() {
        int teamSize = Math.min(3, inventory.size());
        if (teamSize == 0) return new ArrayList<>();
        return new ArrayList<>(inventory.subList(0, teamSize));
    }

    public String getName() { return name; }
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
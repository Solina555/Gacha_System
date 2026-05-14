package Model;

import java.util.*;

// Represents the player, holding bag (cards), battle team, coins, stage progress, etc.
public class Player {
    private String name;
    private List<Card> bag;               // All owned cards
    private List<Card> team;              // Up to 3 cards for battle
    private int coins;
    private int maxUnlockedStage;
    private boolean[] firstClear;         // Tracks if stage's full reward has been claimed
    private static final int TOTAL_STAGES = 5;
    private static final int MAX_TEAM_SIZE = 3;

    public Player(String name) {
        this.name = name;
        this.bag = new ArrayList<>();
        this.team = new ArrayList<>();
        this.coins = 1000;
        this.maxUnlockedStage = 1;
        this.firstClear = new boolean[TOTAL_STAGES + 1];
        Arrays.fill(firstClear, false);
    }


    // ======================= Team Management ========================

    // Returns a copy of the current battle team
    public List<Card> getTeam() {
        return new ArrayList<>(team);
    }

    /**
     * Sets a new battle team from given cards 
     * Validates that all cards are in bag and limits to 3
     * @param newTeam List of cards to form the team
     */
    public void setTeam(List<Card> newTeam) {
        if (newTeam == null || newTeam.isEmpty()) {
            System.out.println("[X] Team cannot be empty! Keep current team.");
            return;
        }
        List<Card> validTeam = new ArrayList<>();
        for (Card card : newTeam) {
            if (bag.contains(card) && validTeam.size() < MAX_TEAM_SIZE) {
                validTeam.add(card);
            }
        }
        if (validTeam.isEmpty()) {
            System.out.println("[X] No valid cards selected. Team unchanged.");
            return;
        }
        this.team = validTeam;
        System.out.println("[OK] Team updated! Current team size: " + team.size());
    }

    /**
     * Automatically sets the team to the first N cards in bag (N up to 3)
     * Called after drawing cards if team is empty
     */
    public void autoSetupTeam() {
        if (bag.isEmpty()) {
            team.clear();
            return;
        }
        int size = Math.min(MAX_TEAM_SIZE, bag.size());
        team = new ArrayList<>(bag.subList(0, size));
        System.out.println("[AUTO] Team set to first " + size + " cards in bag.");
    }


    // ======================= Bag Management ========================

    // Adds a card to the bag. If team is empty, auto-setup is triggered.
    public void addCard(Card card) {
        bag.add(card);
        System.out.println("[NEW] Card obtained: " + card);
        if (team.isEmpty()) {
            autoSetupTeam();
        }
    }

    // Returns a copy of the bag.
    public List<Card> getBag() {
        return new ArrayList<>(bag);
    }

    // Returns a sorted copy of the bag: by rank (S > A > B), then by insertion order (oldest first)
    public List<Card> getSortedBag() {
        List<Card> sorted = new ArrayList<>(bag);
        sorted.sort((c1, c2) -> {
            int order1 = getRankOrder(c1.getRank());
            int order2 = getRankOrder(c2.getRank());
            if (order1 != order2) {
                return Integer.compare(order1, order2);
            }
            else {
                return Integer.compare(bag.indexOf(c1), bag.indexOf(c2));
            }
        });
        return sorted;
    }

    // Displays all cards in the bag, sorted by rank (S > A > B) and insertion order.
    public void showBag() {
        if (bag.isEmpty()) {
            System.out.println("[BAG] Your bag is empty! Go draw some cards.");
            return;
        }

        List<Card> sorted = getSortedBag();

        System.out.println("\n========== MY BAG ==========");
        System.out.println("Total cards: " + bag.size());
        System.out.println("----------------------------");

        String currentRank = null;
        for (Card card : sorted) {
            if (!card.getRank().equals(currentRank)) {
                currentRank = card.getRank();
                System.out.println("\n[" + currentRank + "-rank cards]");
            }
            card.displayInfo();
        }
        System.out.println("============================\n");
    }

    // Helper to get numeric order for rank comparison
    private int getRankOrder(String rank) {
        switch (rank) {
            case "S": return 1;
            case "A": return 2;
            case "B": return 3;
            default: return 4;
        }
    }

    // Displays the current battle team
    public void showTeam() {
        if (team.isEmpty()) {
            System.out.println("[FIGHT] No cards in your team! Please set a team first.");
            return;
        }
        System.out.println("\n===== CURRENT TEAM (max 3) =====");
        for (int i = 0; i < team.size(); i++) {
            System.out.print((i+1) + ". ");
            team.get(i).displayInfo();
        }
        System.out.println("================================\n");
    }


    // ======================= Stage & Reward Management =======================

    // Checks if a stage has been cleared for the first time (if full reward already given)
    public boolean isFirstClear(int stage) {
        if (stage < 1 || stage > TOTAL_STAGES) return false;
        return firstClear[stage];
    }

    // Marks a stage as first cleared
    public void setFirstClear(int stage) {
        if (stage >= 1 && stage <= TOTAL_STAGES) {
            firstClear[stage] = true;
        }
    }

    // Unlocks the next stage if the current stage is the highest unlocked
    public void unlockNextStage(int stage) {
        if (stage == maxUnlockedStage && stage < TOTAL_STAGES) {
            maxUnlockedStage = stage + 1;
            System.out.println("[UNLOCK] New stage unlocked! You can now challenge stage " + maxUnlockedStage + ".");
        }
    }

    public int getMaxUnlockedStage() {
        return maxUnlockedStage;
    }

    public static int getTotalStages() {
        return TOTAL_STAGES;
    }

    // Full reward for a stage: stage number * 100
    public static int getFullReward(int stage) {
        return stage * 100;
    }

    // Half reward for a stage (when repeated)
    public static int getHalfReward(int stage) {
        return getFullReward(stage) / 2;
    }


    // ======================= General Getters & Setters =======================
    public String getName() {
        return name;
    }

    public int getCoins() {
        return coins;
    }

    public void addCoins(int amount) {
        this.coins += amount;
    }

    public boolean spendCoins(int amount) {
        if (coins >= amount) {
            coins -= amount;
            return true;
        }
        return false;
    }

    public int getBagSize() {
        return bag.size();
    }
}
package UI;

import Model.*;
import Service.*;
import java.util.*;

/**
 * Text-based user interface for the Gacha Battle game.
 * Handles all menu displays, user input, and high-level orchestration.
 */
public class GameUI {
    private Player player;
    private GachaService gachaService;
    private BattleService battleService;
    private Scanner scanner;

    public GameUI() {
        this.scanner = new Scanner(System.in);
        initGame();
    }

    /**
     * Initializes the game: welcome message, player name, 1000 starting coins.
     * No free draw is given; player starts directly at main menu.
     */
    private void initGame() {
        System.out.println("========================================");
        System.out.println("      Welcome to Gacha Battle!");
        System.out.println("========================================");
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        this.player = new Player(name);
        this.gachaService = new GachaService();
        this.battleService = new BattleService(player);

        System.out.println("\n[INFO] You have 1000 coins. Draw cards to build your team!");
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }

    /**
     * Main game loop, displays main menu and processes choices.
     */
    public void start() {
        while (true) {
            showMainMenu();
            int choice = getIntInput("Choose: ");

            switch (choice) {
                case 1:
                    showGachaMenu();
                    break;
                case 2:
                    showBagAndTeamMenu();
                    break;
                case 3:
                    selectStageAndBattle();
                    break;
                case 4:
                    showPlayerStatus();
                    break;
                case 0:
                    System.out.println("[EXIT] Thanks for playing! Goodbye.");
                    return;
                default:
                    System.out.println("[X] Invalid choice, try again.");
            }
        }
    }

    /**
     * Displays the main menu with player's current stats.
     */
    private void showMainMenu() {
        System.out.println("\n========================================");
        System.out.println("           [ MAIN MENU ]");
        System.out.println("========================================");
        System.out.println("[COIN] Coins: " + player.getCoins());
        System.out.println("[STAGE] Unlocked: 1 - " + player.getMaxUnlockedStage());
        System.out.println("[BAG] Cards in bag: " + player.getBagSize());
        System.out.print("[FIGHT] Team size: " + player.getTeam().size());
        if (player.getTeam().isEmpty()) System.out.print(" (no team!)");
        System.out.println();
        System.out.println("========================================");
        System.out.println("1. Draw cards");
        System.out.println("2. Bag & Team");
        System.out.println("3. Battle");
        System.out.println("4. Player status");
        System.out.println("0. Exit game");
        System.out.println("========================================");
    }

    /**
     * Sub-menu for bag and team operations.
     */
    private void showBagAndTeamMenu() {
        while (true) {
            System.out.println("\n========== BAG & TEAM ==========");
            System.out.println("1. View all cards in bag");
            System.out.println("2. View current team");
            System.out.println("3. Set up battle team (choose from bag)");
            System.out.println("0. Back to main menu");
            System.out.print("Choose: ");

            int choice = getIntInput("");
            switch (choice) {
                case 1:
                    player.showBag();
                    break;
                case 2:
                    player.showTeam();
                    break;
                case 3:
                    setupTeamFromBag();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();
            scanner.nextLine();
        }
    }

    /**
     * Allows the player to select up to 3 cards from the bag to form the battle team.
     * Cards are displayed sorted by rank (S > A > B) and insertion order.
     */
    private void setupTeamFromBag() {
        List<Card> bag = player.getBag();
        if (bag.isEmpty()) {
            System.out.println("[X] No cards in bag. Draw some first.");
            return;
        }

        // Sort cards for display: by rank (S > A > B), then by order in bag (oldest first)
        List<Card> sorted = new ArrayList<>(bag);
        sorted.sort((c1, c2) -> {
            int order1 = getRankOrder(c1.getRank());
            int order2 = getRankOrder(c2.getRank());
            if (order1 != order2) return Integer.compare(order1, order2);
            else return Integer.compare(bag.indexOf(c1), bag.indexOf(c2));
        });

        System.out.println("\n===== SELECT YOUR TEAM (max 3 cards) =====");
        System.out.println("Available cards in bag (sorted by rank: S > A > B):");
        for (int i = 0; i < sorted.size(); i++) {
            System.out.printf("%d. ", i + 1);
            sorted.get(i).displayInfo();
        }
        System.out.println("Enter the numbers of the cards you want in your team (e.g., 1 3 5)");
        System.out.println("Max 3 cards. Press Enter with nothing to cancel.");
        System.out.print("Your choice: ");
        String line = scanner.nextLine().trim();
        if (line.isEmpty()) {
            System.out.println("Team unchanged.");
            return;
        }

        String[] parts = line.split("\\s+");
        Set<Integer> chosenIndexes = new HashSet<>();
        for (String part : parts) {
            try {
                int idx = Integer.parseInt(part) - 1;
                if (idx >= 0 && idx < sorted.size()) {
                    chosenIndexes.add(idx);
                } else {
                    System.out.println("Invalid number: " + (idx+1) + " ignored.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input: " + part + " ignored.");
            }
        }

        if (chosenIndexes.isEmpty()) {
            System.out.println("No valid cards selected. Team unchanged.");
            return;
        }

        List<Card> newTeam = new ArrayList<>();
        for (int idx : chosenIndexes) {
            if (newTeam.size() < 3) {
                newTeam.add(sorted.get(idx));
            } else {
                System.out.println("Team already has 3 cards, remaining selections ignored.");
                break;
            }
        }

        player.setTeam(newTeam);
        player.showTeam();
    }

    /**
     * Helper to get numeric rank order (S=1, A=2, B=3) for sorting.
     */
    private int getRankOrder(String rank) {
        switch (rank) {
            case "S": return 1;
            case "A": return 2;
            case "B": return 3;
            default: return 4;
        }
    }

    /**
     * Sub-menu for drawing cards (single or 10x).
     */
    private void showGachaMenu() {
        System.out.println("\n========== DRAW MENU ==========");
        System.out.println("1. Single draw (" + gachaService.getCostPerPull() + " coins)");
        System.out.println("2. 10x draw (" + (gachaService.getCostPerPull() * 10) + " coins)");
        System.out.println("0. Back to main menu");
        System.out.print("Choose: ");

        int choice = getIntInput("");
        switch (choice) {
            case 1:
                gachaService.pull(player);
                break;
            case 2:
                gachaService.tenPull(player);
                break;
            case 0:
                return;
            default:
                System.out.println("Invalid choice!");
        }

        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
        scanner.nextLine();
    }

    /**
     * Allows the player to choose a stage to battle, showing unlock and reward status.
     */
    private void selectStageAndBattle() {
        if (player.getTeam().isEmpty()) {
            System.out.println("[WARN] Your team is empty! Please set a team first (Bag & Team -> Set team).");
            return;
        }
        if (player.getBagSize() == 0) {
            System.out.println("[WARN] You have no cards at all. Draw some first.");
            return;
        }

        System.out.println("\n========== SELECT STAGE ==========");
        for (int i = 1; i <= Player.getTotalStages(); i++) {
            String status;
            if (i > player.getMaxUnlockedStage()) {
                status = "[LOCKED] locked";
            } else if (player.isFirstClear(i)) {
                status = "[REPEAT] repeatable (half reward)";
            } else {
                status = "[FIRST] first clear (full reward)";
            }
            System.out.printf("%d. Stage %d - %s%n", i, i, status);
        }
        System.out.println("0. Back to main menu");
        System.out.print("Choose stage: ");

        int choice = getIntInput("");
        if (choice >= 1 && choice <= Player.getTotalStages()) {
            battleService.startBattle(choice);
        } else if (choice != 0) {
            System.out.println("Invalid choice!");
        }

        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
        scanner.nextLine();
    }

    /**
     * Displays detailed player status: coins, stage progress, card count by rank, pity progress, and current team.
     */
    private void showPlayerStatus() {
        System.out.println("\n========== PLAYER STATUS ==========");
        System.out.println("[PLAYER] Name: " + player.getName());
        System.out.println("[COIN] Coins: " + player.getCoins());
        System.out.println("[STAGE] Unlocked stages: 1 - " + player.getMaxUnlockedStage());
        System.out.println("[BAG] Cards in bag: " + player.getBagSize());

        long sCount = player.getBag().stream().filter(c -> c.getRank().equals("S")).count();
        long aCount = player.getBag().stream().filter(c -> c.getRank().equals("A")).count();
        long bCount = player.getBag().stream().filter(c -> c.getRank().equals("B")).count();
        System.out.println("\nCard count by rank:");
        System.out.println("  S-rank: " + sCount);
        System.out.println("  A-rank: " + aCount);
        System.out.println("  B-rank: " + bCount);

        System.out.println("\n[TARGET] Pity progress:");
        System.out.println("  A-pity: " + gachaService.getAPityProgress() + "/10");
        System.out.println("  S-pity: " + gachaService.getSPityProgress() + "/80");

        System.out.println("\n[FIGHT] Current team:");
        player.showTeam();
        System.out.println("===================================");

        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
        scanner.nextLine();
    }

    /**
     * Safely reads an integer input from the user.
     * @param prompt Message to display before input
     * @return Valid integer entered by user
     */
    private int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Please enter a number: ");
            scanner.next();
        }
        int result = scanner.nextInt();
        scanner.nextLine();
        return result;
    }
}
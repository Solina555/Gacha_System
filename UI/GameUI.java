package UI;

import Model.*;
import Service.*;
import java.util.Scanner;

public class GameUI {
    private Player player;
    private GachaService gachaService;
    private BattleService battleService;
    private Scanner scanner;

    public GameUI() {
        this.scanner = new Scanner(System.in);
        initGame();
    }

    private void initGame() {
        System.out.println("========================================");
        System.out.println("      ✨ Welcome to Gacha Battle! ✨");
        System.out.println("========================================");
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        this.player = new Player(name);
        this.gachaService = new GachaService();
        this.battleService = new BattleService(player);

        System.out.println("\n🎁 Newbie gift: 1 free draw!");
        System.out.println("Press Enter to draw...");
        scanner.nextLine();
        gachaService.pull(player);
    }

    public void start() {
        while (true) {
            showMainMenu();
            int choice = getIntInput("Choose: ");

            switch (choice) {
                case 1:
                    showGachaMenu();
                    break;
                case 2:
                    player.showInventory();
                    break;
                case 3:
                    selectStageAndBattle();
                    break;
                case 4:
                    showPlayerStatus();
                    break;
                case 0:
                    System.out.println("👋 Thanks for playing! Goodbye.");
                    return;
                default:
                    System.out.println("❌ Invalid choice, try again.");
            }
        }
    }

    private void showMainMenu() {
        System.out.println("\n========================================");
        System.out.println("           【 MAIN MENU 】");
        System.out.println("========================================");
        System.out.println("💰 Coins: " + player.getCoins());
        System.out.println("📊 Current stage: " + player.getMaxUnlockedStage() + " (unlocked)");
        System.out.println("📦 Cards in inventory: " + player.getInventorySize());
        System.out.println("========================================");
        System.out.println("1. 🎲 Draw cards");
        System.out.println("2. 📦 View inventory");
        System.out.println("3. ⚔️ Battle");
        System.out.println("4. ℹ️ Player status");
        System.out.println("0. 🚪 Exit game");
        System.out.println("========================================");
    }

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

    private void selectStageAndBattle() {
        if (player.getInventorySize() == 0) {
            System.out.println("⚠️ Inventory empty! Draw some cards first.");
            return;
        }

        System.out.println("\n========== SELECT STAGE ==========");
        for (int i = 1; i <= Player.getTotalStages(); i++) {
            String status;
            if (i > player.getMaxUnlockedStage()) {
                status = "🔒 locked";
            } else if (player.isFirstClear(i)) {
                status = "🔄 repeatable (half reward)";
            } else {
                status = "🎁 first clear (full reward)";
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

    private void showPlayerStatus() {
        System.out.println("\n========== PLAYER STATUS ==========");
        System.out.println("👤 Name: " + player.getName());
        System.out.println("💰 Coins: " + player.getCoins());
        System.out.println("🔓 Unlocked stages: 1 - " + player.getMaxUnlockedStage());
        System.out.println("📦 Cards owned: " + player.getInventorySize());

        long sCount = player.getTeam().stream().filter(c -> c.getRank().equals("S")).count();
        long aCount = player.getTeam().stream().filter(c -> c.getRank().equals("A")).count();
        long bCount = player.getTeam().stream().filter(c -> c.getRank().equals("B")).count();
        System.out.println("\nCard count by rank:");
        System.out.println("  S-rank: " + sCount);
        System.out.println("  A-rank: " + aCount);
        System.out.println("  B-rank: " + bCount);

        System.out.println("\n🎯 Pity progress:");
        System.out.println("  A-pity: " + gachaService.getAPityProgress() + "/10");
        System.out.println("  S-pity: " + gachaService.getSPityProgress() + "/80");
        System.out.println("===================================");

        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
        scanner.nextLine();
    }

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
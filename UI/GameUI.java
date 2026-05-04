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
        System.out.println("      ✨ 欢迎来到抽卡闯关世界 ✨");
        System.out.println("========================================");
        System.out.print("请输入你的名字: ");
        String name = scanner.nextLine();
        
        this.player = new Player(name);
        this.gachaService = new GachaService();
        this.battleService = new BattleService(player);
        
        System.out.println("\n🎁 新手礼包：赠送1次免费抽卡！");
        System.out.println("按回车键开始抽卡...");
        scanner.nextLine();
        gachaService.pull(player);
    }
    
    public void start() {
        while (true) {
            showMainMenu();
            int choice = getIntInput("请选择: ");
            
            switch (choice) {
                case 1:
                    showGachaMenu();
                    break;
                case 2:
                    player.showInventory();
                    break;
                case 3:
                    startBattle();
                    break;
                case 4:
                    showPlayerStatus();
                    break;
                case 0:
                    System.out.println("👋 感谢游玩，再见！");
                    return;
                default:
                    System.out.println("❌ 无效选择，请重新输入！");
            }
        }
    }
    
    private void showMainMenu() {
        System.out.println("\n========================================");
        System.out.println("         【主菜单】");
        System.out.println("========================================");
        System.out.println("💰 当前游戏币: " + player.getCoins());
        System.out.println("📊 当前关卡: 第 " + player.getCurrentStage() + " 关");
        System.out.println("📦 背包卡片数: " + player.getInventorySize());
        System.out.println("========================================");
        System.out.println("1. 🎲 抽卡");
        System.out.println("2. 📦 查看背包");
        System.out.println("3. ⚔️ 闯关战斗");
        System.out.println("4. ℹ️ 查看状态");
        System.out.println("0. 🚪 退出游戏");
        System.out.println("========================================");
    }
    
    private void showGachaMenu() {
        System.out.println("\n========== 抽卡系统 ==========");
        System.out.println("1. 单抽 (" + gachaService.getCostPerPull() + " 游戏币)");
        System.out.println("2. 十连抽 (" + (gachaService.getCostPerPull() * 10) + " 游戏币)");
        System.out.println("0. 返回主菜单");
        System.out.print("请选择: ");
        
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
                System.out.println("无效选择！");
        }
        
        System.out.println("\n按回车键继续...");
        scanner.nextLine();
        scanner.nextLine();
    }
    
    private void startBattle() {
        if (player.getInventorySize() == 0) {
            System.out.println("⚠️ 背包空空如也，无法战斗！先去抽卡吧！");
            return;
        }
        
        boolean result = battleService.startBattle();
        if (result && player.getCurrentStage() > 5) {
            System.out.println("\n🎊🎊🎊 恭喜通关！游戏通关！ 🎊🎊🎊");
            System.out.println("感谢你的游玩！");
            System.exit(0);
        }
        
        System.out.println("\n按回车键继续...");
        scanner.nextLine();
        scanner.nextLine();
    }
    
    private void showPlayerStatus() {
        System.out.println("\n========== 玩家状态 ==========");
        System.out.println("👤 玩家: " + player.getName());
        System.out.println("💰 游戏币: " + player.getCoins());
        System.out.println("📊 当前关卡: 第 " + player.getCurrentStage() + " 关");
        System.out.println("📦 卡片数量: " + player.getInventorySize());
        
        // 统计各等级卡片数量
        long sCount = player.getTeam().stream().filter(c -> c.getRank().equals("S")).count();
        long aCount = player.getTeam().stream().filter(c -> c.getRank().equals("A")).count();
        long bCount = player.getTeam().stream().filter(c -> c.getRank().equals("B")).count();
        
        System.out.println("\n卡片统计:");
        System.out.println("  S级: " + sCount + " 张");
        System.out.println("  A级: " + aCount + " 张");
        System.out.println("  B级: " + bCount + " 张");
        System.out.println("=============================");
        
        System.out.println("\n按回车键继续...");
        scanner.nextLine();
        scanner.nextLine();
    }
    
    private int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("请输入数字: ");
            scanner.next();
        }
        int result = scanner.nextInt();
        scanner.nextLine();  // 清除换行符
        return result;
    }
}
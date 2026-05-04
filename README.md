# Gacha_System
gacha-system/
├── src/
│   ├── Main.java                    # 主程序入口
│   ├── model/                       # 数据模型层
│   │   ├── Card.java                # 卡片基类（抽象类）
│   │   ├── SCard.java               # S级卡片
│   │   ├── ACard.java               # A级卡片
│   │   ├── BCard.java               # B级卡片
│   │   └── Player.java              # 玩家类（背包、闯关进度）
│   ├── service/                     # 业务逻辑层
│   │   ├── GachaService.java        # 抽卡服务
│   │   ├── BattleService.java       # 战斗服务
│   │   └── CardFactory.java         # 卡片工厂（创建不同卡片）
│   └── ui/                          # 用户界面层
│       └── GameUI.java              # 游戏主界面
package eq.larry.dev.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

import eq.larry.dev.UltraObjectif;
import eq.larry.dev.utils.DateBuilderTimer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class Objectif {
    static final HashMap<String, Objectif> objectifs = new HashMap();
    private ArrayList<UUID> players = new ArrayList();
    public Material material;
    ArrayList<Block> ignoredBlocks = new ArrayList();
    public int amount;
    public int current;
    public String name;
    public Reward reward;
    public BossBar bossBar;
    public String bossBarTitle;
    public Integer seconds;
    public String win;
    public BukkitTask task;
    public String begin;
    public String end;
    public int data;
    public String description;
    public DateBuilderTimer dateBuilderTimer;

    public Objectif(Material material, int data, int amount, String name, Reward reward, String bossBarName, String description, Integer seconds) {
        this.material = material;
        this.amount = amount;
        this.data = data;
        this.name = name;
        this.reward = reward;
        this.bossBarTitle = bossBarName;
        this.bossBar = Bukkit.createBossBar(bossBarName, BarColor.RED, BarStyle.SOLID, new BarFlag[0]);
        this.seconds = seconds;
        this.win = Configurator.win.replaceAll("%reward%", reward.getName());
        this.begin = Configurator.begin;
        this.end = Configurator.end;
        this.description = description;
        this.genTitle();
    }

    public void genTitle() {
        String i = "";
        if (this.dateBuilderTimer != null) {
            i = this.dateBuilderTimer.getBuild();
        }

        this.bossBar.setTitle(this.bossBarTitle.replaceAll("%progress%", String.valueOf(this.current)).replaceAll("%max%", String.valueOf(this.amount)).replaceAll("%time%", i));
        this.bossBar.setProgress((double)this.current / (double)this.amount);
    }

    public void addCurrent() {
        ++this.current;
        this.genTitle();
        if (this.current >= this.amount) {
            this.players.forEach((uuid) -> {
                Player player = Bukkit.getPlayer(uuid);
                if (player != null) {
                    this.reward.execute(player);
                    player.sendMessage(this.win);
                    if (this.task != null) {
                        this.task.cancel();
                    }
                } else {
                    Reward.getListeDesFDPQuiSeDeco().put(uuid, this.reward);
                }

            });
            UltraObjectif.getInstance().setCurrentObjectif((Objectif)null);
            this.ignoredBlocks.clear();
            this.players.clear();
            Iterator var1 = Bukkit.getOnlinePlayers().iterator();

            while(var1.hasNext()) {
                Player player = (Player)var1.next();
                this.bossBar.removePlayer(player);
                player.sendMessage(this.end);
            }
        }

    }

    public void launch() {
        UltraObjectif.getInstance().setCurrentObjectif(this);
        if (this.seconds != null) {
            this.dateBuilderTimer = new DateBuilderTimer((long)(this.seconds * 1000));
            this.dateBuilderTimer.loadDate();
        }

        Iterator var1 = Bukkit.getOnlinePlayers().iterator();

        while(var1.hasNext()) {
            Player player = (Player)var1.next();
            this.bossBar.addPlayer(player);
            player.sendMessage(this.begin);
            String[] var3 = this.description.split("\\\\n");
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                String s = var3[var5];
                player.sendMessage(s);
            }
        }

        if (this.seconds != null) {
            this.task = Bukkit.getScheduler().runTaskTimerAsynchronously(UltraObjectif.getInstance(), new Runnable() {
                int time;

                {
                    this.time = Objectif.this.seconds;
                }

                public void run() {
                    Objectif.this.dateBuilderTimer.loadDate();
                    --this.time;
                    if (this.time <= 0) {
                        Objectif.this.stop();
                        Objectif.this.task.cancel();
                    }

                    Objectif.this.genTitle();
                }
            }, 0L, 20L);
        }

    }

    public void stop() {
        UltraObjectif.getInstance().setCurrentObjectif((Objectif)null);
        this.ignoredBlocks.clear();
        this.players.clear();
        Iterator var1 = Bukkit.getOnlinePlayers().iterator();

        while(var1.hasNext()) {
            Player player = (Player)var1.next();
            this.bossBar.removePlayer(player);
            player.sendMessage(this.end);
        }

        if (this.task != null) {
            this.task.cancel();
        }

    }

    public ArrayList<UUID> getPlayers() {
        return this.players;
    }

    public Material getMaterial() {
        return this.material;
    }

    public ArrayList<Block> getIgnoredBlocks() {
        return this.ignoredBlocks;
    }

    public int getAmount() {
        return this.amount;
    }

    public int getCurrent() {
        return this.current;
    }

    public String getName() {
        return this.name;
    }

    public Reward getReward() {
        return this.reward;
    }

    public BossBar getBossBar() {
        return this.bossBar;
    }

    public String getBossBarTitle() {
        return this.bossBarTitle;
    }

    public Integer getSeconds() {
        return this.seconds;
    }

    public String getWin() {
        return this.win;
    }

    public BukkitTask getTask() {
        return this.task;
    }

    public String getBegin() {
        return this.begin;
    }

    public String getEnd() {
        return this.end;
    }

    public int getData() {
        return this.data;
    }

    public String getDescription() {
        return this.description;
    }

    public DateBuilderTimer getDateBuilderTimer() {
        return this.dateBuilderTimer;
    }

    public static HashMap<String, Objectif> getObjectifs() {
        return objectifs;
    }
}
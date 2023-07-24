package eq.larry.dev.Listerners;

import eq.larry.dev.UltraObjectif;
import eq.larry.dev.config.Reward;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class ObjectifListener implements Listener {
    UltraObjectif plugin;

    public ObjectifListener(UltraObjectif plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBreak(BlockBreakEvent e) {
        if (e.isCancelled())
            return;
        if (this.plugin.getCurrentObjectif() != null &&
                this.plugin.getCurrentObjectif().getMaterial() == e.getBlock().getType()) {
            if ((this.plugin.getCurrentObjectif()).data != -1 &&
                    e.getBlock().getData() != (byte)(this.plugin.getCurrentObjectif()).data)
                return;
            if (!this.plugin.getCurrentObjectif().getIgnoredBlocks().contains(e.getBlock())) {
                if (!this.plugin.getCurrentObjectif().getPlayers().contains(e.getPlayer().getUniqueId()))
                    this.plugin.getCurrentObjectif().getPlayers().add(e.getPlayer().getUniqueId());
                this.plugin.getCurrentObjectif().addCurrent();
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if (this.plugin.getCurrentObjectif() != null &&
                this.plugin.getCurrentObjectif().getMaterial() == e.getBlock().getType())
            this.plugin.getCurrentObjectif().getIgnoredBlocks().add(e.getBlock());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (this.plugin.getCurrentObjectif() != null)
            (this.plugin.getCurrentObjectif()).bossBar.addPlayer(e.getPlayer());
        if (Reward.getListeDesFDPQuiSeDeco().containsKey(e.getPlayer().getUniqueId())) {
            ((Reward)Reward.getListeDesFDPQuiSeDeco().get(e.getPlayer().getUniqueId())).execute(e.getPlayer());
            Reward.getListeDesFDPQuiSeDeco().remove(e.getPlayer().getUniqueId());
        }
    }
}

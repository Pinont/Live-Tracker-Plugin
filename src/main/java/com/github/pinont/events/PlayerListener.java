package com.github.pinont.events;

import com.github.pinont.Core;
import com.github.pinont.live.Livestream;
import com.github.pinont.live.Platform;
import com.github.pinont.singularitylib.api.annotation.AutoRegister;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

@AutoRegister
public class PlayerListener implements Listener {

    private final Livestream livestream = Core.LIVESTREAM;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String channelName = livestream.getChannelName(player);
        Platform platform = livestream.getPlatform(player);
        if (platform == null) {
            livestream.setLiveDisplayName(player, false);
            return;
        }
        livestream.setLiveDisplayName(player, livestream.isLive(channelName, platform));
    }
}

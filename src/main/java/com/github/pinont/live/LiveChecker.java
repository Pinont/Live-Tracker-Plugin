package com.github.pinont.live;

import com.github.pinont.Core;
import com.github.pinont.singularitylib.api.manager.ConfigManager;
import com.github.pinont.singularitylib.api.runnable.Scheduler;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class LiveChecker {

    private ConfigManager configManager = Core.CONFIG_MANAGER;
    private final Scheduler liveChecker = Core.SCHEDULER;
    private final Livestream livestream = Core.LIVESTREAM;

    private void fetch() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            String channelName = livestream.getChannelName(player);
            Platform platform = livestream.getPlatform(player);
            if (platform == null) {
                livestream.setLiveDisplayName(player, false);
                return;
            }
            livestream.setLiveDisplayName(player, livestream.isLive(channelName, platform));
        }
    }

    public void start() {
        if (configManager.getConfig().get("check_every") == null) {
            configManager.getConfig().set("check_every", 5); // default to 5 minutes
            configManager.getConfig().setComments("check_every", Collections.singletonList("this is a minute value"));
            configManager.saveConfig();
        }
        int delay = Math.min(configManager.getConfig().getInt("check_every"), 1);
        if (configManager.getConfig().getInt("check_every") < delay) {
            configManager.getConfig().set("check_every", delay);
            configManager.saveConfig();
        }
        liveChecker.runRepeatingTaskAsync(this::fetch, 0, delay, TimeUnit.MINUTES);
    }

    public void reload(CommandSender sender) {
        liveChecker.cancelTask();
        configManager = Core.CONFIG_MANAGER;
        start();
        sender.sendMessage("Live Tracker config reloaded.");
    }
}

package com.github.pinont.live;

import com.github.pinont.Core;
import com.github.pinont.singularitylib.api.manager.ConfigManager;
import com.github.pinont.singularitylib.api.utils.Common;
import com.github.pinont.singularitylib.api.utils.Console;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.Collections;

public class LiveChecker {

    private ConfigManager configManager = Core.CONFIG_MANAGER;
    private final BukkitScheduler liveChecker = Core.SCHEDULER;
    private final Livestream livestream = Core.LIVESTREAM;

    public void start() {
        if (configManager.getConfig().get("check_every") == null) {
            configManager.getConfig().set("check_every", 5); // default to 5 minutes
            configManager.getConfig().setComments("check_every", Collections.singletonList("this is a minute value"));
            configManager.saveConfig();
        }

        int delay = Math.min(configManager.getConfig().getInt("check_every"), 1);
        if (configManager.getConfig().getInt("check_every") < delay) {
            Console.logError("Seem like your check_every value is invalid, setting it to " + delay + " minute(s).");
            configManager.getConfig().set("check_every", delay);
            configManager.saveConfig();
        }

        liveChecker.runTaskTimerAsynchronously(Common.plugin, new Runnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    String channelName = livestream.getChannelName(player);
                    Platform platform = livestream.getPlatform(player);
                    if (platform == null) {
                        Console.logError("Platform for player " + player.getName() + " is invalid. Please set it to YOUTUBE/YT TWITCH/TW or TIKTOK/TT");
                        continue;
                    } else if (channelName.isEmpty()) {
                        continue;
                    }
                    livestream.setLiveDisplayName(player, livestream.isLive(channelName, platform));
                }
            }
        }, 0, (long) delay * 20 * 60); // every X minutes
    }

    public void reload(CommandSender sender) {
        liveChecker.cancelTasks(Common.plugin);
        configManager = Core.CONFIG_MANAGER;
        start();
        sender.sendMessage("Live Tracker config reloaded.");
    }
}

package com.github.pinont.live;

import com.github.pinont.Core;
import com.github.pinont.singularitylib.api.manager.ConfigManager;
import com.github.pinont.singularitylib.api.utils.Common;
import com.github.pinont.singularitylib.api.utils.Console;
import io.papermc.paper.threadedregions.scheduler.AsyncScheduler;
import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.Collections;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class LiveChecker {

    private ConfigManager configManager = Core.CONFIG_MANAGER;
    private final BukkitScheduler liveChecker = Core.SCHEDULER;
    private final AsyncScheduler asyncScheduler = Core.ASYNC_SCHEDULER;
    private final Livestream livestream = Core.LIVESTREAM;
    private final boolean isFolia = Core.isFolia;

    private void runner(int delay) {
        liveChecker.runTaskTimerAsynchronously(Common.plugin, new Runnable() {
            @Override
            public void run() {
                fetch();
            }
        }, 0, (long) delay * 20 * 60); // every X minutes
    }

    private void runnerFolia(int delay) {
        asyncScheduler.runAtFixedRate(Common.plugin, new Consumer<ScheduledTask>() {
            @Override
            public void accept(ScheduledTask scheduledTask) {
                fetch();
            }
        }, 0, delay, TimeUnit.MINUTES); // every X minutes
    }

    private void fetch() {
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
        if (isFolia) {
            runnerFolia(delay);
        } else {
            runner(delay);
        }
    }

    public void reload(CommandSender sender) {
        if (isFolia) {
            asyncScheduler.cancelTasks(Common.plugin);
        } else {
            liveChecker.cancelTasks(Common.plugin);
        }
        configManager = Core.CONFIG_MANAGER;
        start();
        sender.sendMessage("Live Tracker config reloaded.");
    }
}

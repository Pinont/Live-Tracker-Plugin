package com.github.pinont;

import com.github.pinont.live.LiveChecker;
import com.github.pinont.live.Livestream;
import com.github.pinont.singularitylib.api.manager.ConfigManager;
import com.github.pinont.singularitylib.plugin.CorePlugin;
import io.papermc.paper.threadedregions.scheduler.AsyncScheduler;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitScheduler;

import static org.bukkit.Bukkit.getAsyncScheduler;

public final class Core extends CorePlugin {

    // MAIN INSTANCES
    public static ConfigManager CONFIG_MANAGER;
    public static BukkitScheduler SCHEDULER;
    public static AsyncScheduler ASYNC_SCHEDULER;

    public static boolean isFolia = false;

    // LIVE TRACKER INSTANCES
    public static Livestream LIVESTREAM;
    public static LiveChecker LIVECHECKER;

    // UNIVERSAL RELOAD METHOD
    public void reload(CommandSender sender) {
        CONFIG_MANAGER = new ConfigManager("config.yml");
        LIVESTREAM.loadPrefix();
        LIVECHECKER.reload(sender);
    }

    @Override
    public void onPluginStart() {
        // INIT
        CONFIG_MANAGER = new ConfigManager("config.yml");

        if (isFolia()) {
            isFolia = true;
            ASYNC_SCHEDULER = getAsyncScheduler();
        } else {
            SCHEDULER = Bukkit.getScheduler();
        }

        LIVESTREAM = new Livestream();
        LIVECHECKER = new LiveChecker();

        // LOAD
        LIVESTREAM.loadPrefix();
        LIVECHECKER.start();
    }

    private static boolean isFolia() {
        try {
            Class.forName("io.papermc.paper.threadedregions.RegionizedServer");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    @Override
    public void onPluginStop() {

    }
}

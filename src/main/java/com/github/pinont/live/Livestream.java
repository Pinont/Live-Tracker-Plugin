package com.github.pinont.live;

import com.github.pinont.Core;
import com.github.pinont.singularitylib.api.manager.ConfigManager;
import com.github.pinont.singularitylib.api.utils.Common;
import com.github.pinont.singularitylib.api.utils.Console;
import org.bukkit.entity.Player;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Livestream {

    private String ytPrefix;
    private String twitchPrefix;
    private String tiktokPrefix;
    private ConfigManager configManager = Core.CONFIG_MANAGER;
    private static final List<String> errorList = new ArrayList<>();

    public boolean isYoutubeLive(String channelName) {
        String url = "https://www.youtube.com/@" + channelName + "/live";

        try {
            // Send GET request with a desktop User-Agent
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                    .get();

            String html = doc.html();

            if (html.contains("isLiveNow")) {
                return true;
            }

        } catch (IOException e) {
            errorList.add(channelName);
            Console.logError("Error checking channel: " + e.getMessage());
        }
        return false;
    }

    public boolean isTwitchLive(String channelName) {
        String url = "https://www.twitch.tv/" + channelName;

        try {
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                    .get();

            String html = doc.html();

            if (html.contains("\"isLiveBroadcast\":true") || html.contains("\"isLive\":true")) {
                return true;
            }
        } catch (IOException e) {
            errorList.add(channelName);
            Console.logError("Error checking Twitch channel: " + e.getMessage());
        }
        return false;
    }

    public static boolean isTikTokLive(String username) {
        String url = "https://www.tiktok.com/@" + username;

        try {
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                    .followRedirects(true)
                    .get();

            String html = doc.html();
            String finalUrl = doc.location(); // final URL after redirects

            if (finalUrl.contains("/live") || html.contains("LIVE") || html.contains("is_live_broadcast")) {
                return true;
            }
        } catch (IOException e) {
            errorList.add(username);
            Console.logError("Error checking TikTok user: " + e.getMessage());
        }

        return false;
    }

    public boolean isLive(String channelName, Platform platform) {
        errorList.remove(channelName);
        return switch (platform) {
            case YOUTUBE -> isYoutubeLive(channelName);
            case TWITCH -> isTwitchLive(channelName);
            case TIKTOK -> isTikTokLive(channelName);
            case null -> false;
        };
    }

    public String getChannelName(Player player) {
        String channelName = configManager.getConfig().getString(player.getName() + "." + "channel_name");
        if (channelName == null || channelName.isEmpty()) {
            configManager.getConfig().set(player.getName() + "." + "channel_name", "");
            configManager.saveConfig();
            return "";
        }
        return channelName;
    }

    public Platform getPlatform(Player player) {
        String platformStr = configManager.getConfig().getString(player.getName() + "." + "platform");
        if (platformStr == null || platformStr.isEmpty()) {
            configManager.getConfig().set(player.getName() + "." + "platform", "");
            configManager.saveConfig();
            return null;
        }
        return Platform.fromString(platformStr);
    }

    public void loadPrefix() {
        final String defaultPrefix = "<red>[LIVE] </red><reset>";
        configManager = Core.CONFIG_MANAGER;
        ytPrefix = configManager.getConfig().getString("yt_live_prefix");
        if (ytPrefix == null || ytPrefix.isEmpty()) {
            configManager.set("yt_live_prefix", defaultPrefix);
            configManager.getConfig().setComments("yt_live_prefix", List.of("Prefix to show on PlayerList for live streams who's streaming", "hint: follow color formatting in https://docs.papermc.io/adventure/minimessage/"));
            configManager.saveConfig();
            ytPrefix = configManager.getConfig().getString("yt_live_prefix");
        }
        twitchPrefix = configManager.getConfig().getString("tw_live_prefix");
        if (twitchPrefix == null || twitchPrefix.isEmpty()) {
            configManager.set("tw_live_prefix", defaultPrefix);
            configManager.saveConfig();
            twitchPrefix = configManager.getConfig().getString("tw_live_prefix");
        }
        tiktokPrefix = configManager.getConfig().getString("tt_live_prefix");
        if (tiktokPrefix == null || tiktokPrefix.isEmpty()) {
            configManager.set("tt_live_prefix", defaultPrefix);
            configManager.saveConfig();
            tiktokPrefix = configManager.getConfig().getString("tt_live_prefix");
        }
    }

    public void setLiveDisplayName(Player player, boolean isLive) {
        Platform platform = getPlatform(player);
        String finalLivePrefix = switch (platform) {
            case YOUTUBE -> ytPrefix;
            case TWITCH -> twitchPrefix;
            case TIKTOK -> tiktokPrefix;
            case null -> "";
        };
        if (errorList.contains(getChannelName(player))) return; // skip if there was an error checking

        String newName = finalLivePrefix + player.getName();

        if (!newName.equals(player.getName())) {
            if (isLive) {
                player.displayName(new Common().colorize(newName));
                player.playerListName(new Common().colorize(newName));
                return;
            }
            player.displayName(new Common().colorize(player.getName()));
            player.playerListName(new Common().colorize(player.getName()));
        }
    }
}

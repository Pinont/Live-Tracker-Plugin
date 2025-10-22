package com.github.pinont.live;

import javax.annotation.Nullable;

public enum Platform {
    YOUTUBE,
    TWITCH,
    TIKTOK;

    @Nullable
    public static Platform fromString(String platformStr) {
        return switch (platformStr.toLowerCase()) {
            case "youtube", "yt" -> YOUTUBE;
            case "twitch", "tw" -> TWITCH;
            case "tiktok", "tt" -> TIKTOK;
            default -> null;
        };
    }
}

# LiveTracker Plugin
![](https://img.shields.io/badge/folia_support-true-blue) ![](https://img.shields.io/badge/paperapi_version-1.21.8+-green) ![](https://img.shields.io/badge/singularityapi_version-55b1c7ed4d-purple)

A minecraft Paper plugin that tracks and displays player stream status via PlayerTabList, supporting Twitch, YouTube, and TikTok.

## Configuration
```yaml
# only for developers
debug: false
# Prefix to show on PlayerList for live streams who's streaming
yt_live_prefix: '<red>[LIVE] </red><reset>'
tw_live_prefix: '<red>[LIVE] </red><reset>'
tt_live_prefix: '<red>[LIVE] </red><reset>'
# this is a minute value
check_every: 1
mc_name: # e.g., 'Pinont_'
  channel_name: 'channel_name_here' # e.g., 'pinont_'
  platform: 'platform_here' # e.g., 'twitch', 'tw' , 'youtube', 'yt', 'tiktok', 'tt'
```

## License
This project is licensed under the MIT License. See the LICENSE file for details.


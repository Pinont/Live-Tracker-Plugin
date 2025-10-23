# LiveTracker Plugin
[![](https://img.shields.io/badge/folia_support-1.21.8+-blue)](https://docs.papermc.io/paper/dev/folia-support/) [![](https://img.shields.io/badge/paperapi_version-1.21.8+-green)](https://jd.papermc.io/paper/1.21.8/) [![](https://img.shields.io/badge/singularityapi_version-55b1c7ed4d-purple)](https://github.com/Pinont/SingularityLib)

A minecraft Paper plugin that tracks and displays player stream status via PlayerTabList, supporting Twitch, YouTube, and TikTok.

## Config.yml Exmaple
```yaml
# only for developers
debug: false
# Prefix to show on PlayerList for live streams who's streaming
# hint: follow color formatting in https://docs.papermc.io/adventure/minimessage/
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


# SingularityPlugin
![](https://img.shields.io/badge/paperapi_version-1.21.8-green)

A plugin template for Minecraft plugin development using SingularityLib. This project is designed for PaperMC 1.21 and provides a starting point for building custom plugins.

## Features
- Easy setup for new plugins
- Uses SingularityLib for extended functionality
- Configurable via `paper-plugin.yml`

## Installation
1. Build the plugin using Maven:
   ```cmd
   mvn clean package
   ```
2. Place the generated JAR file from `target/` into your server's `plugins` folder.
3. Start your PaperMC server (version 1.21 or later).

## Usage
Once installed, the plugin will load at server startup. You can configure its behavior in the `paper-plugin.yml` file located in `src/main/resources`.

## Configuration
Edit the `paper-plugin.yml` to change plugin settings such as name, version, prefix, and description.

## Contributing
Contributions are welcome! Please fork the repository and submit a pull request.

## License
This project is licensed under the MIT License. See the LICENSE file for details.


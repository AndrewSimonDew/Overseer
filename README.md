[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

---

# Overseer ⚡️

**Overseer** is a sleek and powerful player management plugin for Velocity proxy servers. Keep track of your players, manage server populations, and move players around your network effortlessly — all with simple commands and robust logging.

---

## Features 🎮

- `/playercount <server>`  
  Shows how many players are currently on a specified server.  
  **Permission:** `overseer.playercount`

- `/playerinfo <player>`  
  Displays detailed info about a player, including:  
  - Username  
  - UUID  
  - Current server  
  - Ping  
  - Client brand  
  Nicely formatted for easy reading.  
  **Permission:** `overseer.playerinfo`

- `/proxycount`  
  Shows the total number of players connected to the entire proxy.  
  **Permission:** `overseer.proxycount`

- `/send <player> <server>`  
  Send a specific player to another server on the proxy.  
  **Permission:** `overseer.send`

- `/sendall <server>`  
  Send **all** connected players to the specified server.  
  **Permission:** `overseer.sendall`

- **Logging** 📝  
  Logs player join events and server switches across the proxy for better monitoring and auditing.

---

## Permissions 🔐

| Permission              | Description                         |
|-------------------------|-----------------------------------|
| `overseer.playercount`  | Allows usage of `/playercount`     |
| `overseer.playerinfo`   | Allows usage of `/playerinfo`      |
| `overseer.proxycount`   | Allows usage of `/proxycount`      |
| `overseer.send`         | Allows usage of `/send`            |
| `overseer.sendall`      | Allows usage of `/sendall`         |

---

## Installation 🚀

1. Download the latest release `.jar` file from the [Releases](https://github.com/AndrewSimonDew/Overseer/releases) page
2. Place the `.jar` file into your Velocity proxy's `plugins` folder.  
3. Restart or reload your Velocity proxy server.  
4. Configure permissions as needed with your permissions plugin.

---

## Developer 🤖

Created and maintained by **Andrex**

---

## License 📄

This project is licensed under the MIT License - see the [LICENSE](https://github.com/AndrewSimonDew/Overseer?tab=MIT-1-ov-file) file for details.

---


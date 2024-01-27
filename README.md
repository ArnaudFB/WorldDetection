# WorldDetection

Use this plugin to alert your players they are in the wrong world to gather resources. This can be useful if you have multiple worlds for your server.

## Prerequisites

This plugin is meant to be used alongside with MultiVerse-Core.

## Usage

Use /wd reload to reload plugin's config file.

## Config file

In the config file, change these sections according to your needs :

* BlockLimit : Threshold to trigger alerts. Only positive integers.
* BlockList : Blocks to be taken into account for the detection. Has to be in the format of Minecraft material.
* DelayBetweenMessage : Delay (in seconds) between 2 alert messages.
* DelayResetCounter : Delay (in seconds) before the counter resets if no blocks are mined.
* WorldList : The worlds where you want the detection to be active.

## Permissions

* detection.bypass : When set to true, allows a player to bypass alerts.
* detection.reload : Allows one to reload config file. default : op.
* detection.debug : Allows one to see plugin in function in real time to debug. default : op

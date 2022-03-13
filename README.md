Want to help translate this mod to your language?
======================================
You can help me translate this mod on https://crowdin.com/project/worldtime.

Mod description
===============
With 1.14 and 1.15, keeping track of the current time became more important, because of villager working hours and bee flying hours. This little mod, in its original version, showed the current time on the ingame screen, in the top left corner.

The current version of the mod allows you to see real time ("wallclock time") and your position (X/Y/Z) as well, lets you chose the format to display those, and select any position on the screen.

Configuration
==========

With Fabric, you can either use ModMenu's cog wheel button (recommended), or, if you don't have ModMenu, click the book icon in the top right corner of the pause screen.

With Forge, there is a keybind (normally unbound) to access the config. Go to Options/Controls, assign a key to the WorldTime config, press that key ingame. Once you're done configuring, you can delete the keybind.

With both mod loaders, please make sure to use a small GUI scale to use the config menu, as scrolling isn't implemented. You can reset the GUI scale once your config is done.

Date and time format
==============

You can display the time and date in any format you want; default is 24 hours. A list of all date/time format strings is at https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html, in addition, the world time format allows a 'J' to display the current ingame world day number.

Examples:

- 'HH:mm:ss' is the default format and gets you hour (24 hour format), minutes, and seconds
- 'hh:mm:ss a' gives you hour (12 hour format), minutes, seconds, and an am/pm marker
- use 'yyyy-MM-dd' if you want the date in iso format, or 'MM/dd/yyyy' for US format, or 'dd.MM.yyyy' for German format, or even 'MMM d, yy' for something like 'Oct 5, 21' Of course dates won't make sense in ingame time.


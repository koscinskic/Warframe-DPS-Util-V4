# Warframe-DPS-Util-V4

A WIP version of my personal DPS optimizer for [Warframe](https://warframe.fandom.com/wiki/WARFRAME_Wiki)

# Installation

#### This program is intended to operate using the latest JDK provided on [Oracle's Site](https://www.oracle.com/technetwork/java/javase/downloads/index.html)

#### For Windows Users:
Run compile.bat and run.bat provided under the src folder

#### For Linux and Mac Users:
Open a terminal in the root directory of your local installation and run the
following commands:
* cd src
* javac \*.java -d ../classes
* cd ../classes
* java Main

# Usage

#### Current State

By default, all of the mods necessary for modifying conventional assault rifles
and pistols are contained within the mods folder under data. Due to the limited
capability of the program, only mods capable of altering the basic theoretical
DPS of a weapon are included. Complicated matters such as weapon accuracy,
status procs, and multishot technicalities will be included in a future update
and currently require some intuition on the user's behalf.

#### Adding Weapons

Only a sample of weapons are provided to demonstrate the usage of the program. Follow these steps in order to add new weapons:

* Locate and open Template.xlsx under the docs folder
* Replace all of the fields marked with a "<>" symbol with their relevant statistics as provided on the Warframe Wiki
* In specific regards to the "Type" field, enter the weapon's type as determined by the weapon's mod capabilities (e.g. Soma Prime is an assault rifle since it can equip primary, rifle, and assault rifle mods)
* Save the file as Weapon_Name (e.g. Soma_Prime) with the .csv file extension.
* Place the file under its appropriate folder under weapons

#### Execution

The program will open with an inquiry concerning your weapon choice. After selecting a weapon, all of the mods capable of being equipped on said weapon will be displayed. Then, blacklist any mods you do not want to be considered during the optimization process, and whitelist any mods that you want to include regardless of their effect on DPS. Provided there are enough mods to compose a full loadout, elect either burst or sustained DPS to be prioritized, and your optimal combination will be calculated.

# Links

For those either new to or unfamiliar with the inner workings of Warframe's mod system and damage algorithm, here are a few links for concepts with a brief summary of their role in the calculation:

#### [Weapons](https://warframe.fandom.com/wiki/Weapons)
Weapons in Warframe are quite often unorthodox and complicated in both behavior and statistical representation. The vast majority of weapons in Warframe split their damage across multiple physical and elemental channels, with each shot having a random chance to inflict bonus damage as determined by a critical percentage chance and critical damage bonus multiplier. Furthermore, there is a separate chance of shots to impart lasting effects referred to as status procs, which can debuff enemies and increase effective DPS in various ways.

#### [Damage](https://warframe.fandom.com/wiki/Damage)
Focusing specifically on the channels of damage a weapon inflicts, different damage types can either increase or decrease a weapon's effective DPS against different factions of enemies within the game in addition to inflicting different types of status procs.

#### [Critical Hits](https://warframe.fandom.com/wiki/Critical_Hit)
Carefully balancing the percentage and multiplier of a weapon's critical hits can drastically increase the DPS of the majority of weapons in Warframe. These percentages can exceed 100%, and each successive tier imparts a stacking bonus on top of the default critical multiplier, as detailed in the equation provided on the wiki.

#### [Multishot](https://warframe.fandom.com/wiki/Multishot)
Quite simply, multishot refers to a weapon's ability to fire multiple rounds simultaneously per trigger activation. While multishot is an inherently simple concept, its effect on various values in the calculation are far less intuitive, with each shot having its own chance to create a status proc or be a critical hit. A crass but practical oversimplification of multishot is effectively turning any weapon into a shotgun with multiple pellets.

#### [Status Effects](https://warframe.fandom.com/wiki/Status_Effect)
A status effect, or status proc, is an additional effect imparted by shots beyond straight damage. These procs, like critical hits, can occur by chance and are determined by the weapon's damage type distribution.

#### [Mods](https://warframe.fandom.com/wiki/Mod)
Mods are collectable items within the game that alter a weapon's properties in order to either change the weapon's functionality, role, or perceived DPS. Finding the optimal mod configuration requires a mix of mathematics, game knowledge, team balancing, and desired weapon performance. This program exclusively aids in the mathematics of DPS optimization by calculating mixed mod bonuses and their overall effect on DPS

#### [Mod Stacking](https://warframe.fandom.com/wiki/Calculating_Mod_Bonuses)
While these specifications are a small part of the equation, they help to bridge the gap between a Warframe player's understanding of in-game effects and their actual changes in the game's code.

# License

MIT License

Copyright (c) 2019 Caden Koscinski

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

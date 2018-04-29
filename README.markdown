Ajar Game Engine
================

The Ajar Game Engine is a continuation of the [Macchiato Doppio](https://sourceforge.net/projects/macchiatodoppio/) project I started many years ago to develop my own game engine.

The idea is to develop a versatile game engine framework that isn't, necessarily, firmly bound to other libraries for display, logic, sound, or input. Instead, Ajar aims to provide the structure to organize the game, handle resources, develop logic, and then allow the various methods of display or input to be tried out to discover what works best for the core of the game.

In this way game developers don't have to worry about picking the best choice for this early on, and going through the process of deciding between a more complete, but less configurable, library for display, for example, instead of a lower level, but more flexible, one. In other words, the final needs of the game for display or sound, or whatever library is needed, can be determined based on the finished needs of the game.

Note: This engine is under development and is offered without warrenty. There is a very good chance that significant changes will still take place in its structure.

# Status
Currently I'm moving things around and doing some major revisions. The old structure in SVN had a main trunk (now called *dev*) where the more complete code was, as well as a branches section (now called *exp*) where experimental stuff was being done.
Most of this is going to be combined into one new central structure where the experimental stuff that panned out will be combined with the new updated trunk code.

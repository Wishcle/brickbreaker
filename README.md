CIS 120 Game Project README

Core Concepts
==

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. 2-D Array

	Implements the grid of bricks that must be destroyed. A 2-D array is essentially a grid.

  2. File I/O

	All the levels are built from a txt file (levels.txt). The LevelSetup class reads the file and creates a BrickField. The BrickField class handles the creation of the 2-D array mentioned above. Reading from a text file made creating levels easy.

	ex.
	Level 13:
	0000000
	0543210
	0432120
	0321230
	0212340
	0123450
	0000000
	0xxxxx0
	0xxxxx0
	0000000

	The level editor does the reverse. The user creates a level, once they are done it is written to a different file (player_levels.txt). Next time they play, the levels can be loaded and played, again using the LevelSetup class.	

  3. Inheritance / Subtyping

	Sprite, CharSprite, WordSprite
	- The CharSprite extends Sprite but has a fixed width and height
	- WordSprite also extends Sprite but can only be a word (CharSprite[])

	Projectile interface:
	- There are two types of projectiles that the paddle can shoot: rockets and lasers The interface has draw(), update(), and isDead() methods that are implemented separately by each class.

  4. Collections

	- I used ArrayLists to keep track of the projectiles and powerups that need to be drawn and updated because there is never a fixed amount of them.

Your Implementation
==

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.

Game
- contains main method and run()
- sets up frame

GameComp
- extends JComponent
- everything is drawn on here

Values
- contains hard-coded values for all the objects
- e.g. "final static int BALL_SPEED = 2;"

Sprites
- contains hard-coded strings for all the sprites
- also contains a map, mapping characters to their hex for that TI-Calc retro font

Sprite
- interprets 4-bit hexadecimal into 2-D int arrays (only 0's and 1's)
- can draw its own 2-D array. If there's a 1, it draws a square
- the draw method takes in location and pixel size

CharSprite
- extends Sprite
- supers with a fixed width and height (every character is 5x7)
- gets the hex from the map in the Sprites class which maps every character to its hex

WordSprite
- extends Sprite
- takes in a String, makes an array of CharSprites (for each character in the string),
then pieces them together to form one big Sprite for the whole word

LevelSetup
- Reads a file and sets up the levels in the form of a BrickField array;

BrickField
- Creates a 2-D array of bricks. Also has a few methods that iterate through the 2-D array checking for various things. One method, isLevelFinished(), checks to see if all of the bricks are "alive".

Drawable (interface)
- One method: draw(), takes in a Graphics object

Brick (implements Drawable)
- has a heatlh field, ranging from 0-5 (or -1 for unbreakable bricks)
- when its health is 0, it is not drawn and not considered for collision with balls and projectiles
- color changes with health

Ball (implements Drawable)
- handles its own collision
- has pointers to the level's BrickField and Paddle objects allowing it to retrieve their location information when it tests for collision
- PowerUps are also handled by the Ball class for some reason. A design flaw maybe, 
but powerups also interact with the bricks and paddle so it was easy to put here

Paddle (implements Drawable)
- handles projectiles
- moves
- the ball handles its collision with this object
- the paddle also has a pointer to the BrickField so it can pass it on to the projectiles it shoots, which check for their own collision with Bricks

PowerUp
- the class form of the PowerUpType enum
- the enum has the hard-coded text
- the class has a draw method
- the ball handles the PowerUps so it can pass its Paddle pointer to the PowerUps
- the ball was a switch statement on the PowerUpType enum to delegate the effects to the various objects (since it has pointers to everything)
Ex. PowerUpType.LASER -> paddle.setShootingType(ShootingType.LASERS);

Projectile (interface)
- update(), draw(), isDead()

Laser
Rocket
- both implement Projectile
- these do mostly the same thing, just drawn differently
- also, if a projectile collides with a Brick b:
  Lasers call b.removeLaserHealth()
  Rockets call b.destroy()

Menu
- has methods for changing the spacing and adding menu options
- the main menu is a Menu
- the options are drawn using WordSprites

=================================================

- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?

	Brick collision was always slightly buggy. Small improvements were made throughout. I'm sure there are some **very** rare edge cases that were not caught.

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?

	A lot of things should be made private that aren't.
	If I could do it all over, I might use multiple JComponents instead of just one or at least organize the main one better. I would also move more of the hard-coded things to text files (Sprites.java, Values.java).


External Resources
==

- Cite any external resources (libraries, images, tutorials, etc.) that you may
  have used while implementing your game.

	I looked up "blackberry brick breaker" on google images to remember what everything looked like. The text font is from TI graphing calculators.


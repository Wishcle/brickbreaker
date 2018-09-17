import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class GameComp extends JComponent {

	final int WIDTH = Values.FRAME_WIDTH;
	final int HEIGHT = Values.FRAME_HEIGHT;
	final int INTERVAL = Values.GAME_TIME_INTERVAL;

	Drawable[] objects;
	Paddle paddle;
	Ball ball;

	BrickField[] levels; // array of levels
	BrickField bricks;   // current level
	int currentLevel;
	int livesLeft;

	GameState state;
	boolean paused;
	boolean playerLevels;

	Sprite lifeOutline = Sprites.LIFE_SPRITE_OUTLINE;
	Sprite lifeInner = Sprites.LIFE_SPRITE_INNER;
	WordSprite levelText;
	int alpha;

	// MENU
	Menu mainMenu;
	Menu editorMenu;
	
	BrickField newLevel;
	
	WordSprite myName = WordSprite.makeWordSprite("by Riley Cheeseman", Values.MENU_TEXT_COLOR);

	// CREATES GAME OBJECTS
	public GameComp() {

		//		state = GameState.PLAYING;
		//		currentLevel = -1;
		//		livesLeft = Values.NUM_LIVES;
		//		makeLevels();
		//		nextLevel();

		playerLevels = false;
		state = GameState.MAIN_MENU;
		makeMainMenu();
		makeEditorMenu();

		Timer timer = new Timer(INTERVAL, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tick();
			}
		});
		timer.start();

		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ESCAPE) System.exit(0);
			
				if(state == GameState.HELP) {
					switch(e.getKeyCode()) {
					case KeyEvent.VK_ENTER:
					case KeyEvent.VK_SPACE:
					case KeyEvent.VK_Q:
						state = GameState.MAIN_MENU;
					}
					
					if(e.getKeyCode() == KeyEvent.VK_SPACE ||
					   e.getKeyCode() == KeyEvent.VK_ENTER) {
						state = GameState.MAIN_MENU;
					}
				}
				
				else if(state == GameState.MAIN_MENU) {
					switch(e.getKeyCode()) {
					case KeyEvent.VK_UP:
						mainMenu.moveUp();
						break;
					case KeyEvent.VK_DOWN:
						mainMenu.moveDown();
						break;
					case KeyEvent.VK_SPACE:
					case KeyEvent.VK_ENTER:
						handleMainMenuSelection();
						break;
					case KeyEvent.VK_Q:
						System.exit(0);
					}
				}
				
				else if(state == GameState.PLAYING) {
					
					switch(e.getKeyCode()) {
					case KeyEvent.VK_EQUALS:
						if(currentLevel+1 < levels.length)
							nextLevel();
						break;
					case KeyEvent.VK_MINUS:
						currentLevel -= 2;
						if(playerLevels) {
							makePlayerLevels();
						} else {
							makeLevels();
						}
						nextLevel();
						break;
					case KeyEvent.VK_L:
						livesLeft++;
						break;
					case KeyEvent.VK_N:
						if(paddle.getRadius() == Values.PADDLE_LONG_RADIUS) {
							paddle.setRadius(PaddleRadius.NORMAL);
						} else {
							paddle.setRadius(PaddleRadius.LONG);
						}
						break;
					case KeyEvent.VK_Z:
						paddle.setShootingType(ShootingType.LASERS);
						break;
					case KeyEvent.VK_G:
						paddle.setShootingType(ShootingType.ROCKETS);
					}

					if(ball.getStatus() == BallStatus.BOUNCING) {
						switch(e.getKeyCode()) {
						case KeyEvent.VK_LEFT:
							paddle.addMove(Move.LEFT);
							break;
						case KeyEvent.VK_RIGHT:
							paddle.addMove(Move.RIGHT);
							break;
						case KeyEvent.VK_SPACE:
							paddle.shoot(); 
							break;
						case KeyEvent.VK_DELETE:
							ball.putBallOnPaddle(false);
							break;
						}
					}

					else if(ball.getStatus() == BallStatus.LAUNCHING) {
						switch(e.getKeyCode()) {
						case KeyEvent.VK_LEFT:
							ball.addAngleMove(Move.LEFT);
							break;
						case KeyEvent.VK_RIGHT:
							ball.addAngleMove(Move.RIGHT);
							break;
						case KeyEvent.VK_SPACE:
							ball.launch();
							break;
						}
					}
				}
				
				else if(state == GameState.WIN_SCREEN || state == GameState.LOSE_SCREEN) {
					switch(e.getKeyCode()) {
					case KeyEvent.VK_ENTER:
					case KeyEvent.VK_SPACE:
						if(playerLevels) {
							state = GameState.EDITOR_MENU;
						} else {
							state = GameState.MAIN_MENU;
						}
					}
				}
				
				else if(state == GameState.EDITOR_MENU) {
					switch(e.getKeyCode()) {
					case KeyEvent.VK_UP:
						editorMenu.moveUp();
						break;
					case KeyEvent.VK_DOWN:
						editorMenu.moveDown();
						break;
					case KeyEvent.VK_ENTER:
					case KeyEvent.VK_SPACE:
						handleEditorMenuSelection();
						break;
					case KeyEvent.VK_Q:
						state = GameState.MAIN_MENU;
						break;
					}
				}
				
				else if(state == GameState.EDITOR) {
					switch(e.getKeyCode()) {
					case KeyEvent.VK_Q:
						levels = null;
						state = GameState.EDITOR_MENU;
						break;
					case KeyEvent.VK_S:
						try {
							LevelSetup.addPlayerLevelToFile(newLevel);
						} catch (IOException e1) {
							throw new RuntimeException("PROBLEM WITH WRITING LEVELS");
						}
						state = GameState.EDITOR_MENU;
						break;
					case KeyEvent.VK_UP:
						newLevel.moveUp();
						break;
					case KeyEvent.VK_DOWN:
						newLevel.moveDown();
						break;
					case KeyEvent.VK_LEFT:
						newLevel.moveLeft();
						break;
					case KeyEvent.VK_RIGHT:
						newLevel.moveRight();
						break;
					case KeyEvent.VK_SPACE:
						newLevel.cycleBrickHealth();
						break;
					case KeyEvent.VK_C: // fall through
					case KeyEvent.VK_0:
						newLevel.setBrickHealth(0);
						break;
					case KeyEvent.VK_1:
						newLevel.setBrickHealth(1);
						break;
					case KeyEvent.VK_2:
						newLevel.setBrickHealth(2);
						break;
					case KeyEvent.VK_3:
						newLevel.setBrickHealth(3);
						break;
					case KeyEvent.VK_4:
						newLevel.setBrickHealth(4);
						break;
					case KeyEvent.VK_5:
						newLevel.setBrickHealth(5);
						break;
					case KeyEvent.VK_X:
						newLevel.setBrickHealth(-1);
						break;
					case KeyEvent.VK_Z:
						newLevel.clearAllBricks();
						break;
					}
				}
				
				if(state == GameState.PAUSED || state == GameState.PLAYING) {
					switch(e.getKeyCode()) {
					case KeyEvent.VK_P:
						togglePause();
						break;
					case KeyEvent.VK_Q:
						if(playerLevels) {
							state = GameState.EDITOR_MENU;
						} else {
							state = GameState.MAIN_MENU;
						}
						break;
					}
				}
			}

			public void keyReleased(KeyEvent e) {
				if(state == GameState.PLAYING) {
					if(ball.getStatus() == BallStatus.BOUNCING) {
						switch(e.getKeyCode()) {
						case KeyEvent.VK_LEFT:
							paddle.removeMove(Move.LEFT);
							break;
						case KeyEvent.VK_RIGHT:
							paddle.removeMove(Move.RIGHT);
							break;
						case KeyEvent.VK_SPACE:
							paddle.allowShooting();
						}
					}

					if(ball.getStatus() == BallStatus.LAUNCHING) {
						switch(e.getKeyCode()) {
						case KeyEvent.VK_LEFT:
							ball.removeAngleMove(Move.LEFT);
							break;
						case KeyEvent.VK_RIGHT:
							ball.removeAngleMove(Move.RIGHT);
							break;
						}
					}
				}
			}
		});


	}

	public void gameSetup() {
		paused = false;
		state = GameState.PLAYING;
		currentLevel = -1;
		livesLeft = Values.NUM_LIVES;
		if(playerLevels) {
			makePlayerLevels();
		} else {
			makeLevels();
		}
		nextLevel();
	}

	public void handleMainMenuSelection() {
		// show animation?

		switch(mainMenu.getSelectionText()) {
		case "PLAY":
			playerLevels = false;
			gameSetup();
			break;
		case "HELP":
			state = GameState.HELP;
			break;
		case "OPTIONS":
			// NO OPTIONS, sorry!
			break;
		case "EDITOR":
			state = GameState.EDITOR_MENU;
			break;
		case "QUIT":
			System.exit(0);
		default:
			break;
		}
	}
	
	public void handleEditorMenuSelection() {
		switch(editorMenu.getSelectionText()) {
		case "MAKE":
			newLevel = BrickField.makeBlankField();
			newLevel.selectNewBrick();
			state = GameState.EDITOR;
			break;
		case "PLAY":
			playerLevels = true;
			gameSetup();
			break;
		case "BACK":
			state = GameState.MAIN_MENU;
			break;
		}
	}

	public void makeMainMenu() {
		mainMenu = new Menu();

		// CHANGE SETTINGS
		mainMenu.setMargin(130);
		mainMenu.setPixelSize(10);
		mainMenu.setOptionSeparation(2);
		mainMenu.setSelectorSeparation(3);
		mainMenu.allowWrap(false); // default is false, here just in case

		// ADD OPTIONS
		mainMenu.addMenuOption("PLAY");
		mainMenu.addMenuOption("HELP");
		mainMenu.addMenuOption("EDITOR");
		//mainMenu.addMenuOption("OPTIONS");
		mainMenu.addMenuOption("QUIT");
	}
	
	public void makeEditorMenu() {
		editorMenu = new Menu();
		
		// CHANGE SETTINGS
		editorMenu.setMargin(130);
		editorMenu.setPixelSize(10);
		editorMenu.setOptionSeparation(2);
		editorMenu.setSelectorSeparation(3);
		editorMenu.allowWrap(false); // default is false, here just in case

		// ADD OPTIONS
		editorMenu.addMenuOption("PLAY");
		editorMenu.addMenuOption("MAKE");
		editorMenu.addMenuOption("BACK");
	}
	
	public void makePlayerLevels() {
		levels = LevelSetup.getPlayerLevels();
	}

	public void makeLevels() {
		levels = LevelSetup.getLevels();
	}

	public void nextLevel() {
		if(currentLevel < -1)
			currentLevel = -1;

		currentLevel++; // TODO CLEAN UP WHEN MORE LEVELS
		alpha = Values.TEXT_LEVEL_ALPHA;

		if(currentLevel >= levels.length) {
			endGame();
		} else {
			makeObjects();
		}
	}

	public void endGame() {
		state = GameState.WIN_SCREEN;
	}

	public void makeObjects() {
		bricks = levels[currentLevel];
		paddle = new Paddle(bricks);
		ball = new Ball(paddle, bricks);

		objects = new Drawable[]{
				bricks,
				paddle,
				ball
		};

		levelText = WordSprite.makeWordSprite("LEVEL " + (currentLevel+1), Values.LEVEL_TEXT_COLOR);
	}
	
	public void togglePause() {
		paused = !paused;
	}

	public void tick() {
		if(state == GameState.PLAYING) {
			if(bricks.isLevelFinished()) {
				nextLevel();
			} else {
				if(!paused)
					updateAll();
			}
		}

		if(state == GameState.MAIN_MENU) {
			// TODO nothing in tick just drawing I think
			// key listener takes care of menu state updates
		}
		
		if(state == GameState.EDITOR) {
			// nothing here i think // TODO
		}

		repaint();
		//requestFocusInWindow();
	}

	@Override
	public void paintComponent(Graphics gc) {
		super.paintComponent(gc);
		
		if(state == GameState.PLAYING) {
			// BACKGROUND
			gc.setColor(Values.BACKGROUND_COLOR);	
			gc.fillRect(0, 0, WIDTH, HEIGHT);

			if(alpha > 0) { // "LEVEL X" text
				int thickness = 10;
				int width = levelText.getW() * thickness;
				int x = (Values.GAME_WIDTH / 2 + Values.GAME_BOUND_LEFT) - (width / 2);
				levelText.setColor(new Color(255, 255, 255, alpha > 255 ? 255 : alpha));
				levelText.draw(gc, x, 550, thickness);
			}
			
			// DRAW PROJECTILES
			paddle.drawProjectiles(gc);
			
			// DRAW REPEAT BACKGROUND BEHIND PADDLE
			// this hides any projectile parts that are below the paddle
			// so it looks like its coming out
			gc.setColor(Values.BACKGROUND_COLOR);
			gc.fillRect(0, paddle.getY(), WIDTH, Values.GAME_HEIGHT - paddle.getY());

			// GAME OBJECTS
			drawAll(gc);
			
			// PAUSED
			if(paused) {
				Rectangle screen = new Rectangle(0, 0, WIDTH, HEIGHT);
				Sprite pausedSprite = WordSprite.makeWordSprite("PAUSED", Color.BLACK);// DRAW STRIP
				
				int t = 15;
				int stripHeight = (pausedSprite.getH() + 2) * t;
				int middleY = (int) Values.getGameMidpoint().getY();
				
				gc.setColor(Color.WHITE);
				gc.fillRect(	0, middleY - stripHeight/2, WIDTH, stripHeight);
				
				pausedSprite.drawInCenter(gc, screen, 15);
			}
		}

		if(state == GameState.MAIN_MENU) {
			// DRAW BACKGROUND
			gc.setColor(Values.MENU_BACKGROUND_COLOR);	
			gc.fillRect(0, 0, WIDTH, HEIGHT);
			
			// DRAW LOGO STUFF
			Sprites.LOGO_TEXT_SPRITE.draw(gc, 40, 40, 8);
			
			// DRAW MENU
			mainMenu.draw(gc, Values.MAIN_MENU_DIST_FROM_TOP);
			myName.draw(gc, 2, Values.GAME_BOUND_BOT - 16, 2);
		}
		
		if(state == GameState.HELP) {
			int t = 2;
			int x = 90;
			int s = 35;
			
			gc.setColor(Values.BACKGROUND_COLOR);	
			gc.fillRect(0, 0, WIDTH, HEIGHT);
			
			WordSprite.makeWordSprite("HELP", Color.WHITE)
			.draw(gc, x, s, 10);
			
			for(int i = 0; i < Values.INSTRUCTIONS.length; i++) {
				WordSprite wsp = WordSprite.makeWordSprite(Values.INSTRUCTIONS[i], Color.WHITE);
				wsp.draw(gc, x, 70 + s*2 + i*(wsp.getH()+3)*t, t);
			}
		}
		
		if(state == GameState.LOSE_SCREEN) {
			gc.setColor(Color.BLACK);
			gc.fillRect(0, 0, WIDTH, HEIGHT);
			
			Rectangle r = new Rectangle(0, 0, WIDTH, HEIGHT);
			WordSprite.makeWordSprite("GAME OVER", Color.WHITE)
			.drawInCenter(gc, r, 11);
			
			r = new Rectangle(0, 150, WIDTH, HEIGHT);
			WordSprite.makeWordSprite("You made it to level "+(currentLevel+1)+".", Color.WHITE)
			.drawInCenter(gc, r, 3);
		}
		
		if(state == GameState.WIN_SCREEN) {
			gc.setColor(Color.BLACK);
			gc.fillRect(0, 0, WIDTH, HEIGHT);
			
			Rectangle r = new Rectangle(0, 0, WIDTH, HEIGHT);
			WordSprite.makeWordSprite("YOU WIN!", Color.WHITE)
			.drawInCenter(gc, r, 11);
			
			r = new Rectangle(0, 150, WIDTH, HEIGHT);
			if(playerLevels) {
				WordSprite.makeWordSprite("Nice job! You either beat", Color.WHITE)
				.drawInCenter(gc, r, 3);
				
				r.translate(0, 25);
				WordSprite.makeWordSprite("all the player levels", Color.WHITE)
				.drawInCenter(gc, r, 3);
				
				r.translate(0, 25);
				WordSprite.makeWordSprite("or there aren't any...", Color.WHITE)
				.drawInCenter(gc, r, 3);
			} else {
				WordSprite.makeWordSprite("Nice job! You beat the game!", Color.WHITE)
				.drawInCenter(gc, r, 3);
			}
			
		}
		
		if(state == GameState.EDITOR_MENU) {
			// DRAW BACKGROUND
			gc.setColor(Values.MENU_BACKGROUND_COLOR);	
			gc.fillRect(0, 0, WIDTH, HEIGHT);
			
			// DRAW "EDITOR
			WordSprite.makeWordSprite("EDITOR", Color.WHITE)
			.draw(gc, 50, 50, 15);
			
			// DRAW MENU
			editorMenu.draw(gc, Values.MAIN_MENU_DIST_FROM_TOP);
		}
		
		if(state == GameState.EDITOR) {
			// DRAW BACKGROUND
			gc.setColor(Values.MENU_BACKGROUND_COLOR);	
			gc.fillRect(0, 0, WIDTH, HEIGHT);
			
			// DRAW BAR
			gc.setColor(Color.WHITE);
			gc.fillRect(0, 500, WIDTH, 25);
			
			// DRAW INSTRUCTIONS
			int x = 150;
			int t = 2;
			for(int i = 0; i < Values.LEVEL_EDITOR_INSTRUCTIONS.length; i++) {
				WordSprite wsp = WordSprite.makeWordSprite(Values.LEVEL_EDITOR_INSTRUCTIONS[i], Color.WHITE);
				wsp.draw(gc, x, 550 + i*(wsp.getH()+3)*t, t);
			}
			
			// DRAW BRICKS
			newLevel.draw(gc);
		}

	}

	// DRAWS ALL GAME OBJECTS
	public void drawAll(Graphics gc) {
		// DRAW OBJECTS
		for(int i = 0; i < objects.length; i++) {
			objects[i].draw(gc);
		}

		// DRAW HEARTS IN CORNER
		lifeOutline.draw(gc, 2, 776, 2);
		lifeInner.draw(gc, 2, 776, 2);
		WordSprite.makeWordSprite("x"+livesLeft, Values.LEVEL_TEXT_COLOR)
		.draw(gc, 30, 777, 3);
		
		// DRAWS CURRENT GUN AMMO
		if(paddle.getShootingType() == ShootingType.ROCKETS) {
			for(Sprite s : Sprites.ROCKET) {
				s.draw(gc, Values.GAME_BOUND_RIGHT - 11, 776, 1);
			}
			WordSprite ws = WordSprite.makeWordSprite(""+paddle.getAmmo()+"x", Values.LEVEL_TEXT_COLOR);
			ws.draw(gc, Values.GAME_BOUND_RIGHT - 13 - ws.getW()*3, 777, 3);
		}
		
	}

	public void updateAll() {
		checkDeath();
		updateSpriteAlpha();	
		paddle.update();
		ball.update();
		paddle.updateProjectiles();
		if(ball.gainLife()) {
			livesLeft++;
		}
	}

	public void checkDeath() {
		if(ball.isDead()) {
			if(livesLeft <= 0) {
				gameOver();
			} else {
				livesLeft--;
			}
		}
	}

	public void gameOver() {
		state = GameState.LOSE_SCREEN;
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(WIDTH, HEIGHT);
	}

	public void updateSpriteAlpha() {
		alpha -= Values.TEXT_LEVEL_APLHA_STEP;

		if(alpha < 0) {
			alpha = 0;
		}
	}
}

enum GameState {
	MAIN_MENU,
	PLAYING,
	PAUSED,
	WIN_SCREEN,
	LOSE_SCREEN,
	EDITOR_MENU,
	EDITOR,
	HELP;
}

class Keys extends KeyAdapter {
	
}





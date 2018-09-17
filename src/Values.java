import java.awt.Color;
import java.awt.Point;

public class Values {

	// TIME
	final static int GAME_TIME_INTERVAL = 5;

	// FILE PATHS
	final static String PATH_LEVELS = "src/levels.txt";
	final static String PATH_PLAYER_LEVELS = "src/player_levels.txt";

	// TEXT
	final static double TEXT_LEVEL_APLHA_STEP = 1;
	final static int TEXT_LEVEL_ALPHA = 400;
	
	// INSTRUCTIONS
	final static String[] INSTRUCTIONS = {
		"Welcome to Brick Breaker! [SPACE] to return",
		" ",
		"The goal of the game is to use the ball to",
	    "destroy all the bricks in each level. Be",
		"careful not to let the ball get past the",
		"paddle or you will lose a life.",
		" ",
		"Use the ARROW KEYS to control the shooting",
		"angle and paddle. Use the SPACE BAR to launch",
		"the ball and fire any projectiles obtained",
		"through the GUN and LASER powerups.",
		" ",
		"Powerups? Every brick has a chance to",
		"release a powerup when hit. If the powerup",
		"is caught by the paddle, its effects are",
		"applied.",
		" ",
		"P to pause, Q to quit.",
		" ",
		"There are 20 levels. Good luck. ---",
		" ",
		"For demonstration purposes, extra hotkeys",
		"have been enabled, giving the user certain",
		"powerups when pressed:",
		"  [L] -> LIFE",
		"  [G] -> GUN",
		"  [Z] -> LASERS",
		"  [N] -> LONG",
		" ",
		"Also, [+] and [-] allow you to",
		"switch between levels."
	};
	
	// LEVEL EDITOR INSTRUCTIONS
	final static String[] LEVEL_EDITOR_INSTRUCTIONS = {
		"Welcome to the Level Editor!",
		" ",
		"ARROW KEYS to move around",
		"[S] to save",
		"[SPACE] to cycle brick strength",
		"[C] to clear a brick",
		"[Z] to clear everything",
		"[Q] to quit",
		" ",
		"[0], [1], [2], [3], [4], [5], [x]",
		"to place a specific brick",
	};

	// MENU
	final static int MAIN_MENU_DIST_FROM_TOP = 285; // 248

	// ERRORS
	final static String ERROR_FILE_NOT_FOUND = "FILE NOT FOUND: ";
	final static String ERROR_INVALID_FILE_FORMAT = "ERROR: INVALID FILE FORMAT";

	// FRAME
	final static int FRAME_WIDTH = 700;
	final static int FRAME_HEIGHT = 800;

	// GAME BOUNDS
	final static int GAME_BOUND_TOP = 0;
	final static int GAME_BOUND_LEFT = 0;
	final static int GAME_BOUND_BOT = FRAME_HEIGHT;
	final static int GAME_BOUND_RIGHT = FRAME_WIDTH;
	final static int GAME_WIDTH = GAME_BOUND_RIGHT - GAME_BOUND_LEFT;
	final static int GAME_HEIGHT = GAME_BOUND_BOT - GAME_BOUND_TOP;

	// OTHER
	final static int NUM_LIVES = 6;

	// BRICKS
	final static int BRICK_ARRAY_WIDTH = 7;
	final static int BRICK_ARRAY_HEIGHT = 10;
	final static int BRICK_WIDTH = FRAME_WIDTH / BRICK_ARRAY_WIDTH;
	final static int BRICK_HEIGHT = BRICK_WIDTH / 2;
	final static int BRICK_SHIFT = 10;
	final static int BRICK_MAX_SHIFT = 200;
	final static double BRICK_SHIFT_CHANCE = 0;
	final static double BRICK_SHIFT_CHANCE_STEP = 0.01;
	final static double BRICK_HAS_POWERUP_CHANCE = 0.15; // 0.15
	final static int BRICK_LASER_HEALTH = 3;

	// PADDLE
	final static int PADDLE_HEIGHT = 36;
	final static int PADDLE_RADIUS = 72;
	final static int PADDLE_LONG_RADIUS = 108;
	final static int PADDLE_SHORT_RADIUS = 40;
	final static int PADDLE_SPEED = 2;
	final static int PADDLE_DIST_FROM_BOTTOM = 26;
	final static int PADDLE_MAX_BOUNCE_ANGLE = 140;
	final static int PADDLE_PIXEL_SIZE = 3;

	// BALL
	final static int BALL_RADIUS = 6;
	final static double BALL_SLOW_SPEED = 2;
	final static double BALL_BASE_SPEED = 2;
	final static double BALL_MAX_SPEED = 4;
	final static double BALL_SPEED_STEP = 0.3;

	// LAUNCHING BALL FROM PADDLE
	final static int LAUNCH_MAX_ANGLE = 130; // 0 - 180 degrees
	final static int LAUNCH_INDICATOR_LENGTH = 90;
	final static double LAUNCH_ANGLE_STEP = 0.8;  // also in degrees
	final static double LAUNCH_ADJUST_SPEED = 3;

	// COLORS
	final static Color BACKGROUND_COLOR = Color.BLACK;
	final static Color BORDER_COLOR = Color.BLACK;
	final static Color PADDLE_COLOR = Color.RED;
	final static Color BALL_COLOR = Color.WHITE;
	final static Color BRICK_BORDER_COLOR = Color.BLACK;
	final static Color LAUNCH_INDICATOR_COLOR = Color.LIGHT_GRAY;
	final static Color LEVEL_TEXT_COLOR = Color.WHITE;
	final static Color MENU_BACKGROUND_COLOR = Color.BLACK;
	final static Color MENU_TEXT_COLOR = Color.WHITE;

	// POWER-UP CHANGES // TODO
	final static double POWERUP_FALL_SPEED = 0.75;
	final static int POWERUP_PIXEL_SIZE = 4;
	final static int POWERUP_TEXT_SIZE = 2;
	
	// ROCKETS / LASERS
	final static double ROCKET_SPEED = 0.5;
	final static double ROCKET_ACCEL = 0.005;
	final static int ROCKET_PIXEL_SIZE = 3;
	final static int GUN_AMMO = 3;
	final static int LASER_SPEED = 3;
	final static int LASER_PIXEL_SIZE = 2;
	final static double PADDLE_LASER_RADIUS = 12.5;
	
	public static Point getGameMidpoint() {
		return new Point(
				(GAME_BOUND_RIGHT + GAME_BOUND_LEFT) / 2,
				(GAME_BOUND_BOT - GAME_BOUND_TOP) / 2);
	}
}

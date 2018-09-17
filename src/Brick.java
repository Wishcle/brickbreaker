import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Brick implements Drawable {
	
	/* The Brick object has an int health
	 * denoting how many more hits it takes to destroy the brick.
	 * 
	 * If a brick is constructed with invalid health, it is set to 0.
	 * 
	 * If the health of a brick is -1, it is unbreakable.
	 * 
	 * The 'removeHealth()' method will only remove health
	 * from a brick with health > 0, holding the above invariant.
	 * 
	 *  the method 'alive()' will return false when a bricks health is zero.
	 *  - used for drawing and collision
	 */
	
	final int TOP_BOUND;
	final int LEFT_BOUND;
	final int RIGHT_BOUND;
	final int BOT_BOUND;
	
	final int HEIGHT = Values.BRICK_HEIGHT;
	final int WIDTH = Values.BRICK_WIDTH;
	
	private Color border = Values.BRICK_BORDER_COLOR;
	private Color brickColor;
	
	boolean hasPowerup;
	
	// for level editor
	boolean selected;
	
	private int laserHealth;
	private int health;
	int x, y;
	
	public Brick(int h, int x, int y) {
		if(!(-1 <= h && h <= 5)) {
			System.out.println("INVALID BRICK HEALTH");
			h = 0;
		}
		
		selected = false;
		
		if(Math.random() < Values.BRICK_HAS_POWERUP_CHANCE)
			hasPowerup = true;
		
		this.x = x;
		this.y = y;
		health = h;
		laserHealth = Values.BRICK_LASER_HEALTH;
		calibrateColor();
	
		int r = Values.BALL_RADIUS;
		TOP_BOUND = y - r;
		LEFT_BOUND = x - r;
		RIGHT_BOUND = x + WIDTH + r;
		BOT_BOUND = y + HEIGHT + r;
	}
	
	public boolean inHitbox(Ball ball) {
		if(!doDraw())
			return false;
		
		double bx = ball.getX();
		double by = ball.getY();
		
		if(by > getTopBound()
		&& by < getBotBound()
		&& bx > getLeftBound()
		&& bx < getRightBound())
			return true;
		else
			return false;
	}
	
	// returns true if brick has powerup stored
	public boolean removeHealth() {
		if(health > 0) {
			health--;
			calibrateColor();
		}
		
		if(hasPowerup) {
			hasPowerup = false;
			return true;
		} else {
			return false;
		}
			
	}
	
	public void removeLaserHealth() {
		laserHealth--;
		if(laserHealth <= 0) {
			laserHealth = Values.BRICK_LASER_HEALTH;
			removeHealth();
		}
	}
	
	// ROCKET HIT = destroy()
	public void destroy() {
		health = 0;
	}
	
	public void laserHit() {
		laserHealth--;
	}
	
	public void calibrateColor() {
		if(health == -1) {
			brickColor = Color.DARK_GRAY;
		} else {
			brickColor = new Color(120 + 25*(health-1), 50 + 7*(health-1), 50 + 7*(health-1));
		}
	}
	
	public boolean doDraw() {
		if(health != 0)  
			return true;
		return false;
	}
	
	public boolean alive() {
		if(health > 0)  
			return true;
		return false;
	}

	@Override
	public void draw(Graphics gc) {
		if(doDraw()) {
			gc.setColor(brickColor);
			gc.fillRect(x, y, WIDTH, HEIGHT);
			gc.setColor(border);
			gc.drawRect(x, y, WIDTH, HEIGHT);
		}
		
		if(selected) {
			gc.setColor(Color.WHITE);
			//((Graphics2D) gc).setStroke(new BasicStroke(5));
			gc.drawRect(x, y, WIDTH, HEIGHT);
			//((Graphics2D) gc).setStroke(new BasicStroke(1));
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public void shiftY(int dy) {
		y += dy;
	}
	
	public Rectangle getRectangle() {
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}
	
	public int getTopBound() {
		return y - Values.BALL_RADIUS;
	}
	
	public int getLeftBound() {
		return x - Values.BALL_RADIUS;
	}
	
	public int getRightBound() {
		return x + WIDTH + Values.BALL_RADIUS;
	}
	
	public int getBotBound() {
		return y + HEIGHT + Values.BALL_RADIUS;
	}
	
	public Rectangle getBounds() {
		return new Rectangle(
			LEFT_BOUND,
			TOP_BOUND,
			RIGHT_BOUND - LEFT_BOUND,
			BOT_BOUND - TOP_BOUND
		);
	}
	
	// FOR LEVEL EDITOR v ==== v ==== v
	
	public int getHealth() {
		return health;
	}
	
	public void setHealth(int h) {
		if(h >= -1 && h <= 5) {
			health = h;
		}
		calibrateColor();
	}
	
	public void cycleHealth() {
		health++;
		if(health > 5) {
			health = -1;
		}
		calibrateColor();
	}
	
	public void select() {
		selected = true;
	}
	
	public void deselect() {
		selected = false;
	}
}













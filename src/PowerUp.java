import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

public class PowerUp {
	
	static Sprite spriteInner = Sprites.POWERUP_SPRITE_INNER;
	static Sprite spriteOuter = Sprites.POWERUP_SPRITE_OUTLINE;
	WordSprite text;
	
	static int t = Values.POWERUP_PIXEL_SIZE;
	static int w = spriteOuter.getW() * t;
	static int h = spriteOuter.getH() * t;
	
	double x, y;
	PowerUpType type;
	
	boolean paddleContact;
	
	private PowerUp(int x, int y, PowerUpType t) {
		this.x = x;
		this.y = y;
		type = t;
		paddleContact = false;
		text = WordSprite.makeWordSprite(t.getText(), Color.BLACK);
	}
	
	public static PowerUp genPowerUp(Brick b) {
		Point p = Sprite.getCenteredCoords(b.getRectangle(), w, h);
		return new PowerUp((int) p.getX(), (int) p.getY(), PowerUpType.getRandomPowerup());
	}
	
	public void draw(Graphics gc) {
		spriteInner.draw(gc, (int) x, (int) y, t);
		spriteOuter.draw(gc, (int) x, (int) y, t);
		text.drawInCenter(gc, new Rectangle((int) x, (int) y, w, h), 2);
	}
	
	public void update(Paddle p, Ball b) {
		y += Values.POWERUP_FALL_SPEED;
		
		// PADDLE COLLISION
		double r = w/4;
		if(y > p.getY() - h && y < p.getY() - (h/2)) {
			if(x > p.getX() - p.getRadius() - 3*r && 
			   x < p.getX() + p.getRadius() - r) {
				paddleContact = true;
			}
		}
	}
	
	public boolean doDraw() {
		return !(y > Values.GAME_BOUND_BOT || paddleContact);
	}
	
	public boolean getPaddleContact() {
		return paddleContact;
	}
	
	public double getY() {
		return y;
	}
	
	public PowerUpType getType() {
		return type;
	}
	
}

enum PowerUpType {
	LONG ("LONG", 1),	// -> paddle effect incl.
	LIFE ("LIFE", 2),	// -> gamecomp trigger
	FLIP ("FLIP", 1),	// -> paddle effect incl.
	SLOW ("SLOW", 1),	// -> ball trigger
	GUN 	("GUN", 1),		// -> paddle effect excl.
	LASER ("LASER", 1),	// -> paddle effect excl.
	CATCH ("CATCH", 1);	// -> ball effect incl.
	
	String text;
	int weight;
	
	private PowerUpType(String s, int weight) {
		text = s;
		this.weight = weight;
	}
	
	public String getText() {
		return text;
	}
	
	public int getWeight() {
		return weight;
	}
	
	public static PowerUpType getRandomPowerup() {
		ArrayList<PowerUpType> pool = new ArrayList<PowerUpType>();
		
		for(PowerUpType t : PowerUpType.values()) {
			for(int i = 0; i < t.getWeight(); i++) {
				pool.add(t);
			}
		}
		
		int index = (int) (Math.random() * pool.size());
		return pool.get(index);
	}
}

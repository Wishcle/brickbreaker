import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Paddle implements Drawable {
	
	Sprite paddleNormalOutline = Sprites.PADDLE_SPRITE_NORMAL_OUTLINE;
	Sprite paddleNormalInner = Sprites.PADDLE_SPRITE_NORMAL_INNER;
	Sprite paddleLongOutline = Sprites.PADDLE_SPRITE_LONG_OUTLINE;
	Sprite paddleLongInnerInner = Sprites.PADDLE_SPRITE_LONG_INNER_INNER;
	Sprite paddleLongInnerOuter = Sprites.PADDLE_SPRITE_LONG_INNER_OUTER;
	
	Color paddleColor = Values.PADDLE_COLOR;
	static int PADDLE_HEIGHT = Values.PADDLE_HEIGHT;
	
	int dx = Values.PADDLE_SPEED;	
	int x, y;
	boolean flipped;
	
	int gunAmmo;
	boolean shootAllowed;
	
	BrickField bricks;
	ArrayList<Projectile> projectiles;
	
	Move move;
	PaddleRadius radius;
	ShootingType shoot;
	
	public Paddle(BrickField bricks) {
		x = (int) Values.getGameMidpoint().getX();
		y = Values.GAME_BOUND_BOT
		  - Values.PADDLE_DIST_FROM_BOTTOM
		  - Values.PADDLE_HEIGHT;
		
		this.bricks = bricks;
		projectiles = new ArrayList<Projectile>();
		shootAllowed = true;
		
		move = Move.STOP;
		radius = PaddleRadius.NORMAL;
		shoot = ShootingType.NONE;
	}
	
	public void allowShooting() {
		shootAllowed = true;
	}
	
	public void update() {
		x += dx * move.getMultiplier();
		updateProjectiles();
		
		// WALL CHECK
		if(x < radius.get()) {
			x = radius.get();
		} else if (x > Values.GAME_BOUND_RIGHT - radius.get()) {
			x = Values.GAME_BOUND_RIGHT - radius.get();
		}
	}
	
	public void setRadius(PaddleRadius r) {
		radius = r;
	}
	
	@Override
	public void draw(Graphics gc) {
		// TODO old code get rid?
		//gc.setColor(paddleColor);
		//gc.fillRect(x - radius.get(), y, 2 * radius.get(), PADDLE_HEIGHT);
		
		int t = Values.PADDLE_PIXEL_SIZE; // pixel size for paddle;
		
		// DRAW PADDLE
		if(radius == PaddleRadius.NORMAL) {
			paddleNormalOutline.draw(gc, x - radius.get(), y, t); // TODO remove hardcode
			paddleNormalInner.draw(gc, x - radius.get(), y, t);
		} else if(radius == PaddleRadius.LONG) {
			paddleLongOutline.draw(gc, x - radius.get(), y, t);
			paddleLongInnerInner.draw(gc, x - radius.get(), y, t);
			paddleLongInnerOuter.draw(gc, x - radius.get(), y, t);
		}
		
		// DRAW PADDLE MODIFICATIIONS
		if(shoot == ShootingType.ROCKETS) {
			Sprites.PADDLE_GUN.draw(gc, x - PaddleRadius.NORMAL.get(), y, t);
		} else if(shoot == ShootingType.LASERS) {
			Sprites.PADDLE_LASER.draw(gc, x - PaddleRadius.NORMAL.get(), y, t);
		}
	}
	
	public void drawProjectiles(Graphics gc) {
		for(Projectile p : projectiles) {
			p.draw(gc);
		}
	}
	
	public void updateProjectiles() {
		for(int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).update(bricks);
			if(projectiles.get(i).isDead()) {
				projectiles.remove(i);
			}
		}
	}
	
	public void addProjectile(Projectile p) {
		projectiles.add(p);
	}
	
	public void shoot() {
		
		if(shootAllowed)
		switch(shoot) {
		case LASERS:
			shootAllowed = false;
			for(Laser l : Laser.make(this))
				addProjectile(l);
			break;
		case ROCKETS:
			shootAllowed = false;
			Rocket r = Rocket.make(this);
			addProjectile(r);
			gunAmmo--;
			if(gunAmmo <= 0) {
				shoot = ShootingType.NONE;
				shootAllowed = true;
			}
			break;
		default:
			break;
		}
	}
	
	public ShootingType getShootingType() {
		return shoot;
	}
	
	public int getAmmo() {
		return gunAmmo;
	}
	
	public void removePowerups() {
		removeFlip();
		radius = PaddleRadius.NORMAL;
		shoot = ShootingType.NONE;
		gunAmmo = Values.GUN_AMMO;
		shootAllowed = true;
	}
	
	public void applyFlip() {
		flipped = !flipped;
		move = move.flip();
	}
	
	public void removeFlip() {
		if(flipped) {
			applyFlip();
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public int getRadius() {
		return radius.get();
	}
	
	public void setShootingType(ShootingType s) {
		shoot = s;
		if(shoot == ShootingType.ROCKETS) {
			gunAmmo = Values.GUN_AMMO;
		}
	}
	
	public void addMove(Move moveToAdd) {
		if(flipped)
			moveToAdd = moveToAdd.flip();
		
		switch(moveToAdd) {
		case LEFT:
			if(move == Move.RIGHT)
				move = Move.RIGHT_AND_LEFT;
			else if(move == Move.STOP)
				move = moveToAdd;
			break;
		case RIGHT:
			if(move == Move.LEFT)
				move = Move.RIGHT_AND_LEFT;
			else if(move == Move.STOP)
				move = moveToAdd;
			break;
		case STOP:
			if(moveToAdd != Move.RIGHT_AND_LEFT) {
				move = moveToAdd;
			}
			break;
		default:
			break;
		}
	}
	
	public void removeMove(Move moveToRemove) {
		if(flipped)
			moveToRemove = moveToRemove.flip();
		
		switch(moveToRemove) {
		case LEFT:
			if(move == Move.RIGHT_AND_LEFT)
				move = Move.RIGHT;
			else if(move == Move.LEFT)
				move = Move.STOP;
			break;
		case RIGHT:
			if(move == Move.RIGHT_AND_LEFT)
				move = Move.LEFT;
			else if(move == Move.RIGHT)
				move = Move.STOP;
			break;
		default:
			break;
		}
	}
	
	public void setMove(Move move) {
		this.move = move;
	}
}

enum Move {
	RIGHT (1),
	LEFT (-1),
	RIGHT_AND_LEFT (0),
	STOP (0);
	
	private int speedMultiplier;
	
	private Move(int s) {
		speedMultiplier = s;
	}
	
	public int getMultiplier() {
		return speedMultiplier;
	}
	
	public Move flip() {
		if(this == Move.RIGHT)
			return Move.LEFT;
		if(this == Move.LEFT)
			return Move.RIGHT;
		return this;
	}
}

enum PaddleRadius {
	NORMAL (Values.PADDLE_RADIUS),
	LONG (Values.PADDLE_LONG_RADIUS);
	//SHORT (Values.PADDLE_SHORT_RADIUS); // TODO unneeded i think
	
	private int r;
	
	private PaddleRadius(int r) {
		this.r = r;
	}
	
	public int get() {
		return r;
	}
}

enum ShootingType {
	NONE,
	ROCKETS,
	LASERS
}

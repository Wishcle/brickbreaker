import java.awt.Graphics;

public class Laser implements Projectile {

	Sprite[] sprites = Sprites.LASER;
	static int t = Values.LASER_PIXEL_SIZE;
	double x, y;
	
	boolean dead;
	
	double dy = Values.LASER_SPEED;
	
	public Laser(double x, double y) {
		this.x = x;
		this.y = y;
		dead = false;
	}
	
	public static Laser[] make(Paddle p) {
		double x = p.getX();
		double y = p.getY();
		double r = Values.PADDLE_LASER_RADIUS * Values.PADDLE_PIXEL_SIZE;
		
		return new Laser[] {
			new Laser(x-r, y),
			new Laser(x+r, y)
		};
	}
	
	@Override
	public void draw(Graphics gc) {
		for(Sprite s : sprites) {
			s.draw(gc, (int) (x - (sprites[0].getW()/2.0)*t), (int) y, t);
			//                     ^ ^ ^ centers laser ^ ^
		}
	}
	
	@Override
	public void update(BrickField f) {
		y -= dy;
		
		// DEAD OFF SCREEN
		if(y + sprites[0].getH() * t < Values.GAME_BOUND_TOP)
			dead = true;
		
		// if in brick, destroy brick and self
		Brick b = f.getBrickWithPoint(x, y);
		if(b != null) {
			b.removeLaserHealth();
			dead = true;
		}
	}
	
	@Override
	public boolean isDead() {
		return dead;
	}
	
	@Override
	public String toString() {
		return "LASER: ("+((int) x)+","+((int) y)+")";
	}
	
}

import java.awt.Graphics;

public class Rocket implements Projectile {

	static int t = Values.ROCKET_PIXEL_SIZE;
	double x, y;
	
	boolean dead;
	
	double dy = Values.ROCKET_SPEED;
	double ddy = Values.ROCKET_ACCEL;
			
	Sprite[] sprites = Sprites.ROCKET;
	
	public Rocket (double x, double y) {
		this.x = x;
		this.y = y;
		dead = false;
	}
	
	public static Rocket make(Paddle p) {
		double x = p.getX();
		double y = p.getY();
				
		return new Rocket(x, y);
	}
	
	@Override
	public void draw(Graphics gc) {
		for(Sprite s : sprites) {
			s.draw(gc, (int) (x - (sprites[0].getW()/2.0)*t), (int) y, t);
			//                     ^ ^ ^ centers rocket ^ ^
		}
	}
	
	@Override
	public void update(BrickField f) {
		y -= dy;
		dy += ddy;
		
		// DEAD OFF SCREEN
		if(y + sprites[0].getH() * t < Values.GAME_BOUND_TOP)
			dead = true;
		
		// if in brick, destroy brick and self
		Brick b = f.getBrickWithPoint(x, y);
		if(b != null) {
			b.destroy();
			dead = true;
		}
	}
	
	@Override
	public boolean isDead() {
		return dead;
	}
	
	@Override
	public String toString() {
		return "ROCKET: ("+((int) x)+","+((int) y)+")";
	}
	
}

import java.awt.Graphics;

public interface Projectile {
	public void draw(Graphics gc);
	public void update(BrickField f);
	public boolean isDead();
}

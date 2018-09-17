import java.awt.Graphics;
import java.util.ArrayList;

public class Ball implements Drawable {
	
	static int radius = Values.BALL_RADIUS;
	static int TOP_BOUND = Values.GAME_BOUND_TOP + radius;
	static int LEFT_BOUND = Values.GAME_BOUND_LEFT + radius;
	static int RIGHT_BOUND = Values.GAME_BOUND_RIGHT - radius;
	static int BOT_BOUND = Values.GAME_BOUND_BOT + radius;
	static int MAX_ANGLE = Values.LAUNCH_MAX_ANGLE;
	static double ANGLE_STEP = Values.LAUNCH_ANGLE_STEP;
	
	double speed = Values.BALL_BASE_SPEED;
	double xHat, yHat;
	double x, y;
	
	double brickShiftChance = Values.BRICK_SHIFT_CHANCE;
	
	double currentLaunchAngle;
	int angleMoveCounter;
	
	boolean died, gainLife;
	boolean paddleCatch;
	
	Move angleMove;
	BallStatus status;
	
	Paddle paddle;
	BrickField bricks;
	
	ArrayList<PowerUp> powerups;
	
	public Ball(Paddle paddle, BrickField bricks) {
		this.paddle = paddle;
		this.bricks = bricks;
		
		putBallOnPaddle(false);
		currentLaunchAngle = -Values.LAUNCH_ANGLE_STEP / 2;
		
		powerups = new ArrayList<PowerUp>();
		died = false;
		gainLife = false;
		angleMove = Move.STOP;
	}
	
	@Override
	public void draw(Graphics gc) {
		
		// LAUNCH INDICATOR (drawn first, under ball)
		if(status == BallStatus.LAUNCHING) {
			int length = Values.LAUNCH_INDICATOR_LENGTH;
			double angle = Math.toRadians(currentLaunchAngle);
			int x2 = (int) (x + length * Math.sin(angle));
			int y2 = (int) (y - length * Math.cos(angle));
			gc.setColor(Values.LAUNCH_INDICATOR_COLOR);
			gc.drawLine((int) x, (int) y, x2, y2);
		}
		
		// DRAW POWERUPS
		for(int i = 0; i < powerups.size(); i++) {
			powerups.get(i).draw(gc);
		}
		
		// BALL
		gc.setColor(Values.BALL_COLOR);
		gc.fillOval((int) (x - radius), (int) (y - radius), 2*radius, 2*radius);
		
		
	}
	
	public void update() {
		updatePowerups();
		
		switch(status) {
		case BOUNCING:
			x += xHat * speed;
			y += yHat * speed;
			checkBounds();
			checkPaddleCollision();
			checkBrickCollision();
			break;
		case LAUNCHING:
			putBallOnPaddle(true);
			updateLaunchAngle();
			break;
		default:
			break;
		}
	}
	
	private void checkBounds() {
		if(y < TOP_BOUND) {
			flipY();
			y = TOP_BOUND;
		}
		
		if(y > BOT_BOUND) {
			loseLife();
			putBallOnPaddle(false);
		}
		
		if(x > RIGHT_BOUND) {
			flipX();
			x = RIGHT_BOUND;
		}
		
		if(x < LEFT_BOUND) {
			flipX();
			x = LEFT_BOUND;
		}
	}
	
	public boolean isDead() {
		if(died) {
			died = false;
			return true;
		} else {
			return false;
		}
	}
	
	public void loseLife() {
		died = true;
		speed = Values.BALL_BASE_SPEED;
		removePowerups();
	}
	
	public boolean gainLife() {
		if(gainLife) {
			gainLife = false;
			return true;
		} else {
			return false;
		}
	}
	
	public void updatePowerups() {
		for(int i = 0; i < powerups.size(); i++) {
			powerups.get(i).update(paddle, this);
			
			// APPLY CHANGES IF CONTACT
			if(powerups.get(i).getPaddleContact()) {
				switch(powerups.get(i).getType()) {
				case CATCH:
					paddleCatch = true;
					break;
				case FLIP:
					paddle.applyFlip();
					break;
				case GUN:
					paddle.setShootingType(ShootingType.ROCKETS);
					break;
				case LASER:
					paddle.setShootingType(ShootingType.LASERS);
					break;
				case LIFE:
					gainLife = true;
					break;
				case LONG:
					paddle.setRadius(PaddleRadius.LONG);
					break;
				case SLOW:
					this.applySlow();
					break;
				default:
					break;
				}
			}
			
			// CHECK FOR REMOVAL
			if(!powerups.get(i).doDraw())
				powerups.remove(i);
		}
	}
	
	
	
	private void checkPaddleCollision() {
		int px = paddle.getX();
		int py = paddle.getY();
		int pr = paddle.getRadius();
		int br = radius;
		
		// TOP OF PADDLE
		if(x < px + pr && x > px - pr && y > py - br && y < py + speed) {
			if(paddleCatch) {
				putBallOnPaddle(true);
			}
			
			flipY();
			y = py - radius;
			
			// BRICK SHIFT
			if(Math.random() < brickShiftChance)
				bricks.shiftY(Values.BRICK_SHIFT);
			brickShiftChance += Values.BRICK_SHIFT_CHANCE_STEP;
			
			// CHANGE ANGLE ON HIT CALCULATION
			double distFromCenter = x - px;
			double relativeDistFromCenter = distFromCenter / pr;
			double angle = relativeDistFromCenter * (Values.PADDLE_MAX_BOUNCE_ANGLE / 2);
			xHat =  Math.sin(Math.toRadians(angle));
			yHat = -Math.cos(Math.toRadians(angle));
			
			// INCREASE BALL SPEED
			speed += 2 * Math.abs(relativeDistFromCenter) * Values.BALL_SPEED_STEP;
			if(speed > Values.BALL_MAX_SPEED)
				speed = Values.BALL_MAX_SPEED;
			//System.out.println(speed);
		}
		
		// SIDES OF PADDLE
		if(x < px + pr + br && x > px - pr - br &&
		   y > py && y < py + Values.PADDLE_HEIGHT) {
			flipX();
		}
		
		// TODO collision on corners of paddle
		// use distance formula
		// y = -x and y = x
		// are corners like that ^ ?
	}
	
	
	public void checkBrickCollision() {
		ArrayList<Brick> bricksInCollision = bricks.getCollisions(this);
		if(bricksInCollision == null)
			throw new RuntimeException();
		int moveBackCounter = 0;
		
		// how many bricks is the ball "in".
		switch(bricksInCollision.size()) {
		case 0:
			// continue normally
			break;
		case 1:
			Brick b = bricksInCollision.get(0);
			
			do {
				moveBack();
				moveBackCounter++;
			} while (b.inHitbox(this));
			
			if(b.removeHealth()) {
				genPowerup(b);
			}
			
			if(y < b.getTopBound() || y > b.getBotBound())
				flipY();
			if(x < b.getLeftBound() || x > b.getRightBound())
				flipX();
			for(int i = moveBackCounter; i > 0; i--)
				moveForward();
			
			moveBackCounter = 0;
			checkBrickCollision();
			break;
		case 2:
			Brick b1 = bricksInCollision.get(0);
			Brick b2 = bricksInCollision.get(1);
			
			do {
				moveBack();
				moveBackCounter++;
			} while (b1.inHitbox(this) || b2.inHitbox(this));
			
			// b1.x < b2.x && b1.y < b2.y
			// always true by method of brick array traversal
			if(b1.getX() == b2.getX()) {
				
				if(y < b1.getTopBound() || y > b2.getBotBound()) {
					flipY();
				}
					
				if(x < b1.getLeftBound() || x > b1.getRightBound()) {
					flipX();
				}
				
				if(y > b1.getY() && y <= b2.getY()) {
					b1.removeHealth();
				} else {
					b2.removeHealth();
				}
					
			} else if(b1.getY() == b2.getY()) {
				
				if(y < b1.getTopBound() || y > b1.getBotBound()) {
					flipY();
				}
					
				if(x < b1.getLeftBound() || x > b2.getRightBound()) {
					flipX();
				}
				
				if(x > b1.getX() && x <= b2.getX()) {
					b1.removeHealth();
				} else {
					b2.removeHealth();
				}
					
			} else {
				flipX();
				flipY();
				b1.removeHealth();
				b2.removeHealth();
			}
			
			for(int i = moveBackCounter; i > 0; i--)
				moveForward();
			
			moveBackCounter = 0;
			//checkBrickCollision();
			break;
		case 3:
			moveBack();
			flipX();
			flipY();
			//checkBrickCollision();
			break;
		default:
			putBallOnPaddle(false);
			break;
		}
	}
	
	public void genPowerup(Brick b) {
		powerups.add(PowerUp.genPowerUp(b));
	}
	
	public void removePowerups() {
		powerups = new ArrayList<PowerUp>();
		paddleCatch = false;
		paddle.removePowerups();
	}
	
	public void moveBack() {
		x -= xHat;
		y -= yHat;
	}
	
	public void moveForward() {
		x += xHat;
		y += yHat;
	}
	
	// puts ball on paddle
	// changes status to LAUNCHING
	public void putBallOnPaddle(boolean keepX) {
		status = BallStatus.LAUNCHING;
		paddle.setMove(Move.STOP);
		paddle.allowShooting();
		if(!keepX)
			x = paddle.getX();
		y = paddle.getY() - radius;
	}
	
	public void updateLaunchAngle() {
		int m = angleMove.getMultiplier();
		if(Math.abs(currentLaunchAngle + m * ANGLE_STEP) < MAX_ANGLE / 2) {
			currentLaunchAngle += m * ANGLE_STEP;
		}
	}
	
	public void addAngleMove(Move moveToAdd) {
		switch(moveToAdd) {
		case LEFT:
			if(angleMove == Move.RIGHT)
				angleMove = Move.RIGHT_AND_LEFT;
			else if(angleMove == Move.STOP)
				angleMove = moveToAdd;
			break;
		case RIGHT:
			if(angleMove == Move.LEFT)
				angleMove = Move.RIGHT_AND_LEFT;
			else if(angleMove == Move.STOP)
				angleMove = moveToAdd;
			break;
		default:
			break;
		}
	}
	
	public void removeAngleMove(Move moveToRemove) {
		switch(moveToRemove) {
		case LEFT:
			if(angleMove == Move.RIGHT_AND_LEFT)
				angleMove = Move.RIGHT;
			else if(angleMove == Move.LEFT)
				angleMove = Move.STOP;
			break;
		case RIGHT:
			if(angleMove == Move.RIGHT_AND_LEFT)
				angleMove = Move.LEFT;
			else if(angleMove == Move.RIGHT)
				angleMove = Move.STOP;
			break;
		default:
			break;
		}
	}
	
	public BallStatus getStatus() {
		return status;
	}
	
	public void launch() {
		if(status != BallStatus.LAUNCHING) 
			return;
		status = BallStatus.BOUNCING;
		
		angleMove = Move.STOP;
		
		double radians = Math.toRadians(currentLaunchAngle);
		xHat =  Math.sin(radians);
		yHat = -Math.cos(radians);
	}
	
	public void setSpeed(double s) {
		speed = s;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
	
	public void flipX() {
		xHat = -xHat;
	}
	
	public void flipY() {
		yHat = -yHat;
	}
	
	public int getRadius() {
		return radius;
	}
	
	public void applySlow() {
		speed = Values.BALL_SLOW_SPEED;
	}


}

enum BallStatus {
	LAUNCHING,
	BOUNCING
}

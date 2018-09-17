import java.awt.Graphics;
import java.util.ArrayList;

public class BrickField implements Drawable {

	final static int BRICK_WIDTH = Values.BRICK_WIDTH;
	final static int BRICK_HEIGHT = Values.BRICK_HEIGHT;
	final static int w = Values.BRICK_ARRAY_WIDTH;
	final static int h = Values.BRICK_ARRAY_HEIGHT;
	
	int selectorW, selectorH;
	Brick selectedBrick;
	
	int x, y;
	
	Brick[][] bricks;
	
	private BrickField(int[][] field) {
		bricks = new Brick[h][w];
		
		for(int h = 0; h < field.length; h++) {
			for(int w = 0; w < field[h].length; w++) {
				bricks[h][w] = new Brick(
					field[h][w],
					x + w * BRICK_WIDTH,
					y + h * BRICK_HEIGHT);
			}
		}
		
		x = 0;
		y = 0;
		selectedBrick = bricks[0][0];
	}
	
	public ArrayList<Brick> getCollisions(Ball ball) {
		ArrayList<Brick> bricksInCollision = new ArrayList<Brick>();
		for(int h = 0; h < bricks.length; h++) {
			for(int w = 0; w < bricks[h].length; w ++) {
				if(bricks[h][w].inHitbox(ball))
					bricksInCollision.add(bricks[h][w]);
			}
		}
		return bricksInCollision;
	}
	
	public boolean isLevelFinished() {
		boolean finished = true;
		for(int h = 0; h < bricks.length; h++) {
			for(int w = 0; w < bricks[h].length; w++) {
				finished &= !bricks[h][w].alive();
			}
		}
		return finished;
	}
	
	public static BrickField makeBlankField() {
		String[] blankLevel = {
			"0000000",
			"0000000",
			"0000000",
			"0000000",
			"0000000",
			"0000000",
			"0000000",
			"0000000",
			"0000000",
			"0000000"
		};
		return makeField(blankLevel);
	}
	
	public static BrickField makeField(String[] field) throws IllegalArgumentException {
		int[][] newBricks = new int[h][w];
		
		if(field == null || field.length != h) {
			throw new IllegalArgumentException();
		}
		
		for(int h = 0; h < field.length; h++) {
			if(field[h].length() != w) {
				System.out.println(field[h]);
				throw new IllegalArgumentException("" + field[h].length());
			}
			
			for(int w = 0; w < field[h].length(); w++) {
				int health;
				char c = field[h].charAt(w);
				
				if(c == 'x') {
					health = -1;
				} else {
					switch(c) {
					case '0': health = 0; break;
					case '1': health = 1; break;
					case '2': health = 2; break;
					case '3': health = 3; break;
					case '4': health = 4; break;
					case '5': health = 5; break;
					default: health = 0; break;
					}
				}
				
				newBricks[h][w] = health;
			}
		}
		
		return new BrickField(newBricks);
	}

	@Override
	public void draw(Graphics gc) {
		for(int h = 0; h < bricks.length; h++) {
			for(int w = 0; w < bricks[h].length; w++) {
				bricks[h][w].draw(gc);
			}
		}
		
		if(selectedBrick != null) {
			selectedBrick.draw(gc);
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public void shiftY(int dy) {
		if(y < Values.BRICK_MAX_SHIFT) {
			y += dy;
		
			for(int h = 0; h < bricks.length; h++) {
				for(int w = 0; w < bricks[h].length; w++) {
					bricks[h][w].shiftY(dy);
				}
			}
		}
		
	}
	
	public Brick getBrickWithPoint(double rx, double ry) {
		for(int h = 0; h < bricks.length; h++) {
			for(int w = 0; w < bricks[h].length; w++) {
				Brick b = bricks[h][w];
				int bx = b.getX();
				int by = b.getY();
				
				if(b.doDraw())
				if(rx > bx && rx < bx + Values.BRICK_WIDTH
				&& ry > by && ry < by + Values.BRICK_HEIGHT) {
					return b;
				}
			}
		}
		
		return null;
	}
	
	public String[] getLevelInFileFormat() {
		String[] level = new String[10];
		
		for(int i = 0; i < level.length; i++) {
			String row = "";
			for(int j = 0; j < 7; j++) {
				switch(bricks[i][j].getHealth()) {
				case 0:
					row += "0";
					break;
				case 1:
					row += "1";
					break;
				case 2:
					row += "2";
					break;
				case 3:
					row += "3";
					break;
				case 4:
					row += "4";
					break;
				case 5:
					row += "5";
					break;
				case -1:
					row += "x";
					break;
				}
			}
			level[i] = row;
		}
		
		return level;
	}
	
	public void moveUp() {
		selectorH--;
		if(selectorH < 0) {
			//selectorH = h-1;
			selectorH = 0;
		}
		selectNewBrick();
	}
	
	public void moveDown() {
		selectorH++;
		if(selectorH > h-1) {
			//selectorH = 0;
			selectorH = h-1;
		}
		selectNewBrick();
	}
	
	public void moveLeft() {
		selectorW--;
		if(selectorW < 0) {
			//selectorW = w-1;
			selectorW = 0;
		}
		selectNewBrick();
	}
	
	public void moveRight() {
		selectorW++;
		if(selectorW > w-1) {
			//selectorW = 0;
			selectorW = w-1;
		}
		selectNewBrick();
	}
	
	public void selectNewBrick() {
		if(selectedBrick == null) {
			selectedBrick = bricks[selectorH][selectorW];
		}
		selectedBrick.deselect();
		selectedBrick = bricks[selectorH][selectorW];
		selectedBrick.select();
	}
	
	public void cycleBrickHealth() {
		selectedBrick.cycleHealth();
	}
	
	public void setBrickHealth(int h) {
		selectedBrick.setHealth(h);
	}
	
	public void clearAllBricks() {
		for(int h = 0; h < bricks.length; h++) {
			for(int w = 0; w < bricks[h].length; w++) {
				bricks[h][w].setHealth(0);;
			}
		}
	}
}

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class Sprite {

	// Ex..

	// 0 0 0 0 0 0 0
	// 0 1 0 0 0 1 0
	// 0 0 0 0 0 0 0
	// 0 1 1 1 1 1 0
	// 0 0 0 0 0 0 0

	int w, h;
	String hex;
	Color color;

	int[][] pixels;

	public Sprite(int w, int h, String hex, Color c) {
		if(w == 0 || h == 0)
			throw new IllegalArgumentException("NO EMPTY SPRITES ALLOWED");

		this.h = h;
		this.w = w;
		this.color = c;

		// check for valid hex length
		if(hex.length() % h != 0)
			throw new IllegalArgumentException("BAD HEX");

		this.hex = hex;

		pixels = hexToArray();
	}

	public Sprite(int[][] pixels, Color c) {
		this.pixels = pixels;
		h = pixels.length;
		w = pixels[0].length;
		color = c;
	}

	public int[][] hexToArray() {
		if(hex == null) {
			return null;
		}

		int[][] pixels = new int[h][w];
		int wCtr = 0;
		int hCtr = 0; // counters

		for(int i = 0; i < hex.length(); i++) {
			if(hCtr >= h) {
				hCtr = 0;
				wCtr += 4;
			}

			String letter = hex.substring(i, i+1);
			int[] block = Sprites.getIntFromHex(letter);

			int shift = 4 - (w - wCtr);

			for(int j = 0; j < block.length; j++) {
				if(shift < 0)
					shift = 0;

				// it works dont touch!
				// basically prioritizes the last elements in block
				// when there isnt enough room to add all of block to pixels
				if(shift == 0 || j >= shift) {
					pixels[hCtr][wCtr+j-shift] = block[j];
				}
			}

			hCtr++;
		}

		return pixels;
	}

	public void setColor(Color c) {
		color = c;
	}

	public int[][] getPixelArray() {
		int[][] newArray = new int[h][w];
		for(int i = 0; i < h; i++) {
			for(int j = 0; j < w; j++) {
				newArray[i][j] = pixels[i][j];
			}
		}
		return newArray;
	}

	// takes in Graphics, location, and pixel size
	// uses currect Graphics color
	public void draw(Graphics gc, int x, int y, int t) {
		for(int h = 0; h < pixels.length; h++) {
			for(int w = 0; w < pixels[h].length; w++) {
				if(pixels[h][w] == 1) {
					gc.setColor(color);
					gc.fillRect(x + w*t, y + h*t, t, t);
				}
			}
		}
	}
	
	public static Point getCenteredCoords(Rectangle r, int w, int h) {
		// center of rectangle
		int cx = (int) (r.getX() + r.getWidth() / 2);
		int cy = (int) (r.getY() + r.getHeight() / 2);
		
		// centered coordinates
		int nx = cx - w/2; 
		int ny = cy - h/2;
		
		return new Point(nx, ny);
	}
	
	public void drawInCenter(Graphics gc, Rectangle r, int t) {
		int nx = (int) Sprite.getCenteredCoords(r, w*t, h*t).getX();
		int ny = (int) Sprite.getCenteredCoords(r, w*t, h*t).getY();
		
		// draw sprite at nx ny
		draw(gc, nx, ny, t);
	}

	public int getW() {
		return w;
	}

	public int getH() {
		return h;
	}
}

class CharSprite extends Sprite {

	public CharSprite(String l, Color c) {
		super(5, 7, Sprites.getHex(l), c);
	}
}

class WordSprite extends Sprite {

	private WordSprite(int[][] pixels, Color c) {
		super(pixels, c);
	}

	public static WordSprite makeWordSprite(String text, Color c) {
		if(text.length() == 0 || text == null)
			throw new IllegalArgumentException("makeWordSprite: string empty or null");

		int l = text.length();
		int w = l*6;
		int[][] pixels = new int[7][w];
		CharSprite[] letterSprites = new CharSprite[l];

		for(int i = 0; i < l; i++) {
			String letter = text.substring(i, i+1);
			letterSprites[i] = new CharSprite(letter, c);
			int[][] letterPixels = letterSprites[i].getPixelArray();

			for(int j = 0; j < letterPixels.length; j++) {
				for(int k = 0; k < 6; k++) {
					if(k < 5) {
						pixels[j][i*6+k] = letterPixels[j][k];
					} else {
						pixels[j][i*6+k] = 0;
					}
				}
			}
		}

		return new WordSprite(pixels, c);
	}
}












import java.awt.Graphics;
import java.util.ArrayList;

public class Menu {

	Sprite selector;

	ArrayList<MenuOption> options = new ArrayList<MenuOption>();
	int menuIndex; // current slection index
	Alignment currentAlignment;

	int margin;
	int separation;
	int selectorSeparation;
	int pixelSize;

	boolean wrap = true;

	public Menu() {
		selector = Sprites.DEFAULT_SELECTOR_SPRITE;
		menuIndex = 0;

		// DEFAULTS
		currentAlignment = Alignment.LEFT;
		margin = 0;
		separation = 1;
		selectorSeparation = 1;
		pixelSize = 1;
		wrap = false;
	}

	public String getSelectionText() {
		optionCheck();
		return options.get(menuIndex).getText();
	}

	public void moveUp() {
		optionCheck();
		menuIndex--;

		if(menuIndex == -1) {
			menuIndex = wrap ? options.size()-1 : 0;
		}
	}

	public void moveDown() {
		optionCheck();
		menuIndex++;

		if(menuIndex == options.size()) {
			menuIndex = wrap ? 0 : options.size()-1;
		}
	}

	public void draw(Graphics gc, int y) {
		optionCheck();

		for(int i = 0; i < options.size(); i++) {
			// DRAWS SELECTOER
			if(menuIndex == i) {
				int tx = margin - pixelSize * (selector.getW() + selectorSeparation);
				selector.draw(gc, tx, y, pixelSize);
			}

			// DRAWS OPTION TEXT
			if(currentAlignment == Alignment.LEFT) {
				WordSprite ws = options.get(i).getSprite();
				ws.draw(gc, margin, y, pixelSize);
				y += pixelSize * (ws.getH() + separation);
			}
		}

	}

	public void addMenuOption(String text) {
		menuIndex = 0;
		options.add(new MenuOption(text));
	}

	public void setAlignment(Alignment a) {
		if(a != null) {
			currentAlignment = a;	
		}
	}

	public void setMargin(int m) {
		margin = m;
	}

	public void setOptionSeparation(int s) {
		if(s > 0) {
			separation = s;
		}
	}

	public void setSelectorSeparation(int s) {
		if(s > 0) {
			selectorSeparation = s;
		}
	}

	public void setPixelSize(int s) {
		if(s > 0) {
			pixelSize = s;
		}
	}

	public void allowWrap(boolean b) {
		wrap = b;
	}

	public void optionCheck() {
		if(options.size() == 0)
			throw new RuntimeException("NO OPTIONS IN MENU");
	}

}

class MenuOption {

	WordSprite textSprite;
	String text;

	public MenuOption(String text) {
		textSprite = WordSprite.makeWordSprite(text, Values.MENU_TEXT_COLOR);
		this.text = text;
	}

	public void draw(Graphics gc, int x, int y, int t) {
		textSprite.draw(gc, x, y, t);
	}

	public WordSprite getSprite() {
		return textSprite;
	}

	public String getText() {
		return text;
	}
}

enum Alignment {
	LEFT,
	RIGHT,
	CENTER;
}

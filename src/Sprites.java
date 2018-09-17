import java.awt.Color;
import java.util.TreeMap;

public class Sprites {

	final static Sprite PADDLE_SPRITE_NORMAL_OUTLINE = new Sprite(
		48, 12,
		"37ECCCCCCE73"
	  + "FF00000137EC"
	  + "FF0000FF8000"
	  + "FF0000FF0000"
	  + "FF0000FF0000"
	  + "FF0000FF0000"
	  + "FF0000FF0000"
	  + "FF0000FF0000"
	  + "FF0000FF0000"
	  + "FF0000FF1000"
	  + "FF000008CE73"
	  + "CE73333337EC",
		new Color(0x0086C1)
	);
	
	final static Sprite PADDLE_SPRITE_NORMAL_INNER = new Sprite(
		48, 12,
		"001333333100"
	  + "00FFFFFEC800"
	  + "00FFFF000000"
	  + "00FFFF000000"
	  + "00FFFF000000"
	  + "00FFFF000000"
	  + "00FFFF000000"
	  + "00FFFF000000"
	  + "00FFFF000000"
	  + "00FFFF000000"
	  + "00FFFFF73100"
	  + "008CCCCCC800",
		new Color(0x00b0ff)
	);
	
	final static Sprite PADDLE_SPRITE_LONG_OUTLINE = new Sprite(
		72, 12,
		"37ECCE730000"
	  + "FF0000FF0000"
	  + "FF0000FF0000"
	  + "FFECCCCCCE73"
	  + "FF00000137EC"
	  + "FF0000FF8000"
	  + "FF0000FF0000"
	  + "FF0000FF0000"
	  + "FF0000FF0000"
	  + "FF0000FF0000"
	  + "FF0000FF0000"
	  + "FF0000FF0000"
	  + "FF0000FF1000"
	  + "FF000008CE73"
	  + "FF73333337EC"
	  + "FF0000FF0000"
	  + "FF0000FF0000"
	  + "CE7337EC0000",
		new Color(0x0086C1)
	);
	
	final static Sprite PADDLE_SPRITE_LONG_INNER_INNER = new Sprite(
		72, 12,
		"000000000000"
	  + "000000000000"
	  + "000000000000"
	  + "001333333100"
	  + "00FFFFFEC800"
	  + "00FFFF000000"
	  + "00FFFF000000"
	  + "00FFFF000000"
	  + "00FFFF000000"
	  + "00FFFF000000"
	  + "00FFFF000000"
	  + "00FFFF000000"
	  + "00FFFF000000"
	  + "00FFFFF73100"
	  + "008CCCCCC800"
	  + "000000000000"
	  + "000000000000"
	  + "000000000000",
		new Color(0x00b0ff)
	);
	
	final static Sprite PADDLE_SPRITE_LONG_INNER_OUTER = new Sprite(
		72, 12,
		"001331000000"
	  + "00FFFF000000"
	  + "00FFFF000000"
	  + "000000000000"
	  + "000000000000"
	  + "000000000000"
	  + "000000000000"
	  + "000000000000"
	  + "000000000000"
	  + "000000000000"
	  + "000000000000"
	  + "000000000000"
	  + "000000000000"
	  + "000000000000"
	  + "000000000000"
	  + "00FFFF000000"
	  + "00FFFF000000"
	  + "008CC8000000",
		new Color(0xF6C300)
	);
	
	final static Sprite LIFE_SPRITE_OUTLINE = new Sprite(
		13, 11,
		"35888421000"
	  + "85200000852"
	  + "E1000124800"
	  + "00111000000",
		new Color(0xE80000)
	);
	
	final static Sprite LIFE_SPRITE_INNER = new Sprite(
		13, 11,
		"03777310000"
	  + "08DFFFFF720"
	  + "0EFFFEC8000"
	  + "00000000000",
		new Color(0xBA0000)
	);
	
	final static Sprite POWERUP_SPRITE_OUTLINE = new Sprite(
		20, 10,
		"36C8888C63"
	  + "F00000000F"
	  + "F00000000F"
	  + "F00000000F"
	  + "C63111136C",
		new Color(0x69A929)
	);
	
	final static Sprite POWERUP_SPRITE_INNER = new Sprite(
		20, 10,
		"0137777310"
	  + "0FFFFFFFF0"
	  + "0FFFFFFFF0"
	  + "0FFFFFFFF0"
	  + "08CEEEEC80",
		new Color(0x79CC26)
	);
	
	final static Sprite LOGO_TEXT_SPRITE = new Sprite(
		64, 21,
		"FFFEEEEEEFFFEEEEEEFFF"
	  + "FFF000000FFF000000FFF"
	  + "FFF100001FFF100001FFF"
	  + "0CCEEEEECC8CCEEEEECC0"
	  + "00000001360000001136C"
	  + "00000CF8130000008F127"
	  + "000000892C00000012369"
	  + "000404C49F000000C2C1E"
	  + "0000374CE300000035896"
	  + "00008C401F0000004CC57"
	  + "03555FEFD4003555FEFD4"
	  + "0000C22C0F00000C22C0F"
	  + "000000026C000000347C3"
	  + "00000000000000008493E"
	  + "0000000000000000CF813"
	  + "00000000000000000893E",
		new Color(0xA6413A)
	);
	
	final static Sprite PADDLE_GUN_OUTER = new Sprite(
		48, 12,
		"000000000000"
	  + "000000000000"
	  + "000000000000"
	  + "000000000000"
	  + "000000000000"
	  + "443000000000"
	  + "22C000000000"
	  + "000000000000"
	  + "000000000000"
	  + "000000000000"
	  + "000000000000"
	  + "000000000000",
		new Color(0x4D4D4D)
	); 
	
	final static Sprite PADDLE_GUN_INNER = new Sprite(
		48, 12,
		"000000000000"
	  + "000000000000"
	  + "000000000000"
	  + "000000000000"
	  + "000000000000"
	  + "330000000000"
	  + "CC0000000000"
	  + "000000000000"
	  + "000000000000"
	  + "000000000000"
	  + "000000000000"
	  + "000000000000",
		new Color(0x8D8D8D7)
	); // TODO DELETE?
	
	final static Sprite PADDLE_GUN = new Sprite(
		48, 12,
		"000000000000"
	  + "000000000000"
	  + "000000000000"
	  + "000000000000"
	  + "000000000000"
	  + "333000000000"
	  + "CCC000000000"
	  + "000000000000"
	  + "000000000000"
	  + "000000000000"
	  + "000000000000"
	  + "000000000000",
		new Color(0x4D4D4D)
	); 
	
	final static Sprite PADDLE_LASER = new Sprite(
		48, 12,
		"000000000000"
	  + "000000000000"
	  + "111000000000"
	  + "888000000000"
	  + "000000000000"
	  + "000000000000"
	  + "000000000000"
	  + "000000000000"
	  + "111000000000"
	  + "888000000000"
	  + "000000000000"
	  + "000000000000",
		Color.RED
	);
	
	final static Sprite ROCKET_WHITE = new Sprite(
		9, 22,
		"0000000322333333000000"
	  + "0000000E2AEEEEEE000000"
	  + "0000000000000000000000",
		Color.WHITE
	);
	
	final static Sprite ROCKET_LIGHT_GRAY = new Sprite(
		9, 22,
		"0113330000044CCC800000"
	  + "8CCEEE0000099999888888"
	  + "0000000000000111100000",
		new Color(0x787878)
	);
	
	final static Sprite ROCKET_DARK_GRAY = new Sprite(
		9, 22,
		"0000003000000000000000"
	  + "000000E000000000000000"
	  + "0000000000000000000000",
		new Color(0x4B4B4B)
	);
	
	final static Sprite ROCKET_BLUE = new Sprite(
		9, 22,
		"0000000011000000000000"
	  + "00000000C4000000000000"
	  + "0000000000000000000000",
		new Color(0x000FFF)
	);
	
	final static Sprite ROCKET_YELLOW = new Sprite(
		9, 22,
		"0000000000000000100000"
	  + "0000000000000000480000"
	  + "0000000000000000000000",
		new Color(0xDBB800)
	);
	
	final static Sprite ROCKET_ORANGE = new Sprite(
		9, 22,
		"0000000000000000231000"
	  + "000000000000000026C800"
	  + "0000000000000000000000",
		new Color(0xBB6D03)
	);
	
	final static Sprite ROCKET_RED = new Sprite(
		9, 22,
		"0000000000000000002310"
	  + "00000000000000000026C8"
	  + "0000000000000000000000",
		new Color(0xBB2303)
	);
	
	final static Sprite[] ROCKET = {
		ROCKET_WHITE,
		ROCKET_LIGHT_GRAY,
		ROCKET_DARK_GRAY,
		ROCKET_BLUE,
		ROCKET_YELLOW,
		ROCKET_ORANGE,
		ROCKET_RED
	};
	
	final static Sprite LASER_WHITE = new Sprite(
		3, 22,
		"0222222222222222222220",
		Color.WHITE
		);

	final static Sprite LASER_RED = new Sprite(
		3, 22,
		"2555555555555555555552",
		Color.RED
		);
	
	final static Sprite[] LASER = {
		LASER_RED,
		LASER_WHITE
	};
	
	final static Sprite DEFAULT_SELECTOR_SPRITE = 
			WordSprite.makeWordSprite(">", Values.MENU_TEXT_COLOR);
	
	// TODO paddle lasers and gun and long
	
	final static Color DEFAULT_COLOR = Color.DARK_GRAY;
	
	static boolean madeCharToHexMap = false;
	static TreeMap<String, String> charToHexmap;
	
	private static void makeCharToHexMap() {
		madeCharToHexMap = true;
		charToHexmap = new TreeMap<String, String>();
		charToHexmap.put("A","788F8880111111");
		charToHexmap.put("B","F88F88F0110110");
		charToHexmap.put("C","78888870100010");
		charToHexmap.put("D","F88888F0111110");
		charToHexmap.put("E","F88F88F1000001");
		charToHexmap.put("F","F88F8881000000");
		charToHexmap.put("G","788B8870101111");
		charToHexmap.put("H","888F8881111111");
		charToHexmap.put("I","72222270000000");
		charToHexmap.put("J","31111961000000");
		charToHexmap.put("K","89ACA981000001");
		charToHexmap.put("L","888888F0000001");
		charToHexmap.put("M","8DAA8881111111");
		charToHexmap.put("N","88CA9881111111");
		charToHexmap.put("O","78888870111110");
		charToHexmap.put("P","F88F8880110000");
		charToHexmap.put("Q","7888A960111101");
		charToHexmap.put("R","F88FA980110001");
		charToHexmap.put("S","788700F1000110");
		charToHexmap.put("T","F2222221000000");
		charToHexmap.put("U","88888871111110");
		charToHexmap.put("V","88885521111000");
		charToHexmap.put("W","888AAA51111110");
		charToHexmap.put("X","88646881100011");
		charToHexmap.put("Y","88852221110000");
		charToHexmap.put("Z","F01248F1100001");
		charToHexmap.put("a","00707870001111");
		charToHexmap.put("b","88BC88F0001110");
		charToHexmap.put("c","00788870001010");
		charToHexmap.put("d","00698871111111");
		charToHexmap.put("e","0078F870001100");
		charToHexmap.put("f","344E4440100000");
		charToHexmap.put("g","07887070111110");
		charToHexmap.put("h","88BC8880001111");
		charToHexmap.put("i","20622270000000");
		charToHexmap.put("j","10311960000000");
		charToHexmap.put("k","44456540010001");
		charToHexmap.put("l","62222270000000");
		charToHexmap.put("m","00DA8880001111");
		charToHexmap.put("n","00BC8880001111");
		charToHexmap.put("o","00788870001110");
		charToHexmap.put("p","00F8F880001000");
		charToHexmap.put("q","00697000011111");
		charToHexmap.put("r","00BC8880001000");
		charToHexmap.put("s","007870F0000010");
		charToHexmap.put("t","44E44430000010");
		charToHexmap.put("u","00888960011111");
		charToHexmap.put("v","00888520011100");
		charToHexmap.put("w","0088AA50011110");
		charToHexmap.put("x","00852580010001");
		charToHexmap.put("y","00887070011110");
		charToHexmap.put("z","00F124F0010001");
		charToHexmap.put("0","789AC870111110");
		charToHexmap.put("1","26222270000000");
		charToHexmap.put("2","780124F0110001");
		charToHexmap.put("3","F1210871000110");
		charToHexmap.put("4","1359F110000100");
		charToHexmap.put("5","F8F00871001110");
		charToHexmap.put("6","348F8870000110");
		charToHexmap.put("7","F0124441100000");
		charToHexmap.put("8","78878870110110");
		charToHexmap.put("9","78870160111100");
		charToHexmap.put(".","00000660000000");
		charToHexmap.put("?","78012020110000");
		charToHexmap.put(",","00006240000000");
		charToHexmap.put(":","06606600000000");
		charToHexmap.put(" ","00000000000000");
		charToHexmap.put("-","000F0000001000");
		charToHexmap.put("\'","22200000000000");
		charToHexmap.put("(","12444210000000");
		charToHexmap.put(")","42111240000000");
		charToHexmap.put("=","00F0F000010100");
		charToHexmap.put("\"","55500000000000");
		charToHexmap.put("[","32222230000000");
		charToHexmap.put("]","62222260000000");
		charToHexmap.put(">","42101240001000");
		charToHexmap.put("<","12484210000000");
		charToHexmap.put("!","22222020000000");
		charToHexmap.put("+","022F2200001000");
	}
	
	public static String getHex(String l) {
		if(!madeCharToHexMap)
			makeCharToHexMap();
		
		if(l.length() != 1)
			throw new IllegalArgumentException();
		
		String hex = charToHexmap.get(l);
		
		if(hex == null)
			return charToHexmap.get(" ");
		
		return charToHexmap.get(l);
	}
	
	public static int[] getIntFromHex(String h) {
		switch(h) {
		case "0": return new int[]{0,0,0,0};
		case "1": return new int[]{0,0,0,1};
		case "2": return new int[]{0,0,1,0};
		case "3": return new int[]{0,0,1,1};
		case "4": return new int[]{0,1,0,0};
		case "5": return new int[]{0,1,0,1};
		case "6": return new int[]{0,1,1,0};
		case "7": return new int[]{0,1,1,1};
		case "8": return new int[]{1,0,0,0};
		case "9": return new int[]{1,0,0,1};
		case "A": return new int[]{1,0,1,0};
		case "B": return new int[]{1,0,1,1};
		case "C": return new int[]{1,1,0,0};
		case "D": return new int[]{1,1,0,1};
		case "E": return new int[]{1,1,1,0};
		case "F": return new int[]{1,1,1,1};
		default:  return new int[]{0,0,0,0};
		}
	}
}

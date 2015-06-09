import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;

public class TileConstants {
	/**=========================
	// STATIC OBJECT TO ACCESS STATIC CLASSES.
	//==========================*/
	static final TileConstants singleton = new TileConstants();
	
	/**=========================
	// PRIVATE CONSTRUCTOR. - Prevents default instantiation.
	//==========================**/
	private TileConstants() {}
	/**=========================
	// CONSTANTS.
	//==========================**/
	// IMAGE RESOURCES.
	private static String path = "../bin/";
	private static String imgpath = path + "img/";
	private static String levelpath = path + "levels/";
	
	public static final String GRASS = imgpath + "grass.png";
	public static final String TREE = imgpath + "tree.png";
	//public static final String PEAK = imgpath + "peak.png";
	//public static final String CHEST = imgpath + "chest.png";
	//public static final String WATER = imgpath + "water.png";
	//public static final String GROUND = imgpath + "ground.png";
	
	public static final String L1 = levelpath + "level1.txt";
	//public static final String L2 = levelpath + "level2.txt";
	//public static final String L3 = levelpath + "level3.txt";
	//public static final String L4 = levelpath + "level4.txt";
	
	/*public static String[][] LEVELS = {{L1, L3, L2},
										 {L1, L4, L2}};*/
	
	// KEY INPUT CONSTANTS.
	public static final int UP = KeyEvent.VK_UP;
	public static final int LEFT = KeyEvent.VK_LEFT;
	public static final int RIGHT = KeyEvent.VK_RIGHT;
	public static final int DOWN = KeyEvent.VK_DOWN;
	public static final int ESC = KeyEvent.VK_ESCAPE;
	public static final int X = KeyEvent.VK_X;
	public static final int G = KeyEvent.VK_G;
	
	// Load images
	public static BufferedImage loadTile(String filename) {
		BufferedImage b = null;
	
		try {
			b = ImageIO.read(singleton.getClass().getResourceAsStream(filename));
		} catch (Exception e) {
			// Print error if something goes wrong. Assume file is not read or loaded correctly.
			System.err.println("ERROR: cannot read file properly.");
			e.printStackTrace();
			System.exit(1);
		}
		
		return b;
	}
}

import java.awt.*;
import java.awt.image.*;

public class Tile {
	/**================================
	/* TILE VARIABLES.
	/**================================*/
	private BufferedImage img;
	private String type;
	private boolean solid, selected;
	
	public Tile(char c) {
		init(c);
	}
	
	/*public Tile(int i) {
		initTiles(i);
	}*/
	
	private void init(char c) {
		switch(c) {
		case 'G': // grass tile
			img = TileConstants.loadTile(TileConstants.GRASS);
			setType("Grass");
			setSolid(false);
			break;
		case 'T': // tree tile
			img = TileConstants.loadTile(TileConstants.TREE);
			setType("Tree");
			setSolid(true);
			break;
		case 'P': // peak tile
			//img = TileConstants.loadTile(TileConstants.PEAK);
			setType("Peak");
			setSolid(true);
			break;
		case 'C': // chest tile
			//img = TileConstants.loadTile(TileConstants.CHEST);
			setType("Chest");
			setSolid(true);
			break;
		case 'W': // water tile
			//img = TileConstants.loadTile(TileConstants.WATER);
			setType("Water");
			setSolid(true);
			break;
		case 'D': // ground tile
			//img = TileConstants.loadTile(TileConstants.GROUND);
			setType("Ground");
			setSolid(false);
			break;
		default: // default is grass
			img = TileConstants.loadTile(TileConstants.GRASS);
			setType("Grass");
			setSolid(false);
			break;
		}
	}
	
	/*private void init(int i) {
		switch(i) {
		case 0:
			setSolid(false);
			break;
		case 1:
			setSolid(true);
			break;
		case 2:
			setSolid(true);
			break;
		case 3:
			setSolid(true);
			break;
		default:
			setSolid(false);
			break;
		}
	}*/
	
	public BufferedImage getImage() { return img; }
	
	public String getType() { return type; }
	
	public boolean isSolid() { return solid; }
	
	public boolean isSelected() { return selected; }
	
	public void setType(String t) { type = t; }
	
	public void setSolid(boolean s) { solid = s; }
	
	public void setSelected(boolean s) { selected = s; }
	
	public void replaceTile(char replachar) { init(replachar); }
	
	/*public void replaceTile(int repli) { init(repli); }*/
}

import java.awt.*;
import java.awt.event.*;

public class MouseCursor {
	/**=================================
	/* VARIABLES.
	/*=================================*/
	// MEASUREMENTS.
	private int w, h;
	
	// CURRENT CHUNK.
	private Chunk chunk;
	
	/**=====================================
	// CONSTRUCTOR(Chunk,size).
	//=====================================*/
	public MouseCursor(Chunk c, int size) {
		w = h = size;
		chunk = c;
	}
	
	public void update() {}
	
	public void isHovered(int mx, int my) {
		Tile selectTile = chunk.getTile(my/h,mx/w);
		selectTile.setSelected(checkCorners(mx,my));
		
		if (mx >= TilePanel.WIDTH-TilePanel.OFFSET/2 || my >= TilePanel.HEIGHT-TilePanel.OFFSET/2 ||
			mx <= 1 || my <= 1) {
			selectTile.setSelected(false);
		}
	}
	
	private boolean checkCorners(int mx, int my) {
		int tilex = chunk.getTilex(mx/w);
		int tiley = chunk.getTiley(my/h);
		int tilew = chunk.tileWidth();
		int tileh = chunk.tileHeight();
		
		if (mx >= tilex && mx <= tilex + tilew &&
			my >= tiley && my <= tiley + tileh) {
			return true;
		}
		return false;
	}
	
	/**===================================================
	/* MAIN DRAWING METHOD.
	/**==================================================*/
	public void draw(Graphics g, int mx, int my) {
		Graphics2D g2d = antialias(g);
		drawMouseCursor(g2d,mx,my);
	}
	
	private void drawMouseCursor(Graphics2D g2d, int mx, int my) {
		int tmx = mx / w;
		int tmy = my / h;
		Tile selectTile = chunk.getTile(tmy,tmx);
		if (selectTile.isSelected()) {
			g2d.drawRect(chunk.getTilex(tmx), chunk.getTiley(tmy), w, h);
		}
	}
	
	private Graphics2D antialias(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
			RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
			RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		return g2;
	}
}

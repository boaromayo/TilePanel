import java.awt.*;
import java.awt.event.*;

public class Cursor {
	/**====================================
	// VARIABLES.
	//====================================*/
	// POSITIONS AND MEASUREMENTS.
	private int x, y;
	private int dx, dy;
	private int tx, ty;
	private int w, h;
	
	// MOVEMENT.
	private boolean up, left, right, down;
	
	// CURRENT CHUNK.
	private Chunk currentChunk;
	private Chunk previousChunk;
	
	// TOGGLE PASSABILITY.
	private boolean toggle;
	
	/**=====================================
	// CONSTRUCTOR(size).
	//=====================================*/
	public Cursor(int size) {
		tx = 0; 
		ty = 0;
		dx = 0;
		dy = 0;
		w = size;
		h = size;
		this.x = tx * w; 
		this.y = ty * h;
		
		up = left = right = down = false;
		toggle = false;
		
		currentChunk = null;
	}
	
	/**=====================================
	// CONSTRUCTOR(Chunk,x,y,size)
	//======================================*/
	public Cursor(Chunk chunk, int x, int y, int size) {
		checkTileBounds(x,y,size);
		
		dx = 0;
		dy = 0;
		w = size;
		h = size;
		this.x = tx * w;
		this.y = ty * h;
		
		up = left = right = down = false;
		toggle = false;
		
		currentChunk = chunk;
	}
	
	protected void checkTileBounds(int x, int y, int size) {
		if (x > ((TilePanel.WIDTH*TilePanel.SCALE) / (size*TilePanel.SCALE)) - 1 || x < 0) {
			tx = 0;
		} else {
			tx = x;
		}
		if (y > ((TilePanel.HEIGHT*TilePanel.SCALE) / (size*TilePanel.SCALE)) - 1 || y < 0) {
			ty = 0;
		} else {
			ty = y;
		}
	}
	
	public void setPosition(int x, int y) { this.x = x; this.y = y; }
	
	public void setTilePosition(int x, int y) {
		tx = x;
		ty = y;
		setPosition(tx*w,ty*h);
	}
	
	public void update() {
		if (up && ty > 0) {
			ty--; // Update y-tile coords (decrease by 1)
			dy = ty*h; // Update y-window coords (y-tile*height)
		}
		if (left && tx > 0) {
			tx--; // Update x-tile coords (decrease by 1)
			dx = tx*w; // Update x-window coords (x-tile*width)
		}
		if (right && tx < TilePanel.WIDTH / w - 1) {
			tx++; // Update x-tile coords (increase by 1)
			dx = tx*w; // Update x-window coords (x-tile*width)
		}
		if (down && ty < TilePanel.HEIGHT / h - 1) {
			ty++; // Update y-tile coords (increase by 1)
			dy = ty*h; // Update y-window coords (y-tile*height)
		}
		if (toggle) {
			
		}
		
		move();
	}
	
	private void move() {
		if (x >= 0 && x < TilePanel.WIDTH*TilePanel.SCALE &&
			y >= 0 && y < TilePanel.HEIGHT*TilePanel.SCALE) {
			
			if (up) {
				y = dy; // Move y to dy-position
				setUp(false);
			}
			if (left) {
				x = dx; // Move x to dx-position
				setLeft(false);
			}
			if (right) {
				x = dx;
				setRight(false);
			}
			if (down) {
				y = dy;
				setDown(false);
			}
		}
	}
	
	public void isDown(int key) {
		if (key == TileConstants.UP) { setUp(true); }
		if (key == TileConstants.LEFT) { setLeft(true); }
		if (key == TileConstants.RIGHT) { setRight(true); }
		if (key == TileConstants.DOWN) { setDown(true); }
		if (key == TileConstants.X) {
			if (toggle) {
				setToggle(false);
			} else {
				setToggle(true);
			}
		}
	}
	
	public void isUp(int key) {
		if (key == TileConstants.UP) { setUp(false); }
		if (key == TileConstants.LEFT) { setLeft(false); }
		if (key == TileConstants.RIGHT) { setRight(false); }
		if (key == TileConstants.DOWN) { setDown(false); }
	}
	
	/**===================================================
	/* MAIN DRAWING METHOD.
	/**==================================================*/
	public void draw(Graphics g) {
		Graphics2D g2d = antialias(g);
		g2d.setColor(Color.BLACK);
		
		drawCursor(g2d);
		
		drawTileCoords(g2d);
		
		// Depending if the collision toggle is on...
		if (toggle) {
			drawTileSolid(g2d);
		} else {
			drawTileType(g2d);
		}
	
		// NOTE: y is row, x is column
	}
	
	private void drawCursor(Graphics2D g2d) {
		// Draw cursor, draw bolder stroke then rectangle, then back to regular stroke
		g2d.setStroke(new BasicStroke(2));
		g2d.drawRect(x, y, w, h);
		g2d.setStroke(new BasicStroke(1));
	}
	
	private void drawTileCoords(Graphics2D g2d) {
		// Cursor tile coords (tilex,tiley).
		g2d.drawString("(" + tilex() + "," + tiley() + ")", 0, TilePanel.HEIGHT-4);
	}	
	
	private void drawTileSolid(Graphics2D g2d) {
		// Find if cursor tile is passable, note y is row, x is column.
		g2d.drawString("SOLID: " + getCurrentChunk().isTileSolid(tiley(),tilex()), 0, TilePanel.HEIGHT-20); 
	}
	
	private void drawTileType(Graphics2D g2d) {
		// Draw tile character of cursor, note y is row, x is column.
		g2d.drawString("TILE: " + getCurrentChunk().getTileType(tiley(),tilex()), 0, TilePanel.HEIGHT-20); 
	}
	
	private Graphics2D antialias(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
			RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
			RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	
		return g2d;
	}
	
	public void setUp(boolean b) { up = b; }
	
	public void setLeft(boolean b) { left = b; }
		
	public void setRight(boolean b) { right = b; }
	
	public void setDown(boolean b) { down = b; }
	
	public void setToggle(boolean b) { toggle = b; }
	
	public int getx() { return x; }
	
	public int gety() { return y; }
	
	public int tilex() { return tx; }
	
	public int tiley() { return ty; }
	
	public int getWidth() { return w; }
	
	public int getHeight() { return h; }
	
	public Chunk getCurrentChunk() { return currentChunk; }
	
	public Chunk getPreviousChunk() { return previousChunk; }
}

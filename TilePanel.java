/**=====================================================
 * TilePanel - A sample test program
 * meant to showcase the implementation of tile-based
 * graphics. This program is meant as future reference
 * for subsequent projects.
 *--------------------------------------------
 * 2014 Nico Poblete, OM Independent
/**====================================================**/
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class TilePanel extends JPanel implements Runnable, KeyListener {
	/*=================================================*/
	/* VARIABLES.
	/*=================================================*/
	public static final int WIDTH = 320;
	public static final int HEIGHT = 240;
	public static final int SCALE = 2;
	public static final int OFFSET = 10;
	private final int FPS = 24;
	private final int TILESIZE = 20;//32;
	private int targetTime = 1000 / FPS;
	
	private int mx, my;
	private Chunk map;
	//private ChunkMap map;
	private Cursor cursor;
	private MouseCursor mCursor;
	private Thread t;
	private boolean on;
	private boolean grid;
	
	public TilePanel() {
		initialize();
		initThread();
		initFocus();
		initMisc();
		
		t.start(); // Start the thread
	}
	
	private void initialize() {
		on = true; // Set loop to true.
		
		// Set up objects (i.e. map, cursor).
		map = new Chunk(TILESIZE);
		map.loadChunk(TileConstants.L1);
		
		cursor = new Cursor(map,0,0,TILESIZE);
		mCursor = new MouseCursor(map,TILESIZE);
		
		System.out.println("DIMENSIONS: " + (WIDTH*SCALE) + " x " + (HEIGHT*SCALE)); // Print out dimensions of screen.
		System.out.println("SCALE: " + SCALE); // Print out scale.
		System.out.println("R: " + map.getRows() + " C: " + map.getCols()); // Print out rows by cols of tiles.
		
		// Get each character by row by column.
		for (int row = 0; row < map.getRows(); row++) {
			for (int col = 0; col < map.getCols(); col++) {
				System.out.print(map.getTileIndex(row,col) + " ");
			}
			System.out.println();
		}
		
		grid = false; // Set grid to false.
	}
	
	public void run() {
		try {
			long startTime, diffTime;
			long waitTime;
		
			while(on) {
				startTime = System.nanoTime();
				
				update();
				repaint();
				
				diffTime = System.nanoTime() - startTime;
				waitTime = targetTime - diffTime / 1000000;
				
				if (waitTime < 0) waitTime = 5;
				
				Thread.sleep(waitTime);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void initThread() {
		// Make a new thread t.
		t = new Thread(this);
	}
	
	private void initFocus() {
		setFocusable(true);
		requestFocus();
	}
	
	private void initMisc() {
		setPreferredSize(new Dimension(WIDTH*SCALE-OFFSET,HEIGHT*SCALE-OFFSET));
		addKeyListener(this);
		addMouseMotionListener(new MouseMove());
	}
	
	public void update() {
		cursor.update();
		mCursor.update();
	}
	
	@Override
	synchronized protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	
		Image dbi = createImage(WIDTH,HEIGHT);
		Graphics2D dbg = (Graphics2D) dbi.getGraphics();
		draw(dbg);
		g.drawImage(dbi, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
		
		g.dispose();
	}
	
	/**======================================
	/* MAIN DRAWING METHOD.
	/*=======================================**/
	public void draw(Graphics2D g) {
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
			RenderingHints.VALUE_ANTIALIAS_ON); // Set anti-alias on.
		
		map.draw(g);
		
		if (grid) {
			g.setColor(Color.LIGHT_GRAY);
		
			for (int i = 0; i < map.getCols(); i++) {
				g.drawLine(i*TILESIZE, 0, i*TILESIZE, HEIGHT*SCALE);
			}
			for (int j = 0; j < map.getRows(); j++) {
				g.drawLine(0, j*TILESIZE, WIDTH*SCALE, j*TILESIZE);
			}
		}
		
		cursor.draw(g);
		
		mCursor.draw(g,mx/SCALE,my/SCALE);
		
		g.drawString("(" + mx + "," + my + ")", 0, HEIGHT-36);
		g.drawString("(" + (mx/TILESIZE)/SCALE + "," + (my/TILESIZE)/SCALE + ")", 0, HEIGHT-52);
	}
	
	/**========================================
	/* KEY INPUT METHODS.
	/*=========================================**/
	public void keyPressed(KeyEvent kpe) {
		int key = kpe.getKeyCode(); // Get num from pressed key
		cursor.isDown(key); // Control cursor
		
		if (key == TileConstants.G) {
			grid = !grid; // Set grid on if off, off if on
		}
		if (key == TileConstants.ESC) {
			System.exit(0); // Close program.
		}
	}
	
	public void keyReleased(KeyEvent kre) {
		int key = kre.getKeyCode();
		cursor.isUp(key);
	}
	
	public void keyTyped(KeyEvent kte) {}
	
	/**========================================
	/* MOUSE INPUT METHODS.
	/*=========================================**/
	private class MouseMove extends MouseAdapter {
		public void mouseMoved(MouseEvent me) {
			mx = me.getX();
			my = me.getY();
			mCursor.isHovered(mx/SCALE,my/SCALE);
		}
	}
	
	/*private class MouseClick extends MouseAdapter {
		public void mouseClicked(MouseEvent mc) {
			mx = mc.getX();
			my = mc.getY();
			int tmx = (mx/TILESIZE) / SCALE;
			int tmy = (my/TILESIZE) / SCALE;
			cursor.setTilePosition(tmx,tmy);
		}
	}*/
	
	/**==========================
	/* MAIN METHOD.
	/*===========================**/
	public static void main(String[] args) {
		JFrame frm = new JFrame();
		TilePanel t = new TilePanel();
		
		frm.setTitle("TilePanel Test");
		
		frm.add(t);
		frm.pack();

		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frm.setLocationRelativeTo(null);
		
		frm.setVisible(true);
		frm.setResizable(false);
	}
}

import java.io.*;
import java.awt.*;
import java.awt.image.*;

public class Chunk {
	/**=================================
	/* VARIABLES.
	/*=================================*/
	// MEASUREMENTS.
	private int tw, th;
	private int rowlimit, collimit;
	
	// MAP.
	private char[][] chunkChar;
	//private int[][] chunkNum;
	
	// TILES.
	private Tile[][] chunk;
	private int tileSize;
	
	// CHUNK REFERENCES.
	private Chunk next;
	
	/**===============================
	/* CONSTRUCTOR(size).
	/*===============================*/
	public Chunk(int size) {
		next = null;
	
		tileSize = size;
		tw = th = tileSize;
		rowlimit = TilePanel.HEIGHT / th; // Row limit of chunk.
		collimit = TilePanel.WIDTH / tw; // Col limit of chunk.
		
		chunkChar = new char[rowlimit][collimit]; // Set chunk limit of (rowlimit) rows (y) by (collimit) cols (x).
		//chunk = new int[rowlimit][collimit]; // Set chunk limit (int version) of (rowlimit) rows (y) by (collimit) cols (x).
		chunk = new Tile[rowlimit][collimit]; // Set the tile limit to the chunk limit.
	}
	
	/** This will read the selected file to form the chunk of tiles. **/
	public void loadChunk(String filename) {
		try {
			// Call reader to read text file.
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String delim = "\\s+"; // delimiter that ignores whitespace.
		
			for (int row = 0; row < chunk.length; row++) {
				String line = br.readLine();
				String [] chars = line.split(delim);
				for (int col = 0; col < chars.length; col++) {
					char charIndex = chars[col].charAt(0);
					chunkChar[row][col] = charIndex; // Take first character as tile. A space (" ") counts as a skip.
					chunk[row][col] = new Tile(chunkChar[row][col]); // Get the tile obj.
				}
			}
			
			br.close(); // Close stream.
			
		} catch (Exception e) {
			System.err.println("ERROR: Cannot read file correctly!");
			e.printStackTrace();
			System.exit(1); // ERROR!
		}
	}
	
	/**=======================================
	/* MAIN DRAWING METHOD FOR CHUNK CLASS.
	/**=======================================*/
	public void draw(Graphics g) {
		for (int row = 0; row < chunk.length; row++) {
			for (int col = 0; col < chunk[row].length; col++) {
				g.drawImage(chunk[row][col].getImage(), 
					col * tileSize, 
					row * tileSize, 
					null);
			}
		}
	}
	
	public int getTilex(int col) { return col * tileSize; }
	
	public int getTiley(int row) { return row * tileSize; }
	
	public int tileWidth() { return tw; }
	
	public int tileHeight() { return th; }
	
	public int size() { return tileSize; }
	
	public int getRows() { return rowlimit; }
	
	public int getCols() { return collimit; }
	
	public Tile[][] getTiles() { return chunk; }
	
	public Tile getTile(int row, int col) { return chunk[row][col]; }
	
	public String getTileType(int row, int col) { return chunk[row][col].getType(); }
	
	public boolean isTileSolid(int row, int col) { return chunk[row][col].isSolid(); }
	
	public char getTileIndex(int row, int col) { return chunkChar[row][col]; }
	
	public void setTile(int row, int col, char c, String s) {
		setTileType(row,col,s);
		setTileIndex(row,col,c);
	}	
	
	public void setTileType(int row, int col, String s) { chunk[row][col].setType(s); }
	
	public void setTileSolid(int row, int col, boolean b) { chunk[row][col].setSolid(b); }
	
	public void setTileIndex(int row, int col, char c) { chunkChar[row][col] = c; }
	
	public void replace(char c1, char c2) {
		for (int row = 0; row < chunk.length; row++) {
			for (int col = 0; col < chunk[row].length; col++) {
				if (chunkChar[row][col] == c1) { 
					chunkChar[row][col] = c2;
					chunk[row][col].replaceTile(c2);
				}
			}
		}
	}
	
}

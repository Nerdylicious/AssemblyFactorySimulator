package gui;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

/*
 * Acts as a grid to restrict placements 
 * 
 */
public class Grid {
	// well this could have perhaps been done only once in the Grid or Assignment4
	public static int MINX = 150;
	public static int MAXX = 999;
	public static int MINY = 30;
	public static int MAXY = 599;

	public static int GRIDSIZE = 10;
	
	public static int GetWidth() {
		return MAXX-MINX;
	}
	
	public static int GetHeight() {
		return MAXY-MINY;
	}
	
	public static void render(Graphics g) {
		g.drawRect(MINX, MINY, MAXX-MINX, MAXY-MINY);
	}
 	
	// extra to grid objects based on their size
	public static Point GridPoint(Rectangle toGrid) {
		
		// first check the min and max
		if (toGrid.x < MINX)
			toGrid.x = MINX;
		if (toGrid.x + toGrid.width> MAXX)
			toGrid.x = MAXX - toGrid.width;
		if (toGrid.y < MINY)
			toGrid.y = MINY;
		if (toGrid.y + toGrid.height > MAXY)
			toGrid.y = MAXY - toGrid.height;
		
		// mod teach size by the gridsize
		toGrid.x = toGrid.x/GRIDSIZE*GRIDSIZE;
		toGrid.y = toGrid.y/GRIDSIZE*GRIDSIZE;
		
		return new Point(toGrid.x,toGrid.y);		
	}
	
public static Point GridPoint(Point toGrid) {
		
		// first check the min and max
		if (toGrid.x < MINX)
			toGrid.x = MINX;
		if (toGrid.x> MAXX)
			toGrid.x = MAXX;
		if (toGrid.y < MINY)
			toGrid.y = MINY;
		if (toGrid.y > MAXY)
			toGrid.y = MAXY;
		
		// mod teach size by the gridsize
		toGrid.x = toGrid.x/GRIDSIZE*GRIDSIZE;
		toGrid.y = toGrid.y/GRIDSIZE*GRIDSIZE;
		
		return toGrid;		
	}

}

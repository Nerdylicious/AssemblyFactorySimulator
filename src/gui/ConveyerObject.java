package gui;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.StringTokenizer;


/*
 * Conveyer object.  This is quite a lot different than the machine objects.
 * 
 */
public class ConveyerObject  implements BaseObject{

	// stored as a linked list of points, e.g line from point1 to point2, line from
	//  point 2 to point 3 etc.
	LinkedList <Point>pointList;
	// 1/x so every unit takes 1/10 of a unit of time.
	static float distancetotime = 10.0f;
	
	public ConveyerObject() {
		pointList = new LinkedList<Point>();
	}
	
	public void addPointtoList(Point point) {
		pointList.add(point);
	}	
	
	// used when previewing the list
	public void removeLastPoint() {
		pointList.removeLast();
	}

	// draw from one point to the next.
	public void drawObject(Graphics g) {
		// the list should always be two or greater
		boolean first = true;
		Point prev = new Point(0,0);
		for (Point point : pointList) {
			if (first) {
				prev = point;
				first = false;
			}
			else {
				g.drawLine(prev.x,prev.y,point.x,point.y);
				prev = point;
			}			
		}		
	}
	
	public Rectangle getRectangle() {		
		// not used for collisions
		return new Rectangle(-5,-5,-5,-5);
	}
	
	// do collision detection just where the corners are
	public boolean isCollision(BaseObject object) {
		Rectangle rect = object.getRectangle();
		// k check for collision
		// we are only coliding end points, since we don't have or want line to rectangle intersection, and
		//  we allow diagnal lines
		for (Point point : pointList) {
			int size = 2;
			Rectangle rect1 = new Rectangle(point.x-size,point.y-size,size*2,size*2);	
			if (rect.intersects(rect1))
				return true;
		}		
		return false;
	}
	

	public String saveObject() {
		String treturn = "Conveyer ";
		for (Point point : pointList) {
			treturn += point.x + " " + point.y + " ";
		}
		return treturn;		
	}

	public void loadObject(String object) {
		 StringTokenizer st = new StringTokenizer(object);
		 String type = st.nextToken();
		 while (st.hasMoreTokens()) {
			 int x = Integer.parseInt(st.nextToken());
			 int y = Integer.parseInt(st.nextToken());	
			 Point point = new Point(x,y);
			 pointList.add(point);
		 }
	}
	
	
	/*
	 * PURPOSE: Return the distance of the conveyer belt 
	 *          
	 */
	public float getTimeDistance() {
		float treturn = 0;
		Point prev = new Point(0,0);
		boolean first = true;
		for (Point point : pointList) {
			
			if (first) {
				prev = point;
				first = false;
			}
			else {
				treturn += point.distance(prev);
				prev = point;
			}
		}
		return treturn/distancetotime;
	}

	public String getName() {
		
		return "Conveyer";
	}
	
	/*
	 * PURPOSE: Get the location we are in in the conveyer belt. Uses
	 * 			linear interpolation.
	 */
	public Point getLocation(int currenttime, int timeslotstarttime) {
		
		// first find the time difference
		int currtimediff = currenttime - timeslotstarttime;
		// then find the distance from the start that is travelled
		float distance = currtimediff*distancetotime;
		// k now find the location by travelling the belt
		float currenumdistance = 0;
		boolean first = true;
		Point prev = new Point(0,0);
		
		// enumerate the points
		for (Point point : pointList) {
			
			if (first) {
				prev = point;
				first = false;
			}
			else {
				
				// find the distance of the current link
				float currlinklength = (float)prev.distance(point);
				// if the current link plus the distance traveled so far is less keep
				//going
				if (currlinklength + currenumdistance < distance) {
					currenumdistance += currlinklength;
				}
				else {
					// we are in the current link, find the percentage
					float distanceinthislink = distance - currenumdistance;
					float percentage = 1-(currlinklength -
					distanceinthislink)/currlinklength;
					// this is the slope of the line
					Point diff = new Point(point.x-prev.x,point.y-prev.y);
					diff.x = (int)(diff.x*percentage) + prev.x;
					diff.y = (int)(diff.y*percentage) + prev.y;
					return diff;
				}
				
				prev = point;
			}

		}
		// if we are at the end the last point is returned
		//regardless if we would have gone
		// past the conveyer belt.
		return prev;
	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

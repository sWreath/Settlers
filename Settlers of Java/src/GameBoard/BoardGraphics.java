package GameBoard;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Cursor;

import javax.swing.JPanel;

public class BoardGraphics extends JPanel {

	//auto generated id
	private static final long serialVersionUID = -6080271973075824997L;
	
	//set the length from the center to the far vertices of the hex. 
    //The radius of the circumscribed circle
	private double radius = 62;
	//calculate the radius of the inscribed circle
    //this helps with the math to place them
	private double shortR = radius * Math.sqrt(3)/2;
	//center of the topmost left hexagon
	private Point2D.Double start = new Point2D.Double(3*shortR+30, radius+65);
	//array with the center points of all hexagon
	private ArrayList<Point2D.Double> centerPoints = new ArrayList<Point2D.Double>(25);
	//array of all the vertex locations that can have settlements.
	private ArrayList<Point2D.Double> vertex = new ArrayList<Point2D.Double>(60);
	//array of all centers of edge locations where roads could be
	private ArrayList<Point2D.Double> edge = new ArrayList<Point2D.Double>(80);
	//store the state the program is in for hit checking on the map
	//0=do no checks
	//1=try to build settlement
	//2=try to build road
	//3=try to build city
	private int cursorState = 0;
	
	//constructor to setup public variables and create any swing components for this panel
	public BoardGraphics(){
		BoardGraphics bg = this;
		//store original Cursor in case we change it
		Cursor old = bg.getCursor();
		//calculate the important points on the grid, store in global array
		generateCenters();
		generateVertex();
		generateEdges();
		
		//Mouse listener that tracks clicks in map
		bg.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseReleased(MouseEvent arg0){
				bg.setCursor(old);
				if(cursorState == 1){
					Point2D pos = bg.getMousePosition();
					for(int i=0;i<vertex.size();i++){
						if(vertex.get(i).distance(pos)<(radius/3)){
							System.out.println("Build Settlement");
							break;
						}
					}
					cursorState = 0;
				}
				if(cursorState == 2){
					Point2D pos = bg.getMousePosition();
					for(int i=0;i<edge.size();i++){
						if(edge.get(i).distance(pos)<(radius/3)){
							System.out.println("Build Road");
							break;
						}
					}
					cursorState = 0;
				}
				if(cursorState == 3){
					Point2D pos = bg.getMousePosition();
					for(int i=0;i<vertex.size();i++){
						if(vertex.get(i).distance(pos)<(radius/3)){
							System.out.println("Build City");
							break;
						}
					}
					cursorState = 0;
				}
			}
		});
		
		//add buttons for building
		JButton bldSettlement = new JButton("Build Settlement");
		bldSettlement.setBounds(0, 0, 89, 23);
		bldSettlement.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				Cursor c = new Cursor(Cursor.HAND_CURSOR);
				bg.setCursor(c);
				cursorState = 1;
			}
		});
		bg.add(bldSettlement);
		
		JButton bldRoad = new JButton("Build Road");
		bldRoad.setBounds(95, 0, 89, 23);
		bldRoad.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				Cursor c = new Cursor(Cursor.HAND_CURSOR);
				bg.setCursor(c);
				cursorState = 2;
			}
		});
		bg.add(bldRoad);
		
		JButton bldCity = new JButton("Build City");
		bldCity.setBounds(184, 0, 89, 23);
		bldCity.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				Cursor c = new Cursor(Cursor.HAND_CURSOR);
				bg.setCursor(c);
				cursorState = 3;
			}
		});
		bg.add(bldCity);
	}
	
	//get the graphics object from the parent by overriding the normal paint and making our own
	@Override
    public void paintComponent(Graphics g) {
		//cast to the more capable graphics type
        Graphics2D g2d = (Graphics2D) g;
        //draw the hex grid
        drawGrid(g2d);
    }
	
	public static void main(String[] args) {
		// stub for if we decide to make this stand alone for prototyping reasons

	}
	
	private void drawGrid(Graphics2D g2d){
		//set the rules for drawing with the graphics object passed in
        g2d.setStroke(new BasicStroke(2.5f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));
		//draw tiles
        for(int i=0; i<centerPoints.size();i++){
        	//set the colour used by graphics and fill in a hexagon
        	g2d.setPaint(new Color((float)Math.random(),(float)Math.random(),(float)Math.random(),(float)Math.random()));
        	g2d.fill(Hexagon(centerPoints.get(i).getX(),centerPoints.get(i).getY(),radius));
        	//draw hex outline
        	//g2d.draw(Hexagon(centerPoints.get(i).getX(),centerPoints.get(i).getY(),radius));
        }
        //draw labels for tiles, vertexes and edges
        for(int i=0; i<centerPoints.size();i++){
        	g2d.drawString(Integer.toString(i+1), (float)centerPoints.get(i).getX(), (float)centerPoints.get(i).getY());
        }
        for(int i=0;i<vertex.size();i++){
        	g2d.drawString(Integer.toString(i+1), (float)vertex.get(i).getX(), (float)vertex.get(i).getY());
        }
        
        for(int i=0;i<edge.size();i++){
        	g2d.drawString(Integer.toString(i+1), (float)edge.get(i).getX(), (float)edge.get(i).getY());
        }
	}
	
	private GeneralPath Hexagon(double Xcenter, double Ycenter, double radius ){
		//calculate the relative location of all points on a hexagon
		double shortR = radius * Math.sqrt(3)/2;
		double Xpoints[] = {Xcenter+shortR,		Xcenter+shortR,		Xcenter,		Xcenter-shortR, 	Xcenter-shortR, 	Xcenter};
		double Ypoints[] = {Ycenter+0.5*radius,	Ycenter-0.5*radius, Ycenter-radius,	Ycenter-0.5*radius,	Ycenter+0.5*radius,	Ycenter+radius};
		GeneralPath hex = new GeneralPath(GeneralPath.WIND_EVEN_ODD,Xpoints.length);
		//add coordinates to the shape
		hex.moveTo(Xpoints[0], Ypoints[0]);
		for (int index = 1; index < Xpoints.length; index++) {
	        hex.lineTo(Xpoints[index], Ypoints[index]);
		};
		//close the polygon off
		hex.closePath();
		return hex;
	}
	
	private void generateCenters(){
		//construct the array of center locations
		double Xstart = start.getX();
		double Ystart = start.getY();
		for(int i=0;i<3;i++){
			double Xnext = 2*i*shortR+Xstart;
			centerPoints.add(new Point2D.Double(Xnext,Ystart));
		}
		Xstart = Xstart-shortR;
		Ystart = Ystart+1.5*radius;
		for(int i=0;i<4;i++){
			double Xnext = 2*i*shortR+Xstart;
			centerPoints.add(new Point2D.Double(Xnext,Ystart));
		}
		Xstart = Xstart-shortR;
		Ystart = Ystart+1.5*radius;
		for(int i=0;i<5;i++){
			double Xnext = 2*i*shortR+Xstart;
			centerPoints.add(new Point2D.Double(Xnext,Ystart));
		}
		Xstart = Xstart+shortR;
		Ystart = Ystart+1.5*radius;
		for(int i=0;i<4;i++){
			double Xnext = 2*i*shortR+Xstart;
			centerPoints.add(new Point2D.Double(Xnext,Ystart));
		}
		Xstart = Xstart+shortR;
		Ystart = Ystart+1.5*radius;
		for(int i=0;i<3;i++){
			double Xnext = 2*i*shortR+Xstart;
			centerPoints.add(new Point2D.Double(Xnext,Ystart));
		}
	}
	private void generateVertex(){
		//add vertex points
		double Xstart = start.getX();
		double Ystart = start.getY()-radius;
		//move in Y direction
		for(int i=0;i<12;i++){
			//adjust y position as we move in rows
			//moving from even to odd rows is half a radius down, odd to even is a full radius moved
			if(i%2==0 && i!=0){Ystart+=radius;}
			if(i%2==1){Ystart+=0.5*radius;}
			//adjust the first x coordinate
			//we go out one shortR on even to odd transitions in the top half
			//and we go in one shortR on even to odd transitions in the bottom
			if(i%2==1 && i<6){Xstart-=shortR;}
			if(i%2==1 && i>6){Xstart+=shortR;}
			//calculate number of vertexes in row
			int col = 6;
			if(i<=4){
				col = 3+((int)((i+1)/2));
			}
			if(i>=7){
				col = 6-((int)((i-5)/2));
			}
			//move in X position
			for(int j=0;j<col;j++){
				double Xnext = Xstart+2*j*shortR;
				vertex.add(new Point2D.Double(Xnext, Ystart));
			}
		}//end calculate vertexes
	}
	private void generateEdges(){
		//add locations of edge centers
		double Xstart = start.getX()-0.5*shortR;
		double Ystart = start.getY()-0.75*radius;
		//move in Y direction
		for(int i=0;i<11;i++){
			//adjust y position as we move in rows
			//moving down 0.75 radius every time
			if(i!=0){Ystart+=0.75*radius;}
			//adjust the first x coordinate
			//always step out half a short radius until row 5 then in
			if(i!=0 && i<=5){Xstart-=0.5*shortR;}
			if(i>5){Xstart+=0.5*shortR;}
			//calculate number of edges in row
			int col=0;
			if(i%2==0&&i<5){col = 6+i;}
			if(i%2==0&&i>5){col = 10-(i-6);}
			if(i%2==1&&i<6){col = (int)i/2+4;}
			if(i%2==1&&i>6){col = (int)6 - (i-5)/2;}
			//move in X position
			for(int j=0;j<col;j++){
				double Xnext;
				if(i%2==0){
					Xnext = Xstart+shortR*j;
				}else{
					Xnext = Xstart+2*j*shortR;
				}
				edge.add(new Point2D.Double(Xnext, Ystart));
			}
		}//end edge center calc
	}
}

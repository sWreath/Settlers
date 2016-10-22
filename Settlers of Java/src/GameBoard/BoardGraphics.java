package GameBoard;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.GeneralPath;
import java.awt.Stroke;

import javax.swing.JPanel;

public class BoardGraphics extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6080271973075824997L;

	@Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(4.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));
        drawGrid(g2d);
    }
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	private void drawGrid(Graphics2D g2d){
		g2d.draw(Hexagon(50,50,20));
		//for(int i=0,)
	}
	
	private GeneralPath Hexagon(double Xcenter, double Ycenter, double radius ){
		//calculate the relative location of all points on a hexagon
		double height = radius * Math.sqrt(3)/2;
		double Xpoints[] = {Xcenter+radius,	Xcenter+0.5*radius, Xcenter-0.5*radius, Xcenter-radius, Xcenter-0.5*radius, Xcenter+0.5*radius};
		double Ypoints[] = {Ycenter, 		Ycenter+height, 	Ycenter+height,		Ycenter,		Ycenter-height,		Ycenter-height};
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
}

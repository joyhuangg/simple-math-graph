/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw6;

import static hw6.ArrayMath.linspace;
import static hw6.ArrayMath.zeros;
import hw6.MyFunction;
import java.awt.Color;
import static java.awt.Color.*;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Canvas extends JPanel {
    private Plottable2D myFun = new MyFunction();
    private double[] axis = new double[]{0,1,0,1};
    private double xmin = 0;
    private double xmax = 1;
    private double ymin = 0;
    private double ymax = 1;
    private int width = 650;
    private int height = 400;      
    private String title = "";
    private String xlabel = "";
    private String ylabel = "";
    private final double THETA = 270 * java.lang.Math.PI/180;
    private Color lineColor = red; 
    private double[] xpoints = new double[width];
    private double[] ypoints = new double[width];
    private int[] xpixels = new int[width];
    private int[] ypixels = new int[width];
    
    public void setWidth(int w){
        width = w;
    }
    
    public void setHeight(int h){
        height = h;
    }
    @Override
    public void paintComponent(Graphics g){
        setPoints();
        g.setColor(Color.white);
        g.fillRect(0,0, this.getWidth(), this.getHeight());
        //draw axis
        g.setColor(Color.black);
        g.drawLine(0, height-50, width, height-50);
        g.drawLine(0, height-50, 0, 0);
        //put min and max (x,y) labels
        g.setColor(Color.black);
        //g.drawString(Double.toString(xmin), 50, height - 30);
        //g.drawString(Double.toString(xmax), width - 50, height - 30);
        //g.drawString(Double.toString(ymin), 20, height-50);
        //g.drawString(Double.toString(ymax), 20, 50);

        //set x labels
        int labLength = xlabel.length();
        int mid = width/2; 
        int firstLet = mid - labLength/2; 
        //g.drawString(xlabel, firstLet, height-30);

        //set y labels           
        // Create a rotation transformation for the font.
        AffineTransform fontAT = new AffineTransform();

        // get the current font
        Font theFont = g.getFont();

        // Derive a new font using a rotatation transform
        fontAT.rotate(THETA);
        Font theDerivedFont = theFont.deriveFont(fontAT);

        // set the derived font in the Graphics2D context
        g.setFont(theDerivedFont);

        // Render a string using the derived font
        int labLengthY = ylabel.length();
        int midY = height/2; 
        int firstLetY = midY - labLengthY/2; 
        //g.drawString(ylabel, 20, firstLetY);

        // put the original font back
        g.setFont(theFont);   

        //set title
        int labLengthT = title.length();
        int midT = width/2; 
        int firstLetT = mid - labLengthT/2; 
        //g.drawString(title, firstLetT, 30);

        //create line
        g.setColor(lineColor);
        for (int i = 0; i < xpoints.length; i++){
            if (ypoints[i] <= ymax && ypoints[i] >= ymin)
                g.drawLine((int)xpixels[i], ypixels[i], (int)xpixels[i], ypixels[i]);
        }



    }
    public void setPoints(){
        xpoints = new double[width];
        ypoints = new double[width];
        xpixels = new int[width];
        ypixels = new int[width];        
        double yRange = ymax-ymin;
        double xRange = xmax-xmin;
        //create array of x values to test and calculate y values from that
        xpoints = linspace(xmin, xmax, width);
        ypoints = myFun.evaluate(xpoints);
        //create corresponding pixels for x values
        int xpix = 0;
        for (int i = 0; i < xpixels.length; i++){
            xpixels[i] = xpix;
            xpix++;
        }

        //solve y pixels from obtained y values
        for (int i = 0; i <ypixels.length; i++){
            //y proportion
            double yprop = (ypoints[i]-ymin)/yRange;
            int ypix = height - (int)(yprop*(height-100) + 50);
            ypixels[i] = ypix;
        }
    }
    public void setAxis(double[] x){
        axis = x;
        xmin = x[0];
        xmax = x[1];
        ymin = x[2];
        ymax = x[3];
        setPoints();
        this.repaint();
    }
    
    public void setFunction(Plottable2D f){
        myFun = f;
    }
    public void setDisplaySize(int w, int h){
        setPoints();
        width = w;
        height = h;
        xpoints = new double[w];
        this.setPreferredSize(new Dimension(width,height));
        this.repaint();
        
    }
    public void setXLabel(String s){
        xlabel = s;
        this.repaint();
        
    }
    public void setYLabel(String s){
        ylabel = s;
        this.repaint();
    }
    public void setTitle(String s){
        title = s;
        //frame.setTitle(s);
        this.repaint();
    }
    public void setLineColor(Color col){
        lineColor = col;
        this.repaint();
    }

}

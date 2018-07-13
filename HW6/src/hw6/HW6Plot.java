/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw6;

/**
 *
 * @author JoyHuang
 */


//import hw6.Canvas.*;
import hw6.MyFunction.*;
import static hw6.ArrayMath.linspace;
import static hw6.ArrayMath.zeros;
import java.awt.BasicStroke;
import java.awt.Color;
import static java.awt.Color.*;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.text.DecimalFormat;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class HW6Plot extends JFrame implements Plottable2D{
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
    private int lineWidth = 1;
    private int endX;
    private int endY;
    boolean dragged = false;
    private int startX;
    private int startY;
    
    public void setWidth(int w){
        width = w;
    }
    
    public void setHeight(int h){
        height = h;
    }
    @Override
    public void paintComponent(Graphics g){
        //boolean dragged = false;
        setPoints();
        g.setColor(Color.white);
        g.fillRect(0,0, this.getWidth(), this.getHeight());
        //draw axis
        g.setColor(Color.black);
        g.drawLine(0, height-1, width, height-1);
        g.drawLine(0, height, 0, 0);
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
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(lineWidth));
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
            int ypix = height - (int)(yprop*(height));
            ypixels[i] = ypix;
        }
    }
    public void printDebug(){
        System.out.print("xpoints: ");
        ArrayMath.println(xpoints);
        System.out.print("ypoints: ");
        ArrayMath.println(ypoints);
        System.out.print("xpixels: ");
        System.out.println("xmin: " + xmin);
        System.out.println("xmax: " + xmax);
        System.out.println("ymin: " + ymin);
        System.out.println("ymax: " + ymax);
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
        this.repaint();
    }
    public void setLineColor(Color col){
        lineColor = col;
        this.repaint();
    }

}

    private Canvas c;  
    @Override
    //make default evaluate funciton
    public double[] evaluate(double[] x) {
        return zeros(x.length);
    }
    
     
    /**
     * Creates new form HW6Plot
     */
    public HW6Plot() {
        initComponents();
        c = (Canvas)jPanel1;
        c.setWidth(jPanel1.getWidth()); 
        c.setHeight(jPanel1.getHeight());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new Canvas();
        Title = new javax.swing.JLabel();
        xLabel = new javax.swing.JLabel();
        yAxis = new javax.swing.JLabel();
        lineThick = new javax.swing.JComboBox<>();
        lineColor = new javax.swing.JComboBox<>();
        XMax = new javax.swing.JTextField();
        XMin = new javax.swing.JTextField();
        YMin = new javax.swing.JTextField();
        YMax = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanel1MouseDragged(evt);
            }
        });
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel1MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jPanel1MouseReleased(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 680, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 334, Short.MAX_VALUE)
        );

        Title.setText("My Plot");

        xLabel.setText("X Label");

        yAxis.setText("Y Label");

        lineThick.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6" }));
        lineThick.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lineThickActionPerformed(evt);
            }
        });

        lineColor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "red", "black", "blue", "green" }));
        lineColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lineColorActionPerformed(evt);
            }
        });

        XMax.setText("1");
        XMax.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                XMaxKeyTyped(evt);
            }
        });

        XMin.setText("0");
        XMin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                XMinKeyTyped(evt);
            }
        });

        YMin.setText("0");
        YMin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                YMinKeyTyped(evt);
            }
        });

        YMax.setText("1");
        YMax.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                YMaxActionPerformed(evt);
            }
        });
        YMax.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                YMaxKeyTyped(evt);
            }
        });

        jButton1.setText("jButton1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(97, 97, 97)
                .addComponent(lineColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(215, 215, 215)
                .addComponent(xLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(51, 51, 51)
                .addComponent(lineThick, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(125, 125, 125))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(yAxis, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(YMin, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(YMax, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(XMin, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(587, 587, 587)
                                .addComponent(XMax, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(407, 407, 407)
                        .addComponent(Title)))
                .addGap(100, 120, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Title, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(YMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(140, 140, 140)
                        .addComponent(yAxis, javax.swing.GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE)
                        .addGap(101, 101, 101)
                        .addComponent(YMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(XMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(XMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lineThick, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton1))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lineColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(xLabel))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lineThickActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lineThickActionPerformed
        String thick = (String)lineThick.getSelectedItem();
      
        if (thick == "1")
            c.lineWidth = 1;
        else if (thick == "2")
            c.lineWidth = 2;
        else if (thick == "3")
            c.lineWidth = 3;
        else if (thick == "4")
            c.lineWidth = 4;
        else
            c.lineWidth = 5;
        c.repaint();
    }//GEN-LAST:event_lineThickActionPerformed

    private void lineColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lineColorActionPerformed

       String col=  (String)lineColor.getSelectedItem();
       if (col == "red")
           c.setLineColor(Color.red);
       else if (col == "black")
           c.setLineColor(Color.black);
       else if (col == "green")
           c.setLineColor(Color.green);
       else
           c.setLineColor(Color.blue);
                   
    }//GEN-LAST:event_lineColorActionPerformed

    private void XMaxKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_XMaxKeyTyped

        
        try{
            String n = XMax.getText();
            double[] k = c.axis;
            c.xmax = Double.valueOf(n);
            k[1] = Double.valueOf(n);
            c.axis = k;
            c.repaint();
            c.printDebug();            
        } catch (Exception e){
            e.printStackTrace();
        }
    }//GEN-LAST:event_XMaxKeyTyped

    private void XMinKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_XMinKeyTyped
        
        try{
            String n = XMin.getText();
            double[] k = c.axis;
            k[0] = Double.valueOf(n);
            c.xmin = Double.valueOf(n);
            c.axis = k;
            c.repaint();
            c.printDebug();            
        } catch (Exception e){
            e.printStackTrace();
        }
    }//GEN-LAST:event_XMinKeyTyped

    private void YMinKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_YMinKeyTyped

        try{
            String n = YMin.getText();
            double[] k = c.axis;
            k[2] = Double.valueOf(n);
            c.ymin = Double.valueOf(n);
            c.axis = k;
            c.repaint();
            c.printDebug();
        } catch (Exception e){
            e.printStackTrace();
        }      
    }//GEN-LAST:event_YMinKeyTyped

    private void YMaxKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_YMaxKeyTyped

        try{
            String n = YMax.getText();
            double[] k = c.axis;
            k[3] = Double.valueOf(n);
            c.ymax = Double.valueOf(n);
            c.axis = k;
            c.repaint();
            c.printDebug();
        } catch (Exception e){
            e.printStackTrace();
        }      
        
    }//GEN-LAST:event_YMaxKeyTyped

    private void jPanel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseClicked
        double xProp = (double)evt.getX()/c.getWidth();
        double yProp = (c.getHeight() - (double)evt.getY())/c.getHeight();

        double yRange = c.ymax-c.ymin;
        double xRange = c.xmax-c.xmin;  
        double ZOOMOUT = 2;
        double ZOOMIN = 0.5;
        double x = xRange*xProp + c.xmin;
        double y = yRange*(yRange-yProp) + c.ymin;


        //Zoom in         
        if (SwingUtilities.isLeftMouseButton(evt)){
            xRange *= ZOOMIN;
            yRange *= ZOOMIN;
            c.xmin += xProp*xRange; 
            c.xmax = c.xmin + xRange;
            c.ymin += yProp*yRange;
            c.ymax = c.ymin + yRange;
            
        }
        //Zoom out
        else if(SwingUtilities.isRightMouseButton(evt)){
            xRange *= ZOOMOUT;
            yRange *= ZOOMOUT;
            c.xmin -= xProp*(xRange/2); 
            c.xmax = c.xmin + xRange;
            c.ymin -= yProp*(yRange/2);
            c.ymax = c.ymin + yRange;            
        }
        c.axis = new double[]{c.xmin, c.xmax, c.ymin, c.ymax};
        DecimalFormat dF = new DecimalFormat("#.00");
        XMax.setText(dF.format(c.xmax));
        YMax.setText(dF.format(c.ymax));
        XMin.setText(dF.format(c.xmin));
        YMin.setText(dF.format(c.ymin));
        c.repaint();
    }//GEN-LAST:event_jPanel1MouseClicked

    private void jPanel1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseDragged
        c.dragged = true;
        
       

    }//GEN-LAST:event_jPanel1MouseDragged

    private void jPanel1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseReleased
        //need to find change in x and y from startig point to ending point and alter the graph
        //this way not make it the middle
        if (c.dragged){
            c.endX = evt.getX();
            c.endY = evt.getY();
            double xProp = (double)c.endX/c.getWidth();
            double yProp = (double)c.endY/c.getHeight();

            double yRange = c.ymax-c.ymin;
            double xRange = c.xmax-c.xmin;  
            double midX = xRange*xProp + c.xmin;
            double midY = yRange*(yRange-yProp) + c.ymin;

            c.xmin -= ((double)(c.endX - c.startX)/(double)c.width)*xRange;
            c.xmax -= ((double)(c.endX - c.startX)/(double)c.width)*xRange;
            c.ymin -= ((double)(c.startY- c.endY)/(double)c.height)*yRange;
            c.ymax -= ((double)(c.startY- c.endY)/(double)c.height)*yRange;
            c.axis = new double[]{c.xmin, c.xmax, c.ymin, c.ymax};           
            DecimalFormat dF = new DecimalFormat("#.00");
            XMax.setText(dF.format(c.xmax));
            YMax.setText(dF.format(c.ymax));
            XMin.setText(dF.format(c.xmin));
            YMin.setText(dF.format(c.ymin));
            c.repaint();            
            c.dragged = false;
            
        }        
 
        
        
    }//GEN-LAST:event_jPanel1MouseReleased

    private void jPanel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MousePressed
        c.startX = evt.getX();
        c.startY = evt.getY();
        System.out.println("Pressed");
    }//GEN-LAST:event_jPanel1MousePressed

    private void YMaxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_YMaxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_YMaxActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
 
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {  

                new HW6Plot().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Title;
    private javax.swing.JTextField XMax;
    private javax.swing.JTextField XMin;
    private javax.swing.JTextField YMax;
    private javax.swing.JTextField YMin;
    private javax.swing.JButton jButton1;
    private static javax.swing.JPanel jPanel1;
    private javax.swing.JComboBox<String> lineColor;
    private javax.swing.JComboBox<String> lineThick;
    private javax.swing.JLabel xLabel;
    private javax.swing.JLabel yAxis;
    // End of variables declaration//GEN-END:variables
}

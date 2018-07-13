package hw6;

import static hw6.ArrayMath.*;

public class HW6Tester {

    public static void main(String[] args) {
		// Note that this is not good test code because it is difficult to 
        // verify that our operations are being performed CORRECTLY. You are 
        // encouraged to write your own. This file simply demonstrates the syntax
        // that will be used to call your methods.

        System.out.println("**Testing Basic Array Math**");

        System.out.println("--Testing println() and array generation--");
        double[] a = zeros(3);
        double[] b = ones(5);
        double[] c = linspace(0, 1, 5);
        double[] d = {.1, .1, .1, .1, .1};
        double[] e = {1, 2, 3, 4, 5};
        println(a);
        println(b);
        println(c);
        println(d);
        println(e);

        System.out.println("--Testing element-wise operations--");
        println(add(c, e));
        println(subtract(c, e));
        println(multiply(c, e));
        println(divide(c, e));
        println(pow(c, e));

        System.out.println("--Testing min/max operations--");
        System.out.println(min(new double[]{5, 2.2, 7, -1.2, -3.5, 6}));
        System.out.println(imin(new double[]{5, 2.2, 7, -1.2, -3.5, 6}));
        System.out.println(max(new double[]{5, 2.2, 7, -1.2, -3.5, 6}));
        System.out.println(imax(new double[]{5, 2.2, 7, -1.2, -3.5, 6}));

        System.out.println("**Testing Additional Array Math**");

        System.out.println("--Testing overloads--");
        println(add(2.5, b));
        println(subtract(2.5, b));
        println(multiply(2.5, b));
        println(divide(2.5, d));
        println(pow(2, new double[]{1, 2, 3, 4, 5}));

        System.out.println("--Testing other overloads--");
        println(add(b, 2.5));
        println(subtract(b, 2.5));
        println(multiply(b, 2.5));
        println(divide(d, 2.5));
        println(pow(new double[]{1, 2, 3, 4, 5}, 2));

        System.out.println("--Testing formatting--");
        setDisplayFormat("%10.5f");
        println(d);

        System.out.println("**Testing basic plotting**");
        Canvas myPlot = new Canvas();
        Plottable2D myFun = new MyFunction();
        myPlot.setAxis(new double[]{-1, 1, -.5, 1}); // axis limits specified {xmin, xmax, ymin, ymax}
        myPlot.setFunction(myFun);                 // class MyFunction implements interface Plottable2D

        System.out.println("**Testing plotting with options**");
        Canvas myPlot2 = new Canvas();
        myPlot2.setDisplaySize(1000, 500);
        myPlot2.setFunction(myFun);
        myPlot2.setAxis(new double[]{0, 1, 0, 1});
        myPlot2.setXLabel("X Label");
        myPlot2.setYLabel("Y Label");
        myPlot2.setTitle("The Title");
        myPlot2.setLineColor(java.awt.Color.BLUE);

    }



}

/* console output of this file:
 **Testing Basic Array Math**
 --Testing println() and array generation--
 [0.000, 0.000, 0.000]
 [1.000, 1.000, 1.000, 1.000, 1.000]
 [0.000, 0.250, 0.500, 0.750, 1.000]
 [0.100, 0.100, 0.100, 0.100, 0.100]
 [1.000, 2.000, 3.000, 4.000, 5.000]
 --Testing element-wise operations--
 [1.000, 2.250, 3.500, 4.750, 6.000]
 [-1.000, -1.750, -2.500, -3.250, -4.000]
 [0.000, 0.500, 1.500, 3.000, 5.000]
 [0.000, 0.125, 0.167, 0.188, 0.200]
 [0.000, 0.063, 0.125, 0.316, 1.000]
 --Testing min/max operations--
 -3.5
 4
 7.0
 2
 **Testing Additional Array Math**
 --Testing overloads--
 [3.500, 3.500, 3.500, 3.500, 3.500]
 [1.500, 1.500, 1.500, 1.500, 1.500]
 [2.500, 2.500, 2.500, 2.500, 2.500]
 [25.000, 25.000, 25.000, 25.000, 25.000]
 [2.000, 4.000, 8.000, 16.000, 32.000]
 --Testing other overloads--
 [3.500, 3.500, 3.500, 3.500, 3.500]
 [-1.500, -1.500, -1.500, -1.500, -1.500]
 [2.500, 2.500, 2.500, 2.500, 2.500]
 [0.040, 0.040, 0.040, 0.040, 0.040]
 [1.000, 4.000, 9.000, 16.000, 25.000]
 --Testing formatting--
 [   0.10000,    0.10000,    0.10000,    0.10000,    0.10000]
 **Testing basic plotting**
 **Testing plotting with options**
 */


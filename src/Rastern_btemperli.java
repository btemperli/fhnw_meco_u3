import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Rastern_btemperli extends JPanel
{

    private BufferedImage canvas;

    class Edge {
        Point2D start;
        Point2D end;
        Double m;

        public Edge(Point2D start, Point2D end) {
            this.start = start;
            this.end = end;
            this.m = (this.end.getY() - this.start.getY()) / (this.end.getX() - this.start.getX());
        }

        @Override
        public String toString() {
            return "edge from (" + this.start.getX() + "," + this.start.getY() + ") to (" + this.end.getX() + "," + this.end.getY() + "). m is " + this.m;
        }
    }

    /* ---------- für kantentabellen und filter ----------*/
    /* initialize test polygon A */
    Point2D[] polyA = new Point2D[5];
    /* initialize test polygon B */
    Point2D[] polyB = new Point2D[5];
    // initialize test polygon C */
    Point2D[] polyC = new Point2D[12];
    Point2D[] polyD = new Point2D[4];
    Point2D[] polyE = new Point2D[4];
    Point2D[] polyF = new Point2D[4];

    private double unweightedFilter(double distance)
    {
        double intensity, blending, L0, L1;
            /* done: implement a unweighted filter using a linear function*/
        intensity = 1 - distance; //f(d)
        //blending = intensity * L0 + (1-intensity) * L1
        return intensity;

    }

    private double weightedFilter(double distance)
    {
        double intensity, blending, L0, L1;
            /* done: implement a weighted filter using a square root function */
        intensity = 1 - Math.sqrt(distance);
        //blending = intensity * L0 + (1-intensity) * L1
        return intensity;
    }
    /*---------- fuer kantentabellen und filter end ----------*/

    public Rastern_btemperli(int width, int height)
    {
    	/*---------- fuer kantentabellen und filter start ----------*/
        polyA[0] = new Point2D.Double(10.0,80.0);
        polyA[1] = new Point2D.Double(50.0,55.0);
        polyA[2] = new Point2D.Double(90.0,70.0);
        polyA[3] = new Point2D.Double(65.0,20.0);
        polyA[4] = new Point2D.Double(25.0,30.0);

        polyB[0] = new Point2D.Double(10.0,140.0);
        polyB[1] = new Point2D.Double(20.0,195.0);
        polyB[2] = new Point2D.Double(60.0,110.0);
        polyB[3] = new Point2D.Double(60.0,180.0);
        polyB[4] = new Point2D.Double(95.0,155.0);

        polyC[0] = new Point2D.Double(30.0,30.0);
        polyC[1] = new Point2D.Double(30.0,40.0);
        polyC[2] = new Point2D.Double(60.0,40.0);
        polyC[3] = new Point2D.Double(60.0,30.0);
        polyC[4] = new Point2D.Double(50.0,30.0);
        polyC[5] = new Point2D.Double(50.0,60.0);
        polyC[6] = new Point2D.Double(60.0,60.0);
        polyC[7] = new Point2D.Double(60.0,50.0);
        polyC[8] = new Point2D.Double(30.0,50.0);
        polyC[9] = new Point2D.Double(30.0,60.0);
        polyC[10] = new Point2D.Double(40.0,60.0);
        polyC[11] = new Point2D.Double(40.0,30.0);

        polyD[0] = new Point2D.Double(500,10);
        polyD[1] = new Point2D.Double(500,150);
        polyD[2] = new Point2D.Double(600,150);
        polyD[3] = new Point2D.Double(600,60);

        polyE[0] = new Point2D.Double(500,200);
        polyE[1] = new Point2D.Double(500,250);
        polyE[2] = new Point2D.Double(550,250);
        polyE[3] = new Point2D.Double(600,200);

        polyF[0] = new Point2D.Double(500,300);
        polyF[1] = new Point2D.Double(500,400);
        polyF[2] = new Point2D.Double(600,400);
        polyF[3] = new Point2D.Double(600,300);

    	/*---------- ruer kantentabellen und filter end ----------*/

        canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        //fillCanvas(Color.BLUE);
        //drawRect(Color.RED, 0, 0, width/2, height/2);
    }

    public static void main(String[] args)
    {
        int width = 640;
        int height = 480;
        JFrame frame = new JFrame("Direct draw demo");

        Rastern_btemperli panel = new Rastern_btemperli(width, height);
        panel.drawLine(10,10,100,20,new Color(0,0,0));
        panel.drawLine(100,20,400,400,new Color(0,0,0));

        panel.drawMidpointLine(20,20,120,30, new Color(150,0,0));
        panel.drawMidpointLine(120,30,420,420, new Color(150,0,0));

        panel.drawAALine(400,10,200,50, new Color(50,200,100));
        panel.drawAALine(200,50,100,300, new Color(50,200,100));

        panel.drawSimpleCircle(300, 300, 50, new Color(0, 100, 200));

        panel.drawMidpointCircle(200,200,50, new Color(0, 100, 0));

        panel.drawFloodFill(200, 200, new Color(0,100,0), new Color(200, 100, 0));

//        panel.drawScanlineFill(panel.polyA, 0, new Color(150,50,100));
        panel.drawScanlineFill(panel.polyB, 0, new Color(150,50,100));
        panel.drawScanlineFill(panel.polyC, 0, new Color(150,50,100));
        panel.drawScanlineFill(panel.polyD, 0, new Color(150,50,100));
        panel.drawScanlineFill(panel.polyE, 0, new Color(150,50,100));
        panel.drawScanlineFill(panel.polyF, 0, new Color(150,50,100));

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public Dimension getPreferredSize()
    {
        return new Dimension(canvas.getWidth(), canvas.getHeight());
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(canvas, null, null);
    }


    public void fillCanvas(Color c)
    {
        int color = c.getRGB();
        for (int x = 0; x < canvas.getWidth(); x++) {
            for (int y = 0; y < canvas.getHeight(); y++) {
                canvas.setRGB(x, y, color);
            }
        }
        repaint();
    }

    /*
    * Method drawLine: Uses the simple math line equotation to draw a line
    * @param double x0: x coordinate start point
    * @param double y0: y coordinate start point
    * @param double x1: x coordinate end point
    * @param double y1: y coordinate start point
    * @param Color c: Color variable for drawing the line
    */
    public void drawLine(double x0, double y0, double x1, double y1, Color c)
    {
        double x;
        double m = (y1 - y0)/(x1 - x0);
        double y = y0;

        for(x = x0; x <= x1; x++) {
            canvas.setRGB(Math.round(Math.round(x)), Math.round(Math.round(y)), c.getRGB());
            y += m;
        }

        repaint();
    }

    /*
    * Method drawMidpointLine: Uses the midpoint / bresenham algorithm to draw a line
    * @param double x0: x coordinate start point
    * @param double y0: y coordinate start point
    * @param double x1: x coordinate end point
    * @param double y1: y coordinate start point
    * @param Color c: Color variable for drawing the line
    */
    public void drawMidpointLine(double x0, double y0, double x1, double y1, Color c)
    {
        double dx = x1 - x0;
        double dy = y1 - y0;
        double d = 2 * dy - dx;

        double incrE = 2 * dy;
        double incrNE = 2 * (dy - dx);

        double x = x0;
        double y = y0;

        canvas.setRGB(Math.round(Math.round(x)), Math.round(Math.round(y)), c.getRGB());
        while(x < x1) {
            if(d <= 0) {
                d += incrE;
                x++;
            } else {
                d += incrNE;
                x++;
                y++;
            }
            canvas.setRGB(Math.round(Math.round(x)), Math.round(Math.round(y)), c.getRGB());
        }

        repaint();
    }

    /*
    * Method drawAALine Uses a line drawing algorithm with integrated anti-aliasing (pre or post)
    * @param double x0: x coordinate start point
    * @param double y0: y coordinate start point
    * @param double x1: x coordinate end point
    * @param double y1: y coordinate start point
    * @param Color c: Color variable for drawing the line
    */
    public void drawAALine(double x0, double y0, double x1, double y1, Color c)
    {
        //Implement anti-aliasing drawing
        double dx = Math.abs(x1-x0), sx = x0<x1 ? 1 : -1;
        double dy = Math.abs(y1-y0), sy = y0<y1 ? 1 : -1;
        // error
        double err = dx-dy, e2, x2;
        double ed = dx+dy == 0 ? 1 : Math.sqrt((float)dx*dx+(float)dy*dy);
        Color color;
        int iColor;
        int colorRed;
        int colorBlue;
        int colorGreen;

        for ( ; ; ){ // pixel loop
            colorRed = Math.round(Math.round(c.getRed()*Math.abs(err-dx+dy)/ed));
            colorGreen = Math.round(Math.round(c.getGreen()*Math.abs(err-dx+dy)/ed));
            colorBlue = Math.round(Math.round(c.getBlue()*Math.abs(err-dx+dy)/ed));
            color = new Color(colorRed, colorGreen, colorBlue);
            canvas.setRGB(Math.round(Math.round(x0)), Math.round(Math.round(y0)), color.getRGB());
            e2 = err; x2 = x0;

            // draw x
            if (2*e2 >= -dx) {
                if (x0 == x1) break;
                if (e2+dy < ed) {
                    colorRed = Math.round(Math.round(c.getRed()*(e2+dy)/ed));
                    colorGreen = Math.round(Math.round(c.getGreen()*(e2+dy)/ed));
                    colorBlue = Math.round(Math.round(c.getBlue()*(e2+dy)/ed));
                    color = new Color(colorRed, colorGreen, colorBlue);
                    canvas.setRGB(Math.round(Math.round(x0)), Math.round(Math.round(y0+sy)), color.getRGB());
                }
                err -= dy; x0 += sx;
            }

            // draw y
            if (2*e2 <= dy) {
                if (y0 == y1) break;
                colorRed = (int)(c.getRed()*(dx-e2)/ed);
                colorGreen = (int)(c.getGreen()*(dx-e2)/ed);
                colorBlue = (int)(c.getBlue()*(dx-e2)/ed);
                if (colorRed > 255) colorRed = 255;
                if (colorGreen > 255) colorGreen = 255;
                if (colorBlue > 255) colorBlue = 255;

                color = new Color(colorRed, colorGreen, colorBlue);
                if (dx-e2 < ed) canvas.setRGB(Math.round(Math.round(x2+sx)), Math.round(Math.round(y0)), color.getRGB());
                err += dx; y0 += sy;
            }
        }

        repaint();
    }

    /*
    * Method drawCirclePoints: gets a point and a centerPoint of a circle
    * mirrors the point 7 times at the centerPoint.
    * draw 8 points in a circle round.
    *
    * @param int centerX: x-coordinate of the midPoint of the circle
    * @param int centerY: y-coordinate of the midPoint of the circle
    * @param int x: x distance of the pixel to mirror
    * @param int y: y distance of the pixel to mirror
    * @param Color c: Color variable for mirror pixel
    */
    public void drawCirclePoints(int centerX, int centerY, int x, int y, Color c)
    {
        int xO1 = centerX + x;
        int yO1 = centerY - y;

        int xO2 = centerX + y;
        int yO2 = centerY - x;

        int xO3 = centerX + y;
        int yO3 = centerY + x;

        int xO4 = centerX + x;
        int yO4 = centerY + y;

        int xO5 = centerX - x;
        int yO5 = centerY + y;

        int xO6 = centerX - y;
        int yO6 = centerY + x;

        int xO7 = centerX - y;
        int yO7 = centerY - x;

        int xO8 = centerX - x;
        int yO8 = centerY - y;

        canvas.setRGB(xO1, yO1, c.getRGB());
        canvas.setRGB(xO2, yO2, c.getRGB());
        canvas.setRGB(xO3, yO3, c.getRGB());
        canvas.setRGB(xO4, yO4, c.getRGB());
        canvas.setRGB(xO5, yO5, c.getRGB());
        canvas.setRGB(xO6, yO6, c.getRGB());
        canvas.setRGB(xO7, yO7, c.getRGB());
        canvas.setRGB(xO8, yO8, c.getRGB());

        repaint();
    }

    /*
    * Method drawSimpleCircle: uses the simple circle equotation to draw a quarter circle
    * which has to be mirrored pixel per pixel by the method drawCirclePoints
    * @param double x: x coordinate of circle center
    * @param double y: y coordinate of circle center
    * @param double R: radius of circle
    * @param Color c: Color variable for circle line
    */
    public void drawSimpleCircle(double x0, double y0, double R, Color c)
    {
        // Braucht noch verbesserung, momentan noch eine eigene Insel
        int x,
            y,
            r = (int)R,
            r2 = r * r;

        for(x = -r; x<= 0; x++) {
            y = (int) (Math.sqrt(r2 - x*x) + 0.5);
            drawCirclePoints((int) x0, (int) y0, x, y, c);
        }

        repaint();
    }

    /*
    * Method drawMidpointCircle: uses the midpointcircle algorithm to draw a quarter circle
    * which has to be mirrored pixel per pixel by the method drawCirclePoints
    * @param double x: x coordinate of circle center
    * @param double y: y coordinate of circle center
    * @param double R: radius of circle
    * @param Color c: Color variable for circle line
    */
    public void drawMidpointCircle(double x, double y, double R, Color c)
    {
        //Implement midpoint circle drawing
        double x0 = -R,
            y0 = 0,
            err = 2-2*R;

        do {
            drawCirclePoints((int) x, (int) y, (int) x0, (int) y0, c);

            R = err;
            if (R <= y0) {
                err += ++y0 * 2 + 1;           	// e_xy + e_y < 0
            }
            if (R > x0 || err > y0) {
                err += ++x0 * 2 + 1;  // e_xy + e_x > 0 or no 2nd y-step
            }
        } while (x0 < -(R/2)); // draw one eight

        repaint();
    }

    /*
    * Method drawFloodFill (recursive!): uses the regionfill algorithm to fill a polygon given
    * by the method drawRect
    * @param double x: x coordinate of a start point in the rect
    * @param double y: y coordinate of a start point in the rect
    * @param Color floodc: Color variable of the border
    * @param Color c: Color variable of the fill
    */
    public void drawFloodFill(int x, int y, Color floodc, Color c)
    {

        // check if point is not border color and not filled yet.
        if (canvas.getRGB(x, y) != floodc.getRGB() && canvas.getRGB(x,y) != c.getRGB()) {

            // fill point with c-color
            canvas.setRGB(x, y, c.getRGB());

            // recursive call of north, east, south and west point of the start point.
            drawFloodFill(x, y - 1, floodc, c);
            drawFloodFill(x, y + 1, floodc, c);
            drawFloodFill(x - 1, y, floodc, c);
            drawFloodFill(x + 1, y, floodc, c);
        }

        repaint();
    }

    /*
    * Method drawScanlineFill uses the scanlinefill algorithm to fill a polygon given
    * by the method drawRect
    * @param double []poly: array with vertices
    * @param double nV: incrementor for current vertice
    * @param Color c: Color variable of the fill
    */
    public void drawScanlineFill(Point2D[] poly, double nV, Color c)
    {

        this.drawPolygon(poly, new Color(255,255,255));

        double minY = poly[0].getY();
        double maxY = poly[0].getY();
        double minX = poly[0].getX();
        double maxX = poly[0].getX();
        Point2D lastPoint = poly[poly.length - 1];

        List<ArrayList<Integer>> scanLines = new ArrayList<ArrayList<Integer>>();

        for (Point2D point:poly) {
            if (point.getY() < minY) {
                minY = point.getY();
            }
            if (point.getY() > maxY) {
                maxY = point.getY();
            }
            if (point.getX() < minX) {
                minX = point.getX();
            }
            if (point.getX() > maxX) {
                maxX = point.getX();
            }
        }

        for (int i = 0; i < maxX; i++) {
            scanLines.add(i, new ArrayList<Integer>());
        }


        for (Point2D point:poly) {

            // create edge from poly
            Point2D start = point;
            Point2D end = lastPoint;
            if (point.getX() > lastPoint.getX()) {
                end = point;
                start = lastPoint;
            }
            Edge edge = new Edge(start, end);

            // go along on the edge and save all the points.
            if (edge.m != Double.NEGATIVE_INFINITY && edge.m != Double.POSITIVE_INFINITY) {

                for (int i = (int) edge.start.getX(); i < (int) (edge.end.getX()); i++) {

                    ArrayList<Integer> thisLineList;
                    thisLineList = scanLines.get(i);

                    int k = i - (int) edge.start.getX();

                    thisLineList.add((int) (edge.start.getY() + (k * edge.m)));
                    scanLines.set(i, thisLineList);
                }
            }

            lastPoint = point;
        }

        for (int i = 0; i < maxX; i++) {
            if (!scanLines.get(i).isEmpty()) {
                ArrayList<Integer> thisLineList = scanLines.get(i);
                Collections.sort(thisLineList);

                for (int k = 0; k < thisLineList.size(); k++) {
                    if (k%2 != 0) {
                        for (int y = thisLineList.get(k-1); y < thisLineList.get(k); y++) {
                            canvas.setRGB(i, y, c.getRGB());
                        }
                    }
                }

            }
        }

        repaint();
    }

    public void drawRect(Color c, int x1, int y1, int width, int height)
    {
        int color = c.getRGB();
        // Implement rectangle drawing
        for (int x = x1; x < x1 + width; x++)
        {
            for (int y = y1; y < y1 + height; y++)
            {
                canvas.setRGB(x, y, color);
            }
        }
        repaint();
    }

    public void drawPolygon(Point2D[] poly, Color c)
    {
        Point2D lastPoint = poly[poly.length - 1];

        for (Point2D point:poly) {

            Point2D start = point;
            Point2D end = lastPoint;

            if (point.getX() > lastPoint.getX()) {
                end = point;
                start = lastPoint;
            }

            Edge edge = new Edge(start, end);

            drawAALine(edge.start.getX(), edge.start.getY(), edge.end.getX(), edge.end.getY(), c);

            lastPoint = point;
        }

        repaint();
    }
}
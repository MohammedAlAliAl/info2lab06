package triangle;

import resizable.ResizableImage;

import java.awt.*;
import java.awt.image.BufferedImage;

import static resizable.Debug.print;

/**
 * Implement your Sierpinski Triangle here.
 *
 *
 * You only need to change the drawTriangle
 * method!
 *
 *
 * If you want to, you can also adapt the
 * getResizeImage() method to draw a fast
 * preview.
 *
 */
public class Triangle implements ResizableImage {
    int drawTriangle = 0;
    /**
     * change this method to implement the triangle!
     * @param size the outer bounds of the triangle
     * @return an Image containing the Triangle
     */
    private BufferedImage drawTriangle(Dimension size) {
        print("drawTriangle: " + ++drawTriangle + "size: " + size);
        BufferedImage bufferedImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gBuffer = (Graphics2D) bufferedImage.getGraphics();

        gBuffer.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gBuffer.setColor(Color.white);
        gBuffer.fillRect(0, 0, size.width, size.height);
        int height = size.height-15;
        int width = size.width;
        Point p1 = new Point(width / 2, 10);
        Point p2 = new Point(10,  height);
        Point p3 = new Point(width - 10, height);

        int maxDepth = 8;
        drawSierpinski(gBuffer, p1, p2, p3, maxDepth, maxDepth);

        // drawSingleTriangle(gBuffer, p1, p2, p3);
        gBuffer.setColor(Color.red);
        int border = 2;
        gBuffer.drawRect(border, border, size.width - 2 * border, size.height - 2 * border);
        gBuffer.setColor(Color.darkGray);
        border = 8;
        gBuffer.drawRect(border, border, size.width - 2 * border, size.height - 2 * border);
        gBuffer.drawString("Triangle goes here", border * 2, border * 4);

        return bufferedImage;
    }
    /*private void drawSingleTriangle(Graphics2D gBuffer, Point p1, Point p2, Point p3) {
        gBuffer.setColor(Color.blue);
        int [] xPoints = {p1.x, p2.x, p3.x};
        int [] yPoints = {p1.y, p2.y, p3.y};
        gBuffer.fillPolygon(xPoints, yPoints, 3);
    }*/


    /**
     * Recursively draws a Sierpinski triangle.
     *
     * At the base case (depth == 0), it draws a filled triangle with a random color.
     * Otherwise, it computes the midpoints of the sides and recursively draws three smaller triangles.
     *
     * @param g     the Graphics2D object used for drawing
     * @param p1    the first corner point of the triangle
     * @param p2    the second corner point of the triangle
     * @param p3    the third corner point of the triangle
     * @param depth the current recursion depth
     */

    private void  drawSierpinski( Graphics2D g, Point p1, Point p2, Point p3, int depth, int maxDepth) {




        // float hue = (float)(maxDepth - depth)/ maxDepth;
        //g.setColor(Color.getHSBColor(hue, 0.6f, 1.0f));
        g.setColor(Color.getHSBColor((float) Math.random(), 0.5f, 1.0f));
        int[] xPoints = {p1.x, p2.x, p3.x};
        int[] yPoints = {p1.y, p2.y, p3.y};
        g.fillPolygon(xPoints, yPoints, 3);
        if(depth == 0) {
            return;
        }


        // Midpoints of triangle sides
        Point m1 = midpoint(p1, p2);
        Point m2 = midpoint(p2, p3);
        Point m3 = midpoint(p3, p1);

        // Recurse into 3 corner triangles
        drawSierpinski(g, p1, m1, m3, depth - 1, maxDepth);
        drawSierpinski(g, m1, p2, m2, depth - 1, maxDepth);
        drawSierpinski(g, m3, m2, p3, depth - 1, maxDepth);

    }

    /**
     * Calculates the midpoint between two points.
     *
     * @param a the first point
     * @param b the second point
     * @return a new Point representing the midpoint between a and b
     */

    private Point midpoint(Point a, Point b) {
        return new Point((a.x + b.x) / 2, (a.y + b.y) / 2);
    }

    BufferedImage bufferedImage;
    Dimension bufferedImageSize;

    @Override
    public Image getImage(Dimension triangleSize) {
        if (triangleSize.equals(bufferedImageSize))
            return bufferedImage;
        bufferedImage = drawTriangle(triangleSize);
        bufferedImageSize = triangleSize;
        return bufferedImage;
    }
    @Override
    public Image getResizeImage(Dimension size) {
        BufferedImage bufferedImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gBuffer = (Graphics2D) bufferedImage.getGraphics();
        gBuffer.setColor(Color.pink);
        int border = 2;
        gBuffer.drawRect(border, border, size.width - 2 * border, size.height - 2 * border);
        return bufferedImage;
    }
}

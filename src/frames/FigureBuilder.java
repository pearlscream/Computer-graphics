package frames;

import utils.Point;
import utils.Side;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Dima on 22.10.2017.
 */
public class FigureBuilder extends JPanel {
    private int rotate = 0;
    private int scale = 1;
    private int translateX = 0;
    private int translateY = 0;

    private Point basePoint = new Point(0, -150);

    public void paintComponent(Graphics g) {
        g.setColor(Color.WHITE);
        super.paintComponent(g);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.BLACK);

        // Set up a Cartesian coordinate system
        // get the size of the drawing area
        Dimension size = this.getSize();

        // place the origin at the middle
        g.translate(size.width / 2, size.height / 2);


        // draw the x and y axes
        drawGridAndAxes(g);
//        drawFigure(g);
        List<Point> innerShape = translateTransform((List<Point>) getPointsInner(100, 60, 35).get(0));
        Graphics2D g2 = (Graphics2D) g;

//        List<Point> outerShape = translateTransform(getPointsOuter(g2));
        int x1;
        int x2;
        int y1;
        int y2;
        int r = 40;
        Point center = new Point(10,15);
        for (double i = 180; i < 360; i++)
        {
            x1 = (int)(r * Math.cos(2 * Math.PI * i / 360) + center.getX());
            y1 = (int)(r * Math.sin(2 * Math.PI * i / 360) + center.getY());
            x2 = (int)(r * Math.cos(2 * Math.PI * (i + 1) / 360) + center.getX());
            y2 = (int)(r * Math.sin(2 * Math.PI * (i + 1) / 360) + center.getY());
            g2.drawLine(x1,y1,x2,y2);
        }
//        drawShape(g2, outerShape);
        Point startPoint;
        Point endPoint;

    }

    private List<Point> getPointsOuter(Graphics2D g2) {
        LinkedList<Point> points = new LinkedList<>();
        points.add(basePoint);
        points.add(new Point(50, basePoint.getY()));
        points.add(new Point(60, basePoint.getY() - 60));
        points.add(new Point(200, basePoint.getY() - 60));
        points.add(new Point(200, basePoint.getY() + 400));
        points.add(new Point(-100, basePoint.getY() + 400));
        return points;

//        int radius = 150;
//        for (int i = radius; i >= 100; i--) {
//            points.add(new Point(i,-Math.sqrt(Math.pow(radius, 2) - Math.pow(i, 2))));
//        }

//        double cicleStart = 200;
//        double circleEnd = 250;
//        int a = -200;
//        int b = 100;
//        for (double i = cicleStart; i <= circleEnd; i++) {
//            points.add(new Point(-Math.sqrt(Math.pow(radius, 2) - Math.pow(i - b, 2)) + a, i));
//        }


//
//        for (int i = -radius; i <= radius; i++) {
//            points.add(new Point(i, -Math.sqrt(Math.pow(radius, 2) - Math.pow(i, 2))));
//        }


//        return customCircleDraw(new Point(40, 70), 150, 100, 150, true, g2);
    }

    private List<Point> customCircleDraw(Point center, double radius, int start, int end, boolean clockwise, Graphics2D g2) {
        int stepSize = (end - start) / 50;
        int angle = start;
        List<Point> points = new ArrayList<>();
        Point startPoint = this.getCirclePoint(center, angle, radius, clockwise);
        GeneralPath ctx = new GeneralPath();
        ctx.moveTo(startPoint.getX(), startPoint.getY());
        points.add(startPoint);
        while (angle <= end) {
            angle = angle + stepSize;
            Point endPoint = this.getCirclePoint(center, angle, radius, clockwise);
            ctx.lineTo(endPoint.getX(), endPoint.getY());
        }
        ctx.closePath();
        g2.draw(ctx);
        return points;
    }


    private Point getCirclePoint(Point center, double angle, double radius, boolean clockwise) {
        double x = (Math.sin(angle) * radius) + (clockwise ? -center.getX() : center.getX());
        double y = (-Math.cos(angle) * radius) + center.getY();
        return new Point(x, y);
    }

    private void drawShape(Graphics2D g2, List<Point> points) {
        GeneralPath ctx = new GeneralPath();
        ctx.moveTo(points.get(0).getX(), points.get(0).getY());
        points.forEach((point) -> ctx.lineTo(point.getX(), point.getY()));
        ctx.closePath();
        g2.setStroke(new BasicStroke(3));
        g2.draw(ctx);
    }

    private List<Point> translateTransform(List<Point> points) {
        double rotateDegree = this.rotate / 180 * Math.PI;
        double scale = this.scale;
        double translateX = +this.translateX;
        double translateY = -this.translateY;
        return points
                .stream()
                .map(point -> {
                    double x = point.getX() * scale * Math.cos(rotateDegree) - point.getY() * scale * Math.sin(rotateDegree) + translateX;
                    double y = point.getX() * scale * Math.sin(rotateDegree) + point.getY() * scale * Math.cos(rotateDegree) + translateY;
                    return new Point(x, y);
                })
                .collect(Collectors.toList());
    }

    private void drawFigure(Graphics g) {
//        GeneralPath generalPath = new GeneralPath();
//        generalPath.moveTo(0, 0);
//        int radius = 150;
//        int side = 80;
//        for (int i = radius; i >= side; i--) {
//            generalPath.lineTo(i, -Math.sqrt(Math.pow(radius, 2) - Math.pow(i, 2)));
//        }
//        generalPath.lineTo(side, 0);
//        generalPath.closePath();
//        Graphics2D g2 = (Graphics2D) g;
//        g2.draw(generalPath);
    }

    private List<Object> getPointsInner(int radiusR2, int sideC, int sideD) {
        List<Object> result = new ArrayList<>();
        LinkedList<Point> points = new LinkedList<>();
        int radius = radiusR2;
        int side = sideC;
        int verticalSide = sideD;
        List<Side> sides = new ArrayList<>();
        for (int i = radius; i >= side; i--) {
            points.add(new Point(i, -Math.sqrt(Math.pow(radius, 2) - Math.pow(i, 2))));
        }
        sides.add(new Side("R2",
                new Point(basePoint.getX(), basePoint.getY()),
                new Point(points.getLast().getX() + basePoint.getX(), points.getLast().getY() + basePoint.getY())));
        sides.add(new Side("D",
                new Point(points.getLast().getX() + basePoint.getX(), points.getLast().getY() + basePoint.getY()),
                new Point(points.getLast().getX() + basePoint.getX(), points.getLast().getY() - verticalSide + basePoint.getY())));

        sides.add(new Side("C",
                new Point(points.getLast().getX() + basePoint.getX(), points.getLast().getY() + basePoint.getY()),
                new Point(points.getLast().getX() + basePoint.getX(), points.getLast().getY() - verticalSide + basePoint.getY())));

        points.add(new Point(points.getLast().getX(), points.getLast().getY() - verticalSide));
        sides.add(new Side("C",
                new Point(points.getLast().getX() + basePoint.getX(), points.getLast().getY() + basePoint.getY()),
                new Point(points.getLast().getX() - 2 * side + basePoint.getX(), points.getLast().getY() - basePoint.getY())));
        points.add(new Point(points.getLast().getX() - side, points.getLast().getY()));

//        points = fullShape(points);
        result.add(points);
        result.add(sides);
        return result;
    }


    private void drawGridAndAxes(Graphics g) {
        Dimension size = this.getSize();
        int hBound = ((size.width / 2) / 50) * 50;
        int vBound = ((size.height / 2) / 50) * 50;
        int tic = size.width / 200;
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));

        // draw the x-axis
        g.drawLine(-hBound, 0, hBound, 0);

        // draw the tic marks along the x axis
        g.setFont(new Font("Times New Roman", Font.PLAIN, 10));
        for (Integer k = -hBound; k <= hBound; k += 25) {
            g.drawLine(k, tic, k, -tic);
            drawGridLine(g2, vBound, k);
            g2.drawString(k.toString(), k - 10, 13);
        }

        // draw the y-axis
        g.drawLine(0, vBound, 0, -vBound);

        // draw the tic marks along the y axis
        for (Integer k = -vBound; k <= vBound; k += 25) {
            if (!k.equals(0)) {
                g.drawLine(-tic, k, +tic, k);
                g2.setColor(Color.GRAY);
                g2.setStroke(new BasicStroke(1));
                g2.drawLine(-hBound, k, hBound, k);
                g2.setColor(Color.BLACK);
                g2.setStroke(new BasicStroke(2));
                g2.drawString(k.toString(), 10, k);
            }
        }
    }

    private void drawGridLine(Graphics2D g2, int bound, int k) {
        g2.setColor(Color.GRAY);
        g2.setStroke(new BasicStroke(1));
        g2.drawLine(k, -bound, k, bound);
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2));
    }
}

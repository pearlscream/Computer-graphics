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
        Graphics2D g2 = (Graphics2D) g;

        List<Point> outerShape = translateTransform(getPointsOuter());
        drawShape(g2, outerShape);

        List<Point> firstCircle = translateTransform(getFirstCircle());
        drawShape(g2, firstCircle);

        List<Point> secondCircle = translateTransform(getSecondCircle());
        drawShape(g2, secondCircle);

        List<Point> squad = translateTransform(getSquadPoints());
        drawShape(g2, squad);
    }

    private List<Point> getPointsOuter() {
        LinkedList<Point> points = new LinkedList<>();
        points.add(basePoint);
        points.add(new Point(40, basePoint.getY()));
        points.add(new Point(60, basePoint.getY() - 60));
        points.add(new Point(200, basePoint.getY() - 60));
        points.add(new Point(200, basePoint.getY() + 400));
        points.add(new Point(-100, basePoint.getY() + 400));
        Point center = new Point(-90, 212);

        int x;
        int y;
        int radius = 40;
        int startDegree = 100;
        int endDegree = 210;

        for (double i = startDegree; i < endDegree; i++) {
            x = (int) (radius * Math.cos(2 * Math.PI * (i + 1) / 360) + center.getX());
            y = (int) (radius * Math.sin(2 * Math.PI * (i + 1) / 360) + center.getY());
            points.add(new Point(x, y));
        }
        points.add(new Point(-100, 125));
        points.add(new Point(-100, -85));

        center = new Point(-35, -85);
        startDegree = 200;
        endDegree = 270;
        radius = 67;

        for (double i = startDegree; i < endDegree; i++) {
            x = (int) (radius * Math.cos(2 * Math.PI * (i + 1) / 360) + center.getX());
            y = (int) (radius * Math.sin(2 * Math.PI * (i + 1) / 360) + center.getY());
            points.add(new Point(x, y));
        }

        return points;
    }

    private List<Point> getFirstCircle() {
        int radius = 30;
        int startDegree = 0;
        int endDegree = 360;
        int x;
        int y;
        LinkedList<Point> points = new LinkedList<>();
        Point center = new Point(-50, -100);
        for (double i = startDegree; i < endDegree; i++) {
            x = (int) (radius * Math.cos(2 * Math.PI * (i + 1) / 360) + center.getX());
            y = (int) (radius * Math.sin(2 * Math.PI * (i + 1) / 360) + center.getY());
            points.add(new Point(x, y));
        }
        return points;
    }

    private List<Point> getSecondCircle() {
        int radius = 30;
        int startDegree = 0;
        int endDegree = 360;
        int x;
        int y;
        LinkedList<Point> points = new LinkedList<>();
        Point center = new Point(-75, 200);
        for (double i = startDegree; i < endDegree; i++) {
            x = (int) (radius * Math.cos(2 * Math.PI * (i + 1) / 360) + center.getX());
            y = (int) (radius * Math.sin(2 * Math.PI * (i + 1) / 360) + center.getY());
            points.add(new Point(x, y));
        }
        return points;
    }

    private List<Point> getSquadPoints() {
        LinkedList<Point> points = new LinkedList<>();
        points.add(new Point(-25, -25));
        points.add(new Point(75, -25));
        points.add(new Point(75, 75));
        points.add(new Point(-25, 75));
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

package Model;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.ArrayList;

import static Controller.DrawPanel.*;
import static Controller.Queue.queue;
import static View.MainWindow.shapeBox;

public class Rectangle extends Polygon implements Serializable {

    private static final long serialVersionUID = 1;

    public Rectangle() {
        hard=false;
    }

    @Override
    public void paint(Graphics g){
        if (!ended) {
            g.drawLine((int) Math.round((dots.get(0).x-shiftX)*Math.exp(scale)), (int) Math.round((dots.get(0).y-shiftY)*Math.exp(scale)), currentX, (int) Math.round((dots.get(0).y-shiftY)*Math.exp(scale)));
            g.drawLine((int) Math.round((dots.get(0).x-shiftX)*Math.exp(scale)), (int) Math.round((dots.get(0).y-shiftY)*Math.exp(scale)), (int) Math.round((dots.get(0).x-shiftX)*Math.exp(scale)), currentY);
            g.drawLine(currentX, (int) Math.round((dots.get(0).y-shiftY)*Math.exp(scale)), currentX, currentY);
            g.drawLine((int) Math.round((dots.get(0).x-shiftX)*Math.exp(scale)), currentY, currentX, currentY);
        }
        else {
            super.paint(g);
        }

    }

    @Override
    public void end(MouseEvent e){
        points.add(new Point(e.getX()/Math.exp(scale)+shiftX,points.get(0).y));  // scale
        points.add(new Point(e.getX()/Math.exp(scale)+shiftX, e.getY()/Math.exp(scale)+shiftY));
        points.add(new Point(points.get(0).x,e.getY()/Math.exp(scale)+shiftY));
        dots.add(new Point(e.getX()/Math.exp(scale)+shiftX,dots.get(0).y));
        dots.add(new Point(e.getX()/Math.exp(scale)+shiftX, e.getY()/Math.exp(scale)+shiftY));
        dots.add(new Point(dots.get(0).x,e.getY()/Math.exp(scale)+shiftY));
        indQ = queue.size();
        queue.add(activeShape);
        ind = count;
        setName(count);
        shapeBox.addItem(name);
        ended=true;
        findBorder();
        findCenter();
        activeShape = new Rectangle();
    }

    @Override
    public void setName(int num){
        name = "Rectangle"+num;

    }

    public Rectangle(ArrayList<Point> points, String[] options, boolean ended, int ind, int indQ) {
        super(points,options,ended, ind, indQ);
    }

    public Shape copyIt() {
        return new Rectangle(points, options, ended, ind, indQ);
    }
}

package Model;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.ArrayList;

import static Controller.DrawPanel.*;
import static Controller.Queue.queue;
import static View.MainWindow.shapeBox;

public class Polygon extends Polyline implements Serializable {

    private static final long serialVersionUID = 1;

    public Polygon() {
        hard=true;
    }


    @Override
    public void paint(Graphics g){
        super.paint(g);
        if (ended){
            g.drawLine((int) Math.round((dots.get(dots.size()-1).x-shiftX)*Math.exp(scale)),(int) Math.round((dots.get(dots.size()-1).y-shiftY)*Math.exp(scale)),(int) Math.round((dots.get(0).x-shiftX)*Math.exp(scale)),(int) Math.round((dots.get(0).y-shiftY)*Math.exp(scale))); //scale
        }

    }

    @Override
    public void end(MouseEvent e){
        indQ = queue.size();
        queue.add(activeShape);
        ind = count;
        setName(count);
        shapeBox.addItem(name);
        ended=true;
        findBorder();
        findCenter();
        activeShape = new Polygon();
    }

    @Override
    public void setName(int num){
        name = "Polygon"+num;

    }

    public Polygon(ArrayList<Point> points, String[] options, boolean ended, int ind, int indQ) {
        super(points,options,ended, ind, indQ);
    }

    public Shape copyIt() {
        return new Polygon(points, options, ended, ind, indQ);
    }
}

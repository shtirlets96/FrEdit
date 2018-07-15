package Model;

import Controller.DrawPanel;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.ArrayList;

import static Controller.DrawPanel.*;
import static Controller.Queue.queue;
import static View.MainWindow.shapeBox;

public class Polyline extends Shape implements Serializable {

    private static final long serialVersionUID = 1;

    public Polyline() {
        hard=true;
    }

    public Polyline(ArrayList<Point> points, String[] options, boolean ended, int ind, int indQ) {
        super(points,options,ended, ind, indQ);
    }

    public Shape copyIt() {
        return new Polyline(points, options, ended, ind, indQ);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (!ended){
            g.drawLine((int) Math.round((dots.get(dots.size()-1).x-shiftX)*Math.exp(scale)),(int) Math.round((dots.get(dots.size()-1).y-shiftY)*Math.exp(scale)),currentX,currentY);
        }
    }

    public void press(MouseEvent e){
        super.press(e);
        if (e.getButton()==3 && hard){
            end(e);
            count++;
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
        activeShape = new Polyline();
    }

    @Override
    public void setName(int num){
        name = "Polyline"+num;

    }

}

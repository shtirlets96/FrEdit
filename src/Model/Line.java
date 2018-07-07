package Model;

import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.ArrayList;

import static Controller.DrawPanel.activeShape;
import static Controller.DrawPanel.count;
import static Controller.DrawPanel.undo;
import static Controller.Queue.queue;
import static View.MainWindow.shapeBox;

public class Line extends Polyline implements Serializable {

    private static final long serialVersionUID = 1;

    public Line(){
        hard=false;
    }

    @Override
    public void end(MouseEvent e){
        press(e);
        indQ = queue.size();
        queue.add(activeShape);
        ind = count;
        setName(count);
        shapeBox.addItem(name);
        ended=true;
        findBorder();
        findCenter();
        undo.add(new Add(this.copyIt()));
        activeShape = new Line();
    }

    @Override
    public void setName(int num){
        name = "Line"+num;

    }
    public Line(ArrayList<Point> points, String[] options, boolean ended, int ind, int indQ) {
        super(points,options,ended, ind, indQ);
    }

    public Shape copyIt() {
        return new Line(points, options, ended, ind, indQ);
    }
}

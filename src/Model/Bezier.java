package Model;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.ArrayList;

import static Controller.DrawPanel.*;
import static Controller.Queue.queue;
import static View.MainWindow.shapeBox;

public class Bezier extends Polyline implements Serializable {

    transient ArrayList<Point> NewPoints = new ArrayList<>();
    private static final long serialVersionUID = 1;

    public Bezier() {
        hard=true;
    }

    public void afterLoad(){
        dots = new ArrayList<>();
        NewPoints = new ArrayList<>();
        border = new Point[4];
        for (int i=0; i<4; i++){
            border[i]= new Point(0,0);
        }
        for(int i=0; i<points.size(); i++){
            dots.add(new Point (points.get(i).x, points.get(i).y));
        }
        findCenter();
        super.findBorder();
        ended=true;
        bor = new Color(Integer.parseInt(options[6]), Integer.parseInt(options[7]), Integer.parseInt(options[8]));
        f = new Color(Integer.parseInt(options[1]), Integer.parseInt(options[2]), Integer.parseInt(options[3]));
        setName(count);
    }
    @Override
    public void paint(Graphics g) {
        NewPoints.clear();
        double step=0.01;
        if (!ended){
            points.add(new Point(currentX/Math.exp(scale)+shiftX, currentY/Math.exp(scale)+shiftY));
            dots.add(new Point(currentX/Math.exp(scale)+shiftX, currentY/Math.exp(scale)+shiftY));
        }
        int n=points.size();
        NewPoints.add(dots.get(0));
        double[] a,b;
        a= new double[n];
        b= new double[n];
        for (double t=step; t<=1; t+=step){
            for (int i=0;i<(dots.size ());i++){
                a[i]=dots.get(i).x;
                b[i]=dots.get(i).y;
            }
            for (int k=n; k>=0; k--){
                for(int i=0; i<k-1; i++){
                    a[i]=a[i]*(1-t)+a[i+1]*t;
                    b[i]=b[i]*(1-t)+b[i+1]*t;
                }
            }
            NewPoints.add(new Point((int) a[0],(int) b[0]));
        }
        NewPoints.add(dots.get(dots.size()-1));
        Graphics2D g2D = (Graphics2D) g;
        g.setColor(bor);
        g2D.setStroke(new BasicStroke((float) Integer.parseInt(options[4])));

        if (options[0].equals("---")) {
            for (int i = 0; i < (NewPoints.size() - 1); i+=3) {
                g2D.drawLine((int) ((NewPoints.get(i).x - shiftX) * Math.exp(scale)), (int) ((NewPoints.get(i).y - shiftY) * Math.exp(scale)), (int) ((NewPoints.get(i + 1).x - shiftX) * Math.exp(scale)), (int) ((NewPoints.get(i + 1).y - shiftY) * Math.exp(scale)));
            }
        } else {
            if (options[0].equals("-") || options[0].equals("0")){
                for (int i = 0; i < (NewPoints.size() - 1); i++) {
                    g2D.drawLine((int) ((NewPoints.get(i).x - shiftX) * Math.exp(scale)), (int) ((NewPoints.get(i).y - shiftY) * Math.exp(scale)), (int) ((NewPoints.get(i + 1).x - shiftX) * Math.exp(scale)), (int) ((NewPoints.get(i + 1).y - shiftY) * Math.exp(scale)));
                }
            } else {
                for (int i = 0; i < (NewPoints.size() - 1); i+=2) {
                    g2D.drawLine((int) ((NewPoints.get(i).x - shiftX) * Math.exp(scale)), (int) ((NewPoints.get(i).y - shiftY) * Math.exp(scale)), (int) ((NewPoints.get(i + 1).x - shiftX) * Math.exp(scale)), (int) ((NewPoints.get(i + 1).y - shiftY) * Math.exp(scale)));
                }
            }
        }



        if (!ended){
            points.remove(points.size()-1);
            dots.remove(dots.size()-1);
        }

    }

    public Bezier(ArrayList<Point> points, String[] options, boolean ended, int ind, int indQ) {
        super(points,options,ended, ind, indQ);
    }

    public Shape copyIt() {
        return new Bezier(points, options, ended, ind, indQ);
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
        undo.add(new Add(this.copyIt()));
        activeShape = new Bezier();
    }

    @Override
    public void setName(int num){
        name = "Bezier"+num;

    }

    @Override
    public void findBorder(){
        leftX=NewPoints.get(0).x;
        rightX=NewPoints.get(0).x;
        topY=NewPoints.get(0).y;
        botY=NewPoints.get(0).y;
        for (int i=1; i<=NewPoints.size()-1; i++){
            if(NewPoints.get(i).x>rightX){
                rightX=NewPoints.get(i).x;
            }
            if(NewPoints.get(i).x<leftX){
                leftX=NewPoints.get(i).x;
            }
            if(NewPoints.get(i).y>botY){
                botY=NewPoints.get(i).y;
            }
            if(NewPoints.get(i).y<topY){
                topY=NewPoints.get(i).y;
            }
        }
        x3=(rightX-leftX)/3;
        y3=(botY-topY)/3;

        lxc=leftX;
        rxc=rightX;
        byc=botY;
        tyc=topY;

        border[0].x=leftX;
        border[0].y=topY;
        border[1].x=rightX;
        border[1].y=topY;
        border[2].x=rightX;
        border[2].y=botY;
        border[3].x=leftX;
        border[3].y=botY;
    }

    @Override
    public void findBorderC(){
        lxc=NewPoints.get(0).x;
        rxc=NewPoints.get(0).x;
        tyc=NewPoints.get(0).y;
        byc=NewPoints.get(0).y;
        for (int i=1; i<=NewPoints.size()-1; i++){
            if(NewPoints.get(i).x>rxc){
                rxc=NewPoints.get(i).x;
            }
            if(NewPoints.get(i).x<lxc){
                lxc=NewPoints.get(i).x;
            }
            if(NewPoints.get(i).y>byc){
                byc=NewPoints.get(i).y;
            }
            if(NewPoints.get(i).y<tyc){
                tyc=NewPoints.get(i).y;
            }
        }
    }



}

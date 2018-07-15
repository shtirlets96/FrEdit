package Model;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.ArrayList;

import static Controller.Queue.queue;
import static Controller.DrawPanel.*;
import static View.MainWindow.shapeBox;

public class Ellipse extends Shape implements Serializable {
    transient int a,b,sx,sy,a1,b1,sx1,sy1;
    transient ArrayList<Point> NewPoints = new ArrayList<>();
    transient ArrayList<Point> NewPoints1 = new ArrayList<>();
    transient ArrayList<Point> lastPoints = new ArrayList<>();
    double phi;
    private static final long serialVersionUID = 1;

    public Ellipse(){
        hard=false;
    }

    @Override
    public void end(MouseEvent e){
        points.add(new Point(e.getX()/Math.exp(scale)+shiftX, e.getY()/Math.exp(scale)+shiftY));
        dots.add(new Point(e.getX()/Math.exp(scale)+shiftX, e.getY()/Math.exp(scale)+shiftY));
        indQ = queue.size();
        queue.add(activeShape);
        ind = count;
        setName(count);
        shapeBox.addItem(name);
        ended=true;
        findBorder();
        findCenter();
        activeShape = new Ellipse();
    }

    @Override
    public void setName(int num){
        name = "Ellipse"+num;

    }
    public void afterLoad(){
        NewPoints1 = new ArrayList<>();
        NewPoints = new ArrayList<>();
        lastPoints = new ArrayList<>();
        dots = new ArrayList<>();
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
        bor = new Color(Integer.parseInt(options[1]), Integer.parseInt(options[2]), Integer.parseInt(options[3]));
        f = new Color(Integer.parseInt(options[6]), Integer.parseInt(options[7]), Integer.parseInt(options[8]));
        setName(count);
    }

    @Override
    public void paint (Graphics g){
        NewPoints1.clear();
        NewPoints.clear();
        lastPoints.clear();
        int x1 = (int) dots.get(0).x;
        int y1 = (int) dots.get(0).y;
        int x2,y2;
        if (ended){
            x2 = (int) dots.get(1).x;
            y2 = (int) dots.get(1).y;}
        else{
            x2 =(int) (currentX/Math.exp(scale)+shiftX);
            y2 =(int) (currentY/Math.exp(scale)+shiftY);}
        a1=Math.abs(x1-x2)/2;
        b1=Math.abs(y1-y2)/2;
        if (a1!=0 & b1!=0){
            if (x1<x2 & y1<y2){
                sx1=x1+a1;
                sy1=y1+b1;}
            else {
                if (x1<x2 & y1>y2){
                    sx1=x1+a1;
                    sy1=y1-b1;}
                else {
                    if (x1>x2 & y1>y2){
                        sx1=x1-a1;
                        sy1=y1-b1;}
                    else{
                        sx1=x1-a1;
                        sy1=y1+b1;}
                }
            }
            a=(int) (a1);
            b=(int) (b1);
            sx=(int) (sx1);
            sy=(int) (sy1);



            int x=0;
            int y=b;
            int delta= 4*b*b*((x+1)*(x+1))+ a*a*((2*y-1)*(2*y-1))-4*a*a*b*b;
            while (a*a*(2*y-1)>2*b*b*(x+1)){
                pixels(x,y,g);
                if (delta<0){
                    x++;
                    delta+=4*b*b*(2*x+3);
                }
                else{
                    x++;
                    delta=delta-8*a*a*(y-1)+4*b*b*(2*x+3);
                    y--;
                }
            }
            delta=b*b*((2*x+1)*(2*x+1))+4*a*a*((y+1)*(y+1))-4*a*a*b*b;
            while (y+1!=0){
                pixels(x,y,g);
                if (delta<0) {
                    y--;
                    delta+=4*a*a*(2*y+3);
                }
                else{
                    y--;
                    delta=delta-8*b*b*(x+1)+4*a*a*(2*y+3);
                    x++;
                }
            }
        }
        for(int i=0; i<=NewPoints1.size()-1;i++) {
            NewPoints1.get(i).x = (NewPoints.get(i).x - sx1) * Math.cos(phi) - (NewPoints.get(i).y - sy1) * Math.sin(phi) + sx1;
            NewPoints1.get(i).y = (NewPoints.get(i).x - sx1) * Math.sin(phi) + (NewPoints.get(i).y - sy1) * Math.cos(phi) + sy1;
        }
        madeLast();
        g.setColor(f);
        if (options[5]!="0") {
            switch (options[5]) {
                case "1":
                    fill(g);
                    break;
                case "20":
                    fillH(g);
                    break;
                case "21":
                    fillV(g);
                    break;
                case "23":
                    fillD2(g);
                    break;
                case "22":
                    fillD1(g);
                    break;
                case "24":
                    fillP(g);
                    break;
                    /* case "3":
                    fillG(g);
                    break;*/
            }
        }

        g.setColor(bor);
        Graphics2D g2D = (Graphics2D) g;
        g2D.setStroke(new BasicStroke((float) Integer.parseInt(options[4])));
        if (options[0].equals("---")){
            for (int i=0; i<NewPoints1.size()-5; i++){
                g2D.drawLine((int) ((NewPoints1.get(i).x-shiftX)*Math.exp(scale)), (int)((NewPoints1.get(i).y-shiftY)*Math.exp(scale)),(int) ((NewPoints1.get(i+4).x-shiftX)*Math.exp(scale)),(int) ((NewPoints1.get(i+4).y-shiftY)*Math.exp(scale)));
                if ((i+1)%32==0){
                    i+=32;
                }
            }
        } else {
            if (options[0].equals("-") || options[0].equals("0")){
            for (int i=0; i<NewPoints1.size()-5; i++){
                g2D.drawLine((int) ((NewPoints1.get(i).x-shiftX)*Math.exp(scale)), (int)((NewPoints1.get(i).y-shiftY)*Math.exp(scale)),(int) ((NewPoints1.get(i+4).x-shiftX)*Math.exp(scale)),(int) ((NewPoints1.get(i+4).y-shiftY)*Math.exp(scale)));
            }}
            else{
                for (int i=0; i<NewPoints1.size()-5; i++){
                    g2D.drawLine((int) ((NewPoints1.get(i).x-shiftX)*Math.exp(scale)), (int)((NewPoints1.get(i).y-shiftY)*Math.exp(scale)),(int) ((NewPoints1.get(i+4).x-shiftX)*Math.exp(scale)),(int) ((NewPoints1.get(i+4).y-shiftY)*Math.exp(scale)));
                    if ((i+1)%16==0){
                        i+=16;
                    }
                }
            }
        }





        g2D.setStroke(new BasicStroke(1f));
    }
    @Override
    public void findBorder(){
        leftX=NewPoints1.get(0).x;
        rightX=NewPoints1.get(0).x;
        topY=NewPoints1.get(0).y;
        botY=NewPoints1.get(0).y;
        for (int i=1; i<=NewPoints1.size()-1; i++){
            if(NewPoints1.get(i).x>rightX){
                rightX=NewPoints1.get(i).x;
            }
            if(NewPoints1.get(i).x<leftX){
                leftX=NewPoints1.get(i).x;
            }
            if(NewPoints1.get(i).y>botY){
                botY=NewPoints1.get(i).y;
            }
            if(NewPoints1.get(i).y<topY){
                topY=NewPoints1.get(i).y;
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
        lxc=NewPoints1.get(0).x;
        rxc=NewPoints1.get(0).x;
        tyc=NewPoints1.get(0).y;
        byc=NewPoints1.get(0).y;
        for (int i=1; i<=NewPoints1.size()-1; i++){
            if(NewPoints1.get(i).x>rxc){
                rxc=NewPoints1.get(i).x;
            }
            if(NewPoints1.get(i).x<lxc){
                lxc=NewPoints1.get(i).x;
            }
            if(NewPoints1.get(i).y>byc){
                byc=NewPoints1.get(i).y;
            }
            if(NewPoints1.get(i).y<tyc){
                tyc=NewPoints1.get(i).y;
            }
        }
    }


    void madeLast(){
        for (int i=0; i<NewPoints1.size(); i+=4){
            lastPoints.add(NewPoints1.get(i));
        }
        for (int i=1; i<NewPoints1.size(); i+=4){
            lastPoints.add(NewPoints1.get(i));
        }
        for (int i=2; i<NewPoints1.size(); i+=4){
            lastPoints.add(NewPoints1.get(i));
        }
        for (int i=3; i<NewPoints1.size(); i+=4){
            lastPoints.add(NewPoints1.get(i));
        }
        System.out.println(lastPoints.size());
    }


    @Override
    public boolean needFill(int x, int y){
        boolean result = false;
        int n = lastPoints.size();
        int j = n-1;
        for (int i=0; i<n; i++){
            if(((( (int)Math.round((lastPoints.get(i).y-shiftY)*Math.exp(scale))<=y)&&(y<(int)Math.round((lastPoints.get(j).y-shiftY)*Math.exp(scale)))) ||  (((int)Math.round((lastPoints.get(j).y-shiftY)*Math.exp(scale))<=y)&&(y<(int)Math.round((lastPoints.get(i).y-shiftY)*Math.exp(scale))))) && (x>(((int)Math.round((lastPoints.get(j).x-shiftX)*Math.exp(scale))-(int)Math.round((lastPoints.get(i).x-shiftX)*Math.exp(scale)))*(y-(int)Math.round((lastPoints.get(i).y-shiftY)*Math.exp(scale)))/((int)Math.round((lastPoints.get(j).y-shiftY)*Math.exp(scale))-(int)Math.round((lastPoints.get(i).y-shiftY)*Math.exp(scale)))+(int)Math.round((lastPoints.get(i).x-shiftX)*Math.exp(scale))))){
                result=!result;
            }
            j=i;
        }
        return result;
    }

    void pixels(int x, int y, Graphics g){

            NewPoints.add(new Point(sx+x,sy-y));
            NewPoints.add(new Point(sx+x,sy+y));
            NewPoints.add(new Point(sx-x,sy+y));
            NewPoints.add(new Point(sx-x,sy-y));
        NewPoints1.add(new Point(sx+x,sy-y));
        NewPoints1.add(new Point(sx+x,sy+y));
        NewPoints1.add(new Point(sx-x,sy+y));
        NewPoints1.add(new Point(sx-x,sy-y));

    }
    public Ellipse(ArrayList<Point> points, String[] options, boolean ended, int ind, int indQ, double phi) {
        super(points,options,ended, ind, indQ);
        this.phi = phi;

    }

    public Shape copyIt() {
        return new Ellipse(points, options, ended, ind, indQ, phi);
    }

    @Override
    public void rotate(int x, int y){
        findCenter();
        phi = phi + Math.atan2((rtx - cX)*(y - cY) - (x - cX)*(rty - cY), (rtx - cX)*(x - cX) + (y - cY)*(rty - cY));
        rtx=x;
        rty=y;
    }
}

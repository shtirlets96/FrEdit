package Model;

import View.MainWindow;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.ArrayList;

import static Controller.DrawPanel.*;

public class Shape implements Serializable {
    public ArrayList<Point> points = new ArrayList<>();
    transient public ArrayList<Point> dots = new ArrayList<>();
    transient public boolean ended=false, hard;
    transient public int currentX, currentY, _x, _y, tx, ty;
    transient public String name = "";
    transient public double cX, cY, phi, rtx, rty, leftX, rightX, topY, botY, x3, y3, lxc, rxc, byc, tyc;
    transient Point[] border = new Point[4];
    public String[] options = new String[9];
    transient public Color f=new Color(0,0,0),bor = new Color(0,0,0);
    public int ind, indQ;
    private static final long serialVersionUID = 1;

    public Shape() {
        for (int i=0; i<4; i++){
            border[i]= new Point(0,0);
        }
        for (int i=0; i<9; i++){
            options[i]="0";
        }
    }
    public Shape(ArrayList<Point> points, String[] options, boolean ended, int ind, int indQ) {
        for(int i = 0; i < points.size(); i++) {
            this.points.add(new Point(points.get(i).x, points.get(i).y));
        }
        this.options = options.clone();
        this.ended = ended;
        this.indQ = indQ;
        this.ind = ind;
        afterLoad();
    }

    public Shape copyIt() {
        return new Shape(points, options, ended, ind, indQ);
    }

    public void afterLoad(){
        dots = new ArrayList<>();
        border = new Point[4];
        for (int i=0; i<4; i++){
            border[i]= new Point(0,0);
        }
        for(int i=0; i<points.size(); i++){
            dots.add(new Point (points.get(i).x, points.get(i).y));
        }
        findCenter();
        findBorder();
        ended=true;
        bor = new Color(Integer.parseInt(options[1]), Integer.parseInt(options[2]), Integer.parseInt(options[3]));
        f = new Color(Integer.parseInt(options[6]), Integer.parseInt(options[7]), Integer.parseInt(options[8]));
        setName(count);
    }


    public void paint(Graphics g){
        Graphics2D g2D = (Graphics2D) g;
        g2D.setStroke(new BasicStroke(1f));

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
        if (options[0].equals("---")){
            g2D.setStroke(new BasicStroke((float) Integer.parseInt(options[4]), BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0));
        } else {
        g2D.setStroke(new BasicStroke((float) Integer.parseInt(options[4])));}

        for (int i=0; i<points.size()-1;i++){
            g2D.drawLine((int) Math.round((dots.get(i).x-shiftX)*Math.exp(scale)), (int) Math.round((dots.get(i).y-shiftY)*Math.exp(scale)), (int) Math.round((dots.get(i+1).x-shiftX)*Math.exp(scale)), (int) Math.round((dots.get(i+1).y-shiftY)*Math.exp(scale)));

        }

    }

    public void press(MouseEvent e){
        if (e.getButton()==1){
            points.add(new Point(e.getX()/Math.exp(scale)+shiftX, e.getY()/Math.exp(scale)+shiftY));
            dots.add(new Point(e.getX()/Math.exp(scale)+shiftX, e.getY()/Math.exp(scale)+shiftY));
        }
    }

    public void end(MouseEvent e){

    }

    public void setName(int num){

    }

    public void findCenter(){
        cX=0;
        cY=0;
        for (int i=0; i<dots.size(); i++){
            cX=cX+dots.get(i).x;
            cY=cY+dots.get(i).y;
        }
       cX=cX/dots.size();
        cY=cY/dots.size();
    }

    public void changePoints(){
        for(int i=0; i<=dots.size()-1;i++){
            points.get(i).x=dots.get(i).x;
            points.get(i).y=dots.get(i).y;
        }
    }

    public void rotate(int x, int y){
        findCenter();
        phi = Math.atan2((rtx - cX)*(y - cY) - (x - cX)*(rty - cY), (rtx - cX)*(x - cX) + (y - cY)*(rty - cY));
        for(int i=0; i<=dots.size()-1;i++){
            dots.get(i).x =  (points.get(i).x - cX) * Math.cos(phi) - (points.get(i).y - cY) * Math.sin(phi) + cX;
            dots.get(i).y =  (points.get(i).x - cX) * Math.sin(phi) + (points.get(i).y - cY) * Math.cos(phi) + cY;
        }
        changePoints();
        rtx=x;
        rty=y;
    }

    public void transfer(int x, int y){
        _x=x-tx;
        _y=y-ty;
        for(int i=0; i<=dots.size()-1;i++){
            dots.get(i).x=points.get(i).x+_x;
            dots.get(i).y=points.get(i).y+_y;
        }
    }

    public void findBorder(){
        leftX=dots.get(0).x;
        rightX=dots.get(0).x;
        topY=dots.get(0).y;
        botY=dots.get(0).y;
        for (int i=1; i<=dots.size()-1; i++){
            if(dots.get(i).x>rightX){
                rightX=dots.get(i).x;
            }
            if(dots.get(i).x<leftX){
                leftX=dots.get(i).x;
            }
            if(dots.get(i).y>botY){
                botY=dots.get(i).y;
            }
            if(dots.get(i).y<topY){
                topY=dots.get(i).y;
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

    public void findBorderC(){
        lxc=dots.get(0).x;
        rxc=dots.get(0).x;
        tyc=dots.get(0).y;
        byc=dots.get(0).y;
        for (int i=1; i<=dots.size()-1; i++){
            if(dots.get(i).x>rxc){
                rxc=dots.get(i).x;
            }
            if(dots.get(i).x<lxc){
                lxc=dots.get(i).x;
            }
            if(dots.get(i).y>byc){
                byc=dots.get(i).y;
            }
            if(dots.get(i).y<tyc){
                tyc=dots.get(i).y;
            }
        }
    }

    public void drawBorderC(Graphics g){
        findBorderC();
        g.setColor(Color.lightGray);
        g.drawRect((int) ((lxc-4-shiftX)*Math.exp(scale)),(int) ((tyc-4-shiftY)*Math.exp(scale)), (int) ((rxc-lxc+8)*Math.exp(scale)), (int) ((byc-tyc+8)*Math.exp(scale)));
        g.setColor(Color.BLACK);
    }

    public void selectAct(int x, int y){
        if ((x>=leftX+x3) && (x<=rightX-x3) && (y>=topY+y3) && (y<=botY-y3)){
            act=0;
        }
        else{
            if ((x < leftX) || (y < topY) || (x > rightX) || (y > botY)) {
            act=1;
        }
             else {
                if ((x >= leftX + x3) && (x <= rightX - x3) && (y <= topY + y3) && (y >= topY)) {
                    act = 2; //верхняя часть
                }
                else {
                    if ((x >= leftX + x3) && (x <= rightX - x3) && (y <= botY) && (y >= botY - y3)){
                        act=3; //нижняя
                    }
                    else{
                        if ((tx >= leftX) && (tx <= leftX + x3) && (ty >= topY + y3) && (ty <= botY - y3)) {
                            act=4; //левая
                        }
                        else{
                            if ((tx >= rightX - x3) && (tx <= rightX) && (ty >= topY + y3) && (ty <= botY - y3)){
                                act=5; //правая
                            }
                            else{
                                if ((tx >= rightX - x3) && (tx <= rightX) && (ty >= topY) && (ty <= topY + y3)){
                                    act=6; //право-верх
                                }
                                else{
                                    if ((tx >= leftX) && (tx <= leftX + x3) && (ty >= topY) && (ty <= topY + y3)) {
                                        act=7; //лево-верх
                                    }
                                    else{
                                        if ((tx >= leftX) && (tx <= leftX + x3) && (ty <= botY) && (ty >= botY - y3)) {
                                            act=8; //лево-низ
                                        }
                                        else{
                                            if ((tx >= rightX - x3) && (tx <= rightX) && (ty >= botY - y3) && (ty <= botY)) {
                                                act=9; //право-низ
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }
    }

    public void pointer(int x, int y){
        double k,kx,ky;
        x=(int) Math.round((x/Math.exp(scale)+shiftX));
        y=(int) Math.round((y/Math.exp(scale)+shiftY));
        if (act==0){
            transfer(x,y);
        }
        else {
            if (act==1) {
                rotate(x, y);
                System.out.println("Вертим");
            }
            else {
                if (act==2) { //верхняя часть
                    if (ty < y) {
                        k = (y - ty) / (botY - topY);
                    } else {
                        k = (ty - y) / (botY - topY);
                    }
                    for (int i = 0; i <= dots.size() - 1; i++) {
                        if (points.get(i).y != botY) {
                            if (ty < y) {
                                if (k == 1) {
                                    k = 0.99;
                                }
                                dots.get(i).y = botY - (botY - points.get(i).y) * (1 - k);
                                System.out.println(dots.get(i).y+" "+points.get(i).y+" "+k+" "+ty+" "+y);
                            } else {
                                if (k == -1) {
                                    k = -0.99;
                                }
                                dots.get(i).y = botY - (botY - points.get(i).y) * (1 + k);
                                System.out.println(dots.get(i).y+" "+points.get(i).y+" "+k+" "+ty+" "+y);
                            }
                        }
                    }
                }
                if (act==3) { //нижняя
                    if (ty > y) {
                        k = (ty - y) / (botY - topY);
                    } else {
                        k = (y - ty) / (botY - topY);
                    }// прямая параллельная Ох, чекни потом.
                    for (int i = 0; i <= dots.size() - 1; i++) {
                        if (points.get(i).y != topY) {
                            if (ty > y) {
                                if (k == 1) {
                                    k = 0.99;
                                }
                                dots.get(i).y = topY + (points.get(i).y - topY) * (1 - k);
                            } else {
                                if (k == -1) {
                                    k = -0.99;
                                }
                                dots.get(i).y = topY + (points.get(i).y - topY) * (1 + k);
                            }
                        }
                    }
                }

                if (act==4) { //левая
                    if (tx < x) {
                        k = (x - tx) / (rightX - leftX);//
                    } else {
                        k = (tx - x) / (rightX - leftX);
                    }

                    for (int i = 0; i <= dots.size() - 1; i++) {
                        if (points.get(i).x != rightX) {
                            if (tx < x) {
                                if (k == 1) {
                                    k = 0.99;
                                }
                                dots.get(i).x = rightX - (rightX - points.get(i).x) * (1 - k);
                            } else {
                                if (k == -1) {
                                    k = -0.99;
                                }
                                dots.get(i).x = rightX - (rightX - points.get(i).x) * (1 + k);
                            }
                        }

                    }
                }
                if (act==5) { //правая
                    if (tx > x) {
                        k = (tx - x) / (rightX - leftX);
                    } else {
                        k = (x - tx) / (rightX - leftX);
                    }// прямая параллельная Ох, чекни потом.
                    for (int i = 0; i <= dots.size() - 1; i++) {
                        if (points.get(i).x != leftX) {
                            if (tx > x) {
                                if (k == 1) {
                                    k = 0.99;
                                }
                                dots.get(i).x = leftX + (points.get(i).x - leftX) * (1 - k);
                            } else {
                                if (k == -1) {
                                    k = -0.99;
                                }
                                dots.get(i).x = leftX + (points.get(i).x - leftX) * (1 + k);
                            }
                        }
                    }
                }
                if (act==6) { //право-верх
                    if (tx > x) {
                        kx = (tx - x) / (rightX - leftX);
                    } else {
                        kx = (x - tx) / (rightX - leftX);
                    }
                    if (ty < y) {
                        ky = (y - ty) / (botY - topY);// прямая параллельная Ох, чекни потом.
                    } else {
                        ky = (ty - y) / (botY - topY);
                    }

                    for (int i = 0; i <= dots.size() - 1; i++) {
                        if (points.get(i).x != leftX) {
                            if (tx > x) {
                                if (kx == 1) {
                                    kx = 0.99;
                                }
                                dots.get(i).x = leftX + (points.get(i).x - leftX) * (1 - kx);
                            } else {
                                if (kx == -1) {
                                    kx = -0.99;
                                }
                                dots.get(i).x = leftX + (points.get(i).x - leftX) * (1 + kx);
                            }
                        }
                        if (points.get(i).y != botY) {
                            if (ty < y) {
                                if (ky == 1) {
                                    ky = 0.99;
                                }
                                dots.get(i).y = botY - (botY - points.get(i).y) * (1 - ky);
                            } else {
                                if (ky == -1) {
                                    ky = -0.99;
                                }
                                dots.get(i).y = botY - (botY - points.get(i).y) * (1 + ky);
                            }
                        }
                    }
                }
                if (act==7) { //лево-верх
                    if (tx < x) {
                        kx = (x - tx) / (rightX - leftX);//
                    } else {
                        kx = (tx - x) / (rightX - leftX);
                    }
                    if (ty < y) {
                        ky = (y - ty) / (botY - topY);// прямая параллельная Ох, чекни потом.
                    } else {
                        ky = (ty - y) / (botY - topY);
                    }

                    for (int i = 0; i <= dots.size() - 1; i++) {
                        if (points.get(i).x != rightX) {
                            if (tx < x) {
                                if (kx == 1) {
                                    kx = 0.99;
                                }
                                dots.get(i).x = rightX - (rightX - points.get(i).x) * (1 - kx);
                            } else {
                                if (kx == -1) {
                                    kx = -0.99;
                                }
                                dots.get(i).x = rightX - (rightX - points.get(i).x) * (1 + kx);
                            }
                        }
                        if (points.get(i).y != botY) {
                            if (ty < y) {
                                if (ky == 1) {
                                    ky = 0.99;
                                }
                                dots.get(i).y = botY - (botY - points.get(i).y) * (1 - ky);
                            } else {
                                if (ky == -1) {
                                    ky = -0.99;
                                }
                                dots.get(i).y = botY - (botY - points.get(i).y) * (1 + ky);
                            }
                        }
                    }
                }
                if (act==8) { //лево-низ
                    if (tx < x) {
                        kx = (x - tx) / (rightX - leftX);//
                    } else {
                        kx = (tx - x) / (rightX - leftX);
                    }
                    if (ty > y) {
                        ky = (ty - y) / (botY - topY);
                    } else {
                        ky = (y - ty) / (botY - topY);
                    }

                    for (int i = 0; i <= dots.size() - 1; i++) {
                        if (points.get(i).x != rightX) {
                            if (tx < x) {
                                if (kx == 1) {
                                    kx = 0.99;
                                }
                                dots.get(i).x = rightX - (rightX - points.get(i).x) * (1 - kx);
                            } else {
                                if (kx == -1) {
                                    kx = -0.99;
                                }
                                dots.get(i).x = rightX - (rightX - points.get(i).x) * (1 + kx);
                            }
                        }
                        if (points.get(i).y != topY) {
                            if (ty > y) {
                                if (ky == 1) {
                                    ky = 0.99;
                                }
                                dots.get(i).y = topY + (points.get(i).y - topY) * (1 - ky);
                            } else {
                                if (ky == -1) {
                                    ky = -0.99;
                                }
                                dots.get(i).y = topY + (points.get(i).y - topY) * (1 + ky);
                            }
                        }
                    }
                }
                if (act==9) { //право-низ
                    if (tx > x) {
                        kx = (tx - x) / (rightX - leftX);
                    } else {
                        kx = (x - tx) / (rightX - leftX);
                    }
                    if (ty > y) {
                        ky = (ty - y) / (botY - topY);
                    } else {
                        ky = (y - ty) / (botY - topY);
                    }

                    for (int i = 0; i <= dots.size() - 1; i++) {
                        if (points.get(i).x != leftX) {
                            if (tx > x) {
                                if (kx == 1) {
                                    kx = 0.99;
                                }
                                dots.get(i).x = leftX + (points.get(i).x - leftX) * (1 - kx);
                            } else {
                                if (kx == -1) {
                                    kx = -0.99;
                                }
                                dots.get(i).x = leftX + (points.get(i).x - leftX) * (1 + kx);
                            }
                        }
                        if (points.get(i).y != topY) {
                            if (ty > y) {
                                if (ky == 1) {
                                    ky = 0.99;
                                }
                                dots.get(i).y = topY + (points.get(i).y - topY) * (1 - ky);
                            } else {
                                if (ky == -1) {
                                    ky = -0.99;
                                }
                                dots.get(i).y = topY + (points.get(i).y - topY) * (1 + ky);
                            }
                        }
                    }
                }

            }

        }
    }

    public boolean chose(int x, int y){
        boolean result = false;
        int j = 3;
        for (int i=0; i<4; i++){
            if((((border[i].y<=y)&&(y<border[j].y)) ||  ((border[j].y<=y)&&(y<border[i].y))) && (x>((border[j].x-border[i].x)*(y-border[i].y)/(border[j].y-border[i].y)+border[i].x))){
                result=!result;
            }
            j=i;
        }
        return result;
    }

    public boolean needFill(int x, int y){
        boolean result = false;
        int n = points.size();
        int j = n-1;
        for (int i=0; i<n; i++){
            if(((( (int)Math.round((dots.get(i).y-shiftY)*Math.exp(scale))<=y)&&(y<(int)Math.round((dots.get(j).y-shiftY)*Math.exp(scale)))) ||  (((int)Math.round((dots.get(j).y-shiftY)*Math.exp(scale))<=y)&&(y<(int)Math.round((dots.get(i).y-shiftY)*Math.exp(scale))))) && (x>(((int)Math.round((dots.get(j).x-shiftX)*Math.exp(scale))-(int)Math.round((dots.get(i).x-shiftX)*Math.exp(scale)))*(y-(int)Math.round((dots.get(i).y-shiftY)*Math.exp(scale)))/((int)Math.round((dots.get(j).y-shiftY)*Math.exp(scale))-(int)Math.round((dots.get(i).y-shiftY)*Math.exp(scale)))+(int)Math.round((dots.get(i).x-shiftX)*Math.exp(scale))))){
                result=!result;
            }
            j=i;
        }
        return result;
    }
    public void fillD1(Graphics g){
        Point first = new Point(0,0), second = new Point(0,0);
        boolean need=false;
        for(int x= (int) ((lxc-shiftX)*Math.exp(scale)); x<= (int) ((rxc-shiftX)*Math.exp(scale)); x++){
            for (int y= (int) ((tyc-shiftY)*Math.exp(scale)+(x-lxc)%6); y<= (int) ((byc-shiftY)*Math.exp(scale)); y+=6){

                if (needFill(x,y)){
                    g.drawLine(x-1,y,x-1,y);
                }
            }
        }
    }
    public void fillD2(Graphics g){
        Point first = new Point(0,0), second = new Point(0,0);
        boolean need=false;
        for(int x= (int) ((lxc-shiftX)*Math.exp(scale)); x<= (int) ((rxc-shiftX)*Math.exp(scale)); x++){
            for (int y= (int) ((tyc-shiftY)*Math.exp(scale)-(x-lxc)%6); y<= (int) ((byc-shiftY)*Math.exp(scale)); y+=6){

                if (needFill(x,y)){
                    g.drawLine(x-1,y,x-1,y);
                }
            }
        }
    }
    public void fillH(Graphics g){
        Point first = new Point(0,0), second = new Point(0,0);
        boolean need=false;
        for (int y= (int) ((tyc-shiftY)*Math.exp(scale)); y<= (int) ((byc-shiftY)*Math.exp(scale)); y+=6){
            for(int x= (int) ((lxc-shiftX)*Math.exp(scale)); x<= (int) ((rxc-shiftX)*Math.exp(scale)); x++){

                if (needFill(x,y)){
                    if (!need) {
                        first = new Point(x,y);}
                    need = true;
                } else {
                    if (need){
                        need=false;
                        g.drawLine((int) first.x,(int) first.y,(int) second.x,(int) second.y);
                    }
                }
                second = new Point (x,y);
            }
            if (need) {
                need=false;
                g.drawLine((int) first.x,(int) first.y,(int) second.x,(int) second.y);
            }
        }
    }

    public void fillV(Graphics g){
        Point first = new Point(0,0), second = new Point(0,0);
        boolean need=false;
        for(int x= (int) ((lxc-shiftX)*Math.exp(scale)); x<= (int) ((rxc-shiftX)*Math.exp(scale)); x+=6){
            for (int y= (int) ((tyc-shiftY)*Math.exp(scale)); y<= (int) ((byc-shiftY)*Math.exp(scale)); y++){

                if (needFill(x,y)){
                    if (!need) {
                        first = new Point(x,y);}
                    need = true;
                } else {
                    if (need){
                        need=false;
                        g.drawLine((int) first.x,(int) first.y,(int) second.x,(int) second.y);
                    }
                }
                second = new Point (x,y);
            }
            if (need) {
                need=false;
                g.drawLine((int) first.x,(int) first.y,(int) second.x,(int) second.y);
            }
        }
    }

    public void fillP(Graphics g){
        for(int x= (int) ((lxc-shiftX)*Math.exp(scale)); x<= (int) ((rxc-shiftX)*Math.exp(scale)); x+=6){
            for (int y= (int) ((tyc-shiftY)*Math.exp(scale)); y<= (int) ((byc-shiftY)*Math.exp(scale)); y+=6){

                if (needFill(x,y)){
                    g.drawLine(x-1,y,x-1,y);
                }
            }
        }
    }

    public void fill(Graphics g){
        Point first = new Point(0,0), second = new Point(0,0);
        boolean need=false;
        for(int x= (int) ((lxc-shiftX)*Math.exp(scale)); x<= (int) ((rxc-shiftX)*Math.exp(scale)); x++){
            for (int y= (int) ((tyc-shiftY)*Math.exp(scale)); y<= (int) ((byc-shiftY)*Math.exp(scale)); y++){

                if (needFill(x,y)){
                    if (!need) {
                    first = new Point(x,y);}
                    need = true;
                } else {
                    if (need){
                        need=false;
                        g.drawLine((int) first.x,(int) first.y,(int) second.x,(int) second.y);
                    }
                }
                second = new Point (x,y);
            }
            if (need) {
                need=false;
                g.drawLine((int) first.x,(int) first.y,(int) second.x,(int) second.y);
            }
        }
    }
}

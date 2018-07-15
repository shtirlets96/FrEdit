package Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import Model.*;
import Model.Action;
import Model.Shape;
import View.MainWindow;

import static Controller.Queue.queue;

public class DrawPanel extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener {

    public static Shape activeShape = new Shape();
    public static double scale=0;
    public static int shiftX=0, shiftY=0, count=1, mode=-1, act; //shift - сдвиги; count - кол-во фигур; mode: 0-рисование, 1-трансформирование, 2-масштаб/сдвиг, 3-выбор фигуры, act - действие в трансформе 0-перемещение, 1- поворот, 2 - изменение
    public static boolean moveEnable=false, borderEnable=false;
    private int x,y,x1,y1;


    public DrawPanel() {
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
    }

    @Override
    public void paintComponent(Graphics g) {
        g.clearRect(0, 0, getWidth(), getHeight());
        Queue.paint(g);
        Graphics2D g2D = (Graphics2D) g;
        g.setColor(Color.BLACK);
        g2D.setStroke(new BasicStroke(1));
        if (!activeShape.points.isEmpty()) {
            activeShape.paint(g);
        }

        g2D.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0));
        if (borderEnable && !activeShape.points.isEmpty()){
            activeShape.drawBorderC(g);
        }

    }




    @Override
    public void mouseClicked(MouseEvent e) {


    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (mode==0){ //рисование

            activeShape.currentX=e.getX();
            activeShape.currentY=e.getY();
            activeShape.press(e);
            repaint(); //update();
        }
        if (mode==1){ // трасформирование фигуры
            //Queue.Chooser(e.getX(), e.getY());
            if (activeShape.ended){
            activeShape.tx=(int) (e.getX()/Math.exp(scale)+shiftX);
            activeShape.ty=(int) (e.getY()/Math.exp(scale)+shiftY);
            activeShape.rtx=(int) (e.getX()/Math.exp(scale)+shiftX);
            activeShape.rty=(int) (e.getY()/Math.exp(scale)+shiftY);
            activeShape.selectAct((int)(e.getX()/Math.exp(scale)+shiftX), (int)(e.getY()/Math.exp(scale)+shiftY));
            repaint(); }//update();
        }
        if (mode==2){ //масштабирование, сдвиг
            x=e.getX();
            y=e.getY();
        }
        if (mode==3){ // выбор фигуры
            Queue.Chooser((int) (e.getX()/Math.exp(scale)+shiftX), (int) (e.getY()/Math.exp(scale)+shiftY));
            repaint();
            System.out.println("done");

        }


    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (mode==0){ // рисование
            if (!activeShape.hard && e.getButton()==1) {
                activeShape.end(e);
                count++;
                System.out.println("отпустили");
            }
            repaint(); //update();
        }
        if (mode==1){ // трасформирование фигуры
            if (activeShape.ended){
            activeShape.changePoints();
            activeShape.findBorder();
            activeShape.findCenter();}
        }
        if (mode==2){ //масштабирование, сдвиг

        }
        if (mode==3){ // выбор фигуры

        }


    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (mode==0) { //рисование
            if (!activeShape.hard){
                activeShape.currentX=e.getX();
                activeShape.currentY=e.getY();

                repaint(); // update();
            }
        }
        if (mode==1){ // трасформирование фигуры
            if (activeShape.ended){
            activeShape.pointer(e.getX(), e.getY());
            ParametersChanger.coords();
            repaint(); }//update();
        }
        if (mode==2){ // считаем сдвиг
            x1=e.getX();
            y1=e.getY();
            if (x1>x){
                shiftX=shiftX - (int) Math.ceil((x1-x)/Math.exp(scale));
            }
            else{
                shiftX=shiftX+ (int) Math.ceil((x-x1)/Math.exp(scale));
            }
            if (y1>y){
                shiftY=shiftY- (int) Math.ceil((y1-y)/Math.exp(scale));
            }
            else{
                shiftY=shiftY+ (int) Math.ceil((y-y1)/Math.exp(scale));
            }
           if (shiftX<0){
               shiftX=0;
           }
           if (shiftY<0){
               shiftY=0;
           }
            repaint(); // update();
            x=x1;
            y=y1;
        }
        if (mode==3){ // выбор фигуры

        }

    }

    @Override
    public void mouseMoved(MouseEvent e) {  //рисование
        if (moveEnable && mode==0) {
            activeShape.currentX=e.getX();
            activeShape.currentY=e.getY();
            repaint(); //update();
        }

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (mode==2){
            if (e.getWheelRotation()==1){  // считаем масштаб
                scale=scale-0.5;
            }
            else{
                scale=scale+0.5;
            }
            MainWindow.textZoom.setText(String.valueOf((int)(scale*2)));
            repaint(); // update();
        }
    }
}

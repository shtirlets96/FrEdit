package Controller;

import Model.*;
import View.MainWindow;

import static Controller.DrawPanel.*;

public class Factory {

    public Factory() {
    }

    public static void getTool(String type){
        MainWindow.jPanel2.repaint();
        switch (type){
            case "Line":
                activeShape = new Line();
                moveEnable=false;
                borderEnable=false;
                mode=0;
                break;
            case "Rectangle":
                activeShape = new Rectangle();
                moveEnable=false;
                borderEnable=false;
                mode=0;
                break;
            case "Ellipse":
                activeShape = new Ellipse();
                moveEnable=false;
                borderEnable=false;
                mode=0;
                break;
            case "Triangle":
                activeShape = new Triangle();
                moveEnable=false;
                borderEnable=false;
                mode=0;
                break;
            case "Polyline":
                activeShape = new Polyline();
                moveEnable=true;
                borderEnable=false;
                mode=0;
                break;
            case "Polygon":
                activeShape = new Polygon();
                moveEnable=true;
                borderEnable=false;
                mode=0;
                break;
            case "Bezier":
                activeShape = new Bezier();
                moveEnable=true;
                borderEnable=false;
                mode=0;
                break;
            case "Change":
                mode=2;
                borderEnable=true;
                break;
            case "Pointer":
                borderEnable=true;
                mode=1;
                break;
            case "Choose":
                borderEnable=true;
                mode=3;
        break;
    }

    }
}

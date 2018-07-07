package Controller;

import Model.Shape;
import View.MainWindow;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import static Controller.DrawPanel.activeShape;

public class Queue {
    public static ArrayList<Shape> queue = new ArrayList<>();

    public static void paint(Graphics g) {
        for (Shape i:queue){
            i.paint(g);
        }
    }
    public static void Chooser(int x, int y){
        for(int i=queue.size()-1; i>=0; i--){
            if (queue.get(i).chose(x,y)){
                activeShape=queue.get(i);
                ParametersChanger.fillBoxChange();
                int b=0;
                switch (activeShape.options[0]){
                    case "-":
                        b=0;
                        break;
                    case "---":
                        b=1;
                        break;
                    case "...":
                        b=2;
                        break;
                }
                MainWindow.shapeBox.setSelectedIndex(i);
                MainWindow.borderBox.setSelectedIndex(b);
                MainWindow.jSlider2.setValue(Integer.parseInt(activeShape.options[4]));

                if (activeShape.name.contains("ine") || activeShape.name.contains("Bezier")) {
                    MainWindow.jButton23.enableInputMethods(false);
                }
                else {
                    MainWindow.jButton23.enableInputMethods(true);
                }
                ParametersChanger.coords();
                break;
            }
        }
    }

    public static void serializeStatic(ObjectOutputStream oos) throws IOException {
        oos.writeObject(queue);
    }

    public static void deserializeStatic(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        queue = (ArrayList<Shape>) ois.readObject();

    }

}

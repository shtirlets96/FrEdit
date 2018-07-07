package Controller;

import View.MainWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Controller.DrawPanel.activeShape;
import static Controller.DrawPanel.borderEnable;
import static Controller.DrawPanel.mode;
import static Controller.Queue.queue;

public class ShapeBox extends JComboBox implements ActionListener {
    public static boolean needAction = true;

    public ShapeBox() {
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (needAction) {
            if (getItemCount() != 0) {

                activeShape = queue.get(getSelectedIndex());
            }
            if (activeShape.ended){
            borderEnable=true;
            mode=3;}
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
            MainWindow.borderBox.setSelectedIndex(b);
            MainWindow.jSlider2.setValue(Integer.parseInt(activeShape.options[4]));
            if (activeShape.name.contains("ine") || activeShape.name.contains("Bezier")) {
                MainWindow.jButton23.enableInputMethods(false);
            } else {
                MainWindow.jButton23.enableInputMethods(true);
            }

            ParametersChanger.coords();
        }
        MainWindow.jPanel2.repaint();
    }
}

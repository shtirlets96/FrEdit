package Controller;

import View.MainWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JMenuItem implements ActionListener {
    public String type;
    MainWindow base;

    public Menu(String type, MainWindow base) {
        this.type = type;
        this.base = base;
        addActionListener(this);
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        switch (type){
            case "Undo":
                ShapeBox.needAction=false;
                if (!DrawPanel.undo.isEmpty()){
                    DrawPanel.undo.get(DrawPanel.undo.size()-1).undo();
                    MainWindow.jPanel2.repaint();
                }
                ShapeBox.needAction=true;
                break;
            case "Redo":
                ShapeBox.needAction=false;
                if (!DrawPanel.redo.isEmpty()){
                    DrawPanel.redo.get(DrawPanel.redo.size()-1).redo();
                    MainWindow.jPanel2.repaint();
                }
                ShapeBox.needAction=true;
                break;

        }
    }
}

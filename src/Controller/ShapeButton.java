package Controller;

import View.MainWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShapeButton extends JButton implements ActionListener {
    public String type;
    MainWindow base;

    public ShapeButton(String type, MainWindow base) {
        this.type = type;
        this.base = base;
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Factory.getTool(this.type);
    }
}

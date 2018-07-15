package Controller;

import Model.Shape;
import View.MainWindow;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;

import static Controller.DrawPanel.*;

import static Controller.Queue.queue;
import static View.MainWindow.*;

public class ChangeButton extends JButton implements ActionListener {
    public String type;
    MainWindow base;
    JFileChooser fileChooser = new JFileChooser();

    public ChangeButton(String type, MainWindow base) {
        this.type = type;
        this.base = base;
        addActionListener(this);
    }

    @Override

    public void actionPerformed(ActionEvent e) {
        switch (type) {
            case "Delete":
                if (!queue.isEmpty()) {
                    int p = shapeBox.getSelectedIndex();
                    queue.remove(p);
                    for (int i=0; i<queue.size(); i++){
                        queue.get(i).indQ =i;
                    }
                    ShapeBox.needAction = false;
                    shapeBox.removeItemAt(p);
                    ShapeBox.needAction = true;
                    if (shapeBox.getItemCount() != 0) {
                        shapeBox.setSelectedIndex(0);
                    } else {
                        activeShape = new Shape();
                    }

                } else {
                    activeShape = new Shape();
                    count=1;
                }
                MainWindow.jPanel2.repaint();

                break;
            case "Panel":
                scale = Integer.parseInt(textZoom.getText()) / 2;
                jPanel2.repaint();
                break;
            case "ShapePanel":
                /*MainWindow.jPanel3.setVisible(true);
                MainWindow.jPanel9.setVisible(false);*/
                break;
            case "Fill":
                ParametersChanger.fill();
                MainWindow.jPanel2.repaint();
                break;
            case "Border":
                ParametersChanger.border();
                MainWindow.jPanel2.repaint();
                break;
            case "BorderColor":
                activeShape.bor = JColorChooser.showDialog(null, "Выберите цвет границы", null);
                MainWindow.jPanel2.repaint();
                activeShape.options[1] = String.valueOf(activeShape.bor.getRed());
                activeShape.options[2] = String.valueOf(activeShape.bor.getGreen());
                activeShape.options[3] = String.valueOf(activeShape.bor.getBlue());
                MainWindow.jPanel2.repaint();
                break;
            case "FillColor":
                activeShape.f = JColorChooser.showDialog(null, "Выберите цвет заливки", null);
                activeShape.options[6] = String.valueOf(activeShape.f.getRed());
                activeShape.options[7] = String.valueOf(activeShape.f.getGreen());
                activeShape.options[8] = String.valueOf(activeShape.f.getBlue());
                MainWindow.jPanel2.repaint();
                break;

            case "Down":
                if (MainWindow.shapeBox.getSelectedIndex() != (queue.size() - 1)) {
                    Shape reserve = activeShape;
                    queue.set(MainWindow.shapeBox.getSelectedIndex(), queue.get(MainWindow.shapeBox.getSelectedIndex() + 1));
                    queue.set(MainWindow.shapeBox.getSelectedIndex() + 1, reserve);
                    MainWindow.shapeBox.insertItemAt(queue.get(MainWindow.shapeBox.getSelectedIndex()).name, MainWindow.shapeBox.getSelectedIndex());
                    MainWindow.shapeBox.removeItemAt(MainWindow.shapeBox.getSelectedIndex() + 1);
                    for (int i=0; i<queue.size(); i++){
                        queue.get(i).indQ =i;
                    }
                }
                break;
            case "Up":
                if (MainWindow.shapeBox.getSelectedIndex() != 0) {
                    Shape reserve = activeShape;
                    queue.set(MainWindow.shapeBox.getSelectedIndex(), queue.get(MainWindow.shapeBox.getSelectedIndex() - 1));
                    queue.set(MainWindow.shapeBox.getSelectedIndex() - 1, reserve);
                    MainWindow.shapeBox.insertItemAt(activeShape.name, MainWindow.shapeBox.getSelectedIndex() - 1);
                    MainWindow.shapeBox.removeItemAt(MainWindow.shapeBox.getSelectedIndex() + 2);
                    MainWindow.shapeBox.setSelectedIndex(MainWindow.shapeBox.getSelectedIndex() - 1);
                    for (int i=0; i<queue.size(); i++){
                        queue.get(i).indQ =i;
                    }
                }
                break;
            case "Download":
                int ret = fileChooser.showDialog(null, "Открыть файл");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();

                    if (file != null) {
                        ObjectInputStream ois = null;
                        try {
                            ois = new ObjectInputStream(new FileInputStream(file));
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        try {
                            Queue.deserializeStatic(ois);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        } catch (ClassNotFoundException e1) {
                            e1.printStackTrace();
                        }
                        DrawPanel.count = 1;
                        activeShape = new Shape();
                        shapeBox.removeAllItems();
                        for (int i = 0; i < Queue.queue.size(); i++) {

                            Queue.queue.get(i).afterLoad();
                            shapeBox.addItem(Queue.queue.get(i).name);
                        }

                        MainWindow.jPanel2.repaint();
                    }
                }
                break;
            case "Save":
                int ret1 = fileChooser.showDialog(null, "Сохранить файл");
                if (ret1 == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    if (file != null) {
                        ObjectOutputStream oos = null;
                        try {
                            oos = new ObjectOutputStream(new FileOutputStream(file));
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        try {
                            Queue.serializeStatic(oos);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }

                        MainWindow.jPanel2.repaint();
                    }
                }
                break;
            case "png":
                fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("PNG Documents", "png"));
                fileChooser.setAcceptAllFileFilterUsed(true);
                int result = fileChooser.showOpenDialog(this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    System.out.println("Selected file: " + selectedFile.getAbsolutePath());

                    BufferedImage bImg = new BufferedImage(jPanel2.getWidth(), jPanel2.getHeight(), BufferedImage.TYPE_INT_RGB);
                    Graphics2D cg = bImg.createGraphics();
                    jPanel2.paintAll(cg);

                    try {
                        if (ImageIO.write(bImg, "png", selectedFile))
                        {
                            System.out.println("-- saved");
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }


                }
                break;
        }
    }


}



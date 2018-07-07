package Model;

import Controller.DrawPanel;
import Controller.Queue;
import View.MainWindow;

public class Add extends Action {

    public Add(Shape shape) {
        super(shape);
    }

    @Override
    public void undo() {
        if ((!Queue.queue.isEmpty())) {
            int size = DrawPanel.undo.size();
            DrawPanel.redo.add(new Add(Queue.queue.get(shape.indQ).copyIt()));
            DrawPanel.undo.remove(size - 1);
            Queue.queue.remove(Queue.queue.size() - 1);
            for(int i = 0; i < Queue.queue.size(); i++){
// MainWindow.queue.get(i).ind = i;
                Queue.queue.get(i).indQ = i;
            }
            MainWindow.shapeBox.removeItemAt(MainWindow.shapeBox.getItemCount() - 1);
            DrawPanel.mode = 3;
            DrawPanel.borderEnable = true;
            if ((!Queue.queue.isEmpty())) {
                DrawPanel.activeShape = Queue.queue.get(Queue.queue.size() - 1);
            } else {
                DrawPanel.activeShape = new Shape();
                DrawPanel.count = 1;
            }
        }
    }

    @Override
    public void redo() {
        Queue.queue.add(shape.indQ, shape);
        DrawPanel.undo.add(new Add(shape.copyIt()));

        for (int i = 0; i < Queue.queue.size(); i++) {
            System.out.println(i + "qqq " + Queue.queue.get(i).indQ);
// MainWindow.queue.get(i).ind = i;
            Queue.queue.get(i).indQ = i;
        }
        MainWindow.shapeBox.addItem(String.valueOf(shape.getClass()).substring(12) + " " + (shape.ind));
        MainWindow.shapeBox.setSelectedIndex(shape.indQ);
        DrawPanel.activeShape = Queue.queue.get(shape.indQ);
        DrawPanel.redo.remove(DrawPanel.redo.size() - 1);
        //DrawPanel.drawMode = false;
        //DrawPanel.cursorMode = true;
//MainWindow.n = MainWindow.queue.size() + 1;
    }

}
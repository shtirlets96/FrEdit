package Model;

import Controller.DrawPanel;
import Controller.Queue;
import View.MainWindow;

public class Delete extends Action{

    public Delete(Shape shape) {
        super(shape);
    }

    @Override
    public void undo() {
        System.out.println("u " + DrawPanel.undo.size());
        Queue.queue.add(shape.indQ, shape);
        DrawPanel.redo.add(new Delete(Queue.queue.get(shape.indQ).copyIt()));
        for(int i = 0; i < Queue.queue.size(); i++){
// Queue.queue.get(i).ind = i;
            Queue.queue.get(i).indQ = i;
        }
        MainWindow.shapeBox.insertItemAt(String.valueOf(shape.getClass()).substring(12) + " " + (shape.ind + 1), shape.indQ);
        MainWindow.shapeBox.setSelectedIndex(shape.indQ);
        DrawPanel.activeShape = Queue.queue.get(shape.indQ);
        DrawPanel.undo.remove(DrawPanel.undo.size() - 1);
        //DrawPanel.drawMode = false;
        //DrawPanel.cursorMode = true;
//MainWindow.n = Queue.queue.size()+1;
    }

    @Override
    public void redo() {
        System.out.println("dfhvadkjfvzlhkgfv");
        if ((!Queue.queue.isEmpty())) {
            int size = DrawPanel.redo.size();
            DrawPanel.undo.add(new Delete(shape.copyIt()));
            DrawPanel.redo.remove(size - 1);
            Queue.queue.remove(Queue.queue.size() - 1);
            for(int i = 0; i < Queue.queue.size(); i++){
// Queue.queue.get(i).ind = i;
                Queue.queue.get(i).indQ = i;
            }

            MainWindow.shapeBox.removeItemAt(MainWindow.shapeBox.getItemCount() - 1);
           // DrawPanel.drawMode = false;
            //DrawPanel.cursorMode = false;
            if ((!Queue.queue.isEmpty())) {
                DrawPanel.activeShape = Queue.queue.get(Queue.queue.size() - 1);
            } else {
                DrawPanel.activeShape = new Shape();
                //DrawPanel.borderMode = false;
//MainWindow.n = 1;
            }
        }
    }
}

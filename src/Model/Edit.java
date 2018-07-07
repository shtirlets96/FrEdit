package Model;

import Controller.DrawPanel;
import Controller.Queue;
import View.MainWindow;

public class Edit extends Action {

    public Edit(Shape shape) {
        super(shape);
    }

    @Override
    public void undo() {
        if ((!Queue.queue.isEmpty())) {
            DrawPanel.redo.add(new Edit(Queue.queue.get(shape.indQ).copyIt()));
            Queue.queue.set(shape.indQ, shape);
            DrawPanel.activeShape = Queue.queue.get(shape.indQ);
           // DrawPanel.drawMode = false;
           // DrawPanel.cursorMode = true;
            DrawPanel.undo.remove(DrawPanel.undo.size() - 1);
        }
    }

    @Override
    public void redo() {
//if ((!Queue.queue.isEmpty())) {
//Queue.queue.set(shape.indQ, shape);
        System.out.println("");
        Queue.queue.set(shape.indQ, shape);
        DrawPanel.redo.remove(DrawPanel.redo.size() - 1);
        DrawPanel.activeShape = Queue.queue.get(shape.indQ);
        //DrawPanel.drawMode = false;
       // DrawPanel.cursorMode = true;

        DrawPanel.undo.add(new Edit(shape.copyIt()));
//}
    }
}
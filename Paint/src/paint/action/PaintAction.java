package paint.action;

import java.util.ArrayList;
import java.util.Stack;
import javafx.scene.canvas.GraphicsContext;
import paint.PaintApp;

public abstract class PaintAction extends PaintApp {

    Stack<Integer> undoInt;
    Stack<Integer> redoInt;
    ArrayList<?>[] arrayBrush;
    ArrayList<?>[] arrayPen;
    ArrayList<?>[] arrayEraser;

    public PaintAction(GraphicsContext gb, GraphicsContext gc) {
        super(gb, gc);
    }

    public void setInteger(Stack<Integer> undoInt, Stack<Integer> redoInt) {
        this.undoInt = undoInt;
        this.redoInt = redoInt;
    }

    public void setArray(ArrayList<?>[] arrayBrush, ArrayList<?>[] arrayPen, ArrayList<?>[] arrayEraser) {
        this.arrayBrush = arrayBrush;
        this.arrayEraser = arrayEraser;
        this.arrayPen = arrayPen;
    }

}

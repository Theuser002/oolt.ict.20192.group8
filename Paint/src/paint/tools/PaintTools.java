package paint.tools;

import java.util.Stack;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Spinner;
import javafx.scene.shape.Shape;
import paint.PaintApp;

public abstract class PaintTools extends PaintApp {

    ColorPicker colorPicker;
    ColorPicker cpFill;
    Spinner<?> sizeTool;
    boolean selected;
    Stack<Shape> undoHistory;
    Stack<Shape> redoHistory;
    Stack<Integer> undoInt;
    Stack<Integer> redoInt;

    public PaintTools(GraphicsContext gc, GraphicsContext gb, ColorPicker colorPicker, ColorPicker cpFill, Spinner<?> sizeTool, boolean selected) {
        super(gb, gc);
        this.colorPicker = colorPicker;
        this.cpFill = cpFill;
        this.sizeTool = sizeTool;
        this.selected = selected;
    }

    public PaintTools(GraphicsContext gc, ColorPicker colorPicker, Spinner<?> sizeTool, boolean selected) {
        super(gc);
        this.colorPicker = colorPicker;
        this.sizeTool = sizeTool;
        this.selected = selected;
    }

    public PaintTools() {

    }

    public void setInteger(Stack<Integer> undoInt, Stack<Integer> redoInt) {
        this.undoInt = undoInt;
        this.redoInt = redoInt;
    }

    public void setStack(Stack<Shape> undoHistory, Stack<Shape> redoHistory) {
        this.undoHistory = undoHistory;
        this.redoHistory = redoHistory;
    }
}

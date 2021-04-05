package paint.tools;

import java.util.Stack;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Spinner;
import javafx.scene.shape.Shape;

public class ShapeDraw extends PaintTools {

    double x;
    double y;
    Shape shape;

    public ShapeDraw(GraphicsContext gc, GraphicsContext gb, ColorPicker colorPicker, ColorPicker cpFill, Spinner<?> sizeTool, boolean selected) {
        super(gc, gb, colorPicker, cpFill, sizeTool, selected);
    }

    public void setProperty(double x, double y, Shape shape) {
        this.x = x;
        this.y = y;
        this.shape = shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public void toolPressed() {
        if (selected) {
            gc.setLineWidth(Integer.parseInt(sizeTool.getValue().toString()));
            gc.setStroke(colorPicker.getValue());
            gc.setFill(cpFill.getValue());
        }
    }

    public void toolDrag() {
        if (selected) {
            gb.setLineWidth(Integer.parseInt(sizeTool.getValue().toString()));
            gb.setStroke(colorPicker.getValue());
            gb.setFill(cpFill.getValue());
        }
    }

    public void toolRelease() {
        redoHistory.clear();
        Shape lastUndo = undoHistory.lastElement();
        lastUndo.setFill(gc.getFill());
        lastUndo.setStroke(gc.getStroke());
        lastUndo.setStrokeWidth(gc.getLineWidth());
    }
}

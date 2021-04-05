package paint.tools;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Spinner;
import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;

public class PencilTool extends PaintTools implements PenBrush {

    ArrayList[] arrayPen;
    public static int count = 0;

    public PencilTool() {

    }

    public PencilTool(GraphicsContext gc, ColorPicker colorPicker, Spinner<?> sizeTool, boolean selected) {
        super(gc, colorPicker, sizeTool, selected);
    }

    public void setStack(ArrayList[] arrayPen) {
        this.arrayPen = arrayPen;
    }

    @Override
    public void drag(double a, double b) {
        gc.setStroke(colorPicker.getValue());
        if (selected) {
            gc.lineTo(a, b);
            gc.stroke();
            arrayPen[count].add(a);
            arrayPen[count].add(b);
        }
    }

    @Override
    public void press(double a, double b) {
        gc.setStroke(colorPicker.getValue());
        if (selected) {
            gc.setLineWidth(Integer.parseInt(sizeTool.getValue().toString()));
            gc.beginPath();
            gc.lineTo(a, b);
            arrayPen[count].add(a);
            arrayPen[count].add(b);
        }
    }

    @Override
    public void release() {
        undoHistory.push(new Path());
        undoInt.push(count);
        count++;
        redoHistory.clear();
        Shape lastUndo = undoHistory.lastElement();
        lastUndo.setFill(gc.getFill());
        lastUndo.setStroke(gc.getStroke());
        lastUndo.setStrokeWidth(gc.getLineWidth());
    }
}

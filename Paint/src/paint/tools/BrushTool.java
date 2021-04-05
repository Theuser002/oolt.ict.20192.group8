package paint.tools;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Spinner;
import javafx.scene.shape.QuadCurve;
import javafx.scene.shape.Shape;

public class BrushTool extends PaintTools implements PenBrush {

    ArrayList[] arrayBrush;
    public static int count = 0;

    public BrushTool(GraphicsContext gc, ColorPicker colorPicker, Spinner<?> sizeTool, boolean selected) {
        super(gc, colorPicker, sizeTool, selected);
    }

    public void setStack(ArrayList[] arrayBrush) {
        this.arrayBrush = arrayBrush;
    }

    @Override
    public void drag(double a, double b) {
        double size = Double.parseDouble(sizeTool.getValue().toString());
        double x = a - size / 2;
        double y = b - size / 2;

        if (selected) {
            gc.setFill(colorPicker.getValue());
            gc.fillRoundRect(x, y, size, size, size, size);
            arrayBrush[count].add(a);
            arrayBrush[count].add(b);
            arrayBrush[count].add(size);
        }
    }

    @Override
    public void press(double a, double b) {
    }

    @Override
    public void release() {
        undoHistory.push(new QuadCurve());
        undoInt.push(count);
        count++;
        redoHistory.clear();
        Shape lastUndo = undoHistory.lastElement();
        lastUndo.setFill(gc.getFill());
        lastUndo.setStroke(gc.getStroke());
        lastUndo.setStrokeWidth(gc.getLineWidth());
    }
}

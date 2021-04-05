package paint.tools;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Spinner;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Shape;

public class EraserTool extends PaintTools implements PenBrush {

    ArrayList[] arrayEraser;
    public static int count = 0;

    public EraserTool() {

    }

    public EraserTool(GraphicsContext gc, GraphicsContext gb, ColorPicker colorPicker, ColorPicker cpFill, Spinner<?> sizeTool, boolean selected) {
        super(gc, gb, colorPicker, cpFill, sizeTool, selected);
    }

    public void setStack(ArrayList[] arrayEraser) {
        this.arrayEraser = arrayEraser;
    }

//    public void eraser(double x, double y) {
//        if (selected) {
//            double lineWidth = Double.parseDouble(sizeTool.getValue().toString());
//            gc.setStroke(Color.WHITE);
//            gc.clearRect(x - lineWidth / 2, y - lineWidth / 2, lineWidth, lineWidth);
//            gb.setStroke(Color.WHITE);
//            gb.clearRect(x - lineWidth / 2, y - lineWidth / 2, lineWidth, lineWidth);
//        }
//    }
    @Override
    public void drag(double a, double b) {
        gc.setStroke(Color.WHITE);
        if (selected) {
            gc.lineTo(a, b);
            gc.stroke();
            arrayEraser[count].add(a);
            arrayEraser[count].add(b);

        }
    }

    @Override
    public void press(double a, double b) {
        gc.setStroke(Color.WHITE);
        if (selected) {
            gc.setLineWidth(Integer.parseInt(sizeTool.getValue().toString()));
            gc.beginPath();
            gc.lineTo(a, b);
            arrayEraser[count].add(a);
            arrayEraser[count].add(b);
        }
    }

    @Override
    public void release() {
        undoHistory.push(new Arc());
        undoInt.push(count);
        count++;
        redoHistory.clear();
        Shape lastUndo = undoHistory.lastElement();
        lastUndo.setFill(gc.getFill());
        lastUndo.setStroke(gc.getStroke());
        lastUndo.setStrokeWidth(gc.getLineWidth());
    }
}

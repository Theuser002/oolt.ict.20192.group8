package paint.tools;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Spinner;
import javafx.scene.shape.Line;

public class LineTool extends ShapeDraw {

    public LineTool(GraphicsContext gc, GraphicsContext gb, ColorPicker colorPicker, ColorPicker cpFill, Spinner<?> sizeTool, boolean selected) {
        super(gc, gb, colorPicker, cpFill, sizeTool, selected);
    }

    public void toolPressed() {
        super.toolPressed();
        Line line = (Line) shape;
        if (selected) {
            line.setStartX(x);
            line.setStartY(y);
        }
    }

    public void toolDrag(Canvas canvas) {
        Line line = (Line) shape;
        if (selected) {
            line.setEndX(x);
            line.setEndY(y);
            gb.setLineWidth(Integer.parseInt(sizeTool.getValue().toString()));
            gb.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            gb.setStroke(colorPicker.getValue());
            gb.strokeLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());
        }
    }

    public void toolRelease() {
        Line line = (Line) shape;
        if (selected) {
            line.setEndX(x);
            line.setEndY(y);
            gc.strokeLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());
            undoHistory.push(new Line(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY()));
            undoInt.push(2);
        }
        super.toolRelease();
    }
}

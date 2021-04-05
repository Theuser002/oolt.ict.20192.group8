package paint.tools;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Spinner;
import javafx.scene.shape.Circle;

public class CircleTool extends ShapeDraw {

    public CircleTool(GraphicsContext gc, GraphicsContext gb, ColorPicker colorPicker, ColorPicker cpFill, Spinner<?> sizeTool, boolean selected) {
        super(gc, gb, colorPicker, cpFill, sizeTool, selected);
    }

    public void toolPressed() {
        super.toolPressed();
        if (selected) {
            ((Circle) shape).setCenterX(x);
            ((Circle) shape).setCenterY(y);
        }
    }

    public void toolDrag(Canvas canvas) {
        super.toolDrag();
        Circle circ = ((Circle) shape);
        if (selected) {
            circ.setRadius((Math.abs(x - circ.getCenterX()) + Math.abs(y - circ.getCenterY())) / 2);
            if (circ.getCenterX() > x) {
                circ.setCenterX(x);
            }
            if (circ.getCenterY() > y) {
                circ.setCenterY(y);
            }
            gb.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            gb.fillOval(circ.getCenterX(), circ.getCenterY(), circ.getRadius(), circ.getRadius());
            gb.strokeOval(circ.getCenterX(), circ.getCenterY(), circ.getRadius(), circ.getRadius());
        }
    }

    public void toolRelease() {
        Circle circ = ((Circle) shape);
        if (selected) {
            circ.setRadius((Math.abs(x - circ.getCenterX()) + Math.abs(y - circ.getCenterY())) / 2);

            if (circ.getCenterX() > x) {
                circ.setCenterX(x);
            }
            if (circ.getCenterY() > y) {
                circ.setCenterY(y);
            }
            gc.fillOval(circ.getCenterX(), circ.getCenterY(), circ.getRadius(), circ.getRadius());
            gc.strokeOval(circ.getCenterX(), circ.getCenterY(), circ.getRadius(), circ.getRadius());
            undoHistory.push(new Circle(circ.getCenterX(), circ.getCenterY(), circ.getRadius()));
            undoInt.push(2);
        }
        super.toolRelease();
    }
}

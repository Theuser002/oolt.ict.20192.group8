package paint.tools;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Spinner;
import javafx.scene.shape.Rectangle;

public class RectangleTool extends ShapeDraw {

    public RectangleTool(GraphicsContext gc, GraphicsContext gb, ColorPicker colorPicker, ColorPicker cpFill, Spinner<?> sizeTool, boolean selected) {
        super(gc, gb, colorPicker, cpFill, sizeTool, selected);
    }

    public void toolPressed() {
        Rectangle rect = (Rectangle) shape;
        super.toolPressed();
        if (selected) {
            rect.setX(x);
            rect.setY(y);
        }
    }

    public void toolDrag(Canvas canvas) {
        super.toolDrag();
        Rectangle rect = (Rectangle) shape;
        if (selected) {
            rect.setWidth(Math.abs((x - rect.getX())));
            rect.setHeight(Math.abs((y - rect.getY())));
            if (rect.getX() > x) {
                rect.setX(x);
            }
            if (rect.getY() > y) {
                rect.setY(y);
            }
            gb.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            gb.fillRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
            gb.strokeRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
        }
    }

    public void toolRelease() {
        Rectangle rect = (Rectangle) shape;
        if (selected) {
            rect.setWidth(Math.abs((x - rect.getX())));
            rect.setHeight(Math.abs((y - rect.getY())));
            if (rect.getX() > x) {
                rect.setX(x);
            }
            if (rect.getY() > y) {
                rect.setY(y);
            }
            gc.fillRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
            gc.strokeRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
            undoHistory.push(new Rectangle(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight()));
            undoInt.push(0);
        }
        super.toolRelease();
    }
}

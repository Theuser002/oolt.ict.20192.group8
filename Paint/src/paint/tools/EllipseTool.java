package paint.tools;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Spinner;
import javafx.scene.shape.Ellipse;

public class EllipseTool extends ShapeDraw {

    public EllipseTool(GraphicsContext gc, GraphicsContext gb, ColorPicker colorPicker, ColorPicker cpFill, Spinner<?> sizeTool, boolean selected) {
        super(gc, gb, colorPicker, cpFill, sizeTool, selected);
    }

    public void toolPressed() {
        super.toolPressed();
        Ellipse elps = (Ellipse) shape;
        if (selected) {
            elps.setCenterX(x);
            elps.setCenterY(y);
        }
    }

    public void toolDrag(Canvas canvas) {
        super.toolDrag();
        Ellipse elps = (Ellipse) shape;
        if (selected) {
            elps.setRadiusX(Math.abs(x - elps.getCenterX()));
            elps.setRadiusY(Math.abs(y - elps.getCenterY()));
            if (elps.getCenterX() > x) {
                elps.setCenterX(x);
            }
            if (elps.getCenterY() > y) {
                elps.setCenterY(y);
            }
            gb.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            gb.strokeOval(elps.getCenterX(), elps.getCenterY(), elps.getRadiusX(), elps.getRadiusY());
            gb.fillOval(elps.getCenterX(), elps.getCenterY(), elps.getRadiusX(), elps.getRadiusY());
        }
    }

    public void toolRelease() {
        Ellipse elps = (Ellipse) shape;
        if (selected) {
            elps.setRadiusX(Math.abs(x - elps.getCenterX()));
            elps.setRadiusY(Math.abs(y - elps.getCenterY()));

            if (elps.getCenterX() > x) {
                elps.setCenterX(x);
            }
            if (elps.getCenterY() > y) {
                elps.setCenterY(y);
            }

            gc.strokeOval(elps.getCenterX(), elps.getCenterY(), elps.getRadiusX(), elps.getRadiusY());
            gc.fillOval(elps.getCenterX(), elps.getCenterY(), elps.getRadiusX(), elps.getRadiusY());

            undoHistory.push(new Ellipse(elps.getCenterX(), elps.getCenterY(), elps.getRadiusX(), elps.getRadiusY()));
            undoInt.push(2);
        }
        super.toolRelease();
    }
}

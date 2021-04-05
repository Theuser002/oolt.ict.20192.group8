package paint.tools;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Spinner;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Polyline;

public class TriangleTool extends ShapeDraw {

    double startX;
    double startY;
    double lastX;
    double lastY;

    public TriangleTool(GraphicsContext gc, GraphicsContext gb, ColorPicker colorPicker, ColorPicker cpFill, Spinner<?> sizeTool, boolean selected) {
        super(gc, gb, colorPicker, cpFill, sizeTool, selected);
    }
    
    public void setTriangle(double startX, double startY, double lastX, double lastY) {
        this.startX = startX;
        this.startY = startY;
        this.lastX = lastX;
        this.lastY = lastY;
    }

    public void toolPressed(MouseEvent e) {
        super.toolPressed();
    }

    public void toolDrag(Canvas canvas) {
        super.toolDrag();
        if (selected) {
            double midX = (startX + lastX) / 2;
            gb.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            gb.fillPolygon(new double[]{midX, startX, lastX},
                    new double[]{startY, lastY, lastY}, 3);
            gb.strokePolygon(new double[]{midX, startX, lastX},
                    new double[]{startY, lastY, lastY}, 3);
        }
    }

    public void toolRelease() {
        Polyline triangle = (Polyline) shape;
        super.toolDrag();
        if (selected) {
            double midX = (startX + lastX) / 2;
            gc.fillPolygon(new double[]{midX, startX, lastX},
                    new double[]{startY, lastY, lastY}, 3);
            gc.strokePolygon(new double[]{midX, startX, lastX},
                    new double[]{startY, lastY, lastY}, 3);
            triangle.getPoints().addAll(midX, startY, startX, lastY, lastX, lastY);
            undoHistory.push(new Polyline(midX, startY, startX, lastY, lastX, lastY));
            undoInt.push(2);
        }
        super.toolRelease();
    }

}

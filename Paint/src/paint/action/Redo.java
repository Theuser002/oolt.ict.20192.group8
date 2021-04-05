package paint.action;

import java.util.Stack;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.QuadCurve;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Redo extends PaintAction {

    public Redo(GraphicsContext gb, GraphicsContext gc) {
        super(gb, gc);
    }

    public void redoAct(Stack<Shape> undoHistory, Stack<Shape> redoHistory) {
        if (!redoHistory.empty()) {
            Shape shape = redoHistory.lastElement();
            int t = redoInt.lastElement();

            gc.setLineWidth(shape.getStrokeWidth());
            gc.setStroke(shape.getStroke());
            gc.setFill(shape.getFill());

            redoHistory.pop();
            redoInt.pop();

            if (shape.getClass() == Line.class) {
                Line tempLine = (Line) shape;
                gc.strokeLine(tempLine.getStartX(), tempLine.getStartY(), tempLine.getEndX(), tempLine.getEndY());
                undoHistory.push(new Line(tempLine.getStartX(), tempLine.getStartY(), tempLine.getEndX(), tempLine.getEndY()));
                undoInt.push(2);

            } else if (shape.getClass() == Rectangle.class) {
                Rectangle tempRect = (Rectangle) shape;
                if (t == 0) {
                    gc.fillRect(tempRect.getX(), tempRect.getY(), tempRect.getWidth(), tempRect.getHeight());
                    gc.strokeRect(tempRect.getX(), tempRect.getY(), tempRect.getWidth(), tempRect.getHeight());
                    undoHistory.push(new Rectangle(tempRect.getX(), tempRect.getY(), tempRect.getWidth(), tempRect.getHeight()));
                    undoInt.push(0);
                } else if (t == 1) {
                    gc.fillRoundRect(tempRect.getX(), tempRect.getY(), tempRect.getWidth(), tempRect.getHeight(), 70f, 70f);
                    gc.strokeRoundRect(tempRect.getX(), tempRect.getY(), tempRect.getWidth(), tempRect.getHeight(), 70f, 70f);
                    undoHistory.push(new Rectangle(tempRect.getX(), tempRect.getY(), tempRect.getWidth(), tempRect.getHeight()));
                    undoInt.push(1);
                }
            } else if (shape.getClass() == Circle.class) {
                Circle tempCirc = (Circle) shape;
                gc.fillOval(tempCirc.getCenterX(), tempCirc.getCenterY(), tempCirc.getRadius(), tempCirc.getRadius());
                gc.strokeOval(tempCirc.getCenterX(), tempCirc.getCenterY(), tempCirc.getRadius(), tempCirc.getRadius());
                undoHistory.push(new Circle(tempCirc.getCenterX(), tempCirc.getCenterY(), tempCirc.getRadius()));
                undoInt.push(2);
            } else if (shape.getClass() == Ellipse.class) {
                Ellipse tempElps = (Ellipse) shape;
                gc.fillOval(tempElps.getCenterX(), tempElps.getCenterY(), tempElps.getRadiusX(), tempElps.getRadiusY());
                gc.strokeOval(tempElps.getCenterX(), tempElps.getCenterY(), tempElps.getRadiusX(), tempElps.getRadiusY());
                undoHistory.push(new Ellipse(tempElps.getCenterX(), tempElps.getCenterY(), tempElps.getRadiusX(), tempElps.getRadiusY()));
                undoInt.push(2);
            } else if (shape.getClass() == Polyline.class) {
                Polyline temp = (Polyline) shape;
                gc.setLineWidth(temp.getStrokeWidth());
                gc.setStroke(temp.getStroke());
                gc.setFill(temp.getFill());
                gc.fillPolygon(new double[]{temp.getPoints().get(0), temp.getPoints().get(2), temp.getPoints().get(4)},
                        new double[]{temp.getPoints().get(1), temp.getPoints().get(3), temp.getPoints().get(5)}, 3);
                gc.strokePolygon(new double[]{temp.getPoints().get(0), temp.getPoints().get(2), temp.getPoints().get(4)},
                        new double[]{temp.getPoints().get(1), temp.getPoints().get(3), temp.getPoints().get(5)}, 3);
                undoHistory.push(new Polyline(temp.getPoints().get(0), temp.getPoints().get(1), temp.getPoints().get(2), temp.getPoints().get(3), temp.getPoints().get(4), temp.getPoints().get(5)));
                undoInt.push(2);
            } else if (shape.getClass() == QuadCurve.class) {
                QuadCurve temp = (QuadCurve) shape;
                int count = arrayBrush[t].size();
                for (int j = 0; j < count; j = j + 3) {
                    double x = (Double) arrayBrush[t].get(j);
                    double y = (Double) arrayBrush[t].get(j + 1);
                    double size = (Double) arrayBrush[t].get(j + 2);
                    gc.setFill(temp.getFill());
                    gc.fillRoundRect(x, y, size, size, size, size);
                }
                undoHistory.push(new QuadCurve());
                undoInt.push(t);
            } else if (shape.getClass() == Path.class) {
                Path temp = (Path) shape;
                int count = arrayPen[t].size();
                gc.beginPath();
                for (int j = 0; j < count; j = j + 2) {
                    double x = (Double) arrayPen[t].get(j);
                    double y = (Double) arrayPen[t].get(j + 1);
                    gc.setStroke(temp.getStroke());
                    gc.lineTo(x, y);
                    gc.stroke();
                    gc.setFill(temp.getFill());
                }
                undoHistory.push(new Path());
                undoInt.push(t);
            } else {
                Arc temp = (Arc) shape;
                int count = arrayEraser[t].size();
                gc.beginPath();
                for (int j = 0; j < count; j = j + 2) {
                    double x = (Double) arrayEraser[t].get(j);
                    double y = (Double) arrayEraser[t].get(j + 1);
                    gc.setStroke(Color.WHITE);
                    gc.lineTo(x, y);
                    gc.stroke();
                    gc.setFill(temp.getFill());
                }
                undoHistory.push(new Arc());
                undoInt.push(t);
            }
            Shape lastUndo = undoHistory.lastElement();
            lastUndo.setFill(gc.getFill());
            lastUndo.setStroke(gc.getStroke());
            lastUndo.setStrokeWidth(gc.getLineWidth());
        }
        System.out.println("redo undoHis"+undoHistory);
        System.out.println("redo redoHis"+redoHistory);
    }
}

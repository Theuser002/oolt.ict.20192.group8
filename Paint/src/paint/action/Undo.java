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

public class Undo extends PaintAction {

    public Undo(GraphicsContext gb, GraphicsContext gc) {
        super(gb, gc);
    }

    public void undoAct(Stack<Shape> undoHistory, Stack<Shape> redoHistory) {
        if (!undoHistory.empty()) {
            gc.clearRect(0, 0, 1834, 1080);
            gb.clearRect(0, 0, 1834, 1080);
            Shape removedShape = undoHistory.lastElement();
            int t = undoInt.lastElement();

            if (removedShape.getClass() == Line.class) {
                Line tempLine = (Line) removedShape;
                tempLine.setFill(gc.getFill());
                tempLine.setStroke(gc.getStroke());
                tempLine.setStrokeWidth(gc.getLineWidth());
                redoHistory.push(new Line(tempLine.getStartX(), tempLine.getStartY(), tempLine.getEndX(), tempLine.getEndY()));
                redoInt.push(2);

            } else if (removedShape.getClass() == Rectangle.class) {
                Rectangle tempRect = (Rectangle) removedShape;
                tempRect.setFill(gc.getFill());
                tempRect.setStroke(gc.getStroke());
                tempRect.setStrokeWidth(gc.getLineWidth());
                redoHistory.push(new Rectangle(tempRect.getX(), tempRect.getY(), tempRect.getWidth(), tempRect.getHeight()));
                if (t == 0) {
                    redoInt.push(0);
                } else if (t == 1) {
                    redoInt.push(1);
                }
            } else if (removedShape.getClass() == Circle.class) {
                Circle tempCirc = (Circle) removedShape;
                tempCirc.setStrokeWidth(gc.getLineWidth());
                tempCirc.setFill(gc.getFill());
                tempCirc.setStroke(gc.getStroke());
                redoHistory.push(new Circle(tempCirc.getCenterX(), tempCirc.getCenterY(), tempCirc.getRadius()));
                redoInt.push(2);
            } else if (removedShape.getClass() == Ellipse.class) {
                Ellipse tempElps = (Ellipse) removedShape;
                tempElps.setFill(gc.getFill());
                tempElps.setStroke(gc.getStroke());
                tempElps.setStrokeWidth(gc.getLineWidth());
                redoHistory.push(new Ellipse(tempElps.getCenterX(), tempElps.getCenterY(), tempElps.getRadiusX(), tempElps.getRadiusY()));
                redoInt.push(2);
            } else if (removedShape.getClass() == Polyline.class) {
                Polyline tempElps = (Polyline) removedShape;
                tempElps.setFill(gc.getFill());
                tempElps.setStroke(gc.getStroke());
                tempElps.setStrokeWidth(gc.getLineWidth());
                redoHistory.push(new Polyline(tempElps.getPoints().get(0), tempElps.getPoints().get(1), tempElps.getPoints().get(2), tempElps.getPoints().get(3), tempElps.getPoints().get(4), tempElps.getPoints().get(5)));
                redoInt.push(2);
            } else if (removedShape.getClass() == QuadCurve.class) {
                redoHistory.push(new QuadCurve());
                redoInt.push(t);
            } else if (removedShape.getClass() == Path.class) {
                redoHistory.push(new Path());
                redoInt.push(t);
            } else{
                redoHistory.push(new Arc());
                redoInt.push(t);
            }
            Shape lastRedo = redoHistory.lastElement();
            lastRedo.setFill(removedShape.getFill());
            lastRedo.setStroke(removedShape.getStroke());
            lastRedo.setStrokeWidth(removedShape.getStrokeWidth());
            undoHistory.pop();
            undoInt.pop();

            for (int i = 0; i < undoHistory.size(); i++) {
                Shape shape = undoHistory.elementAt(i);
                int d = undoInt.elementAt(i);
                System.out.println(shape.getClass()==Arc.class);

                if (shape.getClass() == Line.class) {
                    Line temp = (Line) shape;
                    gc.setLineWidth(temp.getStrokeWidth());
                    gc.setStroke(temp.getStroke());
                    gc.setFill(temp.getFill());
                    gc.strokeLine(temp.getStartX(), temp.getStartY(), temp.getEndX(), temp.getEndY());
                } else if (shape.getClass() == Rectangle.class) {
                    Rectangle temp = (Rectangle) shape;
                    gc.setLineWidth(temp.getStrokeWidth());
                    gc.setStroke(temp.getStroke());
                    gc.setFill(temp.getFill());
                    if (d == 0) {
                        gc.fillRect(temp.getX(), temp.getY(), temp.getWidth(), temp.getHeight());
                        gc.strokeRect(temp.getX(), temp.getY(), temp.getWidth(), temp.getHeight());
                    } else if (d == 1) {
                        gc.fillRoundRect(temp.getX(), temp.getY(), temp.getWidth(), temp.getHeight(), 70f, 70f);
                        gc.strokeRoundRect(temp.getX(), temp.getY(), temp.getWidth(), temp.getHeight(), 70f, 70f);
                    }
                } else if (shape.getClass() == Circle.class) {
                    Circle temp = (Circle) shape;
                    gc.setLineWidth(temp.getStrokeWidth());
                    gc.setStroke(temp.getStroke());
                    gc.setFill(temp.getFill());
                    gc.fillOval(temp.getCenterX(), temp.getCenterY(), temp.getRadius(), temp.getRadius());
                    gc.strokeOval(temp.getCenterX(), temp.getCenterY(), temp.getRadius(), temp.getRadius());
                } else if (shape.getClass() == Ellipse.class) {
                    Ellipse temp = (Ellipse) shape;
                    gc.setLineWidth(temp.getStrokeWidth());
                    gc.setStroke(temp.getStroke());
                    gc.setFill(temp.getFill());
                    gc.fillOval(temp.getCenterX(), temp.getCenterY(), temp.getRadiusX(), temp.getRadiusY());
                    gc.strokeOval(temp.getCenterX(), temp.getCenterY(), temp.getRadiusX(), temp.getRadiusY());
                } else if (shape.getClass() == Polyline.class) {
                    Polyline temp = (Polyline) shape;
                    gc.setLineWidth(temp.getStrokeWidth());
                    gc.setStroke(temp.getStroke());
                    gc.setFill(temp.getFill());
                    gc.fillPolygon(new double[]{temp.getPoints().get(0), temp.getPoints().get(2), temp.getPoints().get(4)},
                            new double[]{temp.getPoints().get(1), temp.getPoints().get(3), temp.getPoints().get(5)}, 3);
                    gc.strokePolygon(new double[]{temp.getPoints().get(0), temp.getPoints().get(2), temp.getPoints().get(4)},
                            new double[]{temp.getPoints().get(1), temp.getPoints().get(3), temp.getPoints().get(5)}, 3);
                } else if (shape.getClass() == QuadCurve.class) {
                    QuadCurve temp = (QuadCurve) shape;
                    int count = arrayBrush[d].size();
                    for (int j = 0; j < count; j = j + 3) {
                        double x = (Double) arrayBrush[d].get(j);
                        double y = (Double) arrayBrush[d].get(j + 1);
                        double size = (Double) arrayBrush[d].get(j + 2);
                        gc.setFill(temp.getFill());
                        gc.fillRoundRect(x, y, size, size, size, size);
                    }
                } else if (shape.getClass() == Path.class) {
                    Path temp = (Path) shape;
                    int count = arrayPen[d].size();
                    gc.beginPath();
                    gc.setLineWidth(temp.getStrokeWidth());
                    for (int j = 0; j < count; j = j + 2) {
                        double x = (Double) arrayPen[d].get(j);
                        double y = (Double) arrayPen[d].get(j + 1);
                        gc.setStroke(temp.getStroke());
                        gc.lineTo(x, y);
                        gc.stroke();
                        gc.setFill(temp.getFill());
                    }
                } else{
                    Arc temp = (Arc) shape;
                    int count = arrayEraser[d].size();
                    gc.beginPath();
                    gc.setLineWidth(temp.getStrokeWidth());
                    for (int j = 0; j < count; j = j + 2) {
                        double x = (Double) arrayEraser[d].get(j);
                        double y = (Double) arrayEraser[d].get(j + 1);
                        gc.setStroke(Color.WHITE);
                        gc.lineTo(x, y);
                        gc.stroke();
                        gc.setFill(temp.getFill());
                    }
                }
            }
        }
        System.out.println("undo undoHis"+undoHistory);
        System.out.println("undo redoHis"+redoHistory);
    }
}

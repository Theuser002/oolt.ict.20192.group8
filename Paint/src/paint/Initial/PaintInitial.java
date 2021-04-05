package paint.Initial;

import java.io.InputStream;

import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class PaintInitial {

    public PaintInitial() {

    }

    public void initialLogo(Button pencil, Button brush, Button circle, Button line, Button eraser, Button undo,
            Button redo, Button rectangle, Button ellipse, Button roundRect, Button triangle) {
        InputStream pencilImgScr = getClass().getResourceAsStream("/paint/Images/pencil1.png");
        InputStream circleImgScr = getClass().getResourceAsStream("/paint/Images/circle1.png");
        InputStream rectangleImgScr = getClass().getResourceAsStream("/paint/Images/rect1.png");
        InputStream brushImgScr = getClass().getResourceAsStream("/paint/Images/brush1.png");
        InputStream lineImgScr = getClass().getResourceAsStream("/paint/Images/line1.png");
        InputStream ellipseImgScr = getClass().getResourceAsStream("/paint/Images/ellipse1.png");
        InputStream eraserImgScr = getClass().getResourceAsStream("/paint/Images/eraser2.png");
        InputStream undoImgScr = getClass().getResourceAsStream("/paint/Images/undo0.png");
        InputStream redoImgScr = getClass().getResourceAsStream("/paint/Images/redo0.png");
        InputStream roundImgScr = getClass().getResourceAsStream("/paint/Images/round-rect1.png");
        InputStream triangleImgScr = getClass().getResourceAsStream("/paint/Images/triangle1.png");

        Image pencilImg = new Image(pencilImgScr, 17, 17, false, true);
        Image circleImg = new Image(circleImgScr, 17, 17, false, true);
        Image rectangleImg = new Image(rectangleImgScr, 17, 17, false, true);
        Image brushImg = new Image(brushImgScr, 17, 17, false, true);
        Image lineImg = new Image(lineImgScr, 17, 17, false, true);
        Image ellipseImg = new Image(ellipseImgScr, 17, 17, false, true);
        Image eraserImg = new Image(eraserImgScr, 17, 17, false, true);
        Image undoImg = new Image(undoImgScr, 15, 15, false, true);
        Image redoImg = new Image(redoImgScr, 15, 15, false, true);
        Image roundImg = new Image(roundImgScr, 15, 15, false, true);
        Image triangleImg = new Image(triangleImgScr, 15, 15, false, true);

        ImageView pencilImgView = new ImageView(pencilImg);
        ImageView circleImgView = new ImageView(circleImg);
        ImageView rectangleImgView = new ImageView(rectangleImg);
        ImageView brushImgView = new ImageView(brushImg);
        ImageView lineImgView = new ImageView(lineImg);
        ImageView ellipseImgView = new ImageView(ellipseImg);
        ImageView eraserImgView = new ImageView(eraserImg);
        ImageView undoImgView = new ImageView(undoImg);
        ImageView redoImgView = new ImageView(redoImg);
        ImageView roundImgView = new ImageView(roundImg);
        ImageView triangleImgView = new ImageView(triangleImg);

        pencil.setGraphic(pencilImgView);
        circle.setGraphic(circleImgView);
        rectangle.setGraphic(rectangleImgView);
        brush.setGraphic(brushImgView);
        line.setGraphic(lineImgView);
        ellipse.setGraphic(ellipseImgView);
        eraser.setGraphic(eraserImgView);
        undo.setGraphic(undoImgView);
        redo.setGraphic(redoImgView);
        roundRect.setGraphic(roundImgView);
        triangle.setGraphic(triangleImgView);
    }

    public void initialColor(AnchorPane panecolor, AnchorPane tilePane, AnchorPane hBox, MenuBar menuBar,
            ColorPicker colorPicker, ColorPicker cpFill) {
        colorPicker.setValue(Color.BLACK);
        cpFill.setValue(Color.TRANSPARENT);
        colorPicker.setCursor(Cursor.HAND);
        cpFill.setCursor(Cursor.HAND);
    }

    public void initOther(Spinner<Integer> sizeTool, Button undo, Button redo) {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 5);
        sizeTool.setValueFactory(valueFactory);
        sizeTool.setEditable(true);
        undo.setCursor(Cursor.HAND);
        redo.setCursor(Cursor.HAND);
    }
}

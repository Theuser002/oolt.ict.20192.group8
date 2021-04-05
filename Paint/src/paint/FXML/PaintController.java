package paint.FXML;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Stack;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.FileChooser;
import javafx.stage.WindowEvent;
import paint.Initial.PaintInitial;
import paint.action.Redo;
import paint.action.Undo;
import paint.file.FileAct;
import paint.tools.BrushTool;
import paint.tools.CircleTool;
import paint.tools.EllipseTool;
import paint.tools.EraserTool;
import paint.tools.LineTool;
import paint.tools.PencilTool;
import paint.tools.RectangleTool;
import paint.tools.RoundRectangleTool;
import paint.tools.TriangleTool;

public class PaintController implements Initializable {

	@FXML
	private Canvas overlay = new Canvas();
	@FXML
	private Canvas canvas = new Canvas();
	@FXML
	private Button pencil;
	@FXML
	private Button brush;
	@FXML
	private Button circle;
	@FXML
	private Button rectangle;
	@FXML
	private Button line;
	@FXML
	private Button ellipse;
	@FXML
	private Button eraser;
	@FXML
	private Button undo;
	@FXML
	private Button redo;
	@FXML
	private Button triangle;
	@FXML
	private Button roundRect;
	@FXML
	private Button labelBtn;
	@FXML
	private AnchorPane panecolor;
	@FXML
	private AnchorPane tilePane;
	@FXML
	private AnchorPane hBox;
	@FXML
	private MenuBar menuBar;
	@FXML
	private MenuItem newFile;
	@FXML
	private MenuItem openFile;
	@FXML
	private MenuItem saveFile;
	@FXML
	private MenuItem exit;
	@FXML
	private MenuItem menuUndo;
	@FXML
	private MenuItem menuRedo;
	@FXML
	private MenuItem menuAbout;
	@FXML
	private ColorPicker colorPicker;
	@FXML
	private ColorPicker cpFill;
	@FXML
	private Spinner<Integer> sizeTool;
	@FXML
	private AnchorPane backgroundPane;
	
	private GraphicsContext gb;
	private GraphicsContext gc;

	boolean brushSelected = false, penSelected = false, circleSelected = false, recSelected = false,
			ellipseSelected = false, lineSelected = false, eraserSelected = false, roundSelected = false,
			triangleSelected = false, textSelected = false;
	Stack<Shape> undoHistory = new Stack<Shape>();
	Stack<Shape> redoHistory = new Stack<Shape>();
	Stack<Integer> undoInt = new Stack<Integer>();
	Stack<Integer> redoInt = new Stack<Integer>();
	double startX, startY, lastX, lastY, oldX, oldY;
	SpinnerValueFactory<Integer> valueFactory;
	ArrayList<?>[] arrayBrush = new ArrayList[100];
	ArrayList<?>[] arrayPen = new ArrayList[100];
	ArrayList<?>[] arrayEraser = new ArrayList[100];

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		PaintInitial init = new PaintInitial();
		init.initialLogo(pencil, brush, circle, line, eraser, undo, redo, rectangle, ellipse, roundRect, triangle);
		init.initialColor(panecolor, tilePane, hBox, menuBar, colorPicker, cpFill);
		init.initOther(sizeTool, undo, redo);
//		canvas.widthProperty().bind(backgroundPane.widthProperty());
//		canvas.heightProperty().bind(backgroundPane.heightProperty());
//		overlay.widthProperty().bind(backgroundPane.widthProperty());
//		overlay.heightProperty().bind(backgroundPane.heightProperty());
		gc = canvas.getGraphicsContext2D();
		gb = overlay.getGraphicsContext2D();

		colorPicker.setOnAction(e -> {
			gc.setStroke(colorPicker.getValue());
		});
		cpFill.setOnAction(e -> {
			gc.setFill(cpFill.getValue());
		});

		for (int i = 0; i < 100; i++) {
			arrayBrush[i] = new ArrayList<Double>();
			arrayPen[i] = new ArrayList<Double>();
			arrayEraser[i] = new ArrayList<Double>();
		}
		// ----- Open file -----
		FileAct file = new FileAct(gc, gb);
		final FileChooser fileChooser = new FileChooser();
		openFile.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));
		openFile.setOnAction(e -> {
			file.openFileAct(fileChooser);
			undoHistory.clear();
			redoHistory.clear();
			undoInt.clear();
			redoInt.clear();
		});

		// ----- Exit -----
		exit.setAccelerator(KeyCombination.keyCombination("Ctrl+W"));
		exit.setOnAction(e -> {
			switch (Notifications.showExitAlert()) {
				case 1:
					System.exit(0);
					break;
				default:
					break;
			}
		});

		// ----- Save file -----
		saveFile.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
		saveFile.setOnAction(e -> {
			file.saveFileAct(canvas);
		});

		// ----- New file -----
		newFile.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));
		newFile.setOnAction(e -> {
			undoHistory.clear();
			redoHistory.clear();
			undoInt.clear();
			redoInt.clear();

			switch (Notifications.showNewFileAlert()) {
				case 1:
					file.saveFileAct(canvas);
				case 2:
					file.newFileAct(canvas, sizeTool, valueFactory);
					break;
				default:
					break;
			}
		});

		// ----- Undo -----
		Undo undoClass = new Undo(gb, gc);
		undoClass.setArray(arrayBrush, arrayPen, arrayEraser);
		undo.setOnAction(e -> {
			undoClass.setInteger(undoInt, redoInt);
			undoClass.undoAct(undoHistory, redoHistory);
		});
		menuUndo.setAccelerator(KeyCombination.keyCombination("Ctrl+Z"));
		menuUndo.setOnAction(e -> {
			undoClass.setInteger(undoInt, redoInt);
			undoClass.undoAct(undoHistory, redoHistory);
		});

		// ----- Redo -----
		Redo redoClass = new Redo(gb, gc);
		redoClass.setArray(arrayBrush, arrayPen, arrayEraser);
		redo.setOnAction(e -> {
			redoClass.setInteger(undoInt, redoInt);
			redoClass.redoAct(undoHistory, redoHistory);
		});
		menuRedo.setAccelerator(KeyCombination.keyCombination("Ctrl+X"));
		menuRedo.setOnAction(e -> {
			redoClass.setInteger(undoInt, redoInt);
			redoClass.redoAct(undoHistory, redoHistory);
		});

		// ----- About -----
		menuAbout.setOnAction(e -> {
			Notifications.showAboutAlert();
		});
	}

	public void setInitial() {
		colorPicker.setValue(Color.BLACK);
		cpFill.setValue(Color.TRANSPARENT);
		valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 5);
		brushSelected = false;
		penSelected = false;
		circleSelected = false;
		recSelected = false;
		ellipseSelected = false;
		lineSelected = false;
		eraserSelected = false;
		roundSelected = false;
		triangleSelected = false;
		textSelected = false;
	}
	
	@FXML
	public void brushToolDraw(ActionEvent event) {
		setInitial();
		brushSelected = true;
		BrushTool brush = new BrushTool(gc, colorPicker, sizeTool, brushSelected);
		brush.setStack(undoHistory, redoHistory);
		brush.setInteger(undoInt, redoInt);
		brush.setStack(arrayBrush);
		canvas.setCursor(new ImageCursor(new Image("paint/Images/brush1.png")));
		canvas.setOnMouseDragged(e -> {
			brush.drag(e.getX(), e.getY());
		});
		canvas.setOnMouseReleased(e -> {
			brush.release();
		});
	}

	@FXML
	public void penToolDraw(ActionEvent event) {
		setInitial();
		penSelected = true;
		PencilTool pen = new PencilTool(gc, colorPicker, sizeTool, penSelected);
		pen.setStack(undoHistory, redoHistory);
		pen.setInteger(undoInt, redoInt);
		pen.setStack(arrayPen);
		canvas.setCursor(new ImageCursor(new Image("paint/Images/pencil1.png")));
		canvas.setOnMousePressed(e -> {
			pen.press(e.getX(), e.getY());
		});
		canvas.setOnMouseDragged(e -> {
			pen.drag(e.getX(), e.getY());
		});
		canvas.setOnMouseReleased(e -> {
			pen.release();
		});
	}

	@FXML
	public void circleToolDraw(ActionEvent event) {
		setInitial();
		circleSelected = true;
		Circle circ = new Circle();
		CircleTool circle = new CircleTool(gc, gb, colorPicker, cpFill, sizeTool, circleSelected);
		canvas.setCursor(Cursor.CROSSHAIR);
		canvas.setOnMousePressed(e -> {
			circle.setProperty(e.getX(), e.getY(), circ);
			circle.toolPressed();
		});
		canvas.setOnMouseDragged(e -> {
			circle.setProperty(e.getX(), e.getY(), circ);
			circle.toolDrag(canvas);
		});
		canvas.setOnMouseReleased(e -> {
			circle.setProperty(e.getX(), e.getY(), circ);
			circle.setInteger(undoInt, redoInt);
			circle.setStack(undoHistory, redoHistory);
			circle.toolRelease();
		});
	}

	@FXML
	public void recToolDraw(ActionEvent event) {
		setInitial();
		recSelected = true;
		Rectangle rect = new Rectangle();
		RectangleTool rectangle = new RectangleTool(gc, gb, colorPicker, cpFill, sizeTool, recSelected);
		canvas.setCursor(Cursor.CROSSHAIR);
		canvas.setOnMousePressed(e -> {
			rectangle.setProperty(e.getX(), e.getY(), rect);
			rectangle.toolPressed();
		});
		canvas.setOnMouseDragged(e -> {
			rectangle.setProperty(e.getX(), e.getY(), rect);
			rectangle.toolDrag(canvas);
		});
		canvas.setOnMouseReleased(e -> {
			rectangle.setProperty(e.getX(), e.getY(), rect);
			rectangle.setInteger(undoInt, redoInt);
			rectangle.setStack(undoHistory, redoHistory);
			rectangle.toolRelease();
		});
	}

	@FXML
	public void ellipseToolDraw(ActionEvent event) {
		setInitial();
		ellipseSelected = true;
		Ellipse elps = new Ellipse();
		EllipseTool ellipse = new EllipseTool(gc, gb, colorPicker, cpFill, sizeTool, ellipseSelected);
		canvas.setCursor(Cursor.CROSSHAIR);
		canvas.setOnMousePressed(e -> {
			ellipse.setProperty(e.getX(), e.getY(), elps);
			ellipse.toolPressed();
		});
		canvas.setOnMouseDragged(e -> {
			ellipse.setProperty(e.getX(), e.getY(), elps);
			ellipse.toolDrag(canvas);
		});
		canvas.setOnMouseReleased(e -> {
			ellipse.setProperty(e.getX(), e.getY(), elps);
			ellipse.setInteger(undoInt, redoInt);
			ellipse.setStack(undoHistory, redoHistory);
			ellipse.toolRelease();
		});
	}

	@FXML
	public void lineToolDraw(ActionEvent event) {
		setInitial();
		lineSelected = true;
		Line line = new Line();
		LineTool lineclass = new LineTool(gc, gb, colorPicker, cpFill, sizeTool, lineSelected);
		canvas.setCursor(Cursor.CROSSHAIR);
		canvas.setOnMousePressed(e -> {
			lineclass.setProperty(e.getX(), e.getY(), line);
			lineclass.toolPressed();
		});
		canvas.setOnMouseDragged(e -> {
			lineclass.setProperty(e.getX(), e.getY(), line);
			lineclass.toolDrag(canvas);
		});
		canvas.setOnMouseReleased(e -> {
			lineclass.setProperty(e.getX(), e.getY(), line);
			lineclass.setInteger(undoInt, redoInt);
			lineclass.setStack(undoHistory, redoHistory);
			lineclass.toolRelease();
		});
	}

	@FXML
	public void eraserToolDraw(ActionEvent event) {
		setInitial();
		eraserSelected = true;
		EraserTool eraser = new EraserTool(gc, gb, colorPicker, cpFill, sizeTool, eraserSelected);
		eraser.setStack(undoHistory, redoHistory);
		eraser.setInteger(undoInt, redoInt);
		eraser.setStack(arrayEraser);
		canvas.setCursor(new ImageCursor(new Image("paint/Images/eraser2.png")));
		canvas.setOnMousePressed(e -> {
			eraser.press(e.getX(), e.getY());
		});
		canvas.setOnMouseDragged(e -> {
			eraser.drag(e.getX(), e.getY());
		});
		canvas.setOnMouseReleased(e -> {
			eraser.release();
		});
	}

	@FXML
	public void roundRectDraw(ActionEvent event) {
		setInitial();
		roundSelected = true;
		Rectangle rect = new Rectangle();
		RoundRectangleTool round = new RoundRectangleTool(gc, gb, colorPicker, cpFill, sizeTool, roundSelected);
		canvas.setCursor(Cursor.CROSSHAIR);
		canvas.setOnMousePressed(e -> {
			round.setProperty(e.getX(), e.getY(), rect);
			round.toolPressed();
		});
		canvas.setOnMouseDragged(e -> {
			round.setProperty(e.getX(), e.getY(), rect);
			round.toolDrag(canvas);
		});
		canvas.setOnMouseReleased(e -> {
			round.setProperty(e.getX(), e.getY(), rect);
			round.setInteger(undoInt, redoInt);
			round.setStack(undoHistory, redoHistory);
			round.toolRelease();
		});
	}

	@FXML
	public void triangleDraw(ActionEvent event) {
		setInitial();
		triangleSelected = true;
		Polyline triangle = new Polyline();
		TriangleTool triangleClass = new TriangleTool(gc, gb, colorPicker, cpFill, sizeTool, triangleSelected);
		canvas.setCursor(Cursor.CROSSHAIR);
		canvas.setOnMousePressed(e -> {
			startX = e.getX();
			startY = e.getY();
			triangleClass.setTriangle(startX, startY, lastX, lastY);
			triangleClass.setShape(triangle);
			triangleClass.toolPressed();
		});
		canvas.setOnMouseDragged(e -> {
			lastX = e.getX();
			lastY = e.getY();
			triangleClass.setTriangle(startX, startY, lastX, lastY);
			triangleClass.setShape(triangle);
			triangleClass.toolDrag(canvas);
		});
		canvas.setOnMouseReleased(e -> {
			lastX = e.getX();
			lastY = e.getY();
			triangleClass.setTriangle(startX, startY, lastX, lastY);
			triangleClass.setInteger(undoInt, redoInt);
			triangleClass.setShape(triangle);
			triangleClass.setStack(undoHistory, redoHistory);
			triangleClass.toolRelease();
		});
	}

	public void labelClick() {
		Notifications.showAboutAlert();
	}

	@FXML
	public void exitApplication(WindowEvent event) {
		switch (Notifications.showExitAlert()) {
			case 1:
				Platform.exit();
				break;
			default:
				event.consume();
				break;
		}
	}

	public PaintController() {
	}
}

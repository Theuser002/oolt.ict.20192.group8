package paint.file;

import javafx.scene.canvas.GraphicsContext;
import paint.PaintApp;

public abstract class PaintFile extends PaintApp {

    public PaintFile(GraphicsContext gb, GraphicsContext gc) {
        super(gb, gc);
    }

    public PaintFile() {
    }
}

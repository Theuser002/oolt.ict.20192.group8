package paint;

import javafx.scene.canvas.GraphicsContext;

public abstract class PaintApp {

    public GraphicsContext gb;
    public GraphicsContext gc;

    public PaintApp(GraphicsContext gb, GraphicsContext gc) {
        this.gb = gb;
        this.gc = gc;
    }

    public PaintApp() {
    }

    public PaintApp(GraphicsContext gc) {
        this.gc = gc;
    }
}

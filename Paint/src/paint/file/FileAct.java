package paint.file;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

public class FileAct extends PaintFile {

    public FileAct(GraphicsContext gc, GraphicsContext gb) {
        super(gc, gb);
    }

    public void newFileAct(Canvas canvas, Spinner<Integer> sizeTool, SpinnerValueFactory<Integer> valueFactory) {
        gc.setFill(Color.WHITE);
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gb.setFill(Color.WHITE);
        gb.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        sizeTool.setValueFactory(valueFactory);
    }

    public void openFileAct(FileChooser fileChooser) {
        setExtFilters(fileChooser);
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            try {
                InputStream io = new FileInputStream(file);
                Image img = new Image(io);
                gc.drawImage(img, 0, 0);
            } catch (IOException ex) {
                System.out.println("Error!");
            }
        }
    }

    private void setExtFilters(FileChooser chooser) {
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
    }

    public void saveFileAct(Canvas canvas) {
        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilter
                = new FileChooser.ExtensionFilter("PNG Files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        File file = fileChooser.showSaveDialog(new Stage());

        if (file != null) {
            try {
                WritableImage writableImage = new WritableImage(1000, 674);
                canvas.snapshot(null, writableImage);
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                ImageIO.write(renderedImage, "png", file);
            } catch (IOException ex) {
                Logger.getLogger(PaintFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

package backend.academy.singlethreading;

import backend.academy.data.image.Format;
import backend.academy.data.image.Fractal;
import backend.academy.data.image.ImageSettings;
import backend.academy.service.Renderer;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class SingleThreadRenderer extends JFrame implements Renderer {
    private transient Fractal fractal;

    public SingleThreadRenderer(ImageSettings settings) {
        setTitle("Fractal Renderer");
        setSize(settings.widthRes(), settings.heightRes());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void update(Fractal fractal) {
        this.fractal = fractal;
        repaint();
    }

    @Override
    public void saveAs(Fractal fractal, String path, String name, Format format) {
        BufferedImage image = fractal.toBufferedImage();
        String fullPath = path + "/" + name + "." + format.toString().toLowerCase();
        try {
            Files.createDirectories(Path.of(path));
            File output = new File(fullPath);
            if (!ImageIO.write(image, format.toString(), output)) {
                log.error("Error saving fractal image {}", fullPath);
            }
        } catch (IOException e) {
            log.error("Error saving fractal image {} {}", fullPath, e);
        }
    }

    private void drawFractal(Graphics g) {
        BufferedImage image = fractal.toBufferedImage();
        g.drawImage(image, 0, 0, null);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (fractal != null) {
            drawFractal(g);
        }
    }
}

package backend.academy.singlethreading;

import backend.academy.data.image.Frame;
import backend.academy.data.image.ImageSettings;
import backend.academy.data.image.Pixel;
import backend.academy.data.image.RGB;
import backend.academy.service.Renderer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.WindowConstants;


public class SingleThreadRenderer extends JFrame implements Renderer {
    private transient Frame frame;

    private transient BufferedImage image;

    public SingleThreadRenderer(ImageSettings settings) {
        setTitle("Fractal Renderer");
        setSize(settings.widthRes(), settings.heightRes());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void update(Frame frame) {
        this.frame = frame;
        if (image == null || image.getWidth() != frame.width() || image.getHeight() != frame.height()) {
            image = new BufferedImage(frame.width(), frame.height(), BufferedImage.TYPE_INT_RGB);
        }
        repaint();
    }

    private void drawFractal(Graphics g) {
        for (int y = 0; y < frame.height(); y++) {
            for (int x = 0; x < frame.width(); x++) {
                Pixel current = frame.getPixel(x, y);
                RGB rgb = current.rgb();
                int color = new Color(rgb.red(), rgb.green(), rgb.blue()).getRGB();
                image.setRGB(x, y, color);
            }
        }

        g.drawImage(image, 0, 0, null);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (frame != null) {
            drawFractal(g);
        }
    }
}

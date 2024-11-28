package backend.academy.service;

import backend.academy.data.image.Format;
import backend.academy.data.image.Frame;

public interface Renderer {
    void update(Frame frame);
    void saveAs(Frame frame, String path, String name, Format format);
}

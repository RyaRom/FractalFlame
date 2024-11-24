package backend.academy.data;

public record ColorRGB(int red, int green, int blue) {
    public ColorRGB(int red, int green, int blue) {
        this.red = Math.clamp(red, 0, 255);
        this.green = Math.clamp(green, 0, 255);
        this.blue = Math.clamp(blue, 0, 255);
    }

    public ColorRGB blend(ColorRGB other) {
        return new ColorRGB(
            (this.red + other.red()) / 2,
            (this.green + other.green()) / 2,
            (this.blue + other.blue()) / 2
        );
    }
}

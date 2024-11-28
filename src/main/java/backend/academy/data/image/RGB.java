package backend.academy.data.image;

public record RGB(int red, int green, int blue) {
    public RGB(int red, int green, int blue) {
        this.red = Math.clamp(red, 0, 255);
        this.green = Math.clamp(green, 0, 255);
        this.blue = Math.clamp(blue, 0, 255);
    }

    public RGB blend(RGB other) {
        return new RGB(
            (this.red + other.red()) / 2,
            (this.green + other.green()) / 2,
            (this.blue + other.blue()) / 2
        );
    }

    public int toRGBInt() {
        return (red << 16) | (green << 8) | blue;
    }

    public RGB gammaCorrected(double gamma, double normalized) {
        return new RGB(
            (int) (red * Math.pow(normalized, 1.0 / gamma)),
            (int) (green * Math.pow(normalized, 1.0 / gamma)),
            (int) (blue * Math.pow(normalized, 1.0 / gamma))
        );
    }
}

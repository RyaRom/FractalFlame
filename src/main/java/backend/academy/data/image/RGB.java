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
}

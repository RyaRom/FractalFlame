package backend.academy.data.image;

public record RGB(int red, int green, int blue) {
    public static final RGB RED = new RGB(255, 0, 0);

    public static final RGB GREEN = new RGB(0, 255, 0);

    public static final RGB BLUE = new RGB(0, 0, 255);

    public static final RGB YELLOW = new RGB(255, 255, 0);

    public static final RGB CYAN = new RGB(0, 255, 255);

    public static final RGB MAGENTA = new RGB(255, 0, 255);

    public static final RGB ORANGE = new RGB(255, 165, 0);

    public static final RGB PURPLE = new RGB(128, 0, 128);

    public static final RGB PINK = new RGB(255, 192, 203);

    public static final RGB LIME = new RGB(0, 255, 0);

    public static final RGB TEAL = new RGB(0, 128, 128);

    public static final RGB NAVY = new RGB(0, 0, 128);

    public static final RGB OLIVE = new RGB(128, 128, 0);

    public static final RGB MAROON = new RGB(128, 0, 0);

    public static final RGB SILVER = new RGB(192, 192, 192);

    public static final RGB GRAY = new RGB(128, 128, 128);

    public static final RGB WHITE = new RGB(255, 255, 255);

    public static final RGB BLACK = new RGB(0, 0, 0);

    public static final RGB AQUA = new RGB(0, 255, 255);

    public static final RGB AZURE = new RGB(240, 255, 255);

    public static final RGB BEIGE = new RGB(245, 245, 220);

    public static final RGB CORAL = new RGB(255, 127, 80);

    public static final RGB CRIMSON = new RGB(220, 20, 60);

    public static final RGB GOLD = new RGB(255, 215, 0);

    public static final RGB INDIGO = new RGB(75, 0, 130);

    public static final RGB IVORY = new RGB(255, 255, 240);

    public static final RGB KHAKI = new RGB(240, 230, 140);

    public static final RGB LAVENDER = new RGB(230, 230, 250);

    public static final RGB MINT = new RGB(189, 252, 201);

    public static final RGB ORCHID = new RGB(218, 112, 214);

    public static final RGB PLUM = new RGB(221, 160, 221);

    public static final RGB SALMON = new RGB(250, 128, 114);

    public static final RGB TAN = new RGB(210, 180, 140);

    public static final RGB VIOLET = new RGB(238, 130, 238);

    public static final RGB WHEAT = new RGB(245, 222, 179);

    public static final RGB CHARTREUSE = new RGB(127, 255, 0);

    public static final RGB EMERALD = new RGB(80, 200, 120);

    public static final RGB FUCHSIA = new RGB(255, 0, 255);

    public static final RGB HOT_PINK = new RGB(255, 105, 180);

    public static final RGB JET = new RGB(52, 52, 52);

    public static final RGB LEMON = new RGB(255, 250, 205);

    public static final RGB MOSS = new RGB(138, 154, 91);

    public static final RGB PEACH = new RGB(255, 218, 185);

    public static final RGB RUST = new RGB(183, 65, 14);

    public static final RGB SAGE = new RGB(188, 184, 138);

    public static final RGB UMBER = new RGB(99, 81, 71);

    public static final RGB VANILLA = new RGB(243, 229, 171);

    public static final RGB MUSTARD = new RGB(255, 219, 88);

    public static final RGB PERIWINKLE = new RGB(204, 204, 255);

    public static final RGB ROSE = new RGB(255, 0, 127);

    public static final RGB SAPPHIRE = new RGB(15, 82, 186);

    public static final RGB TANGERINE = new RGB(242, 133, 0);

    public static final RGB ULTRAMARINE = new RGB(18, 10, 143);

    public static final RGB WISTERIA = new RGB(201, 160, 220);

    public static final RGB ZUCCHINI = new RGB(138, 145, 135);

    public RGB(int red, int green, int blue) {
        this.red = Math.clamp(red, 0, 255);
        this.green = Math.clamp(green, 0, 255);
        this.blue = Math.clamp(blue, 0, 255);
    }

    public RGB(double red, double green, double blue) {
        this(
            (int) (red * 255),
            (int) (green * 255),
            (int) (blue * 255)
        );
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

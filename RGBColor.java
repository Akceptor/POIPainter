public class RGBColor {
    private byte r, g, b;

    public RGBColor(int r, int g, int b) {
        super();
        this.r = (byte) r;
        this.g = (byte) g;
        this.b = (byte) b;
    }

    public byte getR() {
        return r;
    }

    public void setR(byte r) {
        this.r = r;
    }

    public byte getG() {
        return g;
    }

    public void setG(byte g) {
        this.g = g;
    }

    public byte getB() {
        return b;
    }

    public void setB(byte b) {
        this.b = b;
    }

    public String toString() {
        return "R " + this.r + ", G " + this.g + ", B " + this.b;
    }
}

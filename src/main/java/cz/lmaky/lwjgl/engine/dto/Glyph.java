package cz.lmaky.lwjgl.engine.dto;

public class Glyph {

    private final int width;
    private final int height;
    private final int x;
    private final int y;

    public Glyph(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

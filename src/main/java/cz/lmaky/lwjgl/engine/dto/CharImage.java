package cz.lmaky.lwjgl.engine.dto;

/**
 * @author lukas.marek
 */
public class CharImage {

    private final Glyph glyph;

    private final int width;
    private final int height;
    private final int ascent;

    public CharImage(Glyph glyph, int width, int height, int ascent) {
        this.glyph = glyph;
        this.width = width;
        this.height = height;
        this.ascent = ascent;
    }

    public Glyph getGlyph() {
        return glyph;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getAscent() {
        return ascent;
    }
}

package cz.lmaky.lwjgl.engine.dto;

import java.awt.image.BufferedImage;

/**
 * @author lukas.marek
 */
public class CharImage {

    private final BufferedImage charImage;
    private final Glyph glyph;

    public CharImage(BufferedImage charImage, Glyph glyph) {
        this.charImage = charImage;
        this.glyph = glyph;
    }

    public BufferedImage getCharImage() {
        return charImage;
    }

    public Glyph getGlyph() {
        return glyph;
    }
}

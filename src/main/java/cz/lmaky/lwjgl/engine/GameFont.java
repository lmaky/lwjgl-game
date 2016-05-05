package cz.lmaky.lwjgl.engine;

import com.idrsolutions.image.png.PngEncoder;
import cz.lmaky.lwjgl.engine.dto.CharImage;
import cz.lmaky.lwjgl.engine.dto.Glyph;
import cz.lmaky.lwjgl.engine.utils.TextureUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static java.awt.Font.TRUETYPE_FONT;

/**
 * @author lukas.marek
 */
public class GameFont {

    private Map<Character, Glyph> glyphs = new HashMap<>();

    private Texture fontTexture;

    public GameFont(String fontName, float size) {
        Font font;
        try {
            InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fontName);
            font = Font.createFont(TRUETYPE_FONT, inputStream).deriveFont(size);
        } catch (IOException | FontFormatException e) {
            throw new IllegalStateException("Font is not loaded.", e);
        }
        generateFontTexture(font);
    }

    public GameFont(Font font) {
        generateFontTexture(font);
    }

    public float getFontTextureWidth() {
        if (fontTexture == null) {
            throw new IllegalStateException("Texture is null.");
        }
        return fontTexture.getTextureWidth();
    }

    public int getFontTextureId() {
        if (fontTexture == null) {
            throw new IllegalStateException("Texture is null.");
        }
        return fontTexture.getTextureId();
    }

    public Map<Character, Glyph> getGlyphs() {
        return glyphs;
    }

    private void generateFontTexture(Font font) {
        Map<Character, CharImage> charImages = new HashMap<>();

        int imageWidth = 0;
        int imageHeight = 0;

        boolean antiAlias = false;

        /* Start at char #32, because ASCII 0 to 31 are just control codes */
        for (int i = 32; i < 256; i++) {
            if (i == 127) {/* ASCII 127 is the DEL control code, so we can skip it */
                continue;
            }
            char c = (char) i;
            CharImage charImage = createCharImage(font, imageWidth, c, antiAlias);
            if (charImage == null) {
                /* If char image is null that font does not contain the char */
                continue;
            }

            /* Create glyph and draw char on image */
            charImages.put(c, charImage);
            imageWidth += charImage.getWidth();
            imageHeight = Math.max(imageHeight, charImage.getHeight());
        }

        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB_PRE);

        Graphics2D g = image.createGraphics();

        if (antiAlias) {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
        g.setFont(font);
        g.setPaint(Color.WHITE);

        /* Create BufferedImage from all character images */
        for (Map.Entry<Character, CharImage> entry : charImages.entrySet()) {

            CharImage charImage = entry.getValue();
            Glyph glyph = charImage.getGlyph();

            g.drawString(String.valueOf(entry.getKey()), glyph.getX(), charImage.getAscent());
            glyphs.put(entry.getKey(), glyph);
        }
        g.dispose();

        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            PngEncoder encoder = new PngEncoder();
            encoder.write(image, os);
            int textureId = TextureUtils.loadTexture(new ByteArrayInputStream(os.toByteArray()));
            fontTexture = new Texture(textureId, imageWidth, imageHeight);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private CharImage createCharImage(Font font, int imagePosition, char c, boolean antiAlias) {
        /* Creating temporary image to extract character size */
        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        if (antiAlias) {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
        g.setFont(font);
        FontMetrics metrics = g.getFontMetrics();
        g.dispose();

        /* Get char charWidth and charHeight */
        int charWidth = metrics.charWidth(c);
        int charHeight = metrics.getHeight();

        /* Check if charWidth is 0 */
        if (charWidth == 0) {
            return null;
        }

        /* Create glyph and CharImage */
        Glyph glyph = new Glyph(imagePosition, 0, charWidth, charHeight);
        return new CharImage(glyph, charWidth, charHeight, metrics.getAscent());
    }
}

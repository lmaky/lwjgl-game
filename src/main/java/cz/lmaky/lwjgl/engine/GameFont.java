package cz.lmaky.lwjgl.engine;

import com.idrsolutions.image.png.PngEncoder;
import cz.lmaky.lwjgl.engine.dto.CharImage;
import cz.lmaky.lwjgl.engine.dto.Glyph;
import cz.lmaky.lwjgl.engine.utils.TextureUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lukas.marek
 */
public class GameFont {

    Map<Character, Glyph> glyphs = new HashMap<>();

    private Texture fontTexture;

    public GameFont(Font font) {
        generateFontTexture(font);
    }

    private void generateFontTexture(Font font) {
        Map<Character, CharImage> charImages = new HashMap<>();

        int imageWidth = 0;
        int imageHeight = 0;

        boolean antiAlias = true;

        /* Start at char #32, because ASCII 0 to 31 are just control codes */
        for (int i = 32; i < 256; i++) {
            if (i == 127) {/* ASCII 127 is the DEL control code, so we can skip it */
                continue;
            }
            char c = (char) i;
            BufferedImage charImage = createCharImage(font, c, antiAlias);
            if (charImage == null) {
                /* If char image is null that font does not contain the char */
                continue;
            }

            int charWidth = charImage.getWidth();
            int charHeight = charImage.getHeight();

            /* Create glyph and draw char on image */
            Glyph ch = new Glyph(imageWidth, 0, charWidth, charHeight);
            charImages.put(c, new CharImage(charImage, ch));
            imageWidth += charWidth;
            imageHeight = Math.max(imageHeight, charHeight);
        }

        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = image.createGraphics();

        g.clearRect(0, 0, imageWidth, imageHeight);

        /* Create BufferedImage from all character images */
        for (Map.Entry<Character, CharImage> entry : charImages.entrySet()) {
            BufferedImage charImage = entry.getValue().getCharImage();
            Glyph glyph = entry.getValue().getGlyph();
            g.drawImage(charImage, glyph.getX() , 0, null);
            glyphs.put(entry.getKey(), glyph);
        }

        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            PngEncoder encoder = new PngEncoder();
            encoder.write(image, os);
            int textureId = TextureUtils.loadTexture(new ByteArrayInputStream(os.toByteArray()));
            fontTexture = new Texture(textureId, imageWidth, imageHeight);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private BufferedImage createCharImage(Font font, char c, boolean antiAlias) {
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

        /* Create image for holding the char */
        image = new BufferedImage(charWidth, charHeight, BufferedImage.TYPE_INT_ARGB);
        g = image.createGraphics();
        if (antiAlias) {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
        g.setFont(font);
        g.setPaint(Color.WHITE);
//        g.setPaint(Color.black);
        g.drawString(String.valueOf(c), 0, metrics.getAscent());
        g.dispose();
        return image;
    }

    public Texture getFontTexture() {
        return fontTexture;
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
}

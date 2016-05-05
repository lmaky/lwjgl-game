package cz.lmaky.lwjgl.engine.objects.impl;

import cz.lmaky.lwjgl.engine.Mesh;
import cz.lmaky.lwjgl.engine.dto.Glyph;
import cz.lmaky.lwjgl.engine.objects.GraphicsObject;
import cz.lmaky.lwjgl.engine.GameFont;
import gnu.trove.list.TFloatList;
import gnu.trove.list.TIntList;
import gnu.trove.list.array.TFloatArrayList;
import gnu.trove.list.array.TIntArrayList;
import org.joml.Matrix4f;

/**
 * @author lukas.marek
 */
public class Text extends GraphicsObject {

    private final String text;
    private final GameFont gameFont;

    public Text(GameFont gameFont, String text) {
        this.gameFont = gameFont;
        this.text = text;
    }

    @Override
    public void init() {
        int len = text.length();

        TFloatList verticles =  new TFloatArrayList(12 * len);
        TFloatList textCoords =  new TFloatArrayList(8 * len);
        TIntList indices =  new TIntArrayList(6 * len);

        float textureWidth = gameFont.getFontTextureWidth();
        float position = 0.0f;

        for (int i=0; i<len; i++) {
            Glyph glyph = gameFont.getGlyphs().get(text.charAt(i));

            add(verticles, position, 0.0f, 0.0f);
            add(verticles, position, glyph.getHeight(), 0.0f);
            add(verticles, glyph.getWidth() + position, glyph.getHeight(), 0.0f);
            add(verticles, glyph.getWidth() + position, 0.0f, 0.0f);

            position += glyph.getWidth();

            float textCoordX1 = glyph.getX() / textureWidth;
            float textCoordX2 = (glyph.getX() + glyph.getWidth()) / textureWidth;

            add(textCoords, textCoordX1, 0.0f);
            add(textCoords, textCoordX1, 1.0f);
            add(textCoords, textCoordX2, 1.0f);
            add(textCoords, textCoordX2, 0.0f);

            int firstIndice = i*4;
            indices.add(firstIndice + 0);
            indices.add(firstIndice + 1);
            indices.add(firstIndice + 3);
            indices.add(firstIndice + 3);
            indices.add(firstIndice + 1);
            indices.add(firstIndice + 2);
        }
        mesh = new Mesh(verticles.toArray(), textCoords.toArray(), indices.toArray());
        mesh.setTextureId(gameFont.getFontTextureId());
    }

    @Override
    public Matrix4f getModelMatrix() {
        Matrix4f model = new Matrix4f();
        model.translation(0.0f, 100.0f, 0.0f);
        return model;
    }
}

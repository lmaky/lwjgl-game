package cz.lmaky.lwjgl.game;

import cz.lmaky.lwjgl.engine.objects.impl.AbstractCube;
import gnu.trove.list.TFloatList;
import gnu.trove.list.TIntList;
import gnu.trove.list.array.TFloatArrayList;
import gnu.trove.list.array.TIntArrayList;

/**
 * @author lukas.marek
 */
public class Cube extends AbstractCube {

    public Cube() {
        rotationXYZ.set(0.02f, 0.02f, 0.02f);
        scale = 0.8f;
    }

    @Override
    protected float[] getVerticles() {
        TFloatList verticles =  new TFloatArrayList(72);
        //V0
        add(verticles, -0.5f, 0.5f, 0.5f);
        //V1
        add(verticles, -0.5f, -0.5f, 0.5f);
        //V2
        add(verticles, 0.5f, -0.5f, 0.5f);
        //V3
        add(verticles, 0.5f, 0.5f, 0.5f);

        //V4
        add(verticles, -0.5f, 0.5f, -0.5f);
        //V5
        add(verticles, -0.5f, -0.5f, -0.5f);
        //V6
        add(verticles, 0.5f, -0.5f, -0.5f);
        //V7
        add(verticles, 0.5f, 0.5f, -0.5f);


        //V3 - 8
        add(verticles, 0.5f, 0.5f, 0.5f);
        //V2 - 9
        add(verticles, 0.5f, -0.5f, 0.5f);
        //V6 - 10
        add(verticles, 0.5f, -0.5f, -0.5f);
        //V7 - 11
        add(verticles, 0.5f, 0.5f, -0.5f);

        //V4 - 12
        add(verticles, -0.5f, 0.5f, -0.5f);
        //V5 - 13
        add(verticles, -0.5f, -0.5f, -0.5f);
        //V1 - 14
        add(verticles, -0.5f, -0.5f, 0.5f);
        //V0 - 15
        add(verticles, -0.5f, 0.5f, 0.5f);

        //V4 - 16
        add(verticles, -0.5f, 0.5f, -0.5f);
        //V0 - 17
        add(verticles, -0.5f, 0.5f, 0.5f);
        //V3 - 18
        add(verticles, 0.5f, 0.5f, 0.5f);
        //V7 - 19
        add(verticles, 0.5f, 0.5f, -0.5f);

        //V1 - 20
        add(verticles, -0.5f, -0.5f, 0.5f);
        //V5 - 21
        add(verticles, -0.5f, -0.5f, -0.5f);
        //V6 - 22
        add(verticles, 0.5f, -0.5f, -0.5f);
        //V2 - 23
        add(verticles, 0.5f, -0.5f, 0.5f);

        return verticles.toArray();
    }

    @Override
    protected float[] getTextCoords() {
        TFloatList textCoords =  new TFloatArrayList(42);

        add(textCoords, 0.0f, 0.0f);
        add(textCoords, 0.0f, 0.5f);
        add(textCoords, 0.5f, 0.5f);
        add(textCoords, 0.5f, 0.0f);

        add(textCoords, 0.0f, 0.0f);
        add(textCoords, 0.0f, 0.5f);
        add(textCoords, 0.5f, 0.5f);
        add(textCoords, 0.5f, 0.0f);

        add(textCoords, 0.0f, 0.0f);
        add(textCoords, 0.0f, 0.5f);
        add(textCoords, 0.5f, 0.5f);
        add(textCoords, 0.5f, 0.0f);

        add(textCoords, 0.0f, 0.0f);
        add(textCoords, 0.0f, 0.5f);
        add(textCoords, 0.5f, 0.5f);
        add(textCoords, 0.5f, 0.0f);

        add(textCoords, 0.0f, 0.5f);
        add(textCoords, 0.0f, 1.0f);
        add(textCoords, 0.5f, 1.0f);
        add(textCoords, 0.5f, 0.5f);

        add(textCoords, 0.5f, 0.0f);
        add(textCoords, 0.5f, 0.5f);
        add(textCoords, 1.0f, 0.5f);
        add(textCoords, 1.0f, 0.0f);

        return textCoords.toArray();
    }

    @Override
    protected int[] getIndices() {
        TIntList indices =  new TIntArrayList(36);
        //front
        add(indices, 0, 1, 3);
        add(indices, 3, 1, 2);

        //top
        add(indices, 16, 17, 19);
        add(indices, 19, 17, 18);

        //right
        add(indices, 8, 9, 11);
        add(indices, 11, 9, 10);

        //left
        add(indices, 12, 13, 15);
        add(indices, 15, 13, 14);

        //back
        add(indices, 7, 6, 4);
        add(indices, 4, 6, 5);

        //bottom
        add(indices, 20, 21, 23);
        add(indices, 23, 21, 22);

        return indices.toArray();
    }

    @Override
    protected String getTextureName() {
        return "image/grassblock.png";
    }
}

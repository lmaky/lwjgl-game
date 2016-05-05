package cz.lmaky.lwjgl.engine.objects.impl;

import cz.lmaky.lwjgl.engine.Mesh;
import cz.lmaky.lwjgl.engine.objects.GraphicsObject;
import cz.lmaky.lwjgl.engine.utils.TextureUtils;
import org.joml.Matrix4f;
import org.joml.Vector3f;

/**
 * @author lukas.marek
 */
public abstract class AbstractCube extends GraphicsObject {

    protected Vector3f rotation = new Vector3f(0.0f, 0.0f, 0.0f);
    protected Vector3f rotationXYZ = new Vector3f(0.0f, 0.0f, 0.0f);

    protected Vector3f translation = new Vector3f(0.0f, 0.0f, -3.0f);
    protected float scale = 1f;

    protected abstract float[] getVerticles();
    protected abstract float[] getTextCoords();
    protected abstract int[] getIndices();

    protected String getTextureName() {
        return null;
    }

    @Override
    public void init() {
        mesh = new Mesh(getVerticles(), getTextCoords(), getIndices());
        String textureName = getTextureName();
        if (textureName != null) {
            mesh.setTextureId(TextureUtils.loadTexture(textureName));
        }
    }

    @Override
    public Matrix4f getModelMatrix() {
        Matrix4f model = new Matrix4f();
        model.translation(translation);
        rotation.x += rotationXYZ.x;
        rotation.y += rotationXYZ.y;
        rotation.z += rotationXYZ.z;
        model.rotateXYZ(rotation.x, rotation.y, rotation.z);
        model.scale(scale);
        return model;
    }
}

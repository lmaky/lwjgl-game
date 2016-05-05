package cz.lmaky.lwjgl.engine.objects;

import cz.lmaky.lwjgl.engine.Mesh;
import gnu.trove.list.TFloatList;
import gnu.trove.list.TIntList;
import org.joml.Matrix4f;

/**
 * Created by lmaky on 10/20/15.
 */
public abstract class GraphicsObject {

    protected Mesh mesh;

    public abstract void init();

    public abstract Matrix4f getModelMatrix();

    public void render() {
        if (mesh != null) {
            mesh.render();
        }
    }

    protected void add(TFloatList list, float x, float y) {
        list.add(x);
        list.add(y);
    }

    protected void add(TFloatList list, float x, float y, float z) {
        list.add(x);
        list.add(y);
        list.add(z);
    }

    protected void add(TIntList list, int x, int y) {
        list.add(x);
        list.add(y);
    }

    protected void add(TIntList list, int x, int y, int z) {
        list.add(x);
        list.add(y);
        list.add(z);
    }
}

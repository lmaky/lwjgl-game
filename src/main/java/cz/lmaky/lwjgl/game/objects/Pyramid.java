package cz.lmaky.lwjgl.game.objects;

/**
 * Created by lmaky on 10/19/15.
 */
public class Pyramid {

//        fPyramid[0] = 0.0f; fPyramid[1] = 5.0f; fPyramid[2] = 0.0f;
//        fPyramid[3] = -3.0f; fPyramid[4] = 0.0f; fPyramid[5] = 3.0f;
//        fPyramid[6] = 3.0f; fPyramid[7] = 0.0f; fPyramid[8] = 3.0f;
//
//        // Back face
//        fPyramid[9] = 0.0f; fPyramid[10] = 5.0f; fPyramid[11] = 0.0f;
//        fPyramid[12] = 3.0f; fPyramid[13] = 0.0f; fPyramid[14] = -3.0f;
//        fPyramid[15] = -3.0f; fPyramid[16] = 0.0f; fPyramid[17] = -3.0f;
//
//        // Left face
//        fPyramid[18] = 0.0f; fPyramid[19] = 5.0f; fPyramid[20] = 0.0f;
//        fPyramid[21] = -3.0f; fPyramid[22] = 0.0f; fPyramid[23] = -3.0f;
//        fPyramid[24] = -3.0f; fPyramid[25] = 0.0f; fPyramid[26] = 3.0f;
//
//        // Right face
//        fPyramid[27] = 0.0f; fPyramid[28] = 5.0f; fPyramid[29] = 0.0f;
//        fPyramid[30] = 3.0f; fPyramid[31] = 0.0f; fPyramid[32] = 3.0f;
//        fPyramid[33] = 3.0f; fPyramid[34] = 0.0f; fPyramid[35] = -3.0f;
//
//        for(int i=0; i<4; i++) {
//            fPyramidColor[i*9] = 1.0f; fPyramidColor[i*9+1] = 0.0f; fPyramidColor[i*9+2] = 0.0f;
//            if(i < 2)
//            {
//                fPyramidColor[i*9+1] = 0.0f; fPyramidColor[i*9+4] = 1.0f; fPyramidColor[i*9+5] = 0.0f;
//                fPyramidColor[i*9+2] = 0.0f; fPyramidColor[i*9+7] = 0.0f; fPyramidColor[i*9+8] = 1.0f;
//            }
//            else
//            {
//                fPyramidColor[i*9+2] = 0.0f; fPyramidColor[i*9+7] = 1.0f; fPyramidColor[i*9+8] = 0.0f;
//                fPyramidColor[i*9+1] = 0.0f; fPyramidColor[i*9+4] = 0.0f; fPyramidColor[i*9+5] = 1.0f;
//            }
//        }
}


//        Matrix4f rotate = Matrix4f.rotate(10, 0.0f, 1.0f, 0.0f);
//        Matrix4f translate = Matrix4f.translate(0.0f, 0.0f, -2.0f);
//        Matrix4f model = scale.multiply(rotate).multiply(translate);
//        Matrix4f model = Matrix4f.multiply(scale, rotate, translate);




        /* Set view matrix to identity matrix */
//        Matrix4f view = Matrix4f.scale(0.5f, 0.5f, 0.5f);
//            Matrix4f view = Matrix4f.rotate(angle, 0.0f, 1.0f, 0.0f);

//        Matrix4f rotate2 = Matrix4f.rotate(80, 0.0f, 1.0f, 0.0f);
//        Matrix4f scale = Matrix4f.scale(0.5f, 0.5f, 0.5f);
//        Matrix4f view = rotate2.multiply(scale);
//        Matrix4f view = rotate.multiply(rotate2).multiply(scale);
//        Matrix4f translate = Matrix4f.translate(0.0f, -0.5f, 0.0f);
//        Matrix4f rotate2 = Matrix4f.rotate(-45, 1.0f, 0.0f, 0.0f);
//        Matrix4f translate2 = Matrix4f.translate(0.0f, 0.0f, 0.8f);
//        Matrix4f view = translate.multiply(rotate2).multiply(translate2);

//        /* Set projection matrix to an orthographic projection */
//            Matrix4f perspective = Matrix4f.perspective(45.0f, 16.0f / 9.0f, 0.1f, 100f);
package edu.wuwang.opengl.mUtils.view;
import java.io.IOException;
import java.io.InputStream;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLUtils;
import android.opengl.Matrix;
import android.util.Log;
/**
 * Created by Administrator on 2017/12/28.
 */

public class GLRenderer implements Renderer {
    public static float[] projMatrix = new float[16];// 投影
    public static float[] viewMatrix = new float[16];// 相机
    public static float[] mViewPjMatrix;// 总变换矩阵
    public static float[] matrixs = new float[16];
    public static int textureId = -1;
    Context context;
    MyDrawModel drawModel;

    public void setContext(Context context) {
        this.context = context;
    }

    public GLRenderer() {
    }

    @Override
    public void onDrawFrame(GL10 arg0) {
        GLES30.glClear( GLES30.GL_DEPTH_BUFFER_BIT | GLES30.GL_COLOR_BUFFER_BIT);
        Log.e("", "textureId:" + textureId);
        drawModel.drawFrame(textureId);
    }

    @Override
    public void onSurfaceChanged(GL10 arg0, int w, int h) {
        GLES30.glViewport(0, 0, w, h);
        float ratio = (float) w / h;
        Matrix.frustumM(projMatrix, 0, -ratio, ratio, -1, 1, 1, 10);//投影矩阵设置
        Matrix.setLookAtM(viewMatrix, 0, 0, 0, 3, 0, 0, 0, 0.0f, 1.0f, 0.0f);//摄像机坐标设置
    }

    @Override
    public void onSurfaceCreated(GL10 g, EGLConfig eglConfig) {
        GLES30.glClearColor(0.5f,0.5f,0.5f, 1.0f);
        GLES30.glEnable(GLES30.GL_DEPTH_TEST);
        InputStream ins = null;
        drawModel = new MyDrawModel();
        drawModel.init();
        try {
            ins = context.getAssets().open("girl4.jpg");
            textureId = createTexture(ins);
            Log.e("", "textureId:" + textureId);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ins != null) {
                    ins.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        GLES30.glDisable(GLES30.GL_CULL_FACE);
    }

    public static int createTexture(InputStream ins) {
        int[] textures = new int[1];
        GLES30.glGenTextures(1, textures, 0);
        int textureId = textures[0];
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureId);
        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER,GLES30.GL_NEAREST);
        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D,GLES30.GL_TEXTURE_MAG_FILTER,GLES30.GL_LINEAR);
        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_S,GLES30.GL_CLAMP_TO_EDGE);
        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_T,GLES30.GL_CLAMP_TO_EDGE);
        //上面是纹理贴图的取样方式，包括拉伸方式，取临近值和线性值
        Bitmap bitmap = BitmapFactory.decodeStream(ins);
        GLUtils.texImage2D(GLES30.GL_TEXTURE_2D, 0, bitmap, 0);//让图片和纹理关联起来，加载到OpenGl空间中
        Log.d("OPENGL","bitmap:" + bitmap);
        bitmap.recycle();//不需要，可以释放
        return textureId;
    }
}

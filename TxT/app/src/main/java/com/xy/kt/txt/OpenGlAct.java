package com.xy.kt.txt;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Author: Z.T on 2017/2/16.
 * Describe:
 */

public class OpenGlAct extends Activity{

    GLSurfaceView glSurfaceView;
    GLSurfaceView.Renderer mRender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        glSurfaceView = new GLSurfaceView(this);
        mRender = new MyRender();
        glSurfaceView.setRenderer(mRender);
        setContentView(glSurfaceView);
    }

    class MyRender implements GLSurfaceView.Renderer{

        @Override
        public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
            gl10.glShadeModel(GL10.GL_SMOOTH);
            gl10.glClearColor(0,0,0,0);
            // 启用深度测试
            gl10.glEnable(GL10.GL_DEPTH_TEST);
            // 所作深度测试的类型
            gl10.glDepthFunc(GL10.GL_LEQUAL);
            // 告诉系统对透视进行修正
            gl10.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {

            float ratio = (float) width / height;

            //设置OpenGL场景的大小
            gl.glViewport(0,0,width,height);
            //设置投影矩阵
            gl.glMatrixMode(GL10.GL_PROJECTION);
            //重置投影矩阵
            gl.glLoadIdentity();
            // 设置视口的大小 w

            gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);
            // 选择模型观察矩阵
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            // 重置模型观察矩阵
            gl.glLoadIdentity();


        }

        @Override
        public void onDrawFrame(GL10 gl10) {

        }
    }


}

package com.jiancode.animationdemo;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * author：yangjian
 * email：yj@allong.net
 * data：2019-05-3022:12
 * desc：存在未知问题
 * version：1.0
 */
@Deprecated
public class Rotate3DAnimation extends Animation {

    private final float mFromDegrees;
    private final float mToDegrees;
    private final float mCenterX;
    private final float mCentery;
    private final float mDepthz;
    private final boolean mReverse;
    private Camera mCamera;

    public Rotate3DAnimation(float fromDegree, float toDegree, float centerX, float centerY,
                             float depthz, boolean reverse) {
        mFromDegrees = fromDegree;
        mToDegrees = toDegree;
        mCenterX = centerX;
        mCentery = centerY;
        mDepthz = depthz;
        mReverse = reverse;
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        mCamera = new Camera();
    }


    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        final float fromDegree = mFromDegrees;
        float degreee = fromDegree + (mToDegrees - fromDegree) * interpolatedTime;

        final float centerX = mCenterX;
        final float centerY = mCentery;

        final Camera camera = mCamera;

        final Matrix matrix = t.getMatrix();
        if(mReverse){
            camera.translate(0.0f,0.0f,mDepthz*interpolatedTime);
        }else {
            camera.translate(0.0f,0.0f,mDepthz*(1.0f-interpolatedTime));
        }
        camera.rotateY(degreee);
        camera.getMatrix(matrix);
        camera.restore();

        matrix.preTranslate(-centerX,-centerY);
        matrix.postTranslate(centerX,centerY);

        super.applyTransformation(interpolatedTime, t);
    }
}

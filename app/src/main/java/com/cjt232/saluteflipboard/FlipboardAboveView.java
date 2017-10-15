package com.cjt232.saluteflipboard;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;


public class FlipboardAboveView extends View {

    private int centerX, centerY;

    private float rotateDeg = 90;
    private float oldDegree = 0;
    private float newDegree = 0;
    private float scale = 1f;

    private float rectLength;

    private Rect rect;


    Camera camera;
    Matrix matrix = new Matrix();
    private Paint mPaint;

    public FlipboardAboveView(Context context) {
        this(context, null);
    }

    public FlipboardAboveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlipboardAboveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        camera = new Camera();
        matrix = new Matrix();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = getWidth() / 2;
        centerY = getHeight() / 2;
        float prop = 0.6f;
        rectLength = getWidth() < getHeight() ? getWidth() / 2 * prop : getHeight() / 2 * prop;
        rect = new Rect(0, 0, getWidth(), centerY);
    }

    boolean isRotate = false;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.rotate(-rotateDeg, centerX, centerY);

        //上区域
//        canvas.save();
//        canvas.clipRect(new Rect(0, 0, width, centerY));
//        canvas.rotate(rotateDeg, centerX, centerY);
//        drawLogo(canvas, false);
//        canvas.restore();

        matrix.reset();
        camera.save();
        camera.rotateZ(-rotateDeg);
        camera.getMatrix(matrix);
        camera.restore();
        matrix.postScale(1, scale);
        matrix.preTranslate(-centerX, -centerY);
        matrix.postTranslate(centerX, centerY);
        canvas.save();
        canvas.clipRect(rect);
        canvas.concat(matrix);
        if (!isRotate) {
            camera.rotateX(oldDegree);
            camera.rotateX(-newDegree);
            oldDegree = newDegree;
            isRotate = true;
        }

        drawLogo(canvas, true);
        canvas.restore();
//        canvas.rotate(rotateDeg, centerX, centerY);
        //下区域
//        matrix.reset();
//        camera.save();
//        camera.rotateZ(-rotateDeg);
//        camera.getMatrix(matrix);
//        camera.restore();
//        matrix.postScale(1, scale);
//        matrix.preTranslate(-centerX, -centerY);
//        matrix.postTranslate(centerX, centerY);
//
//        canvas.clipRect(new Rect(0, centerY, width, height));
//        canvas.concat(matrix);
//        if (!isRotate) {
//            camera.rotateX(-oldDegree);
//            camera.rotateX(newDegree);
//            oldDegree = newDegree;
//            isRotate = true;
//        }
//        drawLogo2(canvas, true);
    }

    private void drawLogo(Canvas canvas, boolean deepen) {
        int a = 0;
        if (deepen) {
            a = (int) newDegree / 2;
        }
//        mPaint.setColor(0xffDF2B31 - a);
        mPaint.setARGB(
                255,
                deepenColor(199, a),
                deepenColor(8, a),
                deepenColor(19, a));
        canvas.drawRect(
                centerX - rectLength,
                centerY - rectLength,
                centerX + rectLength,
                centerY + rectLength,
                mPaint
        );
        mPaint.setARGB(
                255,
                deepenColor(244, a),
                deepenColor(198, a),
                deepenColor(197, a));
        canvas.drawRect(
                centerX - rectLength * 0.6f,
                centerY - rectLength * 0.6f,
                centerX + rectLength * 0.2f,
                centerY + rectLength * 0.2f,
                mPaint
        );
        mPaint.setARGB(
                255,
                deepenColor(255, a),
                deepenColor(255, a),
                deepenColor(255, a));
        canvas.drawRect(
                centerX - rectLength * 0.6f,
                centerY - rectLength * 0.6f,
                centerX - rectLength * 0.2f,
                centerY + rectLength * 0.6f,
                mPaint
        );
        mPaint.setARGB(
                255,
                deepenColor(248, a),
                deepenColor(225, a),
                deepenColor(226, a));
        canvas.drawRect(
                centerX - rectLength * 0.2f,
                centerY - rectLength * 0.6f,
                centerX + rectLength * 0.6f,
                centerY - rectLength * 0.2f,
                mPaint
        );
    }

    public void setRotateX(float rotate) {
        if (rotate <= 30 && rotate >= -30) {
            // 开口向上的2次函数
            // x轴区间 0～3
            // y轴区间 0~1
            // scale 取值范围 1 ~ 0.703
            scale = (float) (1 - (0.3) * rotate * rotate * 0.0011);
        } else {
            scale = 0.703f;
        }
        isRotate = false;
        //新角度(折叠角度)
        newDegree = rotate;
        invalidate();
    }

    public void setRotateZ(int rotate) {
        rotateDeg = rotate + 90;
        invalidate();
    }


    private int deepenColor(int color, int deepen) {
        return color - deepen < 0 ? 0 : color - deepen;
    }

}

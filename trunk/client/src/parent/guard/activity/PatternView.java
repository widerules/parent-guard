package parent.guard.activity;

import java.util.ArrayList;
import java.util.List;

import parent.guard.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class PatternView extends View {
  private final float HIT_FACTOR = 0.6f;
  private final float DIAMETER_FACTOR = 0.5f;
  
  private Context mContext;
  
  private Paint mPaint;
  private Paint mPathPaint;
  
  private float mSquareWidth;
  private float mSquareHeight;
  
  private Bitmap mDotDefault;
  private Bitmap mDotTouched;
  private Bitmap mAreaDefault;
  private Bitmap mAreaGreen;
  private Bitmap mAreaRed;
  
  private int mPaddingTop;
  private int mPaddingRight;
  private int mPaddingBottom;
  private int mPaddingLeft;
  
  private int mBitmapWidth;
  private int mBitmapHeight;
  
  private Path mPath;
  private List<Integer> mPattern;
  private float mPositionX;
  private float mPositionY;
  
  private OnPatternListener mOnPatternListener;
  private DisplayMode mDisplayMode;
  private boolean mInputDisabled;

  public PatternView(Context pContext) {
    this(pContext, null);
  }
  
  public PatternView(Context pContext, AttributeSet pAttributeSet) {
    super(pContext, pAttributeSet);
    setClickable(true);
    mContext = pContext;
    
    mPaint = new Paint();
    mPathPaint = new Paint();
    
    mPathPaint.setAntiAlias(true);
    mPathPaint.setDither(true);
    mPathPaint.setColor(Color.WHITE);
    mPathPaint.setAlpha(128);
    mPathPaint.setStyle(Paint.Style.STROKE);
    mPathPaint.setStrokeJoin(Paint.Join.ROUND);
    mPathPaint.setStrokeCap(Paint.Cap.ROUND);
    
    mDotDefault = getBitmap(R.drawable.lock_dot_default);
    mDotTouched = getBitmap(R.drawable.lock_dot_touched);
    mAreaDefault = getBitmap(R.drawable.lock_area_default);
    mAreaGreen = getBitmap(R.drawable.lock_area_green);
    mAreaRed = getBitmap(R.drawable.lock_area_red);
    
    mPaddingTop = getPaddingTop();
    mPaddingRight = getPaddingRight();
    mPaddingBottom = getPaddingBottom();
    mPaddingLeft = getPaddingLeft();
    
    mBitmapWidth = mDotDefault.getWidth();
    mBitmapHeight = mDotDefault.getHeight();
    
    mPath = new Path();
    mPattern = new ArrayList<Integer>();
    mPositionX = 0;
    mPositionY = 0;
    
    mDisplayMode = DisplayMode.CORRECT;
    mInputDisabled = false;
  }
  
  private Bitmap getBitmap(int pResourceId) {
    return BitmapFactory.decodeResource(getContext().getResources(), 
        pResourceId);
  }
  
  public static interface OnPatternListener {
    public void onPatternStart();
    public void onPatternCleared();
    public void onPatternDrawn(String pPattern);
  }
  
  private enum DisplayMode {
    CORRECT, MOVE, WRONG
  }
  
  public void setOnPatternListener(OnPatternListener pListener) {
    mOnPatternListener = pListener;
  }
  
  public void notifyWrongPattern() {
    mDisplayMode = DisplayMode.WRONG;
    mInputDisabled = false;
    invalidate();
  }
  
  public void notifyClearPattern() {
    mDisplayMode = DisplayMode.CORRECT;
    mInputDisabled = false;
    mPattern.clear();
    invalidate();
  }

  @Override
  protected void onDraw(Canvas pCanvas) {
    super.onDraw(pCanvas);
    final int tPaddingTop = mPaddingTop;
    final int tPaddingLeft = mPaddingLeft;

    if(mPattern.size() > 0) {
      final Path tPath = mPath;
      tPath.rewind();
      final int tStartPosition = mPattern.get(0);
      final float tStartX = tPaddingLeft + getColumnByPosition(tStartPosition) * mSquareWidth;
      final float tStartY = tPaddingTop + getRowByPosition(tStartPosition) * mSquareHeight;
      tPath.moveTo(tStartX + mSquareWidth / 2.0f, tStartY + mSquareHeight / 2.0f);
      for(int i = 1; i < mPattern.size(); i++) {
        final int tPosition = mPattern.get(i);
        final float tX = tPaddingLeft + getColumnByPosition(tPosition) * mSquareWidth;
        final float tY = tPaddingTop + getRowByPosition(tPosition) * mSquareHeight;
        tPath.lineTo(tX + mSquareWidth / 2.0f, tY + mSquareHeight / 2.0f);
      }
      if(mDisplayMode == DisplayMode.MOVE) {
        tPath.lineTo(mPositionX, mPositionY);
      }
      float tRadius = mSquareWidth * DIAMETER_FACTOR * 0.5f;
      mPathPaint.setStrokeWidth(tRadius);
      pCanvas.drawPath(tPath, mPathPaint);
    }
    
    for(int i = 0; i < 3; i++) {
      float tTopY = tPaddingTop + i * mSquareHeight;
      for(int j = 0; j < 3; j++) {
        float tLeftX = tPaddingLeft + j * mSquareWidth;
        int tPosition = i * 3 + j;
        boolean tHit = mPattern.contains(tPosition);
        drawCircle(pCanvas, (int) tLeftX, (int) tTopY, tHit);
      }
    }
  }
  
  @Override
  public boolean onTouchEvent(MotionEvent pMotionEvent) {
    if(mInputDisabled) {
      return false;
    }
    
    float tX = pMotionEvent.getX();
    float tY = pMotionEvent.getY();
    mPositionX = tX;
    mPositionY = tY;
    switch(pMotionEvent.getAction()) {
      case MotionEvent.ACTION_DOWN:
        mPattern.clear();
        detect(tX, tY);
        return true;
      case MotionEvent.ACTION_UP:
        mDisplayMode = DisplayMode.CORRECT;
        StringBuilder tStringBuilder = new StringBuilder();
        for(int tPosition : mPattern) {
          tStringBuilder.append(tPosition);
        }
        String tPattern = tStringBuilder.toString();
        if(mOnPatternListener != null) {
          mInputDisabled = true;
          mOnPatternListener.onPatternDrawn(tPattern);
        }
        invalidate();
        return true;
      case MotionEvent.ACTION_MOVE:
        detect(tX, tY);
        invalidate();
        return true;
    }
    return false;
  }

  @Override
  protected void onSizeChanged(int pWidth, int pHeight, int pOldWidth,
      int pOldHeight) {
    super.onSizeChanged(pWidth, pHeight, pOldWidth, pOldHeight);
    
    mPaddingTop = getPaddingTop();
    mPaddingRight = getPaddingRight();
    mPaddingBottom = getPaddingBottom();
    mPaddingLeft = getPaddingLeft();
    
    final int tWidth = pWidth - mPaddingLeft - mPaddingRight;
    final int tHeight = pHeight - mPaddingTop - mPaddingBottom;
    mSquareWidth = tWidth / 3.0f;
    mSquareHeight = tHeight / 3.0f;
  }
  
  @Override
  protected void onMeasure(int pWidthMeasure, int pHeightMeasure) {
    super.onMeasure(pWidthMeasure, pHeightMeasure);
    
    WindowManager tWindowManager = (WindowManager) mContext.getSystemService(
        Context.WINDOW_SERVICE);
    final int tWidth = tWindowManager.getDefaultDisplay().getWidth();
    final int tHeight = tWindowManager.getDefaultDisplay().getHeight();
    int tSquareSide = Math.min(tWidth, tHeight);
    setMeasuredDimension(tSquareSide, tSquareSide);
  }

  private void drawCircle(Canvas pCanvas, int pLeftX, int pTopY, boolean pHit) {
    final int tWidth = mBitmapWidth;
    final int tHeight = mBitmapHeight;
    final float tSquareWidth = mSquareWidth;
    final float tSquareHeight = mSquareHeight;
    int tOffsetX = (int) ((tSquareWidth - tWidth) / 2.0f);
    int tOffsetY = (int) ((tSquareHeight - tHeight) / 2.0f);
    
    Bitmap tArea = mAreaDefault;
    Bitmap tDot = mDotDefault;
    if(pHit) {
      if(mDisplayMode == DisplayMode.WRONG) {
        tArea = mAreaRed;
      } else {
        tArea = mAreaGreen;
      }
      tDot = mDotTouched;
    }
    
    pCanvas.drawBitmap(tArea, pLeftX + tOffsetX, pTopY + tOffsetY, mPaint);
    pCanvas.drawBitmap(tDot, pLeftX + tOffsetX, pTopY + tOffsetY, mPaint);
  }
  
  private void detect(float pX, float pY) {
    final int tHitRow = hitRow(pY);
    final int tHitColumn = hitColumn(pX);
    if((tHitRow >= 0) && (tHitColumn >= 0)) {
      int tPosition = getPosition(tHitRow, tHitColumn);
      if(!mPattern.contains(tPosition)) {
        if(mPattern.size() > 0) {
          
        } else {
          mDisplayMode = DisplayMode.MOVE;
        }
        mPattern.add(tPosition);
      }
    }
  }
  
  private int hitRow(float pY) {
    final float tSquareHeight = mSquareHeight;
    float tHitSize = tSquareHeight * HIT_FACTOR;
    final int tPaddingTop = mPaddingTop;
    float tOffset = tPaddingTop + (tSquareHeight - tHitSize) / 2.0f;
    for (int i = 0; i < 3; i++) {
      final float tHitTop = tOffset + tSquareHeight * i;
      if ((pY >= tHitTop) && (pY <= tHitTop + tHitSize)) {
        return i;
      }
    }
    return -1;
  }
  
  private int hitColumn(float pX) {
    final float tSquareWidth = mSquareWidth;
    float tHitSize = tSquareWidth * HIT_FACTOR;
    final int tPaddingLeft = mPaddingLeft;
    float tOffset = tPaddingLeft + (tSquareWidth - tHitSize) / 2.0f;
    for (int i = 0; i < 3; i++) {
      final float tHitLeft = tOffset + tSquareWidth * i;
      if ((pX >= tHitLeft) && (pX <= tHitLeft + tHitSize)) {
        return i;
      }
    }
    return -1;
  }
  
  private int getRowByPosition(int pPosition) {
    return pPosition / 3;
  }
  
  private int getColumnByPosition(int pPosition) {
    return pPosition % 3;
  }
  
  private int getPosition(int pRow, int pColumn) {
    return pRow * 3 + pColumn;
  }
}

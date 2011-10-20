package parent.guard.activity;

import parent.guard.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class PatternView extends View {
  private Paint mPaint;
  
  private float mSquareWidth;
  private float mSquareHeight;
  
  private Bitmap mDotDefault;
  private Bitmap mAreaDefault;
  
  private int mBitmapWidth;
  private int mBitmapHeight;

  public PatternView(Context pContext) {
    this(pContext, null);
  }
  
  public PatternView(Context pContext, AttributeSet pAttributeSet) {
    super(pContext, pAttributeSet);
    mPaint = new Paint();
    mDotDefault = getBitmap(R.drawable.lock_dot_default);
    mAreaDefault = getBitmap(R.drawable.lock_area_default);
    
    mBitmapWidth = mDotDefault.getWidth();
    mBitmapHeight = mDotDefault.getHeight();
  }
  
  private Bitmap getBitmap(int pResourceId) {
    return BitmapFactory.decodeResource(getContext().getResources(), 
        pResourceId);
  }

  @Override
  protected void onDraw(Canvas pCanvas) {
    super.onDraw(pCanvas);
    final int tPaddingTop = getPaddingTop();
    final int tPaddingLeft = getPaddingLeft();
    for(int i = 0; i < 3; i++) {
      float tTopY = tPaddingTop + i * mSquareHeight;
      for(int j = 0; j < 3; j++) {
        float tLeftX = tPaddingLeft + j * mSquareWidth;
        drawCircle(pCanvas, (int) tLeftX, (int) tTopY);
      }
    }
  }

  @Override
  protected void onSizeChanged(int pWidth, int pHeight, int pOldWidth,
      int pOldHeight) {
    super.onSizeChanged(pWidth, pHeight, pOldWidth, pOldHeight);
    final int tWidth = pWidth - getPaddingLeft() - getPaddingRight();
    final int tHeight = pHeight - getPaddingTop() - getPaddingBottom();
    mSquareWidth = tWidth / 3.0f;
    mSquareHeight = tHeight / 3.0f;
  }
  
  private void drawCircle(Canvas pCanvas, int pLeftX, int pTopY) {
    final int tWidth = mBitmapWidth;
    final int tHeight = mBitmapHeight;
    final float tSquareWidth = mSquareWidth;
    final float tSquareHeight = mSquareHeight;
    int tOffsetX = (int) ((tSquareWidth - tWidth) / 2.0f);
    int tOffsetY = (int) ((tSquareHeight - tHeight) / 2.0f);
    
    pCanvas.drawBitmap(mAreaDefault, pLeftX + tOffsetX, pTopY + tOffsetY, mPaint);
    pCanvas.drawBitmap(mDotDefault, pLeftX + tOffsetX, pTopY + tOffsetY, mPaint);
  }
}

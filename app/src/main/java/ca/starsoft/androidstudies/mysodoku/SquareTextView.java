package ca.starsoft.androidstudies.mysodoku;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * Created by slavko on 01-Dec-2016 10:31:15PM.
 */

public class SquareTextView extends AppCompatTextView
{
    public SquareTextView(Context context)
    {
        super(context);
    }

    public SquareTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public SquareTextView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    /*    public SquareTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
        {
            super(context, attrs, defStyleAttr, defStyleRes);
        }
    */

    private float screenRatio()
    {
        // Compute the screen ratio

        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        float ratio = ((float) metrics.heightPixels / (float) metrics.widthPixels);

        return ratio;

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // The following keeps the rectangle square irrespective of the scree aspect ratio

        int size = 0;
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        if (width >= height)
        {
            size = height;
        } else
        {
            size = width;
        }
        /*
        traceE("===========================================");
        traceE("TextAdapter.SquareTextView  width = " + width);
        traceE("TextAdapter.SquareTextView height = " + height);
        traceE("TextAdapter.SquareTextView   size = " + size);
        traceE("===========================================");
        */
        setMeasuredDimension(size, size);
     /*
        // -----------------------------------------------------------------------------------------
        // The following maintains the screen ratio for each ractange
        float RATIO = screenRatio();


        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int widthWithoutPadding = width - getPaddingLeft() - getPaddingRight();
        int heigthWithoutPadding = height - getPaddingTop() - getPaddingBottom();

        int maxWidth = (int) (heigthWithoutPadding * RATIO);
        int maxHeight = (int) (widthWithoutPadding / RATIO);

        if (widthWithoutPadding < maxWidth)
        {
            width = maxWidth + getPaddingLeft() + getPaddingRight();
        } else
        {
            height = maxHeight + getPaddingTop() + getPaddingBottom();
        }

        setMeasuredDimension(width, height);
        // -----------------------------------------------------------------------------------------
     */
    }


    private void traceE(String msg)
    {
        // This generated red lines in the log file
        Log.e(this.getClass().getName(), msg);

    }

}

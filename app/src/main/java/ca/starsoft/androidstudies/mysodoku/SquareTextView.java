package ca.starsoft.androidstudies.mysodoku;

import android.content.Context;
import android.content.res.Resources;
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


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        /**
         * Define the square to be the calculated cell size (based on the screen width)
         * Note that the calculation is in MainActivity
         */
        setMeasuredDimension(MainActivity.gCellSize, MainActivity.gCellSize);
    }


    private void traceE(String msg)
    {
        // This generated red lines in the log file
        Log.e(this.getClass().getName(), msg);

    }

}

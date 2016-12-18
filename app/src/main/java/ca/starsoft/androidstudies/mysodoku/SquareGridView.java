package ca.starsoft.androidstudies.mysodoku;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by slavko on 01-Dec-2016 09:44:05PM.
 */

public class SquareGridView extends GridView
{
    public SquareGridView(Context context)
    {
        super(context);
    }
    public SquareGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int n = Math.max(heightMeasureSpec, widthMeasureSpec);
        super.onMeasure(n, n);
    }
}

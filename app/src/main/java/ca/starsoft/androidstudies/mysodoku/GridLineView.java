package ca.starsoft.androidstudies.mysodoku;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import static android.R.attr.numColumns;

public class GridLineView extends View
{

    //private static final int DEFAULT_PAINT_COLOR = Color.WHITE;
    private static final int DEFAULT_PAINT_COLOR = Color.BLUE;
    private static final int DEFAULT_NUMBER_OF_ROWS = 9;
    private static final int DEFAULT_NUMBER_OF_COLUMNS = 9;
    private static final float WIDE_LINE = 10f;
    private static final float NAROW_LINE = 4f;

    private boolean showGrid = true;
    private final Paint paint = new Paint();
    private int numRows    = DEFAULT_NUMBER_OF_ROWS;
    private int numColumns = DEFAULT_NUMBER_OF_COLUMNS;
    private float mLineWidth;

    public GridLineView(Context context)
    {
        super(context);
        init();
    }

    public GridLineView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public GridLineView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    private void init()
    {
        paint.setColor(DEFAULT_PAINT_COLOR);
    }

    public void setLineColor(int color)
    {
        paint.setColor(color);
    }

    public void setStrokeWidth(int pixels)
    {
        paint.setStrokeWidth(pixels);
    }

    public int getNumberOfRows()
    {
        return numRows;
    }

    public void setNumberOfRows(int numRows)
    {
        if (numRows > 0)
        {
            this.numRows = numRows;
        } else
        {
            throw new RuntimeException("Cannot have a negative number of rows");
        }
    }

    public int getNumberOfColumns()
    {
        return numColumns;
    }

    public void setNumberOfColumns(int numColumns)
    {
        if (numColumns > 0)
        {
            this.numColumns = numColumns;
        } else
        {
            throw new RuntimeException("Cannot have a negative number of columns");
        }
    }

    public boolean isGridShown()
    {
        return showGrid;
    }

    public void toggleGrid()
    {
        showGrid = !showGrid;
        invalidate();
    }

    private void setLineWidth(int n)
    {
        switch(n)
        {
            case 0:
            case 3:
            case 6:
            case 9:
                mLineWidth = WIDE_LINE;
                paint.setStrokeWidth(mLineWidth);
                trace("setLineWidth = wide for i = " + n);
                break;
            default:
                mLineWidth = NAROW_LINE;
                paint.setStrokeWidth(mLineWidth);
        }

    }

    private int shitfLine(int n)
    {
        int shiftLine;
        shiftLine = 0;
        if(n == 0)shiftLine = (int) (mLineWidth/2 + 0);
        if(n == 9)shiftLine = (int)-(mLineWidth/2 + 2);
        return shiftLine;

    }
    @Override
    protected void onDraw(Canvas canvas)
    {
        if (showGrid)
        {
            int width = getMeasuredWidth();
            int height = getMeasuredHeight();
            int shiftLine;
            trace("width = " + width + " height = " + height);
            // Vertical lines
            for (int i = 0; i <= numColumns; i++)
            {
                shiftLine = shitfLine(i);
                setLineWidth(i);
                canvas.drawLine(width * i / numColumns + shiftLine, 0, width * i / numColumns + shiftLine, height, paint);
            }

            // Horizontal lines
            for (int i = 0; i <= numRows; i++)
            {
                shiftLine = shitfLine(i);
                setLineWidth(i);
                canvas.drawLine(0, height * i / numRows + shiftLine, width, height * i / numRows  + shiftLine, paint);
            }
        }
    }

//    private void trace(String msg)
//    {
//        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
//    }

    private void trace(String msg)
    {
        // This generated red lines in the log file
        Log.e(this.getClass().getName(), msg);

    }

}
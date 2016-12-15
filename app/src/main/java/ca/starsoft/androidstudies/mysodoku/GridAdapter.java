package ca.starsoft.androidstudies.mysodoku;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.StringBuilderPrinter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by slavko on 08-Dec-2016 12:32:00AM.
 */

public class GridAdapter extends BaseAdapter
{
    private List<Cell>      mCellList;
    private int             mGroup;
    private LayoutInflater  mInflater;
    private Context         mContext;


    public GridAdapter(Context context, List<Cell> list, int group)
    {
        mContext  = context;
        mInflater = LayoutInflater.from(context);
        mCellList = list;
        mGroup    = group - 1;
    }

    @Override
    public int getCount()
    {
        return 9; //mCellList.size();
    }

    @Override
    public Object getItem(int position)

    {
        return mCellList.get(mGroup*9 + position);
/* debug code
        Cell c = new Cell('1', '?');
        int n = position/9 + 1;
        for (int i = 1; i < 10; ++i)
        {
            if(i != n)
            c.addRemoveGuess(i);
        }
        return c;
/* */
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position)
    {
        return position;
    }

    public void setItemCurrentItem(int position)
    {
        trace("***** --> setItemCurrentItem");
        int pos = mGroup*9 + position;
        ((MainActivity)mContext).mCurEntry = pos;

        // TODO: If item is a single value guess, set colour to red if display only, green if guess
        // TODO: If item is single value guess, scan list and a) reset colour if guess is diferent and b flag all guess that are the same as the current one
        mCellList.get(pos).setColor();

        notifyDataSetChanged();
    }

    public void setItemValue()
    {

    }


    public void setQuakeList(List<Cell> data)
    {
        mCellList.addAll(data);
        notifyDataSetChanged();
    }

    public void clear()
    {
        mCellList.clear();
    }

    public View getView(int position, View convertView, @NonNull ViewGroup parent)
    {
        SquareTextView view = (SquareTextView) convertView;
        if (view == null)
        {
            view = (SquareTextView) mInflater.inflate(R.layout.cell_view, parent, false);
        }

        Cell currentCell = (Cell) getItem(position);
        String cellVal = currentCell.getDisplayValue();
        ((TextView) view).setText((CharSequence) currentCell.getDisplayValue());
        //trace("mGroup: " + mGroup + " position = " + position + " Cel# = " + (mGroup*9 + position) + " color = " + currentCell.getColor());
        view.setBackgroundColor(ContextCompat.getColor(mContext, currentCell.getColor()));

        // Return the view

        return view;
    }


    private void trace(String msg)
    {
        // This generated red lines in the log file
        Log.e(this.getClass().getName(), msg);
    }


    private void traceE(String msg)
    {
        // This generated red lines in the log file
        Log.e(this.getClass().getName(), msg);

    }
}



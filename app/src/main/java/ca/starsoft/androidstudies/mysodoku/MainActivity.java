package ca.starsoft.androidstudies.mysodoku;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.style.TtsSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.R.attr.width;
import static android.view.View.X;

public class MainActivity extends AppCompatActivity
{
    // ---------------------------------------------------------------------------------------------

    List<Cell>        mItemsList;
    int               mCurEntry = -1;
    List<GridAdapter> mGridAdapterList = new ArrayList<>();

    private void trace(String msg)
    {
        Log.e(this.getClass().getName(), msg);
        //Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    private void traceE(String msg)
    {
        // This generated red lines in the log file
        Log.e(this.getClass().getName(), msg);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //
        // Observation:
        // The GridView view works as expected in portrait mode but in landscape mode it uses
        // more space per cell than it should.
        // This causes a drifting to occur in the vertical direction causing the botom row to be cut off.
        // Note that this problem does not exist for the GridLineView which is used to draw the lines
        //      around all the cells
        //
        // This problem occurs whether the action bars is displayed or not.
        // Further investigation is required to find out why this is happening
        mItemsList = getGame();

        setGridListeners((GridView) findViewById(R.id.grid_view_1), 1);
        setGridListeners((GridView) findViewById(R.id.grid_view_2), 2);
        setGridListeners((GridView) findViewById(R.id.grid_view_3), 3);
        setGridListeners((GridView) findViewById(R.id.grid_view_4), 4);
        setGridListeners((GridView) findViewById(R.id.grid_view_5), 5);
        setGridListeners((GridView) findViewById(R.id.grid_view_6), 6);
        setGridListeners((GridView) findViewById(R.id.grid_view_7), 7);
        setGridListeners((GridView) findViewById(R.id.grid_view_8), 8);
        setGridListeners((GridView) findViewById(R.id.grid_view_9), 9);

        //
        // Set-up the key-listeners
        //

        setkeyListener((TextView) findViewById(R.id.key_1), 1);
        setkeyListener((TextView) findViewById(R.id.key_2), 2);
        setkeyListener((TextView) findViewById(R.id.key_3), 3);
        setkeyListener((TextView) findViewById(R.id.key_4), 4);
        setkeyListener((TextView) findViewById(R.id.key_5), 5);
        setkeyListener((TextView) findViewById(R.id.key_6), 6);
        setkeyListener((TextView) findViewById(R.id.key_7), 7);
        setkeyListener((TextView) findViewById(R.id.key_8), 8);
        setkeyListener((TextView) findViewById(R.id.key_8), 9);

        //setkeyListener((TextView) findViewById(R.id.key_1), -1); // Undo
        //setkeyListener((TextView) findViewById(R.id.key_1), -2); // Redo

        // -----------------------------------------------------------------------------------------
        // Move the following into the test classes
        // -----------------------------------------------------------------------------------------
/*
        Cell c = new Cell('1', 'D');
        traceE("Cell content: " + c.dumpString());
        c.addRemoveGuess(2);
        traceE("Cell content2: " + c.dumpString());
        c.addRemoveGuess(3);
        traceE("Cell content3: " + c.dumpString());
        c.addRemoveGuess(2);
        traceE("Cell content4: " + c.dumpString());
        c.addRemoveGuess(3);
        traceE("Cell content5: " + c.dumpString());
        // -----------------------------------------------------------------------------------------
*/
    }

    private void setGridListeners(GridView v, final int group)
    {
        GridAdapter gridAdapter = new GridAdapter(this, mItemsList, group);
        mGridAdapterList.add(gridAdapter);

        v.setAdapter(gridAdapter);
        v.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                mCurEntry = (group - 1)*9 + position;
                char curChar = mItemsList.get(mCurEntry).getCellValue();
                mItemsList.get(mCurEntry).setColorCurr();

                for(int i = 0; i < mItemsList.size(); ++i)
                {
                    int g = i / 9;
                    char c = mItemsList.get(i).getCellValue();
                    if(c != ' ' && c == curChar)
                    {
                        if(i != mCurEntry)
                        {
                            mItemsList.get(i).setColor();
                            //trace("-------> ClickListener color  : mCurEntry = " + mCurEntry + " i = " + i);
                            mGridAdapterList.get(g).notifyDataSetChanged();
                        }
                    } else
                    {
                        mItemsList.get(i).resetColor();
                        mGridAdapterList.get(g).notifyDataSetChanged();
                        //trace("-------> ClickListener reset color  : mCurEntry = " + mCurEntry + " i = " + i);
                    }
                }

                trace("Setting current colour");
                mItemsList.get(mCurEntry).setColorCurr();
                mGridAdapterList.get(mCurEntry/9).notifyDataSetChanged();

            }
        });
    }

    private void setkeyListener(TextView textView, final int keyId)
    {
        textView.setOnClickListener(new AdapterView.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                trace("------------- keyClick -------------");
                trace("mCurEntry = " + mCurEntry + " group = " + (mCurEntry/9) + " keyId = " + keyId);

                if(mCurEntry != -1)
                {
                    mItemsList.get(mCurEntry).addRemoveGuess(keyId);
                    mGridAdapterList.get(mCurEntry/9).notifyDataSetChanged();
                    trace("Cell Dump = " + mItemsList.get(mCurEntry).dumpString());
                    trace("Cell Display = " + mItemsList.get(mCurEntry).getDisplayValue());
                }

            }
        });
    }

    private void mkGameLine(List<Cell> l, String v)
    {
        //trace("mkGameLine: v = " + v);
        for(int i = 0; i < v.length(); i = i+ 2)
        {
            //trace("mkGameLine: i = " + i);
            l.add(new Cell(v.charAt(i), v.charAt(i + 1)));
        }
    }

    private List<Cell> getGame()
    {
        ArrayList<Cell> g = new ArrayList<>();
        //mkGameLine(g, "9?3D6D" + "8?5D1?" + "4D2?7?");
        //mkGameLine(g, "7?4D5D" + "9D3?2?" + "6?1D8D");
        //mkGameLine(g, "1?1?8D" + "6?7?4?" + "9?5?3?");
        mkGameLine(g, "9?3D6D" +
                      "7?4D5D" +
                      "1?1?8D"
                  );
        mkGameLine(g, "8?5D1?" +
                      "9D3?2?" +
                      "6?7?4?"
                  );
        mkGameLine(g, "4D2?7?" +
                      "6?1D8D" +
                      "9?5?3?"
                  );

        //mkGameLine(g, "3D5?1D" + "2?4?6D" + "7D8D9?");
        //mkGameLine(g, "8?6?7?" + "1?9D5?" + "3?4?2?");
        //mkGameLine(g, "4?9D2D" + "7D8?3?" + "1D6?5D");
        mkGameLine(g, "3D5?1D" +
                      "8?6?7?" +
                      "4?9D2D"
                  );
        mkGameLine(g, "2?4?6D" +
                      "1?9D5?" +
                      "7D8?3?"
                   );
        mkGameLine(g, "7D8D9?" +
                      "3?4?2?" +
                      "1D6?5D"
                   );

        //mkGameLine(g, "5?8?4?" + "3?1?9?" + "2D7?6?");
        //mkGameLine(g, "2D1D3?" + "5?6?7D" + "8D9D4?");
        //mkGameLine(g, "6?7?9D" + "4?2D8?" + "5D3D1?");
        mkGameLine(g, "5?8?4?" +
                      "2D1D3?" +
                      "6?7?9D"
                   );
        mkGameLine(g, "3?1?9?" +
                      "5?6?7D" +
                      "4?2D8?"
                   );
        mkGameLine(g, "2D7?6?" +
                      "8D9D4?" +
                      "5D3D1?"
                   );

        return g;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        trace(" " + newConfig.screenWidthDp);

        // fontSize = (TextView Height) * (width DPI / height DPI) / (Screen Width * Screen Height);
        Display display = this.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        trace("newConfig.screenWidthDp  = " + newConfig.screenWidthDp);
        trace("newConfig.screenHeightDp = " + newConfig.screenHeightDp);
        trace("Screen x = " + size.x);
        trace("Screen y = " + size.y);
    }
    // ---------------------------------------------------------------------------------------------
}

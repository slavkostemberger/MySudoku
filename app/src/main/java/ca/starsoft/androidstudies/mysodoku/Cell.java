package ca.starsoft.androidstudies.mysodoku;

import android.util.Log;

import java.util.List;

/**
 * Created by slavko on 02-Dec-2016 04:48:50PM.
 */

public class Cell
{
    /**
     * Private values in class
     */
    private char          mValidValue;      // The valid value for this cell
    private final boolean mDisplayOnly;     // true if this is a display only cell
    private int           mNumberOfGuesses; // Number of guesses made in this cell
    private char          mSingleGuess;     // Value of guess when there is only one guess in cell
                                            // This is blank when no guesses or many guesses are made
    private StringBuilder mGuessList;       // List of all guesses
    private int           mColor;           // Background Colour

    /**
     * Class constructor
     * @param validValue - character value of the correct answer
     * @param displayFlag - 'D' if this is a display only cell
     *
     *                    - '?' if this is not displayed and user must "guess" it
     */
    public Cell(char validValue, char displayFlag)
    {
        mValidValue      = validValue;
        mDisplayOnly     = displayFlag == 'D';
        mNumberOfGuesses = mDisplayOnly ? 1 : 0;
        mSingleGuess     = mDisplayOnly ? validValue : ' ';
        mGuessList       = new StringBuilder("         ");
        mColor           = R.color.color_white;

        if(mDisplayOnly)
        {
            mGuessList.setCharAt(Integer.parseInt(Character.toString(validValue))-1, validValue);
        }
        //trace("Cell: " + dumpString());
    }

    /***
     * Convert Cell to a string useful for debugging.
     * @return - String value of all private variables
     */
    public String dumpString()
    {
        return "mValidValue = " + mValidValue +
               " mDisplayOnly = " + mDisplayOnly +
               " mNumberOfGuesses = " + mNumberOfGuesses +
               " mSingleGuess = '" + mSingleGuess + "'" +
               " mGuessList = '" + mGuessList + "'";
    }
    @Override
    public String toString()
    {
        return mNumberOfGuesses < 2 ? Character.toString(mSingleGuess)
                                    : mGuessList.substring(0, 3) + "\n" +
                                      mGuessList.substring(3, 6) + "\n" +
                                      mGuessList.substring(6, 9);
    }

    public boolean isDispalyOnly()
    {
        return mDisplayOnly;
    }
    public char getCellValue()
    {
        return mSingleGuess;
    }
    /***
     * Add/Remove a guess to the list of guesses for the cell
     * If this values was guest previously, it is removed rather than added
     * @param guess - int value between 1 and 9
     */
    public void addRemoveGuess(int guess)
    {
        int n = guess - 1;
        // For display only cells, ignore change requests
        //trace(dumpString());
        if(!mDisplayOnly)
        {
            //trace("addRemoveGuess: guess = " + guess);
            char c = Integer.toString(guess).charAt(0);
            // Add if the guess has not yet been made
            if (mGuessList.charAt(n) == ' ')
            {
                // Save single guess in display char
                if(++mNumberOfGuesses == 1)
                {
                    mSingleGuess = c;
                } else
                {
                    mSingleGuess = ' ';
                }
                mGuessList.setCharAt(n, c);
            } else
            {
                mGuessList.setCharAt(n, ' ');
                if(--mNumberOfGuesses == 1)
                {
                    for (int i = 0; i < 9; ++i)
                    {
                        if (mGuessList.charAt(i) != ' ')
                        {
                            mSingleGuess = mGuessList.charAt(i);
                            break;
                        }
                    }
                } else
                {
                    mSingleGuess = ' ';

                }
            }
        }
    }


    /**
     * Check if a cell contains a guess that is correct among all guesses
     * Note that no guess is treated as guessing all 9 possibilities
     * @return true if the valid answer in one of the guesses
     *         false if the valid answer is not in the valid guesses
     */
    public boolean containsValidGuess()
    {
        boolean rc;
        if(mNumberOfGuesses == 0)
        {
            rc = true;
        } else
        {
            rc = false;
            for (int i = 0; i < 9; ++i)
            {
                if (mGuessList.charAt(i) == mValidValue)
                {
                    rc = true;
                    break;
                }
            }
        }
        return rc;
    }

    /**
     * determine if only the valid was guessed was made
     * @return true the valid answer was guessed
     *         false if the valid answer was not guessed (no guess or more tha one guess)
     */
    public boolean hasValidGuess()
    {
        return mValidValue == mSingleGuess;
    }

    public void setColor()
    {
        mColor = R.color.color_yellow;
    }

    public void setColorCurr()
    {
        mColor = mDisplayOnly ? R.color.color_red
                              : R.color.color_green;
        //trace("---------------> mColor = " + mColor);
    }

    public void resetColor()
    {
        mColor = R.color.color_white;
    }

    public int getColor()
    {
        return mColor;
    }

    private String mapChar(int n)
    {
        return mGuessList.charAt(n) == ' ' ? "\u0020" : "" + mGuessList.charAt(n);
    }

    /**
     *
     * @return guess/guess list for display as a string
     */
    public String getDisplayValue()
    {

        return mNumberOfGuesses < 2 ? Character.toString(mSingleGuess)
                                    : mGuessList.substring(0, 3) + "\n" +
                                      mGuessList.substring(3, 6) + "\n" +
                                      mGuessList.substring(6, 9);
    }

    /**
     * Debug helper - list info in log
     * @param msg
     */
    private void trace(String msg)
    {
        Log.e(this.getClass().getName(), msg);
        // Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}

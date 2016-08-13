package zo2m4bie.com.myexpandlelistview.model;

/**
 * Created by dima on 8/13/16.
 */
public class PuzzleModel {

    private String mPuzzle;
    private String mAnswer;
    private int mBackColor;

    public PuzzleModel(String puzzle, String answer, int backColor) {
        mPuzzle = puzzle;
        mAnswer = answer;
        mBackColor = backColor;
    }

    public String getPuzzle() {
        return mPuzzle;
    }

    public void setPuzzle(String puzzle) {
        mPuzzle = puzzle;
    }

    public String getAnswer() {
        return mAnswer;
    }

    public void setAnswer(String answer) {
        mAnswer = answer;
    }

    public int getBackColor() {
        return mBackColor;
    }

    public void setBackColor(int backColor) {
        mBackColor = backColor;
    }
}

package zo2m4bie.com.myexpandlelistview.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zo2m4bie.firstitemexpandablelistview.adapter.IFirstExpandableAdapter;
import com.zo2m4bie.firstitemexpandablelistview.holder.ISelfExpandableHolder;

import zo2m4bie.com.myexpandlelistview.R;
import zo2m4bie.com.myexpandlelistview.model.PuzzleModel;
import zo2m4bie.com.myexpandlelistview.model.RecipeModel;

/**
 * Created by dima on 8/13/16.
 */
public class PuzzleAdapter  extends IFirstExpandableAdapter<PuzzleModel> {

    protected LayoutInflater mInflater;

    public PuzzleAdapter(Context context, PuzzleModel[] testArray) {
        super(context, 0, 0, testArray);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent, int percent) {
        if (convertView == null) {
            convertView = initHolder();
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        PuzzleModel model = getItem(position);
        holder.init(model);
        holder.heightPercentage((float) percent / 100);
        return convertView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    private View initHolder() {
        View view = mInflater.inflate(R.layout.item_puzzle, null);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);
        return view;
    }

    static class ViewHolder implements ISelfExpandableHolder {
        private TextView mPuzzle;
        private TextView mAnswer;
        private LinearLayout mMainLayout;

        public ViewHolder(View view) {
            mMainLayout = (LinearLayout) view.findViewById(R.id.main_layout);
            mPuzzle = (TextView) view.findViewById(R.id.puzzle);
            mAnswer = (TextView) view.findViewById(R.id.answer);
        }

        @Override
        public void heightPercentage(float percent) {
            if(percent >= 0.5) {
                mAnswer.setVisibility(View.VISIBLE);
                mAnswer.setAlpha((percent - 0.5f) * 2);
            }
        }

        public void init(PuzzleModel model) {
            mPuzzle.setText(model.getPuzzle());
            mAnswer.setText(model.getAnswer());
            mMainLayout.setBackgroundColor(model.getBackColor());
        }
    }
}
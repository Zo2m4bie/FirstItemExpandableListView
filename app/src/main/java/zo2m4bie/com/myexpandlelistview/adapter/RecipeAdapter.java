package zo2m4bie.com.myexpandlelistview.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zo2m4bie.firstitemexpandablelistview.adapter.IFirstExpandableAdapter;
import com.zo2m4bie.firstitemexpandablelistview.holder.ISelfExpandableHolder;

import zo2m4bie.com.myexpandlelistview.R;
import zo2m4bie.com.myexpandlelistview.model.RecipeModel;

/**
 * Created by dima on 8/13/16.
 */
public class RecipeAdapter extends IFirstExpandableAdapter<RecipeModel> {

    protected LayoutInflater mInflater;

    public RecipeAdapter(Context context, RecipeModel[] testArray) {
        super(context, 0, 0, testArray);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent, int percent) {
        if(convertView == null){
            convertView = initHolder();
        }
        ViewHolder holder = (ViewHolder)convertView.getTag();
        RecipeModel model = getItem(position);
        holder.mTitle.setText(model.getTitle());
        holder.mPicture.setImageResource(model.getImageId());
        holder.mPicture.setAlpha((float)percent / 100);
        holder.setPosition(position);
        return convertView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    private View initHolder() {
        View view = mInflater.inflate(R.layout.item_recipe, null);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);
        return view;
    }

    static class ViewHolder implements ISelfExpandableHolder {
        private TextView mTitle;
        private ImageView mPicture;
        private int position;

        public ViewHolder(View view) {
            mTitle = (TextView) view.findViewById(R.id.title);
            mPicture = (ImageView) view.findViewById(R.id.picture);
        }

        public void setPosition(int position) {
            this.position = position;
        }

        @Override
        public void heightPercentage(float percent) {
            mPicture.setAlpha(percent);
            Log.d("Percentage", "percent = " + percent + " position" + position);
        }
    }

}

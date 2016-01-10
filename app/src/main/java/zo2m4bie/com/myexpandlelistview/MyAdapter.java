package zo2m4bie.com.myexpandlelistview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zo2m4bie.firstitemexpandablelistview.adapter.IMyAdapter;
import com.zo2m4bie.firstitemexpandablelistview.holder.ISelfExpandableHolder;

/**
 * Created by dima on 1/8/16.
 */
public class MyAdapter extends IMyAdapter<String> {

    protected LayoutInflater mInflater;

    public MyAdapter(Context context, int item_test, int text1, String[] testArray) {
        super(context, item_test, text1, testArray);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent, int percent) {
        if(convertView == null){
            convertView = initHolder();
        }
        ViewHolder holder = (ViewHolder)convertView.getTag();
        holder.mTitle.setText(getItem(position));
        holder.mPicture.setAlpha((float)percent / 100);
        return convertView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    private View initHolder() {
        ViewHolder viewHolder = new ViewHolder();
        View view = mInflater.inflate(R.layout.item_test, null);
        viewHolder.mTitle = (TextView) view.findViewById(android.R.id.text1);
        viewHolder.mPicture = (ImageView) view.findViewById(R.id.picture);
        view.setTag(viewHolder);
        return view;
    }

    static class ViewHolder implements ISelfExpandableHolder {
        TextView mTitle;
        ImageView mPicture;

        @Override
        public void heightPercentage(int percent) {
            mPicture.setAlpha((float)percent / 100);
            Log.d("Percentage", "percent = " + percent);
        }
    }

}

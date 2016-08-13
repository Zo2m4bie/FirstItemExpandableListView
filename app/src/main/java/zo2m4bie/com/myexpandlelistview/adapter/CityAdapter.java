package zo2m4bie.com.myexpandlelistview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zo2m4bie.firstitemexpandablelistview.adapter.IFirstExpandableAdapter;
import com.zo2m4bie.firstitemexpandablelistview.holder.ISelfExpandableHolder;

import zo2m4bie.com.myexpandlelistview.R;
import zo2m4bie.com.myexpandlelistview.model.CityModel;

/**
 * Created by dima on 8/13/16.
 */
public class CityAdapter extends IFirstExpandableAdapter<CityModel> {

    protected LayoutInflater mInflater;

    public CityAdapter(Context context, CityModel[] testArray) {
        super(context, 0, 0, testArray);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent, int percent) {
        if (convertView == null) {
            convertView = initHolder();
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        CityModel model = getItem(position);
        holder.setData(model);
        holder.mPicture.setAlpha((float) percent / 100);
        return convertView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    private View initHolder() {
        View view = mInflater.inflate(R.layout.item_city, null);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);
        return view;
    }

    static class ViewHolder implements ISelfExpandableHolder {
        private TextView mTitle;
        private TextView mInfo;
        private ImageView mPicture;

        public ViewHolder(View view) {
            mTitle = (TextView) view.findViewById(R.id.title);
            mInfo = (TextView) view.findViewById(R.id.some_info);
            mPicture = (ImageView) view.findViewById(R.id.picture);
        }

        @Override
        public void heightPercentage(float percent) {
            mPicture.setAlpha(percent);
            mInfo.setAlpha(percent);
        }

        public void setData(CityModel model) {
            mTitle.setText(model.getTitle());
            mInfo.setText(model.getSomeInfo());
            mPicture.setImageResource(model.getImageId());
        }
    }
}
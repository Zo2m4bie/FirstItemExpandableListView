package zo2m4bie.com.myexpandlelistview.model;

/**
 * Created by dima on 8/13/16.
 */
public class CityModel {
    private String mTitle;
    private String mSomeInfo;
    private int mImageId;

    public CityModel(String title, String someInfo, int imageId) {
        mTitle = title;
        mSomeInfo = someInfo;
        mImageId = imageId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getSomeInfo() {
        return mSomeInfo;
    }

    public void setSomeInfo(String someInfo) {
        mSomeInfo = someInfo;
    }

    public int getImageId() {
        return mImageId;
    }

    public void setImageId(int imageId) {
        mImageId = imageId;
    }
}

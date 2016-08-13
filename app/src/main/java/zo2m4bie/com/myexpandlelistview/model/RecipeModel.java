package zo2m4bie.com.myexpandlelistview.model;

/**
 * Created by dima on 8/13/16.
 */
public class RecipeModel {

    private String mTitle;

    private int mImageId;

    public RecipeModel(String title, int imageId) {
        mTitle = title;
        mImageId = imageId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public int getImageId() {
        return mImageId;
    }

    public void setImageId(int imageId) {
        mImageId = imageId;
    }
}

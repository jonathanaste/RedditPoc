package jonas.com.redditpoc.presenters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import jonas.com.redditpoc.interfaces.ImageFragmentView;

import static android.content.ContentValues.TAG;

public class ImageFragmentPresenter {

    private static final String REDDIT_PICTURE_PATH = "/Pictures/RedditImages";
    private ImageFragmentView view;

    public ImageFragmentPresenter(ImageFragmentView view){
        this.view = view;
    }

    public void saveImage(String url, String name) {

        Bitmap bitmap = getBitmapFromURL(url);
        File pictureFile = getOutputMediaFile(name);
        if (pictureFile != null && bitmap != null) {
            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);
                fos.close();
                view.onImageSaved();
            } catch (FileNotFoundException e) {
                Log.d(TAG, "File not found: " + e.getMessage());
            } catch (IOException e) {
                Log.d(TAG, "Error accessing file: " + e.getMessage());
            }
        }
    }

    private static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            return null;
        }
    }

    private File getOutputMediaFile(String name) {
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + REDDIT_PICTURE_PATH);

        Log.d("Path: ", mediaStorageDir.getAbsolutePath());

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        File mediaFile;
        String mImageName = "MI_" + name + ".jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }
}

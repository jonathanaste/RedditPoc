package jonas.com.redditpoc.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import jonas.com.redditpoc.R;
import jonas.com.redditpoc.adapters.TopAdapter;
import jonas.com.redditpoc.interfaces.ImageFragmentView;
import jonas.com.redditpoc.model.Post;
import jonas.com.redditpoc.presenters.ImageFragmentPresenter;

public class ImageFragment extends Fragment implements ImageFragmentView {

    private static final int MY_PERMISSIONS_REQUEST_STORAGE = 99;
    private static String URL_EXTRA = "URL_EXTRA";
    private static String NAME_EXTRA = "NAME_EXTRA";
    private String name;
    private String url;
    private ImageView image;
    private TextView description;
    private ImageFragmentPresenter presenter;

    public static ImageFragment newInstance(Post post) {
        ImageFragment imageFragment = new ImageFragment();
        Bundle bundle = new Bundle();
        bundle.putString(URL_EXTRA, post.getThumbnail());
        bundle.putString(NAME_EXTRA, post.getName());
        imageFragment.setArguments(bundle);
        return imageFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ImageFragmentPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_image, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);

        url = getArguments().getString(URL_EXTRA);
        name = getArguments().getString(NAME_EXTRA);

        if (url != null && !url.isEmpty() && url.startsWith(TopAdapter.THUMBNAIL_BASE_URL)) {
            Picasso.with(getContext()).load(url).error(R.drawable.reddit).into(image);
            if (areStoragePermissionGranted()) {
                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        presenter.saveImage(url, name);
                    }
                });
            } else {
                requestStoragePermissions();
            }
        } else {
            image.setImageResource(R.drawable.reddit);
            description.setText(R.string.no_image);
        }
    }

    private void initViews(View view) {
        image = (ImageView) view.findViewById(R.id.image);
        description = (TextView) view.findViewById(R.id.description);
    }

    @Override
    public void onImageSaved() {
        if (getView() != null) {
            Snackbar snackbar = Snackbar
                    .make(getView(), R.string.image_saved, Snackbar.LENGTH_SHORT);

            snackbar.show();
        }
    }

    public void checkPermissionsMsg() {
        if (getView() != null) {
            Snackbar snackbar = Snackbar
                    .make(getView(), R.string.check_permissions, Snackbar.LENGTH_SHORT);

            snackbar.show();
        }
    }

    private boolean areStoragePermissionGranted() {

        return (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);

    }

    private void requestStoragePermissions() {

        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                MY_PERMISSIONS_REQUEST_STORAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("permissions", " populateData(weaponList)");
                    image.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            presenter.saveImage(url, name);
                        }
                    });
                }else {
                    image.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            checkPermissionsMsg();
                        }
                    });
                }
            }
        }
    }

}


package jonas.com.redditpoc.ui.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import jonas.com.redditpoc.R;
import jonas.com.redditpoc.ui.adapters.TopAdapter;
import jonas.com.redditpoc.mvp.views.ImageFragmentView;
import jonas.com.redditpoc.model.Post;
import jonas.com.redditpoc.mvp.presenters.ImageFragmentPresenter;

public class ImageFragment extends Fragment implements ImageFragmentView {

    private static final int MY_PERMISSIONS_REQUEST_STORAGE = 99;
    private static String URL_EXTRA = "URL_EXTRA";
    private static String NAME_EXTRA = "NAME_EXTRA";
    private String name;
    private String url;
    private ImageView image;
    private ImageFragmentPresenter presenter;
    private boolean alreadyAskPermission;

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
        presenter = new ImageFragmentPresenter(getContext(), this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_image, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        url = getArguments().getString(URL_EXTRA);
        name = getArguments().getString(NAME_EXTRA);

        initViews(view);
        initListeners();

    }

    private void initListeners() {
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (areStoragePermissionGranted()) {
                    presenter.saveImage(url, name);
                } else if (alreadyAskPermission) {
                    checkPermissionsMsg();
                } else {
                    requestStoragePermissions();
                }
            }
        });
    }

    private void initViews(View view) {
        image = (ImageView) view.findViewById(R.id.image);
        TextView description = (TextView) view.findViewById(R.id.description);

        if (url != null && !url.isEmpty() && url.startsWith(TopAdapter.THUMBNAIL_BASE_URL)) {
            Picasso.with(getContext()).load(url).error(R.drawable.reddit).into(image);
        } else {
            image.setImageResource(R.drawable.reddit);
            description.setText(R.string.no_image);
        }
    }

    @Override
    public void onImageSaved() {
        showSnackBar(R.string.image_saved);
    }

    @Override
    public void showImageNotSaveError() {
        showSnackBar(R.string.error);
    }

    public void checkPermissionsMsg() {
        showSnackBar(R.string.check_permissions);
    }

    private void showSnackBar(int msg) {
        if (getView() != null) {
            Snackbar snackbar = Snackbar
                    .make(getView(), msg, Snackbar.LENGTH_SHORT);

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
        alreadyAskPermission = true;
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
                    presenter.saveImage(url, name);
                } else {
                    checkPermissionsMsg();
                }
            }
        }
    }

}


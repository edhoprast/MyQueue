package com.myqueue.myqueue.Fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.myqueue.myqueue.Activities.ProfileActivity;
import com.myqueue.myqueue.Callbacks.OnActionbarListener;
import com.myqueue.myqueue.Preferences.SessionManager;
import com.myqueue.myqueue.R;
import com.myqueue.myqueue.Views.RoundedImage;

/**
 * Created by 高橋六羽 on 2016/03/21.
 */
public class ProfileFragment extends BaseFragment implements View.OnClickListener {

    private int isOwner = 0;

    private LinearLayout customerContainer;
    private LinearLayout shopOwnerContainer;

    private ImageView imgcover, profilePicture;

    private EditText storeAddress;
    private EditText storePhone;
    private EditText storeEmail;
    private EditText storeCategory;
    private EditText storeName;

    private EditText userPhone;
    private EditText userEmail;
    private EditText userName;


    private RoundedImage cropCircle;
    private Button updatebtn;

    ProfileActivity activity;

    StoreLocationFragment storeLocationFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = (ProfileActivity) getActivity();


    }

    private void setupActionBar() {
        ProfileActivity mainActivity = (ProfileActivity) getActivity();
        mainActivity.setDefaultActionbarIcon();
        mainActivity.setRightIcon(0);
        mainActivity.setActionBarTitle(getPageTitle());
    }

    @Override
    public void onClick(View v) {
        if (v == updatebtn) {
            Toast.makeText(getActivity(), "Profile Updated", Toast.LENGTH_SHORT).show();
        } else if (v == storeAddress) {
            storeLocationFragment = new StoreLocationFragment();

            replaceFragment(R.id.fragment_container, storeLocationFragment, true);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void fetchData()
    {
        Glide.with(activity).load(activity.userData.get(SessionManager.KEY_COVERPHOTO)).placeholder(R.drawable.coverpics).into(imgcover);
        Glide.with(activity).load(activity.userData.get(SessionManager.KEY_PROFILEPHOTO)).asBitmap()
                .into(new SimpleTarget<Bitmap>(256, 256) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        cropCircle = new RoundedImage(resource);
                        profilePicture.setImageDrawable(cropCircle);
                    }
                });


        if(isOwner==1)
        {
            storeName.setText(activity.userData.get(SessionManager.KEY_NAME));
            storePhone.setText(activity.userData.get(SessionManager.KEY_PHONE));
            storeEmail.setText(activity.userData.get(SessionManager.KEY_EMAIL));
            storeCategory.setText(activity.shopData.get(SessionManager.KEY_CATEGORY));
            storeAddress.setText(activity.shopData.get(SessionManager.KEY_ADDRESS) + activity.shopData.get(SessionManager.KEY_NUMBER));

        }
        else
        {
            userName.setText(activity.userData.get(SessionManager.KEY_NAME));
            userPhone.setText(activity.userData.get(SessionManager.KEY_PHONE));
            userEmail.setText(activity.userData.get(SessionManager.KEY_EMAIL));
        }
    }

    @Override
    public void initView(View v) {

        imgcover = (ImageView) v.findViewById(R.id.coverPicture);
        profilePicture = (ImageView) v.findViewById(R.id.profilepicture);
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.no_user);
        cropCircle = new RoundedImage(bm);
        profilePicture.setImageDrawable(cropCircle);
        updatebtn = (Button) v.findViewById(R.id.btnUpdateProfile);

        storeName = (EditText) v.findViewById(R.id.txtNamaProfileShop);
        storePhone = (EditText) v.findViewById(R.id.txtPhoneNumberShop);
        storeEmail = (EditText) v.findViewById(R.id.txtEmailShop);
        storeCategory = (EditText) v.findViewById(R.id.txtCategoryShop);
        storeAddress = (EditText) v.findViewById(R.id.txtStoreAdd);

        userName = (EditText) v.findViewById(R.id.txtNamaProfile);
        userPhone = (EditText) v.findViewById(R.id.txtPhoneNumber);
        userEmail = (EditText) v.findViewById(R.id.txtEmail);

        customerContainer = (LinearLayout) v.findViewById(R.id.customer_data_container);
        shopOwnerContainer = (LinearLayout) v.findViewById(R.id.shop_data_container);

        updatebtn.setOnClickListener(this);
        storeAddress.setOnClickListener(this);
    }

    @Override
    public void setUICallbacks() {
        getBaseActivity().setActionbarListener(new OnActionbarListener() {
            @Override
            public void onLeftIconClick() {
                getActivity().onBackPressed();
            }

            @Override
            public void onRightIconClick() {

            }
        });

    }

    @Override
    public void updateUI() {

        setupActionBar();



        if(activity.getIsOwner()==true)
        {
            shopOwnerContainer.setVisibility(View.VISIBLE);
            customerContainer.setVisibility(View.GONE);
            isOwner = 1;
        } else {
            customerContainer.setVisibility(View.VISIBLE);
            shopOwnerContainer.setVisibility(View.GONE);
            isOwner = 0;
        }

        fetchData();

    }

    @Override
    public String getPageTitle() {
        return "Profile";
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_profile;
    }
}

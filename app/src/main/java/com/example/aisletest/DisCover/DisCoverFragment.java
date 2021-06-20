package com.example.aisletest.DisCover;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.aisletest.DisCover.ResponseModel.Profile;
import com.example.aisletest.DisCover.ResponseModel.ProfileListResponseModel;
import com.example.aisletest.R;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DisCoverFragment extends Fragment implements DisCoverContract.DisCoverView {

  @BindView(R.id.progressBar)
  ProgressBar progressBar;
  @BindView(R.id.ivProfile)
  ImageView ivProfile;
  @BindView(R.id.tvNameAge)
  TextView tvNameAge;
  @BindView(R.id.rvLinks)
  RecyclerView rvLinks;

  private Unbinder unbinder;
  private String authToken;
  private DisCoverContract.DisCoverPresenter disCoverPresenter;

  public DisCoverFragment(String token) {
    authToken = token;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_dis_cover, container, false);
    unbinder = ButterKnife.bind(this, view);
    return view;
  }

  @Override
  public void onResume() {
    super.onResume();
    disCoverPresenter = new DisCoverScreenPresenter(this, getContext());
    disCoverPresenter.getProfileList(authToken);
  }

  @Override
  public void showErrorMsg(String errorMsg) {
    Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
  }

  @Override
  public void hideProgressBar() {
    progressBar.setVisibility(View.GONE);
  }

  @Override
  public void showProgressBar() {
    progressBar.setVisibility(View.VISIBLE);
  }

  @Override
  public void setUpData(ProfileListResponseModel body) {
    if (body.getInvites() != null) {
      if (body.getInvites().getProfiles() != null &&
          body.getInvites().getProfiles().size() > 0) {
        Profile profileData = body.getInvites().getProfiles().get(0);
        String name = profileData.getGeneralInformation().getFirstName() != null &&
            !profileData.getGeneralInformation().getFirstName().isEmpty() ?
            profileData.getGeneralInformation().getFirstName() : "";
        tvNameAge.setText(name.concat(", ")
            .concat(String.valueOf(profileData.getGeneralInformation().getAge())));

        if (profileData.getPhotos() != null &&
            profileData.getPhotos().size() > 0) {
          for (int i = 0; i < profileData.getPhotos().size(); i++) {
            if (profileData.getPhotos().get(i).getSelected()) {
              Glide.with(Objects.requireNonNull(getActivity()))
                  .load(profileData.getPhotos().get(i).getPhoto())
                  .into(ivProfile);
            }
          }
        }
      }
    }

    if (body.getLikes() != null) {
      if (body.getLikes().getProfiles() != null &&
          body.getInvites().getProfiles().size() > 0) {
        LinksAdapter linksAdapter = new LinksAdapter(body.getLikes().getProfiles());
        rvLinks.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rvLinks.setAdapter(linksAdapter);
      }
    }
  }
}
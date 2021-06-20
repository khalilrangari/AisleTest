package com.example.aisletest.HomeScreen;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.aisletest.R;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.aisletest.Utility.AppConstants.TOKEN;

public class HomeActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

  @BindView(R.id.bottomMainTab)
  TabLayout bottomMainTab;
  @BindView(R.id.mainViewPager)
  ViewPager mainViewPager;

  private String token;
  private PagerAdapter pagerAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);
    ButterKnife.bind(this);
    getIntentData();
    initilization();
  }

  private void initilization() {
    bottomMainTab.addTab(bottomMainTab.newTab().setText("Discover").setIcon(R.drawable.ic_discover));
    bottomMainTab.addTab(bottomMainTab.newTab().setText("Notes").setIcon(R.drawable.ic_notes));
    bottomMainTab.addTab(bottomMainTab.newTab().setText("Matches").setIcon(R.drawable.ic_matches));
    bottomMainTab.addTab(bottomMainTab.newTab().setText("Profile").setIcon(R.drawable.ic_profile));
    bottomMainTab.setTabGravity(TabLayout.GRAVITY_FILL);

    Objects.requireNonNull(Objects.requireNonNull(bottomMainTab.getTabAt(0)).getIcon()).setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_IN);
    Objects.requireNonNull(Objects.requireNonNull(bottomMainTab.getTabAt(1)).getIcon()).setColorFilter(Color.parseColor("#a09c9c"), PorterDuff.Mode.SRC_IN);
    Objects.requireNonNull(Objects.requireNonNull(bottomMainTab.getTabAt(2)).getIcon()).setColorFilter(Color.parseColor("#a09c9c"), PorterDuff.Mode.SRC_IN);
    Objects.requireNonNull(Objects.requireNonNull(bottomMainTab.getTabAt(3)).getIcon()).setColorFilter(Color.parseColor("#a09c9c"), PorterDuff.Mode.SRC_IN);

    pagerAdapter = new PagerAdapter(getSupportFragmentManager(), bottomMainTab.getTabCount(),token);
    mainViewPager.setAdapter(pagerAdapter);
    bottomMainTab.setOnTabSelectedListener(this);

    mainViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
      @Override
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

      }

      @Override
      public void onPageSelected(int position) {
        updateBottomTabView(position);
      }

      @Override
      public void onPageScrollStateChanged(int state) {

      }
    });
  }

  private void updateBottomTabView(int position) {
    switch (position) {
      case 0:
        Objects.requireNonNull(Objects.requireNonNull(bottomMainTab.getTabAt(0)).getIcon()).setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_IN);
        Objects.requireNonNull(Objects.requireNonNull(bottomMainTab.getTabAt(1)).getIcon()).setColorFilter(Color.parseColor("#a09c9c"), PorterDuff.Mode.SRC_IN);
        Objects.requireNonNull(Objects.requireNonNull(bottomMainTab.getTabAt(2)).getIcon()).setColorFilter(Color.parseColor("#a09c9c"), PorterDuff.Mode.SRC_IN);
        Objects.requireNonNull(Objects.requireNonNull(bottomMainTab.getTabAt(3)).getIcon()).setColorFilter(Color.parseColor("#a09c9c"), PorterDuff.Mode.SRC_IN);

        break;
      case 1:
        Objects.requireNonNull(Objects.requireNonNull(bottomMainTab.getTabAt(0)).getIcon()).setColorFilter(Color.parseColor("#a09c9c"), PorterDuff.Mode.SRC_IN);
        Objects.requireNonNull(Objects.requireNonNull(bottomMainTab.getTabAt(1)).getIcon()).setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_IN);
        Objects.requireNonNull(Objects.requireNonNull(bottomMainTab.getTabAt(2)).getIcon()).setColorFilter(Color.parseColor("#a09c9c"), PorterDuff.Mode.SRC_IN);
        Objects.requireNonNull(Objects.requireNonNull(bottomMainTab.getTabAt(3)).getIcon()).setColorFilter(Color.parseColor("#a09c9c"), PorterDuff.Mode.SRC_IN);
        break;
      case 2:
        Objects.requireNonNull(Objects.requireNonNull(bottomMainTab.getTabAt(0)).getIcon()).setColorFilter(Color.parseColor("#a09c9c"), PorterDuff.Mode.SRC_IN);
        Objects.requireNonNull(Objects.requireNonNull(bottomMainTab.getTabAt(1)).getIcon()).setColorFilter(Color.parseColor("#a09c9c"), PorterDuff.Mode.SRC_IN);
        Objects.requireNonNull(Objects.requireNonNull(bottomMainTab.getTabAt(2)).getIcon()).setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_IN);
        Objects.requireNonNull(Objects.requireNonNull(bottomMainTab.getTabAt(3)).getIcon()).setColorFilter(Color.parseColor("#a09c9c"), PorterDuff.Mode.SRC_IN);
        break;
      case 3:
        Objects.requireNonNull(Objects.requireNonNull(bottomMainTab.getTabAt(0)).getIcon()).setColorFilter(Color.parseColor("#a09c9c"), PorterDuff.Mode.SRC_IN);
        Objects.requireNonNull(Objects.requireNonNull(bottomMainTab.getTabAt(1)).getIcon()).setColorFilter(Color.parseColor("#a09c9c"), PorterDuff.Mode.SRC_IN);
        Objects.requireNonNull(Objects.requireNonNull(bottomMainTab.getTabAt(2)).getIcon()).setColorFilter(Color.parseColor("#a09c9c"), PorterDuff.Mode.SRC_IN);
        Objects.requireNonNull(Objects.requireNonNull(bottomMainTab.getTabAt(3)).getIcon()).setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_IN);
        break;
    }
  }

  private void getIntentData() {
    Intent intent = getIntent();
    token = intent.getStringExtra(TOKEN);
  }

  @Override
  public void onTabSelected(TabLayout.Tab tab) {
    mainViewPager.setCurrentItem(tab.getPosition());
    updateBottomTabView(tab.getPosition());
  }

  @Override
  public void onTabUnselected(TabLayout.Tab tab) {

  }

  @Override
  public void onTabReselected(TabLayout.Tab tab) {

  }
}
package com.example.aisletest.HomeScreen;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.aisletest.DisCover.DisCoverFragment;

import org.jetbrains.annotations.NotNull;

public class PagerAdapter extends FragmentStatePagerAdapter {

  private int tabCount;
  private String token;

  public PagerAdapter(FragmentManager supportFragmentManager, int tabCount, String token) {
    super(supportFragmentManager);
    this.tabCount = tabCount;
    this.token = token;
  }


  @Override
  public Fragment getItem(int position) {

    switch (position) {
      case 0:
        return new DisCoverFragment(token);
      case 1:
        return new DisCoverFragment(token);
      case 2:
        return new DisCoverFragment(token);
      case 3:
        return new DisCoverFragment(token);
      default:
        return null;
    }

  }

  @Override
  public int getCount() {
    return tabCount;
  }
}

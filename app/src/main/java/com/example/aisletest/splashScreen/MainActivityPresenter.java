package com.example.aisletest.splashScreen;

public class MainActivityPresenter implements MainContract.MainPresenter {

  private MainContract.MainView mainView;

  MainActivityPresenter(Object view){
    mainView = (MainActivity)view;
  }

}

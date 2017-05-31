package com.owo.common.model;

public class RunningProvder<T> implements DataProvider<T> {
  private boolean mIsRunning;
  private DataProvider<T> mSubDataProvider;

  public RunningProvder(DataProvider<T> provider) {
    mSubDataProvider = provider;
  }

  @Override
  public void request(final DataCallback<T> callback) {
    mIsRunning = true;
    mSubDataProvider.request(new DataCallback<T>() {
      @Override
      public void onResult(Result<T> result) {
        mIsRunning = false;
        callback.onResult(result);
      }
    });
  }

  @Override
  public boolean hasMore() {
    return mIsRunning;
  }
}

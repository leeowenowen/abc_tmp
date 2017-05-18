package com.owo.common.model;

import com.owo.common.fsm.RV;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class UntilOneDataProvider<T> implements DataProvider<T> {
  private static Executor sSequenceExecutor = Executors.newSingleThreadExecutor();
  private List<DataProvider<T>> mChildren;

  public UntilOneDataProvider<T> first(DataProvider<T> dataProvider) {
    mChildren = new ArrayList<>();
    return then(dataProvider);
  }

  public UntilOneDataProvider<T> then(DataProvider<T> dataProvider) {
    mChildren.add(dataProvider);
    return this;
  }

  private int mCurrentProviderIndex = -1;
  private Result mResult;
  // FSM
  private final static int STATE_NONE = 0;
  private final static int STATE_INIT = 1;
  private final static int STATE_REQUEST_DATA = 2;
  private final static int STATE_REQUEST_DATA_COMPLETE = 3;
  private final static int STATE_COMPLETE = 4;
  private int nextState;

  private int doLoop(int result) {
    int rv = result;
    do {
      int state = nextState;
      nextState = STATE_NONE;
      switch (state) {
        case STATE_INIT:
          rv = handleInit();
          break;
        case STATE_REQUEST_DATA:
          rv = handleRequestData();
          break;
        case STATE_REQUEST_DATA_COMPLETE:
          rv = handleRequestDataComplete();
          break;
        case STATE_COMPLETE:
          rv = handleComplete();
          break;
        default:
          break;
      }
    } while (rv != RV.PENDING && nextState != STATE_NONE);
    return rv;
  }

  private void switchState(final int nextState) {
    sSequenceExecutor.execute(new Runnable() {
      @Override
      public void run() {
        UntilOneDataProvider.this.nextState = nextState;
        doLoop(nextState);
      }
    });
  }

  private int handleInit() {
    if (nextState != STATE_NONE) {
      mResult = Result.make(ResultCode.ERROR_REENTRY, "call init when already running !", null);
      switchState(STATE_COMPLETE);
      return RV.ERROR;
    }
    if (mChildren == null || mChildren.isEmpty()) {
      mResult = Result.make(ResultCode.ERROR_INTERNAL, "no child provider !", null);
      switchState(STATE_COMPLETE);
      return RV.ERROR;
    }
    mCurrentProviderIndex = 0;
    switchState(STATE_REQUEST_DATA);
    return RV.OK;
  }

  private int handleRequestData() {
    DataProvider<T> provider = mChildren.get(mCurrentProviderIndex);
    provider.request(new DataCallback<T>() {
      @Override
      public void onResult(Result<T> result) {
        if (result.success()) {
          mResult = result;
          switchState(STATE_COMPLETE);
        } else {
          switchState(STATE_REQUEST_DATA_COMPLETE);
        }
      }
    });
    return RV.PENDING;
  }

  private int handleRequestDataComplete() {
    mCurrentProviderIndex++;
    if (mCurrentProviderIndex == mChildren.size() - 1) {
      mResult = Result.make(ResultCode.ERROR_NO_DATA, "no success provider !", null);
      switchState(STATE_COMPLETE);
      return RV.ERROR;
    }
    switchState(STATE_REQUEST_DATA);
    return RV.OK;
  }

  private int handleComplete() {
    mCallback.onResult(mResult);
    return RV.OK;
  }

  private DataCallback<T> mCallback;

  @Override
  public void request(DataCallback<T> callback) {
    mCallback = callback;
    switchState(STATE_INIT);
  }
}

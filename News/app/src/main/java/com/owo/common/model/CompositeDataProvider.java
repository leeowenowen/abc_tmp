package com.owo.common.model;

import com.owo.Action;
import com.owo.common.fsm.RV;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CompositeDataProvider<T> implements DataProvider<List<T>> {
  private static Executor sSequenceExecutor = Executors.newSingleThreadExecutor();
  private List<DataProvider<T>> dataProviders;

  public void add(DataProvider<T> dataProvider) {
    if (dataProviders == null) {
      dataProviders = new ArrayList<>();
    }
    dataProviders.add(dataProvider);
  }

  private List<Result> mChildResults;
  private Result mResult;
  // FSM
  private final static int STATE_NONE = 0;
  private final static int STATE_INIT = 1;
  private final static int STATE_REQUEST_DATA = 2;
  private final static int STATE_COMPLETE = 3;
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
        CompositeDataProvider.this.nextState = nextState;
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
    if (dataProviders == null || dataProviders.isEmpty()) {
      mResult = Result.make(ResultCode.ERROR_NO_DATA, "no child provider !", null);
      switchState(STATE_COMPLETE);
      return RV.ERROR;
    }
    mChildResults = new ArrayList<>();
    switchState(STATE_REQUEST_DATA);
    return RV.OK;
  }

  private int handleRequestData() {
    for (DataProvider<T> dataProvider : dataProviders) {
      dataProvider.request(new DataCallback<T>() {
        @Override
        public void onResult(final Result<T> result) {
          sSequenceExecutor.execute(new Runnable() {
            @Override
            public void run() {
              mChildResults.add(result);
              if (mChildResults.size() == dataProviders.size()) {
                switchState(STATE_COMPLETE);
              }
            }
          });
        }
      });
    }
    return RV.PENDING;
  }

  private int handleComplete() {
    List<T> results = new ArrayList<>();
    for (Result<T> result : mChildResults) {
      if (result.success()) {
        results.add(result.data());
      }
    }
    if (results.size() > 0) {
      mCallback.onResult(Result.make(ResultCode.SUCCESS, "", results));
    } else {
      mCallback.onResult(Result.make(ResultCode.ERROR_NO_DATA, "", (List<T>) null));
    }
    return RV.OK;
  }

  private DataCallback<List<T>> mCallback;

  @Override
  public void request(DataCallback<List<T>> callback) {
    mCallback = callback;
    switchState(STATE_INIT);
  }

  @Override
  public boolean hasMore() {
    return !mResult.success() && nextState != STATE_COMPLETE;
  }
}

package com.owo.common.ui;

import android.support.v4.util.SparseArrayCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public abstract class MultiTypeListAdapter extends BaseAdapter {
  private SparseArrayCompat<Stack<View>> mViewCaches = new SparseArrayCompat<>();
  private Map<Class, Integer> mViewTypes = new HashMap<>();
  private SparseArrayCompat<ItemViewMaker> mItemViewMaker = new SparseArrayCompat<>();

  public interface ItemViewMaker {
    View make(View v, Object data);
  }

  public MultiTypeListAdapter() {
  }

  public MultiTypeListAdapter registerViewType(int type,
                                               Class viewClass,
                                               ItemViewMaker itemViewMaker) {
    mViewTypes.put(viewClass, type);
    mItemViewMaker.put(type, itemViewMaker);
    return this;
  }

  private int getViewType(View v) {
    Class cls = v.getClass();
    return mViewTypes.get(cls);
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    View ret = null;
    int newType = getItemViewType(position);
    if (convertView != null) {
      int oldType = getViewType(convertView);
      if (oldType == newType) {
        ret = convertView;
      } else {
        Stack<View> viewCache = mViewCaches.get(oldType);
        if (viewCache == null) {
          viewCache = new Stack<>();
          mViewCaches.put(oldType, viewCache);
        }
        viewCache.push(convertView);
      }
    }
    ItemViewMaker itemViewMaker = mItemViewMaker.get(newType);
    Object data = getItem(position);
    ret = itemViewMaker.make(ret, data);
    return ret;
  }

  @Override
  public int getItemViewType(int position) {
    return super.getItemViewType(position);
  }
}

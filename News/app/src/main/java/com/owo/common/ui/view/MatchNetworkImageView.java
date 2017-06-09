package com.owo.common.ui.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.owo.common.utils.UIUtils;

public class MatchNetworkImageView extends android.support.v7.widget.AppCompatImageView {
  private String mUrl;

  private int mDefaultImageId;
  private int mErrorImageId;
  private ImageLoader mImageLoader;
  private ImageLoader.ImageContainer mImageContainer;

  public MatchNetworkImageView(Context context) {
    this(context, null);
  }

  public MatchNetworkImageView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public MatchNetworkImageView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }
  public void setImageUrl(String url, ImageLoader imageLoader) {
    UIUtils.setBackgroundDrawable(this, null);
    mUrl = url;
    mImageLoader = imageLoader;
    // The URL has potentially changed. See if we need to load it.
    loadImageIfNecessary(false);
  }
  public String getImageURL() {
    return mUrl;
  }

  public void setDefaultImageResId(int defaultImage) {
    mDefaultImageId = defaultImage;
  }

  public void setErrorImageResId(int errorImage) {
    mErrorImageId = errorImage;
  }

  void loadImageIfNecessary(final boolean isInLayoutPass) {
    if(mImageLoader == null){
      return;
    }
    int width = getWidth();
    int height = getHeight();
    ImageLoader.ImageContainer newContainer = mImageLoader.get(mUrl, new ImageLoader.ImageListener() {
      @Override
      public void onErrorResponse(VolleyError error) {
        if (mErrorImageId != 0) {
          setBackgroundResource(mErrorImageId);
        }
      }

      @Override
      public void onResponse(final ImageLoader.ImageContainer response, boolean isImmediate) {
        // If this was an immediate response that was delivered inside of a layout
        // pass do not set the image immediately as it will trigger a requestLayout
        // inside of a layout. Instead, defer setting the image by posting back to
        // the main thread.
        if (isImmediate && isInLayoutPass) {
          post(new Runnable() {
            @Override
            public void run() {
              onResponse(response, false);
            }
          });
          return;
        }

        if (response.getBitmap() != null) {
          UIUtils.setBackgroundDrawable(MatchNetworkImageView.this, new BitmapDrawable(getResources(),response.getBitmap()));
        } else if (mDefaultImageId != 0) {
          setBackgroundResource(mDefaultImageId);
        }
      }
    }, width, height, getScaleType());

    // update the ImageContainer to be the new bitmap container.
    mImageContainer = newContainer;
  }

  private void setDefaultImageOrNull() {
    if (mDefaultImageId != 0) {
      setImageResource(mDefaultImageId);
    } else {
      setImageBitmap(null);
    }
  }

  @Override
  protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    super.onLayout(changed, left, top, right, bottom);
    loadImageIfNecessary(true);
  }

  @Override
  protected void onDetachedFromWindow() {
    if (mImageContainer != null) {
      // If the view was bound to an image request, cancel it and clear
      // out the image from the view.
      mImageContainer.cancelRequest();
      setImageBitmap(null);
      // also clear out the container so we can reload the image if necessary.
      mImageContainer = null;
    }
    super.onDetachedFromWindow();
  }

  @Override
  protected void drawableStateChanged() {
    super.drawableStateChanged();
    invalidate();
  }
}

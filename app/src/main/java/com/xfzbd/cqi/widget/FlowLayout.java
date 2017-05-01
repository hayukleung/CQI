package com.xfzbd.cqi.widget;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

/**
 * View
 * com.hayukleung.view.UsingViewGroup
 * FlowLayout.java
 *
 * by hayukleung
 * at 2017-03-23 15:44
 */

/**
 * 流式布局
 */
public class FlowLayout extends BaseViewGroup {

  /**
   * 存储所有的View，按行记录
   */
  private List<List<View>> mLineList = new ArrayList<>();
  /**
   * 记录每一行的最大高度
   */
  private List<Integer> mLineHeightList = new ArrayList<>();

  private List<View> mTemp = new ArrayList<>();
  /**
   * 每一行的剩余间隙
   */
  private List<Integer> mGapList = new ArrayList<>();

  public FlowLayout(Context context) {
    super(context);
  }

  public FlowLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }

  @Override
  protected void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
  }

  @Override protected void onLayout(boolean changed, int l, int t, int r, int b) {
    mLineList.clear();
    mLineHeightList.clear();
    mGapList.clear();

    int width = getWidth();

    int lineWidth = 0;
    int lineHeight = 0;
    // 存储每一行所有的childView
    List<View> line = new ArrayList<>();

    int cCount = getChildCount();

    // 遍历所有的孩子
    for (int i = 0; i < cCount; i++) {
      View child = getChildAt(i);
      if (View.GONE == child.getVisibility()) {
        continue;
      }
      MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
      int childWidth = child.getMeasuredWidth();
      int childHeight = child.getMeasuredHeight();

      // 如果已经需要换行
      if (childWidth + lp.leftMargin + lp.rightMargin + lineWidth
          > width - getPaddingLeft() - getPaddingRight()) {

        mGapList.add(width - getPaddingLeft() - getPaddingRight() - lineWidth);
        // 记录这一行所有的View以及最大高度
        mLineHeightList.add(lineHeight);
        // 将当前行的childView保存，然后开启新的ArrayList保存下一行的childView
        mLineList.add(line);
        // 重置行宽
        lineWidth = 0;
        lineHeight = 0;
        line = new ArrayList<>();
      }
      // 如果不需要换行，则累加
      lineWidth += childWidth + lp.leftMargin + lp.rightMargin;
      lineHeight = Math.max(lineHeight, childHeight + lp.topMargin + lp.bottomMargin);
      line.add(child);
    }
    // 记录最后一行
    mLineHeightList.add(lineHeight);
    mLineList.add(line);
    mGapList.add(width - getPaddingLeft() - getPaddingRight() - lineWidth);

    int left = getPaddingLeft();
    int top = getPaddingTop();
    // 得到总行数
    int lineCount = mLineList.size();
    for (int i = 0; i < lineCount; i++) {
      // 每一行的所有的views
      line = mLineList.get(i);
      // 当前行的最大高度
      lineHeight = mLineHeightList.get(i);

      mTemp.clear();
      mTemp.addAll(line);
      for (int j = 0; j < line.size(); j++) {
        View child = line.get(j);
        if (View.GONE == child.getVisibility()) {
          mTemp.remove(child);
        }
      }

      int gap = 1 < mTemp.size() ? mGapList.get(i) / (mTemp.size() - 1) : 0;

      // 遍历当前行所有的View
      for (int j = 0; j < line.size(); j++) {
        View child = line.get(j);
        if (child.getVisibility() == View.GONE) {
          continue;
        }
        MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

        // 计算childView的left top right bottom
        int lc = left + lp.leftMargin;
        int tc = top + lp.topMargin;
        int rc = lc + child.getMeasuredWidth();
        int bc = tc + child.getMeasuredHeight();

        child.layout(lc, tc, rc, bc);
        left += child.getMeasuredWidth() + lp.rightMargin + lp.leftMargin + gap;
      }
      left = getPaddingLeft();
      top += lineHeight;
    }
  }

  @Override public LayoutParams generateLayoutParams(AttributeSet attrs) {
    return new MarginLayoutParams(getContext(), attrs);
  }

  @Override public int getPaddingLeft() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
      return 0 == super.getPaddingStart() ? super.getPaddingLeft() : super.getPaddingStart();
    } else {
      return super.getPaddingLeft();
    }
  }

  @Override public int getPaddingRight() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
      return 0 == super.getPaddingEnd() ? super.getPaddingRight() : super.getPaddingEnd();
    } else {
      return super.getPaddingRight();
    }
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

    super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    // 获得FlowLayout的父容器为它设置的测量模式和大小
    int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
    int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
    // int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
    int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

    // 如果是wrap_content情况下，记录宽和高
    int width = 0;
    int height = 0;
    // 记录每一行的宽度，width不断取最大宽度
    int lineWidth = 0;
    // 每一行的高度，累加至height
    int lineHeight = 0;

    int cCount = getChildCount();

    // 遍历每个子元素
    for (int i = 0; i < cCount; i++) {
      View child = getChildAt(i);
      if (View.GONE == child.getVisibility()) {
        // 如果是最后一个child
        // 将当前记录的最大宽度和当前lineWidth做比较
        if (i == cCount - 1) {
          width = Math.max(width, lineWidth);
          height += lineHeight;
        }
        continue;
      }
      // 测量每一个child的宽和高
      measureChild(child, widthMeasureSpec, heightMeasureSpec);
      // 得到child的lp
      MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
      // 当前child实际占据的宽度
      int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
      // 当前child实际占据的高度
      int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

      // 如果加入当前child超出FlowLayout最大宽度
      // 则将最大宽度给width，累加height
      // 然后开启新行
      if (lineWidth + childWidth > sizeWidth - getPaddingLeft() - getPaddingRight()) {
        width = Math.max(lineWidth, childWidth);
        // 重新开启新行，开始记录
        lineWidth = childWidth;
        // 累加当前高度
        height += lineHeight;
        // 开启记录下一行的高度
        lineHeight = childHeight;
      } else
      // 否则
      // 累加lineWidth
      // lineHeight取最大高度
      {
        lineWidth += childWidth;
        lineHeight = Math.max(lineHeight, childHeight);
      }
      // 如果是最后一个child
      // 将当前记录的最大宽度和当前lineWidth做比较
      if (i == cCount - 1) {
        width = Math.max(width, lineWidth);
        height += lineHeight;
      }
    }

    setMeasuredDimension(
        // ((modeWidth == MeasureSpec.EXACTLY) ? sizeWidth : width),
        sizeWidth, ((modeHeight == MeasureSpec.EXACTLY) ? sizeHeight : height)
            + getPaddingTop()
            + getPaddingBottom());
  }
}

package com.wpt.ellipsize;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * @author wpt
 * @dec 在textView末尾添加类似 ...展开文案 或者[🔽]图片
 */
public class EllipsizeTextView extends AppCompatTextView {

    //在第几行显示展开
    private int minLines;
    //结尾字符，比如：...展开
    private String endText;
    //结尾字符颜色
    private int endColor;
    //结尾图片
    private Drawable endIcon;
    //全部文字
    private String originText;
    private Context context;

    private boolean endIsBold;

    //是否展开了
    private boolean isExpand;

    //收起的文字
    private SpannableStringBuilder ellipText;

    public EllipsizeTextView(Context context) {
        super(context);
        this.context = context;
    }

    public EllipsizeTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public EllipsizeTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        //获取自定义属性的值
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.EllipsizeTextView, defStyleAttr, 0);
        minLines = a.getInt(R.styleable.EllipsizeTextView_minLines, 1);
        endIcon = a.getDrawable(R.styleable.EllipsizeTextView_endIcon);
        endColor = a.getColor(R.styleable.EllipsizeTextView_endColor, Color.BLACK);
        endText = a.getString(R.styleable.EllipsizeTextView_endText);
        originText = a.getString(R.styleable.EllipsizeTextView_originText);
        endIsBold = a.getBoolean(R.styleable.EllipsizeTextView_endIsBold,false);
        a.recycle();  //注意回收
        if (!TextUtils.isEmpty(originText)) {
            setText(originText);
        }

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(ellipText)){
                    return;
                }
                EllipsizeTextView.super.setText(isExpand ? ellipText : originText);
                isExpand = !isExpand;
            }
        });

    }

    public void setMinLine(int minLines) {
        this.minLines = minLines;
    }

    public void setEndText(String endText) {
        this.endText = endText;
    }

    public void setEndColor(int endColor) {
        this.endColor = endColor;
    }

    public void setEndIcon(Drawable endIcon) {
        this.endIcon = endIcon;
    }

    public void setText(String originText) {
        this.originText = originText;
        if (!TextUtils.isEmpty(endText)) {
            ellipsizeText();
        } else if (endIcon != null) {
            ellipsizeImage();
        }
        EllipsizeTextView.super.setText(originText);
    }

    /**
     * 设置textView结尾...后面显示的文字和颜色
     *
     */
    private void ellipsizeText() {
        if (TextUtils.isEmpty(originText)) {
            return;
        }
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver
                .OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (getLineCount() > minLines) {
                    isExpand = false;
                    int paddingLeft = getPaddingLeft();
                    int paddingRight = getPaddingRight();
                    TextPaint paint = getPaint();
                    float moreText = getTextSize() * (endText.length() + 1);
                    float availableTextWidth = (getWidth() - paddingLeft - paddingRight) *
                            minLines - moreText;
                    CharSequence ellipsizeStr = TextUtils.ellipsize(originText, paint,
                            availableTextWidth, TextUtils.TruncateAt.END);
                    if (ellipsizeStr.length() < originText.length()) {
                        CharSequence temp = ellipsizeStr + endText;
                        ellipText = new SpannableStringBuilder(temp);
                        ellipText.setSpan(new ForegroundColorSpan(endColor),
                                temp.length() - endText.length() - 1, temp.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                        if (endIsBold){
                            StyleSpan span = new StyleSpan(Typeface.BOLD);
                            ellipText.setSpan(span,
                                    temp.length() - endText.length() - 1, temp.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                        }
                        EllipsizeTextView.super.setText(ellipText);
                    } else {
                        EllipsizeTextView.super.setText(originText);
                    }
                }
                if (Build.VERSION.SDK_INT >= 16) {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            }
        });
    }

    private void ellipsizeImage() {
        if (TextUtils.isEmpty(originText) || endIcon == null) {
            return;
        }
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver
                .OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (getLineCount() > minLines) {
                    isExpand = false;
                    int paddingLeft = getPaddingLeft();
                    int paddingRight = getPaddingRight();
                    TextPaint paint = getPaint();
                    float moreText = endIcon.getMinimumWidth() * minLines;
                    float availableTextWidth = (getWidth() - paddingLeft - paddingRight) *
                            minLines - moreText;
                    CharSequence ellipsizeStr = TextUtils.ellipsize(originText, paint,
                            availableTextWidth, TextUtils.TruncateAt.END);
                    if (ellipsizeStr.length() < originText.length()) {
                        CharSequence temp = ellipsizeStr + "#";
                        ellipText = new SpannableStringBuilder(temp);
                        endIcon.setBounds(0, 0, endIcon.getMinimumWidth(), endIcon.getMinimumHeight());
                        ImageSpan span = new ImageSpan(endIcon, ImageSpan.ALIGN_BASELINE);
                        ellipText.setSpan(span,
                                temp.length() - 1, temp.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                        EllipsizeTextView.super.setText(ellipText);
                    } else {
                        EllipsizeTextView.super.setText(originText);
                    }
                }
                if (Build.VERSION.SDK_INT >= 16) {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            }
        });
    }

}

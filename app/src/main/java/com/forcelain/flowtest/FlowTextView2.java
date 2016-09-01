package com.forcelain.flowtest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class FlowTextView2 extends View {

    public static final int ALIGN_LEFT = 0;
    public static final int ALIGN_RIGHT = 1;
    private final TextPaint textPaint;
    private String text = "";
    private Rect flowBounds;
    private Rect tempRect = new Rect();
    private int align;
    private int breakPos;
    private StaticLayout layou1;
    private StaticLayout layou2;

    public FlowTextView2(Context context) {
        super(context);
    }

    public FlowTextView2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public FlowTextView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        textPaint = new TextPaint();
        textPaint.setTextSize(50);
        textPaint.setAntiAlias(true);
    }

    public void setFlowBounds(Rect flowBounds, int align) {
        this.flowBounds = flowBounds;
        this.align = align;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = getMeasuredWidth();
//        int start = 0;
//        int end = text.length();
//        int heightSum = 0;
//        while (start < end) {
//            int count;
//            int width;
//            if (heightSum > flowBounds.height()) {
//                width = measuredWidth;
//                if (breakPos == 0) {
//                    breakPos = start;
//                }
//            } else {
//                width = measuredWidth - flowBounds.width();
//            }
//            count = textPaint.breakText(text, start, end, true, width, null);
//
//            textPaint.getTextBounds(text, start, start + count, tempRect);
//            int height = tempRect.height();
//            heightSum += height;
//            start += count;
//        }

        StaticLayout total = new StaticLayout(text, textPaint, getMeasuredWidth() - flowBounds.width(), Layout.Alignment.ALIGN_NORMAL, 1, 1, true);
        int lineCount = total.getLineCount();
        for (int i = 0; i < lineCount; i++) {
            int lineBottom = total.getLineBottom(i);
            if (lineBottom > flowBounds.height()) {
                breakPos = total.getLineEnd(i);
                break;
            }
        }

        String substring = text.substring(0, breakPos);
        layou1 = new StaticLayout(substring, textPaint, getMeasuredWidth() - flowBounds.width(), Layout.Alignment.ALIGN_NORMAL, 1, 1, true);

        String substring2 = text.substring(breakPos);
        layou2 = new StaticLayout(substring2, textPaint, getMeasuredWidth(), Layout.Alignment.ALIGN_NORMAL, 1, 1, true);

        int height = layou1.getHeight() + layou2.getHeight();

        setMeasuredDimension(measuredWidth, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int offset = 40;

        if (align == ALIGN_LEFT) {
            canvas.save();
            canvas.translate(flowBounds.width(), 0);
            layou1.draw(canvas);
            canvas.restore();
        } else {
            layou1.draw(canvas);
        }

        canvas.save();
        canvas.translate(0, layou1.getHeight());
        layou2.draw(canvas);
        canvas.restore();

    }
}

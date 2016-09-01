package com.forcelain.flowtest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class FlowTextView extends View {

    public static final int ALIGN_LEFT = 0;
    public static final int ALIGN_RIGHT = 1;
    private final List<Line> textLines = new ArrayList<>();
    private final TextPaint textPaint;
    private String text = "";
    private Rect flowBounds;
    private Rect tempRect = new Rect();
    private int align;

    public FlowTextView(Context context) {
        super(context);
    }

    public FlowTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public FlowTextView(Context context, AttributeSet attrs, int defStyleAttr) {
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
        textLines.clear();
        int measuredWidth = getMeasuredWidth();
        int start = 0;
        int end = text.length();
        int heightSum = 0;
        while (start < end) {
            int count;
            int width;
            if (heightSum > flowBounds.height()) {
                width = measuredWidth;
            } else {
                width = measuredWidth - flowBounds.width();
            }
            count = textPaint.breakText(text, start, end, true, width, null);

            textPaint.getTextBounds(text, start, start + count, tempRect);
            int height = tempRect.height();
            textLines.add(new Line(start, start + count, height));
            heightSum += height;
            start += count;
        }
        setMeasuredDimension(measuredWidth, heightSum);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int offset = 40;
        for (Line textLine : textLines) {
            int left;
            if (offset > flowBounds.height()) {
                left = 0;
            } else {
                left = align == ALIGN_RIGHT ? 0 : flowBounds.width();
            }
            canvas.drawText(text, textLine.start, textLine.end, left, offset, textPaint);
            offset += textLine.height;
        }
    }

    private static class Line {
        final int start;
        final int end;
        final int height;

        private Line(int start, int end, int height) {
            this.start = start;
            this.end = end;
            this.height = height;
        }
    }
}

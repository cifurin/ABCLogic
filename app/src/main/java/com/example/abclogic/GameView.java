package com.example.abclogic;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Chris on 29/05/2016.
 */
public class GameView extends View {
    private static final String TAG = "GameView";
    private Paint background = new Paint();
    private Paint paint = new Paint();
    private float scalex;

    private final Rect textBounds = new Rect(); //don't new this up in a draw method

    private ArrayList<Cell> Grid = new ArrayList<Cell>();

    private class Cell extends RectF {
        public Cell(float left, float top, float right, float bottom) {
            super(left, top, right, bottom);
        }

        private int row;
        private int col;
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GameView(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int size = 0;
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        size = width;
        setMeasuredDimension(size, size);
        //scalex = getWidth() / 7f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        scalex = getWidth() / 7f;    //this is the width of a cell for the device

        Log.d(TAG, "width is " + getWidth());
        Log.d(TAG, "height is " + getHeight());
        Log.d(TAG, "scalex is " + scalex);

        paint.setAntiAlias(true);
        paint.setColor(Color.MAGENTA);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeWidth(5f);

        background.setColor(Color.CYAN);
        canvas.drawRect(0, 0, getWidth(), getHeight(), background);

        paint.setColor(Color.BLUE);
        canvas.drawRect(scalex, scalex, 6 * scalex, 6 * scalex, paint);

        Grid.clear();

        for (int row = 1; row < 6; row++) {
            for (int col = 1; col < 6; col++) {

                float top = (float) ((row) * scalex);
                float left = (float) ((col) * scalex);
                float right = (float) ((col + 1) * scalex);
                float bottom = (float) ((row + 1) * scalex);

                //Log.d(TAG, "top =" +top);
                //Log.d(TAG, "left =" +left);
                //Log.d(TAG, "right =" +right);
                //Log.d(TAG, "bottom =" +bottom);

                Cell cell = new Cell(left, top, right, bottom);
                cell.row = row;
                cell.col = col;

                Grid.add(cell);

                canvas.drawRect(left, top, right, bottom, paint);
            }
        }

        paint.setColor(Color.BLACK);
        paint.setTextSize(50);
        paint.setTextAlign(Paint.Align.CENTER);

/*        String text = "A";
        Log.d(TAG, "exactx" + textBounds.exactCenterX());
        Log.d(TAG, "exacty" + textBounds.exactCenterY());

        paint.getTextBounds(text, 0, text.length(), textBounds);*/

        //draw row clues
        for (int row = 1; row < 6; row++) {
            if (PuzzleSolver.getRowClue(1, row) != null) {
                canvas.drawText(PuzzleSolver.getRowClue(1, row), (scalex / 2f), (row * scalex + (scalex / 2f)) + 18, paint);
            }
            if (PuzzleSolver.getRowClue(2, row) != null) {
                canvas.drawText(PuzzleSolver.getRowClue(2, row), 6 * scalex + (scalex / 2f), (row * scalex + (scalex / 2f)) + 18, paint);
            }
        }

        //draw col clues
        for (int col = 1; col < 6; col++) {
            if (PuzzleSolver.getColClue(1, col) != null) {
                canvas.drawText(PuzzleSolver.getColClue(1, col), col * scalex + (scalex / 2f), (scalex / 2f) + 18, paint);
            }
            if (PuzzleSolver.getColClue(2, col) != null) {
                canvas.drawText(PuzzleSolver.getColClue(2, col), col * scalex + (scalex / 2f), 6 * scalex + (scalex / 2f) + 18, paint);
            }
        }

        for (int row = 1; row < 6; row++) {
            for (int col = 1; col < 6; col++) {
                Log.d(TAG, "PuzzleSolver Grid value = " + PuzzleSolver.getChoice(row, col));
                if (PuzzleSolver.getChoice(row, col) != null) {
                    canvas.drawText(PuzzleSolver.getChoice(row, col), ((col * scalex + (scalex / 2f))), (row * scalex + (scalex / 2f)) + 18, paint);
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int eventX = (int) event.getX();
        int eventY = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                for (Cell cell : Grid) {
                    if (cell.contains(eventX, eventY)) {
                        Log.d(TAG, "row is " + cell.row);
                        Log.d(TAG, "col is " + cell.col);
                        PuzzleSolver.setCell(cell.row, cell.col);
                    }
                }
                Log.d(TAG, "eventX is " + eventX);
                Log.d(TAG, "eventY is " + eventY);

                invalidate();

                break;
            default:
                return false;
        }
        return true;
    }
}

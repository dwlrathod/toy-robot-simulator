package com.dwlrathod.toyrobotsimulator.canvas;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.res.ResourcesCompat;

import com.dwlrathod.toyrobotsimulator.R;
import com.dwlrathod.toyrobotsimulator.modal.Face;
import com.dwlrathod.toyrobotsimulator.modal.Position;

public class TableTop extends View implements View.OnTouchListener {

    private final int STROKE_WIDTH = 5;
    private final int MATRIX_SIZE = 5;
    private int SQUARE_SIZE;

    private Canvas canvas;
    private Position currentPosition;

    public TableTop(Context context) {
        super(context);
    }

    public TableTop(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TableTop(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, widthMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas = canvas;
        int width = getWidth();
        @SuppressLint("DrawAllocation") Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(0);
        canvas.drawRect(0, 0, width - STROKE_WIDTH, width - STROKE_WIDTH, paint);
        SQUARE_SIZE = width / MATRIX_SIZE;
        drawTableTopCoordinates(canvas, SQUARE_SIZE);
        if (currentPosition != null) {
            drawRobot();
        }
    }

    private void drawTableTopCoordinates(Canvas canvas, int size) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                Paint paint = new Paint();
                paint.setColor(Color.WHITE);
                Rect r = new Rect(
                        size * i,
                        size * j,
                        size * (i + 1),
                        size * (j + 1)
                );
                canvas.drawRect(r, paint);
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(STROKE_WIDTH);
                paint.setColor(getContext().getResources().getColor(R.color.black_500));
                canvas.drawRect(new Rect(r.left + STROKE_WIDTH, r.top + STROKE_WIDTH, r.right - STROKE_WIDTH, r.bottom - STROKE_WIDTH), paint);
            }
        }
    }

    public void setOnTouchListener() {
        setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int xPoint = (int) motionEvent.getX() / SQUARE_SIZE;
        int yPoint = (int) motionEvent.getY() / SQUARE_SIZE;
        currentPosition = new Position(xPoint, yPoint, -1);
        return false;
    }

    private void drawRobot() {
        int rId = 0;
        switch (currentPosition.getF()) {
            case Face.SOUTH:
                rId = R.drawable.robo_south;
                break;
            case Face.WEST:
                rId = R.drawable.robo_west;
                break;
            case Face.EAST:
                rId = R.drawable.robo_east;
                break;
            case Face.NORTH:
                rId = R.drawable.robo_north;
                break;
        }
        Drawable d = ResourcesCompat.getDrawable(getResources(), rId, getContext().getTheme());
        if (d != null) {
            d.setBounds(currentPosition.getX() * SQUARE_SIZE, currentPosition.getY() * SQUARE_SIZE,
                    (currentPosition.getX() + 1) * SQUARE_SIZE, (currentPosition.getY() + 1) * SQUARE_SIZE);
            d.draw(canvas);
        }
    }

    public void setFace(int face) {
        currentPosition.setF(face);
    }

    public void setCurrentPosition(Position position) {
        currentPosition = position;
    }

    public Position getCurrentPosition() {
        return currentPosition;
    }

    public void changeFace(int change) {
        if (currentPosition != null) {
            int newFace = currentPosition.getF();
            if (change == -1) {
                newFace = newFace == 0 ? 3 : newFace - 1;
            } else {
                newFace = newFace == 3 ? 0 : newFace + 1;
            }
            currentPosition.setF(newFace);
        } else {
            showCurrentPositionError();
        }
    }

    public void move() {
        if (currentPosition != null) {
            switch (currentPosition.getF()) {
                case Face.SOUTH:
                    if (currentPosition.getX() != 0)
                        currentPosition.setX(currentPosition.getX() - 1);
                    else
                        showInvalidMoveError();
                    break;
                case Face.WEST:
                    if (currentPosition.getY() != 0)
                        currentPosition.setY(currentPosition.getY() - 1);
                    else
                        showInvalidMoveError();
                    break;
                case Face.EAST:
                    if (currentPosition.getY() != MATRIX_SIZE - 1)
                        currentPosition.setY(currentPosition.getY() + 1);
                    else
                        showInvalidMoveError();
                    break;
                case Face.NORTH:
                    if (currentPosition.getX() != MATRIX_SIZE - 1)
                        currentPosition.setX(currentPosition.getX() + 1);
                    else
                        showInvalidMoveError();
                    break;
            }
        } else {
            showCurrentPositionError();
        }
    }

    public void report() {
        if (currentPosition != null) {
            String message = String.format(getResources().getString(R.string.report_message),
                    currentPosition.getX(),
                    currentPosition.getY(),
                    getFaceName(currentPosition.getF()));
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Output");
            builder.setMessage(message);
            builder.setPositiveButton("OK", null);
            AlertDialog alert = builder.create();
            alert.show();
            currentPosition = null;
        } else {
            showCurrentPositionError();
        }
    }

    public void validateCurrentPosition() {
        if (currentPosition != null && currentPosition.getF() == -1) {
            currentPosition = null;
        }
    }

    public String getFaceName(int face) {
        switch (face) {
            case Face.SOUTH:
                return "South";
            case Face.WEST:
                return "West";
            case Face.EAST:
                return "East";
            case Face.NORTH:
                return "North";
        }
        return "";
    }

    private void showCurrentPositionError() {
        Toast.makeText(getContext(), getResources().getString(R.string.current_position_error), Toast.LENGTH_SHORT).show();
    }

    private void showInvalidMoveError() {
        Toast.makeText(getContext(), getResources().getString(R.string.invalid_move), Toast.LENGTH_SHORT).show();
    }
}

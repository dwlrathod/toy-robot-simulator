package com.dwlrathod.toyrobotsimulator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.dwlrathod.toyrobotsimulator.canvas.TableTop;
import com.dwlrathod.toyrobotsimulator.modal.Action;
import com.dwlrathod.toyrobotsimulator.modal.Face;

public class MainActivity extends AppCompatActivity {

    TableTop tableTop;
    Button btnPlace, btnLeft, btnRight, btnMove, btnReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void performAction(Action action) {
        switch (action) {
            case PLACE:
                registerTableTopClick();
                tableTop.setOnTouchListener();
                tableTop.setCurrentPosition(null);
                break;
            case MOVE:
                tableTop.move();
                break;
            case LEFT:
                tableTop.changeFace(1);
                break;
            case RIGHT:
                tableTop.changeFace(-1);
                break;
            case REPORT:
                tableTop.report();
                break;
        }
        tableTop.invalidate();
    }

    private void registerTableTopClick() {
        registerForContextMenu(tableTop);
        tableTop.setOnClickListener(this::openContextMenu);
    }

    private void initView() {
        tableTop = findViewById(R.id.tableTop);
        btnPlace = findViewById(R.id.btnPlace);
        btnPlace.setOnClickListener(view -> performAction(Action.PLACE));
        btnLeft = findViewById(R.id.btnLeft);
        btnLeft.setOnClickListener(view -> performAction(Action.LEFT));
        btnRight = findViewById(R.id.btnRight);
        btnRight.setOnClickListener(view -> performAction(Action.RIGHT));
        btnMove = findViewById(R.id.btnMove);
        btnMove.setOnClickListener(view -> performAction(Action.MOVE));
        btnReport = findViewById(R.id.btnReport);
        btnReport.setOnClickListener(view -> performAction(Action.REPORT));
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Choose direction");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.direction_menu, menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.south:
                tableTop.setFace(Face.SOUTH);
                break;
            case R.id.north:
                tableTop.setFace(Face.NORTH);
                break;
            case R.id.west:
                tableTop.setFace(Face.WEST);
                break;
            case R.id.east:
                tableTop.setFace(Face.EAST);
                break;
        }
        tableTop.invalidate();
        return super.onContextItemSelected(item);
    }

    @Override
    public void onContextMenuClosed(@NonNull Menu menu) {
        tableTop.validateCurrentPosition();
        super.onContextMenuClosed(menu);
    }
}
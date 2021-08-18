package com.dwlrathod.toyrobotsimulator;

import org.junit.Test;

import static org.junit.Assert.*;

import com.dwlrathod.toyrobotsimulator.canvas.TableTop;
import com.dwlrathod.toyrobotsimulator.modal.Face;
import com.dwlrathod.toyrobotsimulator.modal.Position;

public class TableTopUnitTest {

    TableTop tableTop = new TableTop(null);

    @Test
    public void changeFaceTest() {
        Position position = new Position(1, 1, Face.EAST);
        tableTop.setCurrentPosition(position);
        tableTop.changeFace(-1);
        assertEquals(Face.SOUTH, tableTop.getCurrentPosition().getF());
    }

    @Test
    public void moveTest() {
        Position position = new Position(1, 1, Face.EAST);
        tableTop.setCurrentPosition(position);
        tableTop.move();
        Position expectedPosition = new Position(1, 2, Face.EAST);
        assertEquals(expectedPosition.getY(), tableTop.getCurrentPosition().getY());
    }

    @Test
    public void getFaceNameTest() {
        Position position = new Position(1, 1, Face.EAST);
        tableTop.setCurrentPosition(position);
        assertEquals("East", tableTop.getFaceName(position.getF()));
    }
}
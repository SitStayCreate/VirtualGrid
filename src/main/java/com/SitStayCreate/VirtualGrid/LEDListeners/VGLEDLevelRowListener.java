package com.SitStayCreate.VirtualGrid.LEDListeners;

import com.SitStayCreate.CerealOSC.LEDListeners.LEDLevelRowListener;
import com.SitStayCreate.VirtualGrid.VGButtons;

import java.awt.*;
import java.util.List;

public class VGLEDLevelRowListener extends LEDLevelRowListener {

    private VGButtons vgButtons;
    private List<Color> colorValues;

    public VGLEDLevelRowListener(VGButtons vgButtons, List<Color> colorValues){
        setVgButtons(vgButtons);
        setColorValues(colorValues);
    }

    public void setVgButtons(VGButtons vgButtons) {
        this.vgButtons = vgButtons;
    }

    public void setColorValues(List<Color> colorValues) {
        this.colorValues = colorValues;
    }

    @Override
    public void setLEDLevelRow(int xOffset, int gridY, int xCounter, int ledState) {

        //Virtual grids never have more than 8 rows
        if(gridY >= 8){
            return;
        }

        //Varibright supported
        if (ledState < 4) {
            vgButtons.getButton(xOffset + (xCounter),gridY).setBackground(colorValues.get(0));
        } else if (ledState < 8) {
            vgButtons.getButton(xOffset + (xCounter),gridY).setBackground(colorValues.get(1));
        } else if (ledState < 12) {
            vgButtons.getButton(xOffset + (xCounter),gridY).setBackground(colorValues.get(2));
        } else {
            vgButtons.getButton(xOffset + (xCounter),gridY).setBackground(colorValues.get(3));
        }
    }
}

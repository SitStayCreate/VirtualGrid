package com.SitStayCreate.VirtualGrid.LEDListeners;

import com.SitStayCreate.CerealOSC.LEDListeners.LEDLevelMapListener;
import com.SitStayCreate.VirtualGrid.VGButtons;

import java.awt.*;
import java.util.List;

public class VGLEDLevelMapListener extends LEDLevelMapListener {

    private VGButtons vgButtons;
    private List<Color> colorValues;

    public VGLEDLevelMapListener(VGButtons vgButtons, List<Color> colorValues){
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
    public void setLEDLevelMap(int xOffset, int yOffset, int ledState, int counter) {
        int xCounter = counter % 8; // increments to 7 then resets
        int yCounter = (int) Math.floor(counter / 8); // 0-7 = 0, 8-15 = 1, ...

        int gridX = xOffset + xCounter;
        int gridY = yOffset + yCounter;

        //Virtual grids are always 16x8
        if(gridY >= 8){
            return;
        }

        //Translate ledLevel to ledSet
        if(ledState < 4){
            vgButtons.getButton(gridX,gridY).setBackground(colorValues.get(0));
        } else if(ledState < 8){
            vgButtons.getButton(gridX,gridY).setBackground(colorValues.get(1));
        }  else if(ledState < 12){
            vgButtons.getButton(gridX,gridY).setBackground(colorValues.get(2));
        } else {
            vgButtons.getButton(gridX,gridY).setBackground(colorValues.get(3));
        }
    }
}

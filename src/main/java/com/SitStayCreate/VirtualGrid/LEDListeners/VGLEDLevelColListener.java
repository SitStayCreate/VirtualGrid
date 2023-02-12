package com.SitStayCreate.VirtualGrid.LEDListeners;

import com.SitStayCreate.CerealOSC.LEDListeners.LEDLevelColListener;
import com.SitStayCreate.VirtualGrid.VGButtons;

import java.awt.*;
import java.util.List;

public class VGLEDLevelColListener extends LEDLevelColListener {

    private VGButtons vgButtons;
    private List<Color> colorValues;

    public VGLEDLevelColListener(VGButtons vgButtons, List<Color> colorValues){
        setVgButtons(vgButtons);
        this.colorValues = colorValues;
    }

    public void setVgButtons(VGButtons vgButtons) {
        this.vgButtons = vgButtons;
    }

    @Override
    public void setLEDLevelCol(int gridX, int yOffset, int ledState, int yCounter) {

        //Virtual Grids are always 16x8
        if(yOffset == 8){
            return;
        }

        if(yCounter >= 8){
            return;
        }

        //Varibright supported
        if (ledState < 4){
            vgButtons.getButton(gridX, yOffset + yCounter).setBackground(colorValues.get(0));
        }  else if(ledState < 8){
            vgButtons.getButton(gridX, yOffset + yCounter).setBackground(colorValues.get(1));
        }  else if(ledState < 12) {
            vgButtons.getButton(gridX, yOffset + yCounter).setBackground(colorValues.get(2));
        } else {
            vgButtons.getButton(gridX, yOffset + yCounter).setBackground(colorValues.get(3));
        }
    }
}

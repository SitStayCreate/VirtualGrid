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
    public void setLEDLevelRow(List oscList) {
        // Input validation - messages should be xOffset y int[8]
        if(oscList.size() != 10){
            return;
        }

        int xOffset = (int) oscList.get(0);
        int y = (int) oscList.get(1);

        for(int i = 2; i < oscList.size(); i++){
            int ledState = (int) oscList.get(i);
            int x = xOffset + (i - 2);
            //Varibright supported
            if (ledState < 4) {
                vgButtons.getButton(x,y).setBackground(colorValues.get(0));
            } else if (ledState < 8) {
                vgButtons.getButton(x,y).setBackground(colorValues.get(1));
            } else if (ledState < 12) {
                vgButtons.getButton(x,y).setBackground(colorValues.get(2));
            } else {
                vgButtons.getButton(x,y).setBackground(colorValues.get(3));
            }
        }
    }
}

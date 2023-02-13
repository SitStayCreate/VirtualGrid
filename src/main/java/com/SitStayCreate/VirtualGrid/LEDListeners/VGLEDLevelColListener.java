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
    public void setLEDLevelCol(List oscList) {
        // Input validation - messages should be x yOffset int[8]
        if(oscList.size() != 10){
            return;
        }

        int x = (int) oscList.get(0);
        int yOffset = (int) oscList.get(1);

        for(int i = 2; i < oscList.size(); i++){
            int ledState = (int) oscList.get(i);
            int y = yOffset + (i - 2);
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

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
    public void setLEDLevelMap(List oscList) {
        // Validate input - messages must be 66 elements long (xOffset + yOffset + 64 buttons = 66)
        if(oscList.size() != 66){
            return;
        }
        // xOffset and yOffset represent 4 groups of 32 buttons
        int xOffset = (int) oscList.get(0);
        int yOffset = (int) oscList.get(1);

        for(int i = 2; i < oscList.size(); i++){
            int x = (i - 2) % 8; // increments to 7 then resets
            int y = (int) Math.floor((i - 2) / 8); // 0-7 = 0, 8-15 = 1, ...
            x += xOffset;
            y += yOffset;
            int ledState = (int) oscList.get(i);
            if(ledState < 4){
                vgButtons.getButton(x, y).setBackground(colorValues.get(0));
            } else if(ledState < 8){
                vgButtons.getButton(x, y).setBackground(colorValues.get(1));
            }  else if(ledState < 12){
                vgButtons.getButton(x, y).setBackground(colorValues.get(2));
            } else {
                vgButtons.getButton(x, y).setBackground(colorValues.get(3));
            }
        }
    }
}

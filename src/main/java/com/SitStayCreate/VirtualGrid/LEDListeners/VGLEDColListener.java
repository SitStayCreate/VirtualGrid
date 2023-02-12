package com.SitStayCreate.VirtualGrid.LEDListeners;

import com.SitStayCreate.CerealOSC.LEDListeners.LEDColListener;
import com.SitStayCreate.VirtualGrid.VGButtons;

import java.awt.*;
import java.util.List;

public class VGLEDColListener extends LEDColListener {

    private VGButtons vgButtons;
    private List<Color> colorValues;

    public VGLEDColListener(VGButtons vgButtons, List<Color> colorValues){
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
    public void setLEDColState(String binaryString, int x, int yCounter, int yOffset) {
        //only supports 8 rows, so return if offset isn't 0
        if(yOffset > 0){
            return;
        }

        //Start at 8 if yCounter is 1
        yCounter*=8;

        //only supports 8 rows, so return if yCounter is 8
        if(yCounter >= 8){
            return;
        }

        //Add leading 0s if fewer than 8 bits
        for (int j = 7; j >= binaryString.length(); j--) {
            vgButtons.getButton(x,yOffset + yCounter).setBackground(colorValues.get(0));
            yCounter++;
        }

        byte[] bytes = binaryString.getBytes();

        //add data from message
        for (int j = 0; j < binaryString.length(); j++) {
            byte b = bytes[j];
            int gridZ = 0;
            if ((b & 1) != 0) {
                gridZ = 1;
            }

            if(gridZ == 0){
                vgButtons.getButton(x,yOffset + yCounter).setBackground(colorValues.get(0));
            } else {
                vgButtons.getButton(x,yOffset + yCounter).setBackground(colorValues.get(3));
            }

            yCounter++;
        }
    }
}

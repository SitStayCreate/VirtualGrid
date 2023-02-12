package com.SitStayCreate.VirtualGrid.LEDListeners;

import com.SitStayCreate.CerealOSC.LEDListeners.LEDRowListener;
import com.SitStayCreate.VirtualGrid.VGButtons;

import java.awt.*;
import java.util.List;

public class VGLEDRowListener extends LEDRowListener {

    private VGButtons vgButtons;
    private List<Color> colorValues;

    public VGLEDRowListener(VGButtons vgButtons, List<Color> colorValues){
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
    public void setLEDRowState(String binaryString, int xOffset, int xCounter, int y) {

        //Virtual grids will never have more than 8 rows, so if y is more than 7, we must return
        if(y >= 8){
            return;
        }

        //Uses the binaryString to set the LED Row state
        //BinaryStrings do not keep their leading 0's in Java. This is a problem in this case
        //because leading 0's turn LEDs off.  0 = 00000000 255 = 11111111
        //Add leading 0s if fewer than 8 bits
        for (int j = 7; j >= binaryString.length(); j--) {
            vgButtons.getButton(xOffset + xCounter,y).setBackground(colorValues.get(0));
            xCounter++;
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
                vgButtons.getButton(xOffset + xCounter,y).setBackground(colorValues.get(0));
            } else {
                vgButtons.getButton(xOffset + xCounter,y).setBackground(colorValues.get(3));
            }

            xCounter++;
        }
    }

}

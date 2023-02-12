package com.SitStayCreate.VirtualGrid.LEDListeners;

import com.SitStayCreate.CerealOSC.LEDListeners.LEDSetListener;
import com.SitStayCreate.VirtualGrid.VGButtons;

import java.awt.*;
import java.util.List;

public class VGLEDSetListener extends LEDSetListener {

    private VGButtons vgButtons;
    private List<Color> colorValues;

    public VGLEDSetListener(VGButtons vgButtons, List<Color> colorValues){
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
    public void setLEDState(int x, int y, int z) {
        //Virtual grids never have more than 8 rows, so y is never > 7.
        if(y >= 8){
            return;
        }

        if(z == 0){
            vgButtons.getButton(x,y).setBackground(colorValues.get(0));
        } else {
            vgButtons.getButton(x,y).setBackground(colorValues.get(3));
        }
    }
}

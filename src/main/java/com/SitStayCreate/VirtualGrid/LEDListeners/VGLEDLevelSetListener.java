package com.SitStayCreate.VirtualGrid.LEDListeners;

import com.SitStayCreate.CerealOSC.LEDListeners.LEDLevelSetListener;
import com.SitStayCreate.VirtualGrid.VGButtons;

import java.awt.*;
import java.util.List;

public class VGLEDLevelSetListener extends LEDLevelSetListener {
    private VGButtons vgButtons;
    private List<Color> colorValues;

    public VGLEDLevelSetListener(VGButtons vgButtons, List<Color> colorValues){
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
    public void setLEDLevelState(int x, int y, int z) {

        if(y >= 8){
            return;
        }

        //Translate ledLevel to ledSet
        if(z < 4){
            vgButtons.getButton(x,y).setBackground(colorValues.get(0));
        } else if(z < 8){
            vgButtons.getButton(x,y).setBackground(colorValues.get(1));
        }  else if(z < 12){
            vgButtons.getButton(x,y).setBackground(colorValues.get(2));
        } else {
            vgButtons.getButton(x,y).setBackground(colorValues.get(3));
        }
    }
}

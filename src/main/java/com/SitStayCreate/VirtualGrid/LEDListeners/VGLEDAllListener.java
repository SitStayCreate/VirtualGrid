package com.SitStayCreate.VirtualGrid.LEDListeners;

import com.SitStayCreate.CerealOSC.LEDListeners.LEDAllListener;
import com.SitStayCreate.VirtualGrid.VGButton;
import com.SitStayCreate.VirtualGrid.VGButtons;

import java.awt.*;
import java.util.List;

public class VGLEDAllListener extends LEDAllListener {

    private VGButtons vgButtons;
    private List<Color> colorValues;

    public VGLEDAllListener(VGButtons vgButtons, List<Color> colorValues){
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
    public void setLedAllState(int state) {
        for(VGButton[] buttonArr : vgButtons.getButtonMatrix()){
            for(VGButton button : buttonArr){
                if(state == 0){
                    button.setBackground(colorValues.get(0));
                } else {
                    button.setBackground(colorValues.get(3));
                }
            }
        }
    }
}

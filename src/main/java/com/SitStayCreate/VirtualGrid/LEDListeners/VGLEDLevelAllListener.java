package com.SitStayCreate.VirtualGrid.LEDListeners;

import com.SitStayCreate.CerealOSC.LEDListeners.LEDLevelAllListener;
import com.SitStayCreate.VirtualGrid.VGButton;
import com.SitStayCreate.VirtualGrid.VGButtons;

import java.awt.*;
import java.util.List;

public class VGLEDLevelAllListener extends LEDLevelAllListener {

    private VGButtons vgButtons;
    private List<Color> colorValues;

    public VGLEDLevelAllListener(VGButtons vgButtons, List<Color> colorValues){
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
    public void setLEDLevelAll(int s) {

        for(VGButton[] buttonArr : vgButtons.getButtonMatrix()){
            for(VGButton button : buttonArr){
                if(s < 4){
                    button.setBackground(colorValues.get(0));
                } else if(s < 8){
                    button.setBackground(colorValues.get(1));
                }  else if(s < 12){
                    button.setBackground(colorValues.get(2));
                } else {
                    button.setBackground(colorValues.get(3));
                }
            }
        }
    }
}

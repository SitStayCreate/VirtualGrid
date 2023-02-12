package com.SitStayCreate.VirtualGrid;

import com.SitStayCreate.CerealOSC.MonomeDevice.Dimensions;
import com.SitStayCreate.Constants;
import com.SitStayCreate.VirtualGrid.MouseListeners.ButtonMouseListener;

import java.awt.*;

public class VGButtons {
    private VGButton[][] buttonMatrix;

    public VGButtons(Dimensions dimensions, VirtualGridController controller) {
        buttonMatrix = new VGButton[dimensions.getWidth()][dimensions.getHeight()];

        for (int y = 0; y < dimensions.getHeight(); y++){
            for (int x = 0; x < dimensions.getWidth(); x++){
                buttonMatrix[x][y] = new VGButton();
                buttonMatrix[x][y].setPreferredSize(new Dimension(Constants.BUTTON_SIZE, Constants.BUTTON_SIZE));
                buttonMatrix[x][y].addMouseListener(new ButtonMouseListener(x, y, controller));
            }
        }
    }

    public VGButton[][] getButtonMatrix(){
        return buttonMatrix;
    }
}

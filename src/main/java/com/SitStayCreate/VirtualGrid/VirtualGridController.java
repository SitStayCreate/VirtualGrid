package com.SitStayCreate.VirtualGrid;

import com.SitStayCreate.CerealOSC.MonomeApp.MonomeApp;
import com.SitStayCreate.CerealOSC.MonomeDevice.Dimensions;
import com.SitStayCreate.CerealOSC.MonomeDevice.GridController;
import com.SitStayCreate.CerealOSC.OSC.DecoratedOSCPortIn;
import com.SitStayCreate.CerealOSC.OSC.DecoratedOSCPortOut;
import com.SitStayCreate.CerealOSC.RequestServer.RequestServer;
import com.SitStayCreate.VirtualGrid.LEDListeners.*;
import com.SitStayCreate.VirtualGrid.MouseListeners.ButtonMouseListener;
import com.illposed.osc.*;
import com.illposed.osc.messageselector.JavaRegexAddressMessageSelector;
import com.SitStayCreate.Constants;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VirtualGridController extends GridController {
    private static final List<Color> LED_COLORS = new ArrayList<>(Constants.LED_COLOR_CAPACITY);
    private VGButton[][] buttonMatrix;
    private VGridFrame vGridFrame;

    //Use this constructor if no Apps have registered with the RequestServer - Max/MSP represents an unknown port as 0
    //so I'm following this convention.
    public VirtualGridController(int portInNumber, VGridFrame vGridFrame, RequestServer requestServer) throws IOException {
        this(new MonomeApp(0), portInNumber, requestServer);
        this.vGridFrame = vGridFrame;
    }

    //Use this constructor if no Apps have registered with the RequestServer - Max/MSP represents an unknown port as 0
    //so I'm following this convention.
    public VirtualGridController(int portInNumber, RequestServer requestServer) throws IOException {
        this(new MonomeApp(0), portInNumber, requestServer);
    }

    //Use this constructor if MonomeApp has registered with the RequestServer
    public VirtualGridController(MonomeApp monomeApp,
                                 int portInNumber,
                                 RequestServer requestServer) throws IOException {
        super(monomeApp,
            new DecoratedOSCPortIn(portInNumber),
            new DecoratedOSCPortOut(monomeApp.getInetAddress(),
            monomeApp.getPortNumber()),
            new Dimensions(Constants.HEIGHT, Constants.WIDTH, false),
            requestServer);
        LED_COLORS.add(Color.DARK_GRAY);
        LED_COLORS.add(Color.decode(Constants.COLOR_LEVEL_TWO));
        LED_COLORS.add(Color.decode(Constants.COLOR_LEVEL_THREE));
        LED_COLORS.add(Color.decode(Constants.COLOR_LEVEL_FOUR));

        buttonMatrix = new VGButton[dimensions.getWidth()][dimensions.getHeight()];

        //give the request server a reference to the controller to serve to apps
        requestServer.addMonomeController(this);
        //Notify apps that a new device exists
        requestServer.notifyListeners(this);

        for (int y = 0; y < dimensions.getHeight(); y++){
            for (int x = 0; x < dimensions.getWidth(); x++){
                    buttonMatrix[x][y] = new VGButton();
                    buttonMatrix[x][y].setPreferredSize(new Dimension(Constants.BUTTON_SIZE, Constants.BUTTON_SIZE));
                    buttonMatrix[x][y].addMouseListener(new ButtonMouseListener(x, y, this));
            }
        }

        addLEDListeners();
    }

    public VGButton[][] getButtonMatrix(){
        return buttonMatrix;
    }

    @Override
    public void addLEDListeners() {
        String ledSetSelectorRegex = Constants.LED_SET_SELECTOR_REGEX;
        JavaRegexAddressMessageSelector ledSetMessageSelector = new JavaRegexAddressMessageSelector(ledSetSelectorRegex);
        VGLEDSetListener vgledSetListener = new VGLEDSetListener(buttonMatrix, LED_COLORS);
        decoratedOSCPortIn.getOscPortIn().getDispatcher().addListener(ledSetMessageSelector, vgledSetListener);

        String ledAllSelectorRegex = Constants.LED_ALL_SELECTOR_REGEX;
        JavaRegexAddressMessageSelector ledAllMessageSelector = new JavaRegexAddressMessageSelector(ledAllSelectorRegex);
        VGLEDAllListener vgledAllListener = new VGLEDAllListener(buttonMatrix, LED_COLORS);
        decoratedOSCPortIn.getDispatcher().addListener(ledAllMessageSelector, vgledAllListener);

        String ledMapSelectorRegex = Constants.LED_MAP_SELECTOR_REGEX;
        JavaRegexAddressMessageSelector ledMapMessageSelector = new JavaRegexAddressMessageSelector(ledMapSelectorRegex);
        VGLEDMapListener vgledMapListener = new VGLEDMapListener(buttonMatrix, LED_COLORS);
        decoratedOSCPortIn.getDispatcher().addListener(ledMapMessageSelector, vgledMapListener);

        String ledRowSelectorRegex = Constants.LED_ROW_SELECTOR_REGEX;
        JavaRegexAddressMessageSelector ledRowMessageSelector = new JavaRegexAddressMessageSelector(ledRowSelectorRegex);
        VGLEDRowListener vgledRowListener = new VGLEDRowListener(buttonMatrix, LED_COLORS);
        decoratedOSCPortIn.getDispatcher().addListener(ledRowMessageSelector, vgledRowListener);

        String ledColSelectorRegex = Constants.LED_COL_SELECTOR_REGEX;
        JavaRegexAddressMessageSelector ledColMessageSelector = new JavaRegexAddressMessageSelector(ledColSelectorRegex);
        VGLEDColListener vgledColListener = new VGLEDColListener(buttonMatrix, LED_COLORS);
        decoratedOSCPortIn.getDispatcher().addListener(ledColMessageSelector, vgledColListener);

        String ledLevelSetSelectorRegex = Constants.LED_LEVEL_SET_SELECTOR_REGEX;
        JavaRegexAddressMessageSelector ledLevelSetMessageSelector = new JavaRegexAddressMessageSelector(ledLevelSetSelectorRegex);
        VGLEDLevelSetListener vgledLevelSetListener = new VGLEDLevelSetListener(buttonMatrix, LED_COLORS);
        decoratedOSCPortIn.getDispatcher().addListener(ledLevelSetMessageSelector, vgledLevelSetListener);

        String ledLevelAllSelectorRegex = Constants.LED_LEVEL_ALL_SELECTOR_REGEX;
        JavaRegexAddressMessageSelector ledLevelAllMessageSelector = new JavaRegexAddressMessageSelector(ledLevelAllSelectorRegex);
        VGLEDLevelAllListener vgledLevelAllListener = new VGLEDLevelAllListener(buttonMatrix, LED_COLORS);
        decoratedOSCPortIn.getDispatcher().addListener(ledLevelAllMessageSelector, vgledLevelAllListener);

        String ledLevelMapSelectorRegex = Constants.LED_LEVEL_MAP_SELECTOR_REGEX;
        JavaRegexAddressMessageSelector ledLevelMapMessageSelector = new JavaRegexAddressMessageSelector(ledLevelMapSelectorRegex);
        VGLEDLevelMapListener vgledLevelMapListener = new VGLEDLevelMapListener(buttonMatrix, LED_COLORS);
        decoratedOSCPortIn.getDispatcher().addListener(ledLevelMapMessageSelector, vgledLevelMapListener);

        String ledLevelRowSelectorRegex = Constants.LED_LEVEL_ROW_SELECTOR_REGEX;
        JavaRegexAddressMessageSelector ledLevelRowMessageSelector = new JavaRegexAddressMessageSelector(ledLevelRowSelectorRegex);
        VGLEDLevelRowListener vgledLevelRowListener = new VGLEDLevelRowListener(buttonMatrix, LED_COLORS);
        decoratedOSCPortIn.getDispatcher().addListener(ledLevelRowMessageSelector, vgledLevelRowListener);

        String ledLevelColSelectorRegex = Constants.LED_LEVEL_COL_SELECTOR_REGEX;
        JavaRegexAddressMessageSelector ledLevelColMessageSelector = new JavaRegexAddressMessageSelector(ledLevelColSelectorRegex);
        VGLEDLevelColListener vgledLevelColListener = new VGLEDLevelColListener(buttonMatrix, LED_COLORS);
        decoratedOSCPortIn.getDispatcher().addListener(ledLevelColMessageSelector, vgledLevelColListener);
    }

    @Override
    public void close(){
        //TODO: stuff needed to close this device and also close the GUI window
        try {
            decoratedOSCPortIn.close();
            decoratedOSCPortOut.close();
            requestServer.removeMonomeController(this);
            vGridFrame.dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package com.SitStayCreate.VirtualGrid;

import com.SitStayCreate.CerealOSC.MonomeApp.MonomeApp;
import com.SitStayCreate.CerealOSC.MonomeDevice.Dimensions;
import com.SitStayCreate.CerealOSC.MonomeDevice.GridController;
import com.SitStayCreate.CerealOSC.OSC.DecoratedOSCPortIn;
import com.SitStayCreate.CerealOSC.OSC.DecoratedOSCPortOut;
import com.SitStayCreate.CerealOSC.RequestServer.RequestServer;
import com.SitStayCreate.VirtualGrid.LEDListeners.*;
import com.illposed.osc.messageselector.JavaRegexAddressMessageSelector;
import com.SitStayCreate.Constants;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VirtualGridController extends GridController {
    private static final List<Color> LED_COLORS = new ArrayList<>(Constants.LED_COLOR_CAPACITY);
    private VGButtons vgButtons;
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
        setVgButtons(new VGButtons(dimensions, this));


        //give the request server a reference to the controller to serve to apps
        requestServer.addMonomeController(this);
        //Notify apps that a new device exists
        requestServer.notifyListeners(this);

        addLEDListeners();
    }

    public VGButtons getVgButtons() {
        return vgButtons;
    }

    public void setVgButtons(VGButtons vgButtons) {
        this.vgButtons = vgButtons;
    }

    @Override
    public void addLEDListeners() {
        String ledSetSelectorRegex = Constants.LED_SET_SELECTOR_REGEX;
        JavaRegexAddressMessageSelector ledSetMessageSelector = new JavaRegexAddressMessageSelector(ledSetSelectorRegex);
        VGLEDSetListener vgledSetListener = new VGLEDSetListener(vgButtons, LED_COLORS);
        decoratedOSCPortIn.getOscPortIn().getDispatcher().addListener(ledSetMessageSelector, vgledSetListener);

        String ledAllSelectorRegex = Constants.LED_ALL_SELECTOR_REGEX;
        JavaRegexAddressMessageSelector ledAllMessageSelector = new JavaRegexAddressMessageSelector(ledAllSelectorRegex);
        VGLEDAllListener vgledAllListener = new VGLEDAllListener(vgButtons, LED_COLORS);
        decoratedOSCPortIn.getDispatcher().addListener(ledAllMessageSelector, vgledAllListener);

        String ledMapSelectorRegex = Constants.LED_MAP_SELECTOR_REGEX;
        JavaRegexAddressMessageSelector ledMapMessageSelector = new JavaRegexAddressMessageSelector(ledMapSelectorRegex);
        VGLEDMapListener vgledMapListener = new VGLEDMapListener(vgButtons, LED_COLORS);
        decoratedOSCPortIn.getDispatcher().addListener(ledMapMessageSelector, vgledMapListener);

        String ledRowSelectorRegex = Constants.LED_ROW_SELECTOR_REGEX;
        JavaRegexAddressMessageSelector ledRowMessageSelector = new JavaRegexAddressMessageSelector(ledRowSelectorRegex);
        VGLEDRowListener vgledRowListener = new VGLEDRowListener(vgButtons, LED_COLORS);
        decoratedOSCPortIn.getDispatcher().addListener(ledRowMessageSelector, vgledRowListener);

        String ledColSelectorRegex = Constants.LED_COL_SELECTOR_REGEX;
        JavaRegexAddressMessageSelector ledColMessageSelector = new JavaRegexAddressMessageSelector(ledColSelectorRegex);
        VGLEDColListener vgledColListener = new VGLEDColListener(vgButtons, LED_COLORS);
        decoratedOSCPortIn.getDispatcher().addListener(ledColMessageSelector, vgledColListener);

        String ledLevelSetSelectorRegex = Constants.LED_LEVEL_SET_SELECTOR_REGEX;
        JavaRegexAddressMessageSelector ledLevelSetMessageSelector = new JavaRegexAddressMessageSelector(ledLevelSetSelectorRegex);
        VGLEDLevelSetListener vgledLevelSetListener = new VGLEDLevelSetListener(vgButtons, LED_COLORS);
        decoratedOSCPortIn.getDispatcher().addListener(ledLevelSetMessageSelector, vgledLevelSetListener);

        String ledLevelAllSelectorRegex = Constants.LED_LEVEL_ALL_SELECTOR_REGEX;
        JavaRegexAddressMessageSelector ledLevelAllMessageSelector = new JavaRegexAddressMessageSelector(ledLevelAllSelectorRegex);
        VGLEDLevelAllListener vgledLevelAllListener = new VGLEDLevelAllListener(vgButtons, LED_COLORS);
        decoratedOSCPortIn.getDispatcher().addListener(ledLevelAllMessageSelector, vgledLevelAllListener);

        String ledLevelMapSelectorRegex = Constants.LED_LEVEL_MAP_SELECTOR_REGEX;
        JavaRegexAddressMessageSelector ledLevelMapMessageSelector = new JavaRegexAddressMessageSelector(ledLevelMapSelectorRegex);
        VGLEDLevelMapListener vgledLevelMapListener = new VGLEDLevelMapListener(vgButtons, LED_COLORS);
        decoratedOSCPortIn.getDispatcher().addListener(ledLevelMapMessageSelector, vgledLevelMapListener);

        String ledLevelRowSelectorRegex = Constants.LED_LEVEL_ROW_SELECTOR_REGEX;
        JavaRegexAddressMessageSelector ledLevelRowMessageSelector = new JavaRegexAddressMessageSelector(ledLevelRowSelectorRegex);
        VGLEDLevelRowListener vgledLevelRowListener = new VGLEDLevelRowListener(vgButtons, LED_COLORS);
        decoratedOSCPortIn.getDispatcher().addListener(ledLevelRowMessageSelector, vgledLevelRowListener);

        String ledLevelColSelectorRegex = Constants.LED_LEVEL_COL_SELECTOR_REGEX;
        JavaRegexAddressMessageSelector ledLevelColMessageSelector = new JavaRegexAddressMessageSelector(ledLevelColSelectorRegex);
        VGLEDLevelColListener vgledLevelColListener = new VGLEDLevelColListener(vgButtons, LED_COLORS);
        decoratedOSCPortIn.getDispatcher().addListener(ledLevelColMessageSelector, vgledLevelColListener);
    }

    @Override
    public void close(){
        // Close the open ports and remove the grid from the server
        super.close();
        // Close the GUI
        vGridFrame.dispose();
    }
}

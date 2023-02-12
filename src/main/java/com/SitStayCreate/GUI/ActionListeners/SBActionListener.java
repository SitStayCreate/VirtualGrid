package com.SitStayCreate.GUI.ActionListeners;

import com.SitStayCreate.GUI.DTPane;
import com.SitStayCreate.GUI.GridPanel;
import com.SitStayCreate.CerealOSC.MonomeDevice.GridController;
import com.SitStayCreate.CerealOSC.MonomeDevice.MonomeController;
import com.SitStayCreate.CerealOSC.RequestServer.RequestServer;
import com.SitStayCreate.VirtualGrid.VGridFrame;
import com.SitStayCreate.Constants;

import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SBActionListener implements ActionListener {
    private GridPanel gridPanel;
    private RequestServer requestServer;
    private DTPane devicePane;

    public SBActionListener(GridPanel gridPanel,
                            RequestServer requestServer,
                            DTPane devicePane) {
        setGridPanel(gridPanel);
        setRequestServer(requestServer);
        setDevicePane(devicePane);
    }

    public void setGridPanel(GridPanel gridPanel) {
        this.gridPanel = gridPanel;
    }

    public void setRequestServer(RequestServer requestServer) {
        this.requestServer = requestServer;
    }

    public void setDevicePane(DTPane devicePane) {
        this.devicePane = devicePane;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Validate portIn is not already in use by another grid or the system
        int portIn = gridPanel.getPortIn();

        List<GridController> controllers = requestServer.getGridControllers();
        //Check that the port is not already in use, if it is, do not create a device
        for(MonomeController controller : controllers){
            if(controller.getDecoratedOSCPortIn().getPortIn() == portIn){
                gridPanel.setErrorLabel(Constants.ERROR_LABEL);
                return;
            }
        }
        // Clear the error for Port in use
        gridPanel.clearErrorLabel();
        // Create a virtual grid
        new VGridFrame(requestServer, devicePane, portIn);return;
    }
}

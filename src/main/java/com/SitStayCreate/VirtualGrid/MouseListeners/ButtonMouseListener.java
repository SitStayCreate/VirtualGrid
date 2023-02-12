package com.SitStayCreate.VirtualGrid.MouseListeners;

import com.SitStayCreate.Constants;
import com.SitStayCreate.VirtualGrid.VirtualGridController;
import com.illposed.osc.OSCMessage;
import com.illposed.osc.OSCMessageInfo;
import com.illposed.osc.OSCSerializeException;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ButtonMouseListener implements MouseListener {
    private int x, y;
    private VirtualGridController virtualGridController;

    public ButtonMouseListener(int x, int y, VirtualGridController controller) {
        setX(x);
        setY(y);
        setVirtualGridController(controller);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //Do nothing
    }

    @Override
    public void mousePressed(MouseEvent e) {
        String addressString = String.format(Constants.FORMAT_STRING, virtualGridController.getPrefix());
        List<Integer> oscArgs = new ArrayList<>();
        oscArgs.add(x);
        oscArgs.add(y);
        oscArgs.add(1);

        OSCMessageInfo oscMessageInfo = new OSCMessageInfo(Constants.FORMAT_TYPE_TAG);
        OSCMessage oscMessage = new OSCMessage(addressString, oscArgs, oscMessageInfo);
        try {
            System.out.println(virtualGridController.getPrefix());
            System.out.println(oscArgs);
            virtualGridController.send(oscMessage);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (OSCSerializeException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        String addressString = String.format(Constants.FORMAT_STRING, virtualGridController.getPrefix());
        List<Integer> oscArgs = new ArrayList<>();
        oscArgs.add(x);
        oscArgs.add(y);
        oscArgs.add(0);

        OSCMessageInfo oscMessageInfo = new OSCMessageInfo(Constants.FORMAT_TYPE_TAG);
        OSCMessage oscMessage = new OSCMessage(addressString, oscArgs, oscMessageInfo);
        try {
            System.out.println(oscArgs);
            virtualGridController.send(oscMessage);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (OSCSerializeException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //Do nothing
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //Do nothing
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public VirtualGridController getVirtualGridController() {
        return virtualGridController;
    }

    public void setVirtualGridController(VirtualGridController virtualGridController) {
        this.virtualGridController = virtualGridController;
    }
}

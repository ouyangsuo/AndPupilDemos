/*
 * Copyright (C) 2013 4th Line GmbH, Switzerland
 *
 * The contents of this file are subject to the terms of either the GNU
 * Lesser General Public License Version 2 or later ("LGPL") or the
 * Common Development and Distribution License Version 1 or later
 * ("CDDL") (collectively, the "License"). You may not use this file
 * except in compliance with the License. See LICENSE.txt for more
 * information.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 */

package com.kitty.androidtest.upnpserver;

import org.fourthline.cling.binding.annotations.*;

import com.kitty.androidtest.activity.LightActivity;

import android.content.Intent;
import android.net.Uri;

import java.beans.PropertyChangeSupport;

// DOC:CLASS
@UpnpService(
        serviceId = @UpnpServiceId("SwitchPower"),
        serviceType = @UpnpServiceType(value = "SwitchPower", version = 1)
)
public class SwitchPower {

    private final PropertyChangeSupport propertyChangeSupport;

    public SwitchPower() {
        this.propertyChangeSupport = new PropertyChangeSupport(this);
    }

    public PropertyChangeSupport getPropertyChangeSupport() {
        return propertyChangeSupport;
    }

    @UpnpStateVariable(defaultValue = "0", sendEvents = false)
    private boolean target = false;
    
    @UpnpStateVariable(defaultValue = "", sendEvents = false)
    private String url = "";

    @UpnpStateVariable(defaultValue = "0")
    private boolean status = false;

    @UpnpAction
    public void setTarget(@UpnpInputArgument(name = "NewTargetValue") boolean newTargetValue) {
        boolean targetOldValue = target;
        target = newTargetValue;
        boolean statusOldValue = status;
        status = newTargetValue;

        // These have no effect on the UPnP monitoring but it's JavaBean compliant
        getPropertyChangeSupport().firePropertyChange("target", targetOldValue, target);
        getPropertyChangeSupport().firePropertyChange("status", statusOldValue, status);

        // This will send a UPnP event, it's the name of a state variable that sends events
        getPropertyChangeSupport().firePropertyChange("Status", statusOldValue, status);
    }
    
    @UpnpAction
    public void play(@UpnpInputArgument(name = "Url") String urlNewValue) {
    	String urlOldValue = url;
    	url = urlNewValue;
    	
    	getPropertyChangeSupport().firePropertyChange("url", urlOldValue, url);
    	
    	iPlay(url);
    }
    
	protected void iPlay(String url) {
		System.out.println("play on server:url="+url);
		final String myUrl=url;
		
		Intent intent = new Intent();
		Uri uri = Uri.parse(myUrl);
		intent.setDataAndType(uri, "audio/*");
		intent.setAction(Intent.ACTION_VIEW);
		LightActivity.context.startActivity(intent);
	}

    @UpnpAction(out = @UpnpOutputArgument(name = "RetTargetValue"))
    public boolean getTarget() {
        return target;
    }

    @UpnpAction(out = @UpnpOutputArgument(name = "ResultStatus"))
    public boolean getStatus() {
        return status;
    }
}
// DOC:CLASS
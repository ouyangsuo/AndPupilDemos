package com.kitty.androidtest.upnpclient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.fourthline.cling.controlpoint.SubscriptionCallback;
import org.fourthline.cling.model.UnsupportedDataException;
import org.fourthline.cling.model.gena.CancelReason;
import org.fourthline.cling.model.gena.GENASubscription;
import org.fourthline.cling.model.gena.RemoteGENASubscription;
import org.fourthline.cling.model.message.UpnpResponse;
import org.fourthline.cling.model.meta.Service;
import org.fourthline.cling.model.state.StateVariableValue;
import org.fourthline.cling.model.types.BooleanDatatype;
import org.fourthline.cling.model.types.Datatype;

import android.os.Handler;
import android.os.Message;

public class LightSubscription extends SubscriptionCallback {

	private List<Handler> activityHandlers=new ArrayList<Handler>();

	protected LightSubscription(Service service) {
		super(service);
	}

	public LightSubscription(Service service, int requestedDurationSeconds) {
		super(service, requestedDurationSeconds);
	}

	public List<Handler> getActivityHandlers() {
		return activityHandlers;
	}

	public void setActivityHandlers(List<Handler> activityHandlers) {
		this.activityHandlers = activityHandlers;
	}

	@Override
	public void established(GENASubscription sub) {
		System.out.println("Established: " + sub.getSubscriptionId());
	}

	@Override
	protected void failed(GENASubscription subscription, UpnpResponse responseStatus, Exception exception, String defaultMsg) {
		System.err.println(defaultMsg);
	}

	@Override
	public void ended(GENASubscription sub, CancelReason reason, UpnpResponse response) {
		// assertNull(reason);
		System.out.println("LightSubscription ended,reson=" + reason);
	}

	@Override
	public void eventReceived(GENASubscription sub) {

		System.out.println("eventReceived: " + sub.getCurrentSequence().getValue());

		Map<String, StateVariableValue> values = sub.getCurrentValues();
		StateVariableValue status = values.get("Status");
		boolean statusValue=(Boolean) status.getValue();
		System.out.println("statusValue is: " + statusValue);

//		assertEquals(status.getDatatype().getClass(), BooleanDatatype.class);
//		assertEquals(status.getDatatype().getBuiltin(), Datatype.Builtin.BOOLEAN);
		
		dealWithNewStatus(statusValue);

	}

	private void dealWithNewStatus(boolean status) {
		if(activityHandlers!=null && activityHandlers.size()!=0){
			for(Handler handler:activityHandlers){
				if(status==true){
					handler.sendEmptyMessage(LightControllerActivity.MSG_LIGHT_ON);
				}else{
					handler.sendEmptyMessage(LightControllerActivity.MSG_LIGHT_OFF);
				}
			}
		}
	}

	@Override
	public void eventsMissed(GENASubscription sub, int numberOfMissedEvents) {
		System.out.println("Missed events: " + numberOfMissedEvents);
	}

	@Override
	protected void invalidMessage(RemoteGENASubscription sub, UnsupportedDataException ex) {
		// Log/send an error report?
	}
}

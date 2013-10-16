package com.off.on.sensors;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class SensorsManager extends BroadcastReceiver {

	@Override
	public void onReceive(Context cxt, Intent receiverIntent) {
		 if(receiverIntent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED))
		   {         
			 getCurrentState(true, true, cxt);
		   }
	}
	
	private void getCurrentState(boolean locationService,
			boolean orientationService, Context cxt) {
		if (locationService) {
			cxt.startService(new Intent(cxt,
					LocationService.class));
		}
		if (orientationService) {
			cxt.startService(new Intent(cxt,
					OrientationService.class));
		}
	}
}

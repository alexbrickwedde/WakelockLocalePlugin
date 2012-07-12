package org.floodping.wakelocklocaleplugin;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.PowerManager;

public class WakeLockService extends Service {

		private PowerManager.WakeLock wl = null;

		protected void DoWL (int uiType) {

			PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
			if (pm == null) {
				return;
			}

			CharSequence contentText = "";
			CharSequence tickerText = "";

			switch (uiType)
			{
			case 1:
				wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
						"org.floodping.wakelocklocaleplugin.WakeLockService");
				contentText = getResources().getText (R.string.wakelockcpucontent);
				tickerText = getResources().getText (R.string.wakelockcputitle);
				break;
			case 2:
				wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK,
						"org.floodping.wakelocklocaleplugin.WakeLockService");
				contentText = getResources().getText (R.string.wakelockfullcontent);
				tickerText = getResources().getText (R.string.wakelockfulltitle);
				break;
			}
			if (wl == null) {
				return;
			}
			wl.acquire();

			Notification notification = null;
			int icon = R.drawable.ic_launcher;
			long when = System.currentTimeMillis();
			notification = new Notification(icon, tickerText, when);
			notification.flags |= Notification.FLAG_NO_CLEAR;
			CharSequence contentTitle = getResources().getText(
					R.string.app_name);
			PendingIntent contentIntent = PendingIntent.getActivity(
					getApplicationContext(), 0, new Intent(), 0);

			notification.setLatestEventInfo(this, contentTitle,
					contentText, contentIntent);
			this.startForeground(1, notification);

		}

		@Override
		public void onCreate() {
		}

		@Override
		public int onStartCommand(Intent intent, int flags, int startId) {
			super.onStartCommand(intent, flags, startId);
			
			int uiType = intent.getIntExtra("uiType", -1);
			this.DoWL(uiType);
			
			return START_STICKY;
		}

		@Override
		public void onStart(Intent intent, int startId) {
		}

		@Override
		public void onDestroy() {
			if (wl != null && wl.isHeld()) {
				wl.release();
			}
			this.stopForeground(true);
		}

		@Override
		public IBinder onBind(Intent intent) {
			return null;
		}
}

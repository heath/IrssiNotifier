package fi.iki.murgo.irssinotifier;

import android.content.Context;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.util.Log;

public class SettingsActivity extends PreferenceActivity {
	private static final String TAG = SettingsActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "Opened settings");
		super.onCreate(savedInstanceState);
		
		addPreferencesFromResource(R.xml.preference_screen);
		
		final Context ctx = this;
		Preference clearPref = (Preference)findPreference("clear");
		clearPref.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			public boolean onPreferenceClick(Preference preference) {
				Preferences p = new Preferences(ctx);
				p.clear();
				
				DataAccess da = new DataAccess(ctx);
				da.clearAll();

				finish();
				return true;
			}
		});

		ListPreference mode = (ListPreference)findPreference("notificationModeString");
		mode.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				NotificationMode m = NotificationMode.PerChannel;
				String v = (String)newValue;
				if (v.equals(ctx.getResources().getStringArray(R.array.notification_modes)[0]))
					m = NotificationMode.Single;
				if (v.equals(ctx.getResources().getStringArray(R.array.notification_modes)[1]))
					m = NotificationMode.PerChannel;
				if (v.equals(ctx.getResources().getStringArray(R.array.notification_modes)[2]))
					m = NotificationMode.PerMessage;
				
				Preferences p = new Preferences(ctx);
				p.setNotificationMode(m);
				return true;
			}
		});
	}
}

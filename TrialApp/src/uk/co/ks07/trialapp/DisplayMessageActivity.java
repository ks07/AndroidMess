package uk.co.ks07.trialapp;

import java.util.Timer;
import java.util.TimerTask;

import uk.co.ks07.trialapp.MainActivity.PlaceholderFragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.os.Build;

public class DisplayMessageActivity extends ActionBarActivity {

	private static final int updateDelay = 1 * 1000;
	
	private Timer autoUpdate;
	
	private void updateStrength() {
		// Get the wifi info
		WifiManager wMan = (WifiManager) this.getSystemService(WIFI_SERVICE);
		WifiInfo wInfo = wMan.getConnectionInfo();
		String msg = Integer.toString(wInfo.getRssi());
		TextView vCurrStr = (TextView) this.findViewById(R.id.lbl_curr_str);
		vCurrStr.setText(msg);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
	}

	@Override
	public void onResume() {
		super.onResume();
		autoUpdate = new Timer();
		autoUpdate.schedule(new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					public void run() {
						updateStrength();
					}
				});
			}
		}, 0, updateDelay);
	}

	@Override
	public void onPause() {
		autoUpdate.cancel();
		super.onPause();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_display_message,
					container, false);
			return rootView;
		}
	}

}

package com.lianigroup.rememberthetahini;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class CreateTaskActivity extends Activity implements ConnectionCallbacks,
		OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks,ResultCallback {
	
	private TaskItem myTask = new TaskItem();
	public final int REQUEST_CODE_GET_LOC = 3;
	
	GeofenceItem geofenceItem;
	List<Geofence> mGeofenceList = new ArrayList<Geofence>();
	
	private LocationServices mLocationService;
	// Stores the PendingIntent used to request geofence monitoring.
	private PendingIntent mGeofenceRequestIntent;
	private GoogleApiClient mApiClient;

	
	private enum REQUEST_TYPE {ADD,DELETE}

	private REQUEST_TYPE mRequestType;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_task);
		
		myTask.setHasDate(false);
		myTask.setHasLocation(false);
		
		Intent i = getIntent();
		
		EditText text = (EditText)findViewById(R.id.taskDescEdit);
		EditText date = (EditText)findViewById(R.id.taskDateEdit);
		EditText time = (EditText)findViewById(R.id.taskTimeEdit);
		date.setInputType(InputType.TYPE_NULL);
		time.setInputType(InputType.TYPE_NULL);
		
		if(i.hasExtra("item"))
		{
			
			setTitle(R.string.title_activity_update_task);
			myTask = (TaskItem)i.getSerializableExtra("item");
			setContentView(R.layout.activity_update_task);
			
			text = (EditText)findViewById(R.id.taskDescEdit);
			date = (EditText)findViewById(R.id.taskDateEdit);
			time = (EditText)findViewById(R.id.taskTimeEdit);
			date.setInputType(InputType.TYPE_NULL);
			time.setInputType(InputType.TYPE_NULL);
			
			text.setText(myTask.getDescription());
			if(myTask.getHasDate())
			{
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				SimpleDateFormat sdft = new SimpleDateFormat("HH:mm");
				date.setText(sdf.format(myTask.getDueDate()));
				time.setText(sdft.format(myTask.getDueDate()));
			}
			
			switch (myTask.getPriority()) 
			{
				case HIGH:
					RadioButton hrb = (RadioButton)findViewById(R.id.highRB);
					hrb.setChecked(true);
					break;
				case MEDIUM:
					RadioButton mrb = (RadioButton)findViewById(R.id.medRB);
					mrb.setChecked(true);
					break;
				case NORMAL:
					RadioButton nrb = (RadioButton)findViewById(R.id.normRB);
					nrb.setChecked(true);
					break;
			}
			
			Log.d("TESTING", date.getText().toString());
			Log.d("TESTING", time.getText().toString());
			Log.d("TESTING", text.getText().toString());
			
		
		}
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		
		if(resultCode == RESULT_OK)
		{
			switch(requestCode)
			{
				case REQUEST_CODE_GET_LOC:
				{
					MapPoint point = (MapPoint)data.getSerializableExtra("latlng");
					myTask.setLocation(point);
					myTask.setHasLocation(true);
				}
				break;
				
				
			}
		}
		
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_task, menu);
		return true;
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
	
	public void createTaskClick(View view)
	{
		
		EditText text = (EditText)findViewById(R.id.taskDescEdit);
		EditText date = (EditText)findViewById(R.id.taskDateEdit);
		EditText time = (EditText)findViewById(R.id.taskTimeEdit);
		
		myTask.setDescription(text.getText().toString());
		myTask.setCompleted(false);
		if(myTask.getPriority() == null)
		{
			myTask.setPriority(Priority.NORMAL);
		}
		
		String oldstring = date.getText()+" "+time.getText();
		try
		{
			Date myDate = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(oldstring);
			myTask.setDueDate(myDate);
			myTask.setHasDate(true);
		}catch(Exception e){myTask.setHasDate(false);};
		
		
		Intent returnIntent = new Intent();
		
		DBHelper db = new DBHelper(this);
		long res = db.addTask(myTask);
		myTask.setTaskId(res);
		
		returnIntent.putExtra("item",myTask);
		setResult(RESULT_OK,returnIntent);
		
		///////tetsting notifications
		if(myTask.getHasDate())
		{
			Intent myIntent = new Intent(getBaseContext(), ReminderNotification.class);
			myIntent.putExtra("task", myTask);
			
			PendingIntent pendingIntent =
					PendingIntent.getBroadcast(getBaseContext(), 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
			
			AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
			
			Calendar calendar = Calendar.getInstance();
			
			calendar.setTimeInMillis(myTask.getDueDate().getTime());
			
			Log.d("rubi", calendar.toString());
			Log.d("rubi", calendar.getTime().toString());
			Log.d("rubi", myTask.getDueDate().toString());
			String s = String.valueOf(Calendar.getInstance().getTimeInMillis());
			String s1 = String.valueOf(calendar.getTimeInMillis());
			String s2 = String.valueOf(Calendar.getInstance().getTimeInMillis() - calendar.getTimeInMillis());
			Log.d("rubi",s);
			Log.d("rubi",s1);
			Log.d("rubi",s2);
	
	        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
		}
		
		
		if(myTask.getHasLocation())
		{
			if (!isGooglePlayServicesAvailable()) {
				Log.e("Rememeber", "Google Play services unavailable.");
				finish();
				return;
			}

			mApiClient = new GoogleApiClient.Builder(this).addApi(LocationServices.API).addConnectionCallbacks(this)
					.addOnConnectionFailedListener(this).build();

			
			mApiClient.connect();
			
			geofenceItem = new GeofenceItem(
					String.valueOf(myTask.getTaskId()), // geofenceId.
					myTask.getLocation().getLat(), myTask.getLocation().getLng(),
					800, Constants.GEOFENCE_EXPIRATION_TIME,
					Geofence.GEOFENCE_TRANSITION_ENTER
							| Geofence.GEOFENCE_TRANSITION_EXIT);
			
			
			mGeofenceList.add(geofenceItem.toGeofence());
			
			finish();
			
		}
	}
	
	private GeofencingRequest getGeofencingRequest() {
	    GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
	    builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
	    builder.addGeofences(mGeofenceList);
	    return builder.build();
	}
	
	public void updateTaskClick(View view)
	{
		EditText text = (EditText)findViewById(R.id.taskDescEdit);
		EditText date = (EditText)findViewById(R.id.taskDateEdit);
		EditText time = (EditText)findViewById(R.id.taskTimeEdit);
		
		myTask.setDescription(text.getText().toString());
		myTask.setCompleted(false);
		String oldstring = date.getText()+" "+time.getText();
		
		try
		{
			Date myDate = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(oldstring);
			myTask.setDueDate(myDate);
			myTask.setHasDate(true);
		}catch(Exception e){myTask.setHasDate(false);};
		
		
		Intent returnIntent = new Intent();
		
		DBHelper db = new DBHelper(this);
		db.updateTask(myTask);
		
		
		returnIntent.putExtra("item",myTask);
		setResult(RESULT_OK,returnIntent);
		finish();
	}
	
	public void onRadioButtonClicked(View view) {
	    // Is the button now checked?
	    boolean checked = ((RadioButton) view).isChecked();
	    
	    // Check which radio button was clicked
	    switch(view.getId()) {
	        case R.id.normRB:
	            if (checked)
	                myTask.setPriority(Priority.NORMAL);
	            break;
	        case R.id.medRB:
	            if (checked)
	            	myTask.setPriority(Priority.MEDIUM);
	            break;
	        case R.id.highRB:
	            if (checked)
	            	myTask.setPriority(Priority.HIGH);
	            break;
	    }
	}
	
	public void showTimePickerDialog(View v) {
	    DialogFragment newFragment = new TimePickerFragment();
	    newFragment.show(getFragmentManager(), "timePicker");
	   
	    
	}
	
	public void showDatePickerDialog(View v) {
	    DialogFragment newFragment = new DatePickerFragment();
	    newFragment.show(getFragmentManager(), "datePicker");
	    
	}
	
	
	public void showMapWindow(View view) {
		
		Intent i = new Intent(this,Map.class);
		startActivityForResult(i, REQUEST_CODE_GET_LOC);
	}
	
	
	public void discardBtnClick(View view)
	{
		finish();
	}
	
	public void deleteBtnClick(View view)
	{
		Intent returnIntent = new Intent();
		
		myTask.setToDelete(true);
		
		DBHelper db = new DBHelper(this);
		db.deleteTask(myTask);
		
		returnIntent.putExtra("item",myTask);
		setResult(RESULT_OK,returnIntent);
		finish();
	}
	
	/**
	 * Checks if Google Play services is available.
	 * 
	 * @return true if it is.
	 */
	private boolean isGooglePlayServicesAvailable() {
		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this);
		if (ConnectionResult.SUCCESS == resultCode) {
			if (Log.isLoggable("Remember", Log.DEBUG)) {
				Log.d("Rememeber", "Google Play services is available.");
			}
			return true;
		} else {
			Log.e("Rememeber", "Google Play services is unavailable.");
			return false;
		}
	}

	/**
	 * Create a PendingIntent that triggers GeofenceTransitionIntentService when
	 * a geofence transition occurs.
	 */
	private PendingIntent getGeofenceTransitionPendingIntent() {
		Intent intent = new Intent(this, GeofencingReceiverIntentService.class);
		return PendingIntent.getService(this, 0, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);

	}

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		// TODO Auto-generated method stub
		if (connectionResult.hasResolution()) {
			try {
				connectionResult.startResolutionForResult(this,
						Constants.CONNECTION_FAILURE_RESOLUTION_REQUEST);
			} catch (IntentSender.SendIntentException e) {
				Log.e("Remember", "Exception while resolving connection error.", e);
			}
		} else {
			int errorCode = connectionResult.getErrorCode();
			Log.e("Remember",
					"Connection to Google Play services failed with error code "
							+ errorCode);
		}
		
	}

	@Override
	public void onConnected(Bundle arg0) {
		// TODO Auto-generated method stub
		
		Toast.makeText(this, "Start geofence service", Toast.LENGTH_SHORT).show();
		
		mGeofenceRequestIntent = getGeofenceTransitionPendingIntent();
		/*getGeofencingRequest();
		LocationServices.GeofencingApi.addGeofences(mApiClient, mGeofenceList,
				mGeofenceRequestIntent);
		*/
		
		LocationServices.GeofencingApi.addGeofences(
				mApiClient,
                getGeofencingRequest(),
                mGeofenceRequestIntent
        ).setResultCallback(this);
		
		
		
		
		
	}

	@Override
	public void onConnectionSuspended(int arg0) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "geofence service suspend", Toast.LENGTH_SHORT).show();
		
		
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		Toast.makeText(this, "geofence service disconnected", Toast.LENGTH_SHORT).show();
		
	}

	@Override
	public void onResult(Result arg0) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "Result Create", Toast.LENGTH_LONG).show();
		
	}
}

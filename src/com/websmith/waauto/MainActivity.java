package com.websmith.waauto;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final EditText txtNumber= (EditText) findViewById(R.id.txtNumber);
		
		Button btnSend=(Button) findViewById(R.id.btnSend);
		
		btnSend.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
				 String whatsappid = "09724111669@s.whatsapp.net";
				 //openWhatsApp(whatsappid);
				/*
		            Cursor c = getContentResolver().query(
		                    ContactsContract.Data.CONTENT_URI,
		                    new String[] { ContactsContract.Contacts.Data._ID },
		                    null,
		                    null, null);
		            c.moveToFirst();

		            while(!c.isAfterLast())
		            {
		            Intent whatsapp = new Intent(Intent.ACTION_VIEW,
		                    Uri.parse("content://com.android.contacts/data/"
		                            + c.getString(0)));

		            c.close();

		            whatsapp.putExtra(Intent.EXTRA_TEXT, "Test...");

		            whatsapp.setPackage("com.whatsapp");

		            try {
		                startActivity(whatsapp);
		            } catch (android.content.ActivityNotFoundException ex) {
		                ex.printStackTrace();
		            }
		            break;
		            }
				*/
				
				Uri uri = Uri.parse("smsto:9724111669");
		        Intent mIntent = new Intent(Intent.ACTION_SENDTO, uri);
		        //mIntent.setType("text/plain");
		        mIntent.setPackage("com.whatsapp");
		        mIntent.putExtra("chat",true);
		        mIntent.putExtra(Intent.EXTRA_TEXT, "hi hello");
		        //startActivity(mIntent);
		        startActivity(Intent.createChooser(mIntent, "Share with"));
				
				/*Intent waIntent = new Intent(Intent.ACTION_SEND,Uri uri = Uri.parse("smsto:9724111669"););
				waIntent.setType("text/plain");
				        String text = "YOUR TEXT HERE";
				waIntent.setPackage("com.whatsapp");
				waIntent.putExtra("chat",true);
				if (waIntent != null) {
				    waIntent.putExtra(Intent.EXTRA_TEXT, text);//
				    startActivity(Intent.createChooser(waIntent, "Share with"));
				} else {
				    Toast.makeText(MainActivity.this, "WhatsApp not Installed", Toast.LENGTH_SHORT)
				            .show();
				}*/
			}
		});
	}
	
	private void openWhatsApp(String id) {

		Cursor c = getContentResolver().query(ContactsContract.Data.CONTENT_URI,
		        new String[] { ContactsContract.Contacts.Data._ID }, ContactsContract.Data.DATA1 + "=?",
		        new String[] { id }, null);
		c.moveToFirst();
		Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("content://com.android.contacts/data/" + c.getString(0)));

		startActivity(i);
		c.close();
		}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
}

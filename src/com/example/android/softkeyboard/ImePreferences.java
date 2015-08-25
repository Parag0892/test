

package com.example.android.softkeyboard;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;





/**
 * Date:4/6/7
 * Author : MAYANK NEVE 
 * Displays the IME preferences inside the input method setting.
 */
public class ImePreferences extends PreferenceActivity {
   

	
	byte[] buffer ;
	
	ListView list ; 
	
	ArrayAdapter <String> adapter  = null ; 
	
	ArrayList <String> array = null ; 
	
	ArrayList <String> urls = null ; 
	
	ArrayList <String> object_id  = null ; 
	
	 StringBuilder str = null ; 
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // We overwrite the title of the activity, as the default one is "Voice Search".
        setTitle("panda");
        addPreferencesFromResource(R.xml.ime_preferences);
               setContentView(R.layout.songs);
               
               array  = new ArrayList () ;  
               urls  = new ArrayList () ;  
               object_id = new ArrayList () ; 
               
               
               adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, array ) ; 
               
               str = new StringBuilder () ;
               
               Button b = (Button)findViewById(R.id.parag) ; 
               
               list = (ListView)findViewById (R.id.listView1) ; 
               
              
               b.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					new LongOperation().execute() ; 
				//Toast.makeText(getApplicationContext(), "Hiii", Toast.LENGTH_LONG).show() ; 
				
				}
			}) ; 
            
               
               
               list.setAdapter(adapter) ;
               
               
               
               list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

       			@Override
       			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
       					long arg3) {
       				// TODO Auto-generated method stub
       				
       				//Toast.makeText(getApplicationContext(), urls.get(arg2).toString() , Toast.LENGTH_LONG).show() ;
       			
       			//	Toast.makeText(getApplicationContext(),"Yups" + urls.get(arg2), Toast.LENGTH_LONG).show() ;
       				new Long().execute(urls.get(arg2).toString()) ;
       				
       			}
       		
               
               
               });
     
               
               
               
               
               
    }
    
    
    class LongOperation extends AsyncTask<Void, Void, Void> {

       	
       	RandomAccessFile file = null; 
       	
   		@Override
   		protected Void doInBackground(Void... params) {
   			// TODO Auto-generated method stub
   			
   			try {
   		   URL url = new URL("https://api.parse.com/1/classes/AudioFile/");
   		   HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
   		  // urlConnection.setSSLSocketFactory(context.getSocketFactory());
   		   //InputStream in = urlConnection.getInputStream();
   		urlConnection.setRequestProperty("X-Parse-Application-Id", "hY5cQkO4KRh8QvasIinKhq7ypJtdfBwrRBQN7BCm") ; 
   		urlConnection.setRequestProperty("X-Parse-REST-API-Key" ,  "XCiBHOcx9LHxgI0unZzOUdEwfUwbtf5NjTIHJ67r") ; 
   		
   	    urlConnection.connect() ; 
   	    
   	    
   	    System.out.println("check"+urlConnection.getResponseCode()) ; 
   		
   	    InputStream x = urlConnection.getInputStream() ; 
   	    
   	     
   	    
   	    
   	    int i = 0 ; 
   	    while ((i = x.read()) != -1 ) 
   	    {
   	    	//System.out.println((char)i) ;
   	    	str.append((char)i) ; 
   	    }
   	   
   	    
   	    System.out.println(str.toString()) ; 
 
   	    
   			}
   			catch (Exception e )
   			{
   				System.out.println(e.toString ()) ; 
   			}
   		
   					
               			return null;
   		}
       
   		 ProgressDialog dialog ;

   		@Override
   		protected void onPostExecute(Void result) {
   			// TODO Auto-generated method stub
   			super.onPostExecute(result);
   			dialog.dismiss() ; 
   			//list.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, array )) ; 
   			
   			
   			//System.out.println ("check"+buffer.toString()) ; 
   			
   			 try {
   				 
   					 JSONObject names= new JSONObject (str.toString());
   					 JSONArray name = names.getJSONArray("results"); 
   			   
   					 
   					 
   					 System.out.println(name.length()) ; 
   					 
   				 
   					 for (int q = 0 ; q< name.length() ; q++)
   					 {
   						System.out.println("inside");
   						
   						JSONObject c  = name.getJSONObject(q) ; 
   						
   						if (!object_id.contains(c.getString("objectId")))
   						{
   						array.add(c.getString("name")) ; 
   						
   						
   						urls.add(c.getString("url")) ; 
   						
   						System.out.println(c.getString("objectId"));
   						object_id.add(c.getString("objectId")) ; 
   						}
   						
   					 }
   					 
   					 
   				    
   			            
   			            list.setAdapter(adapter) ; 
   			            
   			            
   			            
   					
   					
   			} catch (JSONException e) {
   				// TODO Auto-generated catch block
   				e.printStackTrace();
   			} 
   			
   			
   		}

   		@Override
   		protected void onPreExecute() {
   			// TODO Auto-generated method stub
   			super.onPreExecute();
   			 dialog= ProgressDialog.show(ImePreferences.this, "",
        		        "Connecting to server...", true);
   			System.out.println ("check") ; 
   		}
       	
       
       }
    

    
    private class Long extends AsyncTask<String , Void, Void>
    {

		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			dialog.dismiss() ; 
			
		}

		
		ProgressDialog dialog ;
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			
			 
			 
			 dialog= ProgressDialog.show(ImePreferences.this, "",
	     		        "Connecting to server...", true);
				System.out.println ("check") ; 
		}

		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			System.out.println(params[0]); 
		 
			
			try {
		        
		        URL url = new URL(params[0]);

		        //create the new connection
		        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

		        
		        

		        //and connect!
		        urlConnection.connect();
		        
		        System.out.println("code"+urlConnection.getResponseCode()) ; 

		        //set the path where we want to save the file
		        //in this case, going to save it on the root directory of the
		        //sd card.
		       //  File SDCardRoot = Environment.getExternalStorageDirectory();
		        
		         
		         File SDCardRoot = new File (Environment.getExternalStorageDirectory(),"/IKEY");
			       System.out.println( SDCardRoot.mkdir() ) ; 
				
		        
		        //create a new file, specifying the path, and the filename
		        //which we want to save the file as.
		        
		       
		       File file = new File(SDCardRoot,array.get(urls.indexOf(params[0])));
		       
		       
		       
		       System.out.println("check"+"1") ; 
		       file.mkdirs();
		       System.out.println("check"+"2"+file.getCanonicalPath()) ; 
		        
		       if (file.exists ()) file.delete ();
		       //this will be used to write the downloaded data into the file we created
		        FileOutputStream fileOutput = new FileOutputStream(file);

		        System.out.println("check"+"3") ; 
		        
		        
		        //this will be used in reading the data from the internet
		        InputStream inputStream = urlConnection.getInputStream();

		        
		        
		        
		        
		        //this is the total size of the file
		        int totalSize = urlConnection.getContentLength();
		        
		        System.out.println("check"+totalSize); 
		        
		        
		        
		        //variable to store total downloaded bytes
		        int downloadedSize = 0;

		        //create a buffer...
		        byte[] buffer = new byte[1024];
		        int bufferLength = 0; //used to store a temporary size of the buffer

		        //now, read through the input buffer and write the contents to the file
		        while ( (bufferLength = inputStream.read(buffer)) > 0 ) {
		                //add the data in the buffer to the file in the file output stream (the file on the sd card
		                fileOutput.write(buffer, 0, bufferLength);
		                //add up the size so we know how much is downloaded
		            
		        }
		        //close the output stream when done
		        System.out.println(file.length()) ;
		        fileOutput.close();

		//catch some possible errors...
		
		       
		} catch (Exception e) {
		        e.printStackTrace();
		        System.out.println(e.toString ()) ; 
		        
		} 
			
			
			
			
			return null;
		}
    	
    }
    
    
    
}

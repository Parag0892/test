package com.example.song.database ; 

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;



 public class DataBase {



	public static final  String KEY_ROWID = "_id";
	public static final  String KEY_SONG_NAME = "persons_username";
	public static final  String KEY_URL = "persons_email";
    public static final  String KEY_OBJECTID = "persons_pass";
    
    private static final  String DATABASE_TABLE = "register_info";
    
  
    
    
    
    private static final String [] columns = new String [] {KEY_ROWID,KEY_SONG_NAME ,KEY_URL ,KEY_OBJECTID };
   
    
	private static final String DATABASE_NAME = "local_database";
	// private static final String DATABASE_NAME = "image_database";
	
	
	
	
	private static final int  DATABASE_VERSION =  1;
	
	
	
	private static class DBhelper extends SQLiteOpenHelper
	{

		public DBhelper(Context context
				) {
			super(context, DATABASE_NAME, null,DATABASE_VERSION );
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL("CREATE TABLE "+ DATABASE_TABLE +" ("+
			KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_SONG_NAME+" TEXT NOT NULL, "+
					KEY_URL + " TEXT NOT NULL, "
			 +  KEY_OBJECTID + " TEXT NOT NULL);"
					);
			
		}

        @Override
		public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
			// TODO Auto-generated method stub
			
			
			
				arg0.execSQL("DROP IF TABLE EXISTS "+ DATABASE_TABLE);
			
				onCreate(arg0);
			
		}
		
		
		
	}
	
	private Context ourcontext ;
	private DBhelper ourhelper ;
	private SQLiteDatabase  ourdatabase  ;
	
    public DataBase (Context c )
    {
    	ourcontext = c ;
    }
    
    public DataBase open() throws SQLException  
    {
    	ourhelper = new DBhelper (ourcontext);
    	ourdatabase = ourhelper.getWritableDatabase();
        return this ; 
    }
    
    public void close ()
    {
    	ourhelper.close();
    }

    static int empty_count = 0  ; 
    
	public Object createEntry(String song , String url ,String objectid) {
		
		ArrayList <String>al1 = getData_check_song() ; 
		
	
		if (al1.contains(objectid))
		{
			return null ;
		}
		else
		{
			ContentValues cv = new ContentValues ();
			cv.put(KEY_SONG_NAME , song);
			cv.put(KEY_URL , url);
			cv.put(KEY_OBJECTID    , objectid );
			try {
	    	ourdatabase.insert(DATABASE_TABLE, null, cv);
			}
			
			catch (Exception e)
			{
				System.out.println(e.toString ()) ; 
				return null ; 
			}
	    	return 	ourdatabase.insert(DATABASE_TABLE, null, cv);
	        
		}
	
		
	}

	public ArrayList<String> getData_check_song ()
	{
		// TODO Auto-generated method stub
		
	
		 Cursor c =  ourdatabase.query(DATABASE_TABLE, columns, null,  null, null,
				 null, null); 
		 
		 String result  ;
		 
		 ArrayList<String> al = new ArrayList () ; 
		 
		 int iobjectid	 = c.getColumnIndex(KEY_OBJECTID);
			 
		 for (c.moveToFirst(); !c.isAfterLast() ; c.moveToNext())
		 {
			
			 result = c.getString(iobjectid) ; 
			 System.out.println(result) ; 
			 al.add(result);
		 }
		 
		 return al;
	}
	
	
 }
 
 /*
  * DataBase entry = new DataBase (Register.this);
			entry.open();
			if (entry.createEntry(values[0],values[1],values[2],values[3],values[4],values[5]) == null )
			{
				diditworks = false ;
			}
			entry.close(); 
			
			
			
			AlertDialog.Builder ad = new AlertDialog.Builder(this);
					ad.setTitle("Done");
					ad.setMessage("Success");
					ad.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							// TODO Auto-generated method stub
							
							Intent login = new Intent (Register.this , MainActivity.class);
							login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivity(login);
							overridePendingTransition(R.anim.in_right,R.anim.out_right);
							
						}
					});
					
					
					AlertDialog dialog = ad.create();
					dialog.show();
			
			*/

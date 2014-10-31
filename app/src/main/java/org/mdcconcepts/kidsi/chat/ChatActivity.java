package org.mdcconcepts.kidsi.chat;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.mdcconcepts.kidsi.R;
import org.mdcconcepts.kidsi.Util.Util;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Random;


public class ChatActivity extends Activity {
    private static final String TAG = "ChatActivity";

    private ChatArrayAdapter chatArrayAdapter;
	private ListView listView;
	private EditText chatText;
    private Button buttonSend,buttonChoose,buttonAttach;
    String uploadFilePath=null;
    GoogleCloudMessaging gcm;
    Intent intent;
    ProgressDialog dialog = null;
	private static Random random;
    private String toUserName,signUpUser;
    MessageSender messageSender;
    int serverResponseCode = 0;
    String upLoadServerUri = null;
   String downloadUrl=null;
  String filename=null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        try {
            Intent i = getIntent();
            toUserName = i.getStringExtra("TOUSER");
            signUpUser = i.getStringExtra("mainuser");
            signUpUser = Util.getUSER_NAME();
            //String s="swapnil";
            downloadUrl = "http://swapnil.mdcconcepts.com/gcm_server_files/uploads/";
            upLoadServerUri = "http://swapnil.mdcconcepts.com/gcm_server_files/upload_media.php";
            setContentView(R.layout.activity_chat);
//      ActionBar bar=getActionBar();
//      bar.setTitle("");

            DBAdapter.init(GlobalContext.getContext());
            buttonSend = (Button) findViewById(R.id.buttonSend);
            buttonChoose = (Button) findViewById(R.id.buttonChoose);
            buttonAttach = (Button) findViewById(R.id.buttonAttach);
            intent = new Intent(this, GCMNotificationIntentService.class);
            boolean side = true;
            registerReceiver(broadcastReceiver, new IntentFilter("org.mdcconcepts.kidsi.chat.ChatMessage"));

            random = new Random();
            messageSender = new MessageSender();
            listView = (ListView) findViewById(R.id.listView1);
            gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
            chatArrayAdapter = new ChatArrayAdapter(getApplicationContext(), R.layout.activity_chat_singlemessage);
            listView.setAdapter(chatArrayAdapter);

            chatText = (EditText) findViewById(R.id.chatText);
            chatText.setOnKeyListener(new OnKeyListener() {
                public boolean onKey(View v, int keyCode, KeyEvent event) {

                    try {
                        if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                            return sendChatMessage();
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    return false;
                }
            });
            buttonSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    try {
                        sendChatMessage();
                    }catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            });
            buttonAttach.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    //sendChatMessage();
                    dialog = ProgressDialog.show(ChatActivity.this, "", "Uploading file...", true);
                    try {
                        new Thread(new Runnable() {
                            public void run() {
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        //messageText.setText("uploading started.....");
                                    }
                                });
                                Log.d("Uploaded File path", uploadFilePath);
                                uploadFile(uploadFilePath);

                            }
                        }).start();

                        while (true) {
                            if (filename != null) {
                                downloadUrl = downloadUrl + filename;
                                //  chatText.setText(downloadUrl);
                                sendChatMedia(downloadUrl);

                                break;
                            }
                        }
                    } catch (Exception ex) {
                        Log.d("Exception in Attach", ex.toString());
                    }
                }
            });
            buttonChoose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {


                    try {
                        Intent intent = new Intent();
                        intent.setType("*/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select File"), 1);

                        //sendChatMessage();
                        //new LongOperation().execute("working");

                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            });

            listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
            listView.setAdapter(chatArrayAdapter);

            chatArrayAdapter.registerDataSetObserver(new DataSetObserver() {
                @Override
                public void onChanged() {
                    super.onChanged();
                    try {
                        listView.setSelection(chatArrayAdapter.getCount() - 1);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            });
            try {
                //  Log.i("Database value 1",signUpUser);
                Log.i("Database value 2", toUserName);
                List<UserData> data = DBAdapter.getMessageUserData(signUpUser, toUserName);
                // Collections.reverse(data);
                for (UserData dt : data) {
                    Log.d("user name:", dt.getFrom());
                    if (signUpUser.equals(dt.getFrom()))
                        side = false;
                    else
                        side = true;
                    chatArrayAdapter.add(new ChatMessage(side, dt.getMessage(), dt.getMessageType()));

                }
            } catch (Exception ex) {

                Log.d("Exception ", ex.toString());

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


	}
    public void onActivityResult(int requestCode, int resultCode, Intent data) { 

        if (resultCode == RESULT_OK) {

                if (requestCode == 1) {
                	Uri selectedImageUri = data.getData();
                	uploadFilePath=getPath(selectedImageUri);
                	//Log.d("File path is",uploadFilePath);
                	
                	//messageText.setText("Uploading file path :- "+uploadFilePath);
                }
        }
}
   
    
    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
// And to convert the image URI to the direct file system path of the image file
public String getRealPathFromURI(Uri contentUri) {

        // can post image
        String [] proj={MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery( contentUri,
                        proj, // Which columns to return
                        null,       // WHERE clause; which rows to return (all rows)
                        null,       // WHERE clause selection arguments (none)
                        null); // Order-by clause (ascending by name)
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        return cursor.getString(column_index);
}
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

	 protected void onPause() {

	        super.onPause();
	        synchronized (this) {
	            if(broadcastReceiver != null){
	            	try{
	                unregisterReceiver(broadcastReceiver );
	            	}catch(Exception ex){}
	            }
	        }
	       
	    }
	 protected void onStop()
	 {
		 super.onStop();
		  synchronized (this) {
	            if(broadcastReceiver != null){
	                try{
	            	unregisterReceiver(broadcastReceiver );
	                }catch(Exception ex){}
	            }
	        }
	 }
	 
	 /*
	  * To send normal message
	  * 
	  */
    private boolean sendChatMessage(){
        //sending gcm message to the paired device
        Bundle dataBundle = new Bundle();
        dataBundle.putString("ACTION", "CHAT");
        dataBundle.putString("TOUSER", toUserName);
        dataBundle.putString("CHATMESSAGE", chatText.getText().toString());
        messageSender.sendMessage(dataBundle,gcm);
        UserData obj=new UserData(1,signUpUser,toUserName,chatText.getText().toString(),"CHAT");
        DBAdapter.addUserData(obj);
        //updating the current device
        chatArrayAdapter.add(new ChatMessage(false, chatText.getText().toString(),"CHAT"));
        chatText.setText("");
        return true;
    }
    /*
     * To send Media files
     * 
     */
    private boolean sendChatMedia(String message){
        //sending gcm message to the paired device
        Bundle dataBundle = new Bundle();
        dataBundle.putString("ACTION", "MEDIA");
        dataBundle.putString("TOUSER", toUserName);
        dataBundle.putString("CHATMESSAGE", message);
        messageSender.sendMessage(dataBundle,gcm);
        UserData obj=new UserData(1,signUpUser,toUserName,message,"MEDIA");
        DBAdapter.addUserData(obj);
        //updating the current device
        chatArrayAdapter.add(new ChatMessage(false, message,"MEDIA"));
        //chatText.setText("");
        return true;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
        	
        	 this.finish();
            Intent i=new Intent(ChatActivity.this,UserListActivity.class);
            i.getStringExtra("mainuser");
            startActivity(i);
           
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    public int uploadFile(String sourceFileUri) {
        
  	  
  	  String fileName = sourceFileUri;

        HttpURLConnection conn = null;
        DataOutputStream dos = null;  
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024; 
        File sourceFile = new File(sourceFileUri); 
        filename=sourceFile.getName();
        if (!sourceFile.isFile()) {
      	  
	           dialog.dismiss(); 
	           
	           Log.e("uploadFile", "Source File not exist :"
	        		               +uploadFilePath );
	           
	           runOnUiThread(new Runnable() {
	               public void run() {
	            	  // messageText.setText("Source File not exist :"
	            		//	   +uploadFilePath);
	               }
	           }); 
	           
	           return 0;
         
        }
        else
        {
	           try { 
	        	   
	            	 // open a URL connection to the Servlet
	               FileInputStream fileInputStream = new FileInputStream(sourceFile);
	               URL url = new URL(upLoadServerUri);
	               
	               // Open a HTTP  connection to  the URL
	               conn = (HttpURLConnection) url.openConnection(); 
	               conn.setDoInput(true); // Allow Inputs
	               conn.setDoOutput(true); // Allow Outputs
	               conn.setUseCaches(false); // Don't use a Cached Copy
	               conn.setRequestMethod("POST");
	               conn.setRequestProperty("Connection", "Keep-Alive");
	               conn.setRequestProperty("ENCTYPE", "multipart/form-data");
	               conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
	               conn.setRequestProperty("uploaded_file", fileName); 
	               
	               dos = new DataOutputStream(conn.getOutputStream());
	     
	               dos.writeBytes(twoHyphens + boundary + lineEnd); 
	               dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
	            		                     + fileName + "\"" + lineEnd);
	               
	               dos.writeBytes(lineEnd);
	     
	               // create a buffer of  maximum size
	               bytesAvailable = fileInputStream.available(); 
	     
	               bufferSize = Math.min(bytesAvailable, maxBufferSize);
	               buffer = new byte[bufferSize];
	     
	               // read file and write it into form...
	               bytesRead = fileInputStream.read(buffer, 0, bufferSize);  
	                 
	               while (bytesRead > 0) {
	            	   
	                 dos.write(buffer, 0, bufferSize);
	                 bytesAvailable = fileInputStream.available();
	                 bufferSize = Math.min(bytesAvailable, maxBufferSize);
	                 bytesRead = fileInputStream.read(buffer, 0, bufferSize);   
	                 
	                }
	     
	               // send multipart form data necesssary after file data...
	               dos.writeBytes(lineEnd);
	               dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
	     
	               // Responses from the server (code and message)
	               serverResponseCode = conn.getResponseCode();
	               String serverResponseMessage = conn.getResponseMessage();
	                
	               Log.i("uploadFile", "HTTP Response is : " 
	            		   + serverResponseMessage + ": " + serverResponseCode);
	               
	               if(serverResponseCode == 200){
	            	   
	                   runOnUiThread(new Runnable() {
	                        public void run() {
	                        	
	                        	String msg = "File Upload Completed.\n\n See uploaded file here : \n\n"
	                        		          +" http://swapnil.mdcconcepts.com/gcm_server_files/uploads/"
	                        		        ;
	                        	
	                        //	messageText.setText(msg);
	                            Toast.makeText(ChatActivity.this, "File Upload Complete.", 
	                            		     Toast.LENGTH_SHORT).show();
	                        }
	                    });                
	               }    
	               
	               //close the streams //
	               fileInputStream.close();
	               dos.flush();
	               dos.close();
	               
	          } catch (MalformedURLException ex) {
	        	  
	              dialog.dismiss();  
	              ex.printStackTrace();
	              
	              runOnUiThread(new Runnable() {
	                  public void run() {
	                	 // messageText.setText("MalformedURLException Exception : check script url.");
	                      Toast.makeText(ChatActivity.this, "MalformedURLException", Toast.LENGTH_SHORT).show();
	                  }
	              });
	              
	              Log.e("Upload file to server", "error: " + ex.getMessage(), ex);  
	          } catch (Exception e) {
	        	  
	              dialog.dismiss();  
	              e.printStackTrace();
	              final String s=e.toString();
	              runOnUiThread(new Runnable() {
	                  public void run() {
	                	  //messageText.setText("Got Exception : see logcat "+s);
	                      Toast.makeText(ChatActivity.this, "Got Exception : see logcat ", 
	                    		  Toast.LENGTH_SHORT).show();
	                  }
	              });
	              Log.e("Upload file to server Exception", "Exception : " 
	            		                           + e.getMessage(), e);  
	          }
	          dialog.dismiss();       
	          return serverResponseCode; 
	          
         } // End else block 
       }
    
    
    
 
    
    
    
    
    
    
    
    
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
        //	 Bundle extras = intent.getExtras();
        	 
        	 
//        	 for (String key : extras.keySet()) {
//        		    Object value = extras.get(key);
//        		    Log.d("this is", String.format("%s %s (%s)", key,  
//        		        value.toString(), value.getClass().getName()));
//        		}
           // Log.d(TAG, "onReceive: " + intent.getStringExtra("CHATMESSAGE"));
           // Log.d(TAG, "onReceive1111111111111111111111: " + intent.getStringExtra("FROMUSER"));
            //UserData obj=new UserData(1,extras.get("FROMUSER").toString(),extras.get("TOUSER").toString(),intent.getStringExtra("CHATMESSAGE"));
            //DBAdapter.addUserData(obj);
            chatArrayAdapter.add(new ChatMessage(true, intent.getStringExtra("CHATMESSAGE"),intent.getStringExtra("TYPE")));
        }
    };
    
    /*
     * This code not in use.
     * 
     * 
     */

    
      
    
}


package org.mdcconcepts.kidsi.chat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.mdcconcepts.kidsi.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ChatArrayAdapter extends ArrayAdapter<ChatMessage> {

	private TextView chatText;
	private ImageView chatImg;
	private List<ChatMessage> chatMessageList = new ArrayList<ChatMessage>();
	private LinearLayout singleMessageContainer;

	@Override
	public void add(ChatMessage object) {
		chatMessageList.add(object);
		super.add(object);
	}

	public ChatArrayAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
	}

	public int getCount() {
		return this.chatMessageList.size();
	}

	public ChatMessage getItem(int index) {
		return this.chatMessageList.get(index);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		ChatMessage chatMessageObj = getItem(position);
		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			final String url=chatMessageObj.message;
			if(chatMessageObj.type.equals("MEDIA"))
			{
			row = inflater.inflate(R.layout.activity_chat_singlemedia, parent, false);
			singleMessageContainer = (LinearLayout) row.findViewById(R.id.singleMediaContainer);
			chatImg = (ImageView) row.findViewById(R.id.singleMessageImg);
			chatImg.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Log.d("media","media 1");

			        
			        new Thread(new Runnable() {
			            public void run() {
			            	 downloadFile(url);
			            }
			          }).start();
				}
			});
			}
			if(chatMessageObj.type.equals("CHAT"))
			{
			row = inflater.inflate(R.layout.activity_chat_singlemessage, parent, false);
			singleMessageContainer = (LinearLayout) row.findViewById(R.id.singleMessageContainer);
			chatText = (TextView) row.findViewById(R.id.singleMessage);
			chatText.setText(chatMessageObj.message);
			chatText.setBackgroundResource(chatMessageObj.left ? R.drawable.bubble_b : R.drawable.bubble_a);
			
			}
			singleMessageContainer.setGravity(chatMessageObj.left ? Gravity.LEFT : Gravity.RIGHT);
		}
		
		
		
		return row;
	}
void downloadFile(String dwnload_file_path){
    	
    	try {
    		URL url = new URL(dwnload_file_path);
    		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
    		urlConnection.setRequestMethod("GET");
    		urlConnection.setDoOutput(true);
    		String filename=dwnload_file_path.substring(dwnload_file_path.lastIndexOf("/")+1);
    		if(filename==null)
    			filename="swapnilfile.png";
    		//connect
    		urlConnection.connect();

    		//set the path where we want to save the file    		
    		File SDCardRoot = Environment.getExternalStorageDirectory(); 
    		//create a new file, to save the downloaded file 
    		File file = new File(SDCardRoot,filename);
 
    		FileOutputStream fileOutput = new FileOutputStream(file);

    		//Stream used for reading the data from the internet
    		InputStream inputStream = urlConnection.getInputStream();

    		//this is the total size of the file which we are downloading
    	//	totalSize = urlConnection.getContentLength();

    		
    		//create a buffer...
    		byte[] buffer = new byte[1024];
    		int bufferLength = 0;

    		while ( (bufferLength = inputStream.read(buffer)) > 0 ) {
    			fileOutput.write(buffer, 0, bufferLength);
    			//downloadedSize += bufferLength;
    			// update the progressbar //
    		
    		}
    		//close the output stream when complete //
    		fileOutput.close();
    	   		
    	
    	} catch (final MalformedURLException e) {
    		//showError("Error : MalformedURLException " + e);  		
    		e.printStackTrace();
    	} catch (final IOException e) {
    		//showError("Error : IOException " + e);  		
    		e.printStackTrace();
    	}
    	catch (final Exception e) {
    		//showError("Error : Please check your internet connection " + e);
    	}    	
    }
	public Bitmap decodeToBitmap(byte[] decodedByte) {
		return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
	}

}
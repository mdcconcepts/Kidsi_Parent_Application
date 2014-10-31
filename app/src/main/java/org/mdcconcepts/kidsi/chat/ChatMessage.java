package org.mdcconcepts.kidsi.chat;

import android.util.Log;

public class ChatMessage {
	public boolean left;
	public String message;
	public String type;
	public ChatMessage(boolean left, String message,String type) {
		super();
		Log.d("TYPE in ChatMessage", type);
		this.left = left;
		this.message = message;
		this.type=type;
	}
}
package org.mdcconcepts.kidsi.chat;

public  class UserData {

	//private variables
    int _id;
    String _from;
    String _to;
    String _message;
    String _imei;
    String _messageType;
    // Empty constructor
    public UserData(){
 
    }
    // constructor
    public UserData(int id, String from, String to, String message ,String messageType){
        this._id      = id;
        this._from    = from;
        this._to      = to;
        this._message = message;
        this._messageType=messageType;
    }
 
    // getting ID
    public int getID(){
        return this._id;
    }
 
    // setting id
    public void setID(int id){
        this._id = id;
    }
 
    // getting imei
    public String getIMEI(){
        return this._imei;
    }
 
    // setting imei
    public void setIMEI(String imei){
        this._imei = imei;
    }
    
    // getting name
    public String getFrom(){
        return this._from;
    }
 
    // setting name
    public void setFrom(String name){
        this._from = name;
    }
    public String getTo(){
        return this._to;
    }
 
    // setting name
    public void setTo(String name){
        this._to = name;
    }
    // getting Message
    public String getMessage(){
        return this._message;
    }
 
    // setting Message
    public void setMessage(String message){
        this._message = message;
    }
    // getting Message Type
    public String getMessageType(){
        return this._messageType;
    }
 
    // setting Message Type
    public void setMessageType(String messageType){
        this._messageType= messageType;
    }
	
}

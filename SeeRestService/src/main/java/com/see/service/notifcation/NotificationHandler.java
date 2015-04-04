package com.see.service.notifcation;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.see.service.cache.NewCache;
import com.see.service.util.Util;

public class NotificationHandler {

	public static final Object lock = new Object();
	
	private static final NotificationHandler INSTANCE = new NotificationHandler();
	
	private NotificationHandler(){	
	}
	
	public static NotificationHandler getInstance(){
		return INSTANCE;
	}
	
	
	private static Set<String> streamCache = new HashSet<String>();
	
	public void addStream(String channelName) throws Exception{
		
		Set<String> allChannels = NewCache.getInstance().getChannels();
		if(!allChannels.contains(channelName)){
			throw new Exception("The channel name " + channelName + " does not exist");
		}
		
		if(streamCache.contains(channelName)){
			throw new Exception("The channel is already streaming");
		}
		
		streamCache.add(channelName);
	}
	
	public void removeStream(String channelName){
		streamCache.remove(channelName);
	}
	
	public String getStreamUrl(String channelName){		
		Set<String> subscriptions = NewCache.getInstance().getSubscriptions(channelName);
		if(subscriptions != null && !subscriptions.isEmpty()){			
			subscriptions.retainAll(streamCache);			
			if(subscriptions != null && !subscriptions.isEmpty()){
				String firstChannel = null;
				Iterator<String> iter = subscriptions.iterator();
				while(iter.hasNext()){
					firstChannel = iter.next();
					break;
				}
				
				return createStreamUrl(firstChannel);
			}
			else{
				return null;
			}
		}
		else{
			return null;
		}		
	}
	
	private String createStreamUrl(String channelName){
		return Util.PLAY_REMOTE_STREAM_URL + channelName;
	}
}

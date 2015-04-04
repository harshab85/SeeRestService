package com.see.service.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class ChannelCache {

	
	private static ChannelCache INSTANCE = new ChannelCache();
	
	/*
	 * Key - user id
	 * Value - channel name
	 */
	private static Map<String, String> channelCache = new HashMap<String, String>();
	
	/*
	 * Key - channel name
	 * Value - list of subscribed user ids
	 */
	private static Map<String, List<String>> subscribedUsersCache = new HashMap<String, List<String>>();
	
	/*
	 * key - channel name
	 * value - video url list
	 */
	private static Map<String, List<Video>> videoCache = new HashMap<String, List<Video>>();
	
	
	private ChannelCache(){		
	}
	
	public static ChannelCache getInstance(){
		return INSTANCE;
	}
	
	public void register(String userId) throws Exception{		
		if(channelCache.containsKey(userId)){
			throw new Exception("The user id already exists");
		}
		
		channelCache.put(userId, null);		
	}
	
	public void createChannel(String userId, String channelName) throws Exception{		
		
		String cachedChannelName = channelCache.get(userId);		
		if(cachedChannelName != null){
			throw new Exception("The user id already has a channel. Name: " + cachedChannelName);
		}
		else{
			channelCache.put(userId, channelName);
		}
	}
	
	public void subscribe(String userId, List<String> subscribedChannels){	
		
		Iterator<String> iter = subscribedChannels.iterator();
		while(iter.hasNext()){
			String channelName = iter.next();
			List<String> subscribedUsers = subscribedUsersCache.get(channelName);
			if(subscribedUsers == null){
				subscribedUsers = new ArrayList<String>();
				subscribedUsers.add(userId);
				subscribedUsersCache.put(channelName, subscribedUsers);
			}
			else{
				int index = subscribedUsers.indexOf(userId);
				if(index < 0){
					subscribedUsers.add(userId);
					subscribedUsersCache.put(channelName, subscribedUsers);
				}
			}						
		}	
	}
	
	public List<String> getChannels(){
		Collection<String> channels = channelCache.values();
		List<String> channelsList = new ArrayList<String>(channels);
		return channelsList;
	}
	
	public void putVideo(String userId, String ownerChannelName, String videoUrl, long timeStamp) throws Exception{
		if(!channelCache.containsKey(userId)){
			throw new Exception("The user id " + userId + " is not found");
		}
		
		String cachedChannelName = channelCache.get(userId);
		if(!cachedChannelName.equals(ownerChannelName)){
			throw new Exception("The channel name " + ownerChannelName + " is not registered for this user " + userId);
		}
				
		List<String> subscribedUsers = subscribedUsersCache.get(ownerChannelName);
		Iterator<String> iter = subscribedUsers.iterator();
		while(iter.hasNext()){
			String user = iter.next();
			List<Video> videos = videoCache.get(user);
			if(videos == null){
				videos = new ArrayList<ChannelCache.Video>();
				Video video = new Video();
				video.videoUrl = videoUrl;
				video.timeStamp = timeStamp;
				videos.add(video);
				videoCache.put(user, videos);
			}
			else{
				Video video = new Video();
				video.videoUrl = videoUrl;
				video.timeStamp = timeStamp;
				videos.add(video);
				videoCache.put(user, videos);
			}			
		}
	}	
	
	public String getVideo(String userId){		
		List<Video> videos = videoCache.get(userId);
		if(videos != null && !videos.isEmpty()){
			Video video = videos.get(0);
			return video.getVideoUrl();
		}
		
		return null;
	}
	
	public void deleteVideo(String userId, String ownerChannelName, String videoUrl) throws Exception{		
		if(!channelCache.containsKey(userId)){
			throw new Exception("The user id " + userId + " is not found");
		}
		
		String cachedChannelName = channelCache.get(userId);
		if(!cachedChannelName.equals(ownerChannelName)){
			throw new Exception("The channel name " + ownerChannelName + " is not registered for this user " + userId);
		}
				
		List<String> subscribedUsers = subscribedUsersCache.get(ownerChannelName);
		Iterator<String> iter = subscribedUsers.iterator();
		while(iter.hasNext()){
			String user = iter.next();
			List<Video> videos = videoCache.get(user);
			if(videos != null){
				videos.remove(videoUrl);
			}
		}
	}
	
	class Video{
		private String videoUrl;
		private long timeStamp;
		
		public String getVideoUrl() {
			return videoUrl;
		}
		public long getTimeStamp() {
			return timeStamp;
		}
				
	}
}

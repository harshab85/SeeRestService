package com.see.service.cache;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import com.see.service.util.Util;

public class ChannelCache {

	/*
	 * Singleton pattern
	 */
	private static final ChannelCache INSTANCE = new ChannelCache();
	
	private ChannelCache(){
		
	}
	
	public static ChannelCache getInstance(){
		return INSTANCE;
	}
	
	
	/*
	 * Cache
	 * key - channel name
	 * value - list of subscribers
	 */	
	private static Map<String, Set<String>> CACHE = new HashMap<String, Set<String>>();
	
	private static Map<String, Set<String>> NEWCACHE = new HashMap<String, Set<String>>();
		
	
	public Set<String> getRegistrationIds(String channelName){
		return NEWCACHE.get(channelName);
	}
	
	/*
	 * Delete all
	 */
	public void clear(){
		CACHE.clear();
	}
	
	/*
	 * Create channel
	 */
	public void createChannel(String channelName, String registrationId) throws Exception{
		if(CACHE.containsKey(channelName)){
			throw new Exception("The channel name already exists");
		}
		else{
			if(createRemoteChannel(channelName)){			
				CACHE.put(channelName, new HashSet<String>());
				NEWCACHE.put(registrationId, new HashSet<String>());
			}
			else{
				throw new Exception("Remote channel creation failed");
			}
			
			
		}
	}
	
	class CreateRemoteChannelHeader implements Header{

		@Override
		public String getName() {			
			return HttpHeaders.CONTENT_TYPE;
		}

		@Override
		public String getValue() {
			return "application/json";
		}

		@Override
		public HeaderElement[] getElements() throws ParseException {
			return new HeaderElement[0];
		}
		
	}
	
	private boolean createRemoteChannel(String channelName) throws Exception{
		
		CloseableHttpClient client = null;
		CloseableHttpResponse response = null;
		InputStream inputStream = null;		
		
		List<Header> headers = new ArrayList<Header>(1);
		Header header = new CreateRemoteChannelHeader();
		headers.add(header);
		
		try {
			HttpGet request = new HttpGet(Util.CREATE_REMOTE_CHANNEL_URL + channelName);			
			client = HttpClientBuilder.create().setDefaultHeaders(headers).build();			
			response = client.execute(request);
			
			HttpEntity entity = response.getEntity();
			if(entity != null){
				inputStream = entity.getContent();
				if(inputStream != null){
					StringWriter writer = new StringWriter();
					IOUtils.copy(inputStream, writer);
					String responseString = writer.toString();
					if(responseString == null || responseString.isEmpty() || !responseString.trim().equals("1")){
						return false;
					}
					else{
						return true;
					}
				}
			}								
		} 
		catch (Exception e) {
			throw new Exception("Remote channel creation failed");
		} 	
		finally{
			try{				
				if(inputStream != null){
					inputStream.close();
				}
				if(response != null){
					response.close();
				}
				if(client != null){
					client.close();
				}
			}
			catch(Exception e){
				// Ignore
			}
		}
		
		return true;
	}
		
	/*
	 * Subscribe
	 */
	public void subscribe(String registrationId, String newSubscription) throws Exception{
		
		/*if(!CACHE.containsKey(registrationId)){
			throw new Exception("The requestor channel does not exist : " + registrationId);
		}
		
		if(!CACHE.containsKey(newSubscription)){
			throw new Exception("The subscribed channel does not exist : " + newSubscription);
		}
		
		Set<String> cachedSubscriptions = CACHE.get(registrationId);
		cachedSubscriptions.add(newSubscription);
		CACHE.put(registrationId, cachedSubscriptions);*/			
		
		//cachedSubscriptions.clear();
		Set<String> cachedSubscriptions = NEWCACHE.get(registrationId);
		cachedSubscriptions.add(registrationId);
		NEWCACHE.put(newSubscription, cachedSubscriptions);
	}
	
	/*
	 * Get channels
	 */
	public Set<String> getChannels(){
		Set<String> cachedChannels = CACHE.keySet();
		Set<String> channelsList = new HashSet<String>(cachedChannels);		
		return channelsList;
	}
	
	/*
	 * Get subscriptions
	 */
	public Set<String> getSubscriptions(String channelName){
		Set<String> subscriptions = new HashSet<String>(NEWCACHE.get(channelName));
		return subscriptions;
	}
}

package com.see.service.controller;

import java.util.Set;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.see.service.cache.ChannelCache;
import com.see.service.gcm.GCM;
import com.see.service.notifcation.NotificationHandler;
import com.see.service.request.impl.CreateChannelRequest;
import com.see.service.request.impl.NotifyRequest;
import com.see.service.request.impl.ResetStreamHostRequest;
import com.see.service.request.impl.SubscribeRequest;
import com.see.service.request.impl.PollRequest;
import com.see.service.response.impl.CreateChannelResponse;
import com.see.service.response.impl.DeleteAllResponse;
import com.see.service.response.impl.GetStatusResponse;
import com.see.service.response.impl.GetChannelsResponse;
import com.see.service.response.impl.NotifyResponse;
import com.see.service.response.impl.PollResponse;
import com.see.service.response.impl.RemoveStreamResponse;
import com.see.service.response.impl.RestStreamHostResponse;
import com.see.service.response.impl.SubscribeResponse;
import com.see.service.response.intf.IResponse;
import com.see.service.util.Util;


@RestController
@RequestMapping("/see")
public class ServiceController {

	@RequestMapping(value = "/ping", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public IResponse ping(){		
		IResponse response = new GetStatusResponse(true, null);
		return response;
	}	
	
	@RequestMapping(value = "/createChannel", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public IResponse createChannel(@RequestBody CreateChannelRequest createChannelRequest){
			
		IResponse response = null;		
		if(createChannelRequest == null){
			response = new CreateChannelResponse(false, "The request is unavailable.");
		}
		else{
			String channelName = createChannelRequest.getChannelName();
			String registrationId = createChannelRequest.getRegistrationId();
			if(channelName == null || channelName.trim().isEmpty()){
				response = new CreateChannelResponse(false, "The channel name is unavailable.");
			}
			else if(registrationId == null || registrationId.isEmpty()){
				response = new CreateChannelResponse(false, "The registration id is unavailable.");
			}
			else{
				try {
					channelName = channelName.trim();
					ChannelCache.getInstance().createChannel(channelName, registrationId);
					response = new CreateChannelResponse(true, null);
				} 
				catch (Exception e) {
					response = new CreateChannelResponse(false, e.getMessage());
				}
			}			
		}
		
		return response;
	}
	
	
	@RequestMapping(value = "/subscribe", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	public IResponse subscribe(@RequestBody SubscribeRequest subscribeRequest){
		
		IResponse response = null;
		
		if(subscribeRequest == null){
			response = new SubscribeResponse(false, "The request unavailable.");
		}
		else{
			String registrationId = subscribeRequest.getRegistrationId();
			String newSubscription = subscribeRequest.getNewSubscription();
			
			if(registrationId == null || registrationId.isEmpty()){
				response = new SubscribeResponse(false, "The registration id is unavailable.");
			}
			else if(newSubscription == null || newSubscription.isEmpty()){
				response = new SubscribeResponse(false, "Subscribed channel is unavailable.");
			}
			else{
				try {
					ChannelCache.getInstance().subscribe(registrationId, newSubscription);
					response = new SubscribeResponse(true, null);
				} 
				catch (Exception e) {
					response = new SubscribeResponse(false, e.getMessage());
				}				
			}
		}
		
		return response;
	}
	
	@RequestMapping(value = "/getChannels", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public IResponse getChannels(){				
		Set<String> channels = ChannelCache.getInstance().getChannels();
		IResponse response = new GetChannelsResponse(true, null, channels);
		return response;
	}
	
	@RequestMapping(value = "/notify", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public IResponse notify(@RequestBody NotifyRequest notifyRequest){
		
		IResponse response = null;
		
		if(notifyRequest == null){
			response = new NotifyResponse(false, "The request is unavailable.");
		}
		else{
			String channelName = notifyRequest.getChannelName();
			long timeStamp = notifyRequest.getTimeStamp();
			
			if(channelName == null || channelName.isEmpty()){
				response = new NotifyResponse(false, "The channel name is not available.");
			} 
			else{
				
				if(timeStamp == 0){
					timeStamp = System.currentTimeMillis();
				}
				
				channelName = channelName.trim();
				try {		
					synchronized (NotificationHandler.lock) {
						NotificationHandler.getInstance().addStream(channelName);
						Set<String> registrationIds = ChannelCache.getInstance().getRegistrationIds(channelName);
						
						String url = NotificationHandler.getInstance().createStreamUrl(channelName);
						GCM.getInstance().notifySubscribers(registrationIds, url);
					}
					response = new NotifyResponse(true, null);
				} 
				catch (Exception e) {
					response = new NotifyResponse(false, e.getMessage());
				}								
			}			
		}
		
		return response;
	}
	
	@RequestMapping(value = "/removeNotification/{channelName}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public IResponse removeStream(@PathVariable String channelName){
		
		IResponse response = null;
		
		if(channelName == null || channelName.isEmpty()){
			response = new RemoveStreamResponse(false, "The channel name is unavailable.");
		} 			
		else{								
			channelName = channelName.trim();
				
			synchronized (NotificationHandler.lock) {
				NotificationHandler.getInstance().removeStream(channelName);				
			}
				
			response = new RemoveStreamResponse(true, null);											
		}									
				
		return response;
	}
	
	@RequestMapping(value = "/deleteAll", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public IResponse deleteAll(){						
		ChannelCache.getInstance().clear();
		NotificationHandler.getInstance().clear();						
		return new DeleteAllResponse();
	}
	
	@RequestMapping(value = "/poll", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public IResponse poll(@RequestBody PollRequest pollRequest){
		
		IResponse response = null;
		
		if(pollRequest == null){
			response = new PollResponse(false, "The request is unavailable.", null);
		}
		else{
			String channelName = pollRequest.getChannelName();
			if(channelName == null || channelName.isEmpty()){
				response = new PollResponse(false, "The channel name is unavailable.", null);
			}
			else{
				String videoUrl = NotificationHandler.getInstance().getStreamUrl(channelName);
				if(videoUrl != null && !videoUrl.isEmpty()){
					response = new PollResponse(videoUrl);
				}
				else{
					response = new PollResponse();
				}
			}
		}
		
		return response;
	}
	
	@RequestMapping(value = "/resetStreamHost", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)	
	public IResponse resetStreamHost(@RequestBody ResetStreamHostRequest resetStreamHost){
		
		String newHost = resetStreamHost.getNewHost();
		Util.reset(newHost);
		
		return new RestStreamHostResponse();		
	}
	
}

package com.see.service.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.see.service.cache.ChannelCache;
import com.see.service.request.impl.CreateChannelRequest;
import com.see.service.request.impl.DeleteVideoRequest;
import com.see.service.request.impl.PollRequest;
import com.see.service.request.impl.NotifyRequest;
import com.see.service.request.impl.RegisterUserRequest;
import com.see.service.request.impl.SubscribeRequest;
import com.see.service.response.impl.CreateChannelResponse;
import com.see.service.response.impl.DeleteVideoResponse;
import com.see.service.response.impl.GetChannelsResponse;
import com.see.service.response.impl.GetStatusResponse;
import com.see.service.response.impl.PollResponse;
import com.see.service.response.impl.NotifyResponse;
import com.see.service.response.impl.RegisterUserResponse;
import com.see.service.response.impl.SubscribeResponse;
import com.see.service.response.intf.IResponse;


//@RestController
//@RequestMapping("/see")
public class ServiceController {

	@RequestMapping(value = "/getStatus", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public IResponse getStatus(){		
		IResponse response = new GetStatusResponse(true, null);
		return response;
	}	
	
	
	@RequestMapping(value = "/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public IResponse register(@RequestBody RegisterUserRequest registerUserRequest){
		
		IResponse response = null;
		
		if(registerUserRequest == null){
			response = new RegisterUserResponse(false, "The request is not available.");
		}
		else{			
			String userId = registerUserRequest.getUserId();
			if(userId == null || userId.isEmpty()){
				response = new RegisterUserResponse(false, "The user id is not available.");
			}
			else{
				userId = userId.trim();
				try {
					ChannelCache.getInstance().register(userId);
					response = new RegisterUserResponse(true, null);
				} 
				catch (Exception e) {
					response = new RegisterUserResponse(false, "Registration failed!");
				}								
			}
		}
		
		return response;
	}
	
	
	@RequestMapping(value = "/createChannel", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public IResponse createChannel(@RequestBody CreateChannelRequest createChannelRequest){
			
		IResponse response = null;		
		if(createChannelRequest == null){
			response = new CreateChannelResponse(false, "The request is not available.");
		}
		else{
			String channelName = createChannelRequest.getChannelName();
			String userId = createChannelRequest.getUserId();
			
			if(userId == null || userId.isEmpty()){
				response = new CreateChannelResponse(false, "The user id is not available.");
			}
			else if(channelName == null || channelName.isEmpty()){
				response = new CreateChannelResponse(false, "The channel name is not available.");
			}
			else{
				try {
					userId = userId.trim();
					channelName = channelName.trim();
					
					ChannelCache.getInstance().createChannel(userId, channelName);
					response = new CreateChannelResponse(true, null);
				} 
				catch (Exception e) {
					response = new CreateChannelResponse(false, "Create channel failed!");
				}
			}			
		}
		
		return response;
	}
	
	
	@RequestMapping(value = "/subscribe", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	public IResponse subscribeToChannels(@RequestBody SubscribeRequest subscribeRequest){
		
		IResponse response = null;
		
		if(subscribeRequest == null){
			response = new SubscribeResponse(false, "The request is not available.");
		}
		else{
			String userId = subscribeRequest.getUserId();
			List<String> subscribedChannels = subscribeRequest.getSubscriptions();
			
			if(userId == null || userId.isEmpty()){
				response = new SubscribeResponse(false, "The user id is not available.");
			}
			else if(subscribedChannels == null || subscribedChannels.isEmpty()){
				response = new SubscribeResponse(false, "The subscribed channels list is not available.");
			}
			else{
				userId = userId.trim();
				
				ChannelCache.getInstance().subscribe(userId, subscribedChannels);
				response = new SubscribeResponse(true, null);
			}
		}
		
		return response;
	}
	
	@RequestMapping(value = "/getChannels", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public IResponse getChannels(){				
		List<String> channels = ChannelCache.getInstance().getChannels();
		IResponse response = new GetChannelsResponse(true, null, channels);
		return response;
	}
	
	@RequestMapping(value = "/notify", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public IResponse putVideo(@RequestBody NotifyRequest notifyRequest){
		
		IResponse response = null;
		
		if(notifyRequest == null){
			response = new NotifyResponse(false, "The request is unavailable.");
		}
		else{
			String channelName = notifyRequest.getChannelName();
			//String userId = notifyRequest.getUserId();
			//String videoUrl = notifyRequest.getVideoUrl();
			long timeStamp = notifyRequest.getTimeStamp();
			
			/*if(userId == null || userId.isEmpty()){
				response = new NotifyResponse(false, "The user id is not available.");
			}
			else*/ if(channelName == null || channelName.isEmpty()){
				response = new NotifyResponse(false, "The channel name is not available.");
			} 
			/*else if(videoUrl == null || videoUrl.isEmpty()){
				response = new NotifyResponse(false, "The video url is not available.");
			}*/ 
			else{
				
				if(timeStamp == 0){
					timeStamp = System.currentTimeMillis();
				}
				
				channelName = channelName.trim();
				//userId = userId.trim();
				
				try {
					//ChannelCache.getInstance().putVideo(userId, channelName, videoUrl, timeStamp);
					response = new NotifyResponse(true, null);
				} 
				catch (Exception e) {
					response = new NotifyResponse(false, "Put Video Failed!");
				}								
			}			
		}
		
		return response;
	}
	
	@RequestMapping(value = "/poll", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public IResponse poll(@RequestBody PollRequest pollRequest){
		
		IResponse response = null;
		
		if(pollRequest == null){
			response = new PollResponse(false, "The request is not available.", null);
		}
		else{
			String userId = pollRequest.getUserId();
			if(userId == null || userId.isEmpty()){
				response = new PollResponse(false, "The user id is not available.", null);
			}
			else{
				userId = userId.trim(); 
				String videoUrl = ChannelCache.getInstance().getVideo(userId);
				if(videoUrl != null && !videoUrl.isEmpty()){
					response = new PollResponse(true, null, videoUrl);
				}
				else{
					response = new PollResponse(false, "No videos available", null);
				}
			}
		}
		
		System.out.println("Poll");
						
		return response;
	}
	
	@RequestMapping(value = "/deleteVideo", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public IResponse deleteVideo(@RequestBody DeleteVideoRequest deleteVideoRequest){
		
		IResponse response = null;
		
		if(deleteVideoRequest == null){
			return new DeleteVideoResponse(false, "The request is not available");
		}
		else{						
			String channelName = deleteVideoRequest.getChannelName();
			String userId = deleteVideoRequest.getUserId();
			String videoUrl = deleteVideoRequest.getVideoUrl();
			
			if(userId == null || userId.isEmpty()){
				response = new DeleteVideoResponse(false, "The user id is not available.");
			}
			else if(channelName == null || channelName.isEmpty()){
				response = new DeleteVideoResponse(false, "The channel id is not available.");
			} 
			else if(videoUrl == null || videoUrl.isEmpty()){
				response = new DeleteVideoResponse(false, "The video url is not available.");
			} 
			else{								
				channelName = channelName.trim();
				userId = userId.trim();
				
				try {
					ChannelCache.getInstance().deleteVideo(userId, channelName, videoUrl);
					response = new DeleteVideoResponse(true, null);
				} 
				catch (Exception e) {
					response = new DeleteVideoResponse(false, "Delete video failed!");
				}								
			}									
		}
		
		return response;
	}
	
}

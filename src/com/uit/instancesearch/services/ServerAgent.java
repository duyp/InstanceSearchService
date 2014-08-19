package com.uit.instancesearch.services;

public class ServerAgent {
	
	private String serverId;
	private String serverName;
	private String requestTag;
	
	private ClientAgent clientConnected;
	
	public ServerAgent(String id, String name, String requestTag){
		serverId = id;
		serverName = name;
		this.requestTag = requestTag;
		clientConnected = null;
	}
	
	public String getId(){
		return serverId;
	}
	
	public String getName(){
		return serverName;
	}
	
	public String getRequestTag() {
		return requestTag;
	}
	
	public void setClient(ClientAgent client){
		clientConnected = client;
	}
	
	public ClientAgent getClientConnected(){
		return clientConnected;
	}
}

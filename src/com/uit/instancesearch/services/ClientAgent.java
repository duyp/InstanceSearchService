package com.uit.instancesearch.services;

public class ClientAgent {
	
	private String id;
	private String name;
	
	private int status;
	private String requestTag;
	private String queryContent;
	private String[] queryResult;
	
	public ClientAgent(String name, String requestTag,String queryImageContent) {
		refreshId();
		this.name = name;
		this.requestTag = requestTag;
		this.queryContent = queryImageContent;
		queryResult = null;
		status = ClientController.STATUS_NONE;
	}
	
	public String getQueryContent(){
		return queryContent;
	}
	
	// set current status for client 
	// status: STATUS_NONE, STATUS_WAIT_CONNECT, STATUS_WAIT_RESPONE
	public void setStatus(int status) {
		this.status = status;
	}
	
	public int getStatus() {
		return status;
	}
	
	public String getId(){
		return id;
	}
	
	// generate a random id
	public void refreshId() {
		this.id = Tools.getRandomNumberString(10);
	}
	
	public String getName(){
		return name;
	}
	
	public String getRequestTag() {
		return requestTag;
	}
	
	public void setQueryResult(String[] result) {
		queryResult = result;
	}
	
	public String[] getQueryResult() {
		return queryResult;
	}
}

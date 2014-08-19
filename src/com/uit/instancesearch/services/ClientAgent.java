package com.uit.instancesearch.services;

public class ClientAgent {
	
	private int id;
	private String name;
	
	private String requestTag;
	private String queryContent;
	private String[] queryResult;
	
	public ClientAgent(int id, String name, String requestTag,String queryImageContent) {
		this.id = id;
		this.name = name;
		this.requestTag = requestTag;
		this.queryContent = queryImageContent;
		queryResult = null;
	}
	
	public String getContent(){
		return queryContent;
	}
	
	public int getId(){
		return id;
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

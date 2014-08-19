package com.uit.instancesearch.services;


public class ISService {
	
	public static final int SERVER_TIMEOUT = 100000; // ms
	
	public static final String QUERY_TAG = "query";
	public static final String GET_IMAGE_TAG = "get";
	
	public int count = 0;
	
	public String[] serverConnect(String id, String name, String requestTag){
		ServerAgent sa = new ServerAgent(id, name, requestTag);
		if (ServerController.addServer(sa)) {
			ClientAgent client;
			long time = System.currentTimeMillis();
			while((client = sa.getClientConnected()) == null) {
				if (System.currentTimeMillis() - time > SERVER_TIMEOUT) {
					ServerController.removeServer(sa);
					return new String[] {"ERROR: Time Out"};
				}
				try{
					Thread.sleep(100);
				} catch(InterruptedException e) {
					e.printStackTrace();
					return null;
				}
			}
			ServerController.removeServer(sa);
			return new String[] {String.valueOf(client.getId()), client.getName(), client.getContent()};
		}
		return new String[] {"ERROR: Duplicate ID!"};
	}
	
	public String serverRespond(String clientId, String[] result) {
		try{
			ClientAgent c = ClientController.getClient(Integer.valueOf(clientId));
			if (c != null)
				c.setQueryResult(result);
			else return "NULL";
		} catch(Exception e) {
			return e.getMessage() + " \nERROR !" + clientId;
		}
		return "OK";
	}
	
	public String[] clientQueryRequest(String name, String requestTag, String queryImageContent) {
		ClientAgent client = new ClientAgent(ClientController.getClientNumbers(), name, requestTag, queryImageContent);
		if (ClientController.addClient(client)) {
			ServerAgent server;
			try {
				// wait for server connect
				long t = System.currentTimeMillis();
				while ((server = ServerController.getServerAgent(requestTag)) == null) {
					// server connect time out
					if (System.currentTimeMillis() - t > ClientController.CLIENT_CONNECT_TIMEOUT) {
						ClientController.removeClient(client);
						return new String[] {"err", "connect_timeout"};
					}
					Thread.sleep(100);
				}
				server.setClient(client);
				
				// wait for server respond
				t = System.currentTimeMillis();
				while(client.getQueryResult() == null) {
					// server respond time out
					if (System.currentTimeMillis() - t > ClientController.CLIENT_RESPOND_TIMEOUT) {
						ClientController.removeClient(client);
						return new String[] {"err", "respond_timeout"};
					}
					Thread.sleep(100);
				}
				
				// remove client from list and return result
				ClientController.removeClient(client);
				return client.getQueryResult();
				
			} catch (InterruptedException e) {
				//e.printStackTrace();
				ClientController.removeClient(client);
				return new String[] {"err","interupted"};
			}
		} else {
			return new String[] {"err","duplicated"};
		}
	}
}

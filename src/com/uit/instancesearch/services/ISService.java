package com.uit.instancesearch.services;


public class ISService {
	
	public static final String QUERY_TAG = "query";
	public static final String GET_FULL_IMAGE_TAG = "get-full";
	public static final String GET_PREVIEW_IMAGE_TAG = "get-preview";
	
	public int count = 0;
	
	public String[] serverConnect(String id, String name, String requestTag){
		ServerAgent sa = new ServerAgent(id, name, requestTag);
		if (ServerController.addServer(sa)) {
			// wait for client connected
			ClientAgent client;
			// check for time out
			long time = System.currentTimeMillis();
			while((client = sa.getClientConnected()) == null) {
				if (System.currentTimeMillis() - time > ServerController.SERVER_WAIT_TIMEOUT) {
					ServerController.removeServer(sa);
					return new String[] {"ERROR: Time Out"};
				}
				try{
					Thread.sleep(100);
				} catch(InterruptedException e) {
					ServerController.removeServer(sa);
					return new String[] {"ERROR: Thread Interrupted"};
				}
			}
			ServerController.removeServer(sa);
			return new String[] {client.getId(), client.getRequestTag(), client.getName(), client.getQueryContent()};
		}
		
		return new String[] {"ERROR: Duplicate ID!"};
	}
	
	public String serverRespond(String clientId, String[] result) {
		try{
			ClientAgent c = ClientController.getClient(clientId);
			if (c != null)
				// return results to client
				c.setQueryResult(result);
			else return "ERROR: missing connected client!";
		} catch(Exception e) {
			return e.getMessage() + " \nERROR !" + clientId;
		}
		return "OK";
	}
	
	public String[] clientQueryRequest(String name, String requestTag, String queryImageContent) {
		ClientAgent client = new ClientAgent(name, requestTag, queryImageContent);
		ClientController.addClient(client);
			ServerAgent server;
			try {
				// wait for server connect
				client.setStatus(ClientController.STATUS_WAIT_CONNECT);
				long t = System.currentTimeMillis();
				while ((server = ServerController.getServerAgent(requestTag)) == null) {
					// server connect time out
					if (System.currentTimeMillis() - t > ClientController.CLIENT_WAIT_TIMEOUT) {
						ClientController.removeClient(client);
						return new String[] {"err", "connect_timeout"};
					}
					Thread.sleep(100);
				}
				// connect to server
				server.setClient(client);
				
				// wait for server response
				client.setStatus(ClientController.STATUS_WAIT_RESPONE);
				t = System.currentTimeMillis();
				while(client.getQueryResult() == null) {
					// server response time out
					int timeout = requestTag.equals(QUERY_TAG)?	ServerController.SERVER_RESPOND_TIME_OUT_QUERY
																:ServerController.SERVER_RESPOND_TIME_OUT_GET;
					if (System.currentTimeMillis() - t > timeout) {
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
	}
}

package com.uit.instancesearch.services;

import java.util.Vector;

public class ServerController {

	public static final int SERVER_WAIT_TIMEOUT = 100000; // ms
	public static final int SERVER_RESPOND_TIME_OUT_QUERY = 20000; //ms
	public static final int SERVER_RESPOND_TIME_OUT_GET = 5000; //ms
	
	private static Vector<ServerAgent> queryServers = new Vector<ServerAgent>();
	private static Vector<ServerAgent> getServers = new Vector<ServerAgent>();
	
	public static boolean addServer(ServerAgent server){
		return checkInServer(server);
	}
	
	public static boolean addServer(String id, String name, String requestTag) {
		return addServer(new ServerAgent(id, name, requestTag));
	}
	
	public static ServerAgent getServerAgent(String requestTag){
		Vector<ServerAgent> servers = (requestTag.equals(ISService.QUERY_TAG)?queryServers:getServers);
		return servers.size() == 0 ? null : servers.lastElement(); // STACK: first in last out (old: servers.firstElement())
	}
	
	public static void removeServer(ServerAgent server) {
		Vector<ServerAgent> servers = (server.getRequestTag().equals(ISService.QUERY_TAG)?queryServers:getServers);
		servers.remove(server);
	}
	
	public static int getServerNumbers(String requestTag){
		return requestTag.equals(ISService.QUERY_TAG) ? queryServers.size():getServers.size();
	}
	
	private static boolean checkInServer(ServerAgent s) {
		Vector<ServerAgent> servers = (s.getRequestTag().equals(ISService.QUERY_TAG)?queryServers:getServers);
		for(int i = 0; i < servers.size(); i++) {
			if (servers.elementAt(i).getId().equals(s.getId())) {
				return false;
			}
		}
		servers.addElement(s);
		return true;
	}
	
}

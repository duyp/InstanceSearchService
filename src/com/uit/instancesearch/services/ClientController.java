package com.uit.instancesearch.services;

import java.util.Vector;

public class ClientController {
	
	public static final int CLIENT_WAIT_TIMEOUT = 10000;
	
	public static final int STATUS_WAIT_CONNECT = 0;
	public static final int STATUS_WAIT_RESPONE = 1;
	public static final int STATUS_NONE = -1;
	
	public static Vector<ClientAgent> clients = new Vector<ClientAgent>();

	public static boolean addClient(ClientAgent c) {
		while (!checkInClient(c)) {
			c.refreshId();
		}
		clients.add(c);
		return true;
	}
	
	public static int getClientNumbers() {
		return clients.size();
	}
	
	public static ClientAgent getClient(String clientId) {
		for(ClientAgent c : clients) {
			if (c.getId().equals(clientId))
				return c;
		}
		return null;
	}
	
	public static void removeClient(ClientAgent client) {
		clients.remove(client);
	}
	
	private static boolean checkInClient(ClientAgent clientIn) {
		for(ClientAgent c : clients) {
			if (c.getId().equals(clientIn.getId())) {
				return false;
			}
		}
		return true;
	}
}

package com.uit.instancesearch.services;

import java.util.Vector;

public class ClientController {
	
	public static final int CLIENT_CONNECT_TIMEOUT = 10000;
	public static final int CLIENT_RESPOND_TIMEOUT = 20000;
	
	public static Vector<ClientAgent> clients = new Vector<ClientAgent>();

	public static boolean addClient(ClientAgent client) {
		if (checkInClient(client.getId())) {
			clients.add(client);
			return true;
		}
		return false;
	}
	
	public static int getClientNumbers() {
		return clients.size();
	}
	
	public static ClientAgent getClient(int clientId) {
		
		for(int i = 0; i < clients.size(); i++){
			ClientAgent ca = clients.elementAt(i);
			if (ca.getId() == clientId)
				return ca;
		}
		return null;
	}
	
	public static void removeClient(ClientAgent client) {
		clients.remove(client);
	}
	
	private static boolean checkInClient(int id) {
		for(int i = 0; i < clients.size(); i++) {
			if (clients.elementAt(i).getId() == id) {
				return false;
			}
		}
		return true;
	}
}

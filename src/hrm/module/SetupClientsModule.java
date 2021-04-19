package hrm.module;

import java.util.List;

import hrm.entity.Address;
import hrm.entity.Client;
import lebah.module.LebahUserModule;
import lebah.portal.action.Command;

public class SetupClientsModule  extends LebahUserModule {
	
	String path = "apps/setupClients";
	
	@Override
	public String start() {
		listClients();
		return path + "/start.vm";
	}
	
	@Command("listClients")
	public String listClients() {
		List<Client> clients = db.list("select p from Client p order by p.name");
		context.put("clients", clients);
		return path + "/listClients.vm";
	}
	
	@Command("addNewClient")
	public String addNewClient() {
		context.remove("client");
		return path + "/client.vm";
	}
	
	@Command("saveNewClient")
	public String saveNewClient() {
		Client client = new Client();
		client.setName(getParam("clientName"));
		
		Address address = new Address();
		client.setAddress(address);
		
		db.save(client);
		
		return listClients();
	}
	
	@Command("editClient")
	public String editClient() {
		Client client = db.find(Client.class, getParam("clientId"));
		context.put("client", client);
		return path + "/client.vm";
	}
	
	@Command("updateClient")
	public String updateClient() {
		Client client = db.find(Client.class, getParam("clientId"));
		client.setName(getParam("clientName"));
		db.update(client);
		return listClients();
	}
	
	@Command("deleteClient")
	public String deleteClient() {
		context.remove("delete_error");
		Client client = db.find(Client.class, getParam("clientId"));
		try {
			db.delete(client);
		} catch ( Exception e ) {
			context.put("delete_error", "Can't delete client! " + e.getMessage());
		}
		return listClients();
	}

}

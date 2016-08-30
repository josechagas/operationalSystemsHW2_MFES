package models;

public class ResourceConnection {
	
	Resource resource;
	int timeUsed = 0;//time used by the process. Must be setted for 0 in release

	public ResourceConnection(Resource resource) {
		this.resource = resource;
	}
}

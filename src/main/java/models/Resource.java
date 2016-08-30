package models;

import java.util.concurrent.Semaphore;

public class Resource {
	int id;//identifier, unique to each resource
	String name;//the name of the resource
	int quantity;//the general quantity of the recourse
	Semaphore available;//the available quantity of the current recourse.
	
	public int getId(){
		return id;
	}
	
	public String getName(){
		return name;
	}
	
	public int getQuant(){
		return quantity;
	}
	
	public int getAvailableQuant(){
		return available.availablePermits();
	}
	
	public Resource(int id, String name, int quantity){
		this.id = id;
		this.name = name;
		this.quantity = quantity;
		this.available = new Semaphore(quantity);
	}

	public void acquire() {
		try {
			available.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}

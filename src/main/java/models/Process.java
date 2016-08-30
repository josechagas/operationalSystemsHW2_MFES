package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.Semaphore;

import mainpack.Main;

public class Process implements Runnable{
	ArrayList<Integer> necessaryResources;
	
	int id;//identifier, unique to each process
	int timeToRequestResource;//time for each request from resources
	int timeToReleaseResource;//time to release each resource
	int timeRunning;//this value will grow forever
	
	boolean isAlive = true; 
	
	ArrayList<ResourceConnection> resourceConnections = new ArrayList<ResourceConnection>();
	Resource resourceTryingAcquire;
	
	public int getId(){
		return id;
	}
	
	public Resource getResourceTryingAcquire(){
		return resourceTryingAcquire;
	}
	
	public ArrayList<ResourceConnection> getResourceConnections(){
		return resourceConnections;
	}
	
	public int numbOfConnectionWithResource(int id){
		int quant = 0;
		for (ResourceConnection resourceConnection : resourceConnections) {
			if (resourceConnection.resource.getId() == id){
				quant++;
			}
		}
		return quant;
	}
	
	public Process(int id, int timeToRequestResource, int timeToReleaseResource) {
		this.id = id;
		this.timeToRequestResource = timeToRequestResource;
		this.timeToReleaseResource = timeToReleaseResource;
		timeRunning = 0;
		necessaryResources = null;
	}

	public Process(int id, int timeToRequestResource, int timeToReleaseResource,ArrayList<Integer> necessaryResources) {
		this.id = id;
		this.timeToRequestResource = timeToRequestResource;
		this.timeToReleaseResource = timeToReleaseResource;
		timeRunning = 0;

		this.necessaryResources = necessaryResources;
	}
	
	public void run() {
		// TODO Auto-generated method stub
		
		while(isAlive) {
			
			//try to sleep for the determined time.
			try {Thread.sleep(1000);} 
			catch (InterruptedException e) {e.printStackTrace();}
			timeRunning++;
			
			releaseResource();
			if(isAlive){
				requestResource();
			}
			
		}
		
		freeResources();
		System.out.println("morreu id "+ id);
	}
	
	public void kill(){
		
		isAlive = false;
		if(resourceTryingAcquire != null){//incrementa no recurso onde esta travado para que possa sair
			resourceTryingAcquire.available.release();
		}
	}
	
	private void freeResources(){
		/*problemas ao matara thread processo
			1- resourceTryingAcquire != null mas ainda nao fez o aquire, fazendo com que no fim o  valor do semaforo fique maior que o normal
			2- resourceTryingAcquire != null e ja fez o aquire e nao chamando o if abaixo a thread nunca morre fazendo com que no fim o  valor do semaforo fique menor que o normal
			possivel solucao fazer o metodo requestResource nunca fique pela metade a sua execucao
		*/
		
		/*
		 * 1- resourceTryingAcquire != null mas ainda nao fez o aquire, fazendo com que no fim o  valor do semaforo fique maior que o normal
		
		if(resourceTryingAcquire != null){//incrementa no recurso onde esta travado para que possa sair
			resourceTryingAcquire.available.release();
		}*/

		for(int i = 0 ; i < resourceConnections.size() ; i++){
			ResourceConnection rConnection = resourceConnections.get(i);
			rConnection.resource.available.release();

			if (necessaryResources != null && necessaryResources.size() > 0){
				this.necessaryResources.add(this.necessaryResources.size(),rConnection.resource.id);
		}
		}
		resourceConnections = new ArrayList<ResourceConnection>();
		

	}
	
	private void requestResource() {
		if (timeRunning%timeToRequestResource == 0) {
			ArrayList<Resource> resources = this.resourcesAvailable();
			int quantityResources = resources.size();
			
			if(quantityResources == 0){//nao tem como fazer o rand
				return;
			}


			int resourceIndex = 0;
			if (necessaryResources != null && necessaryResources.size() > 0){
				resourceIndex = necessaryResources.get(0);
			}
			else{
				Random rand = new Random();
				resourceIndex = rand.nextInt(quantityResources);
			}


			tryToGetResource(resourceIndex,resources);
		}
	}



	private void tryToGetResource(int resourceIndex,ArrayList<Resource> fromResources){
		Resource resourceForAcquire = fromResources.get(resourceIndex);
		System.out.println("O processo " + id + " tentando pegar " + resourceForAcquire.name);

		resourceTryingAcquire = resourceForAcquire;

		resourceForAcquire.acquire();

		resourceTryingAcquire = null;
		if(isAlive){

			this.resourceConnections.add(new ResourceConnection(resourceForAcquire));

			if (necessaryResources != null && necessaryResources.size() > 0){
				necessaryResources.remove(0);
			}

			System.out.println("O processo " + id + " está de posse do recurso " + resourceForAcquire.name);
		}
	}
	
	
	public ArrayList<Resource> resourcesAvailable() {
		ArrayList<Resource> resources = Main.comp.getResources();
		ArrayList<Integer> resourcesAllocated = new ArrayList<Integer>(Collections.nCopies(resources.size(), 0));
		ArrayList<Resource> resourcesAvailable = new ArrayList<Resource>();
	
		for (ResourceConnection rConnection : resourceConnections) {
			int resourceId = rConnection.resource.id;
			int alreadAllocated = resourcesAllocated.get(resourceId/*-1*/);
			resourcesAllocated.set(resourceId/*-1*/, alreadAllocated+1);
		}
				
		for (int i = 0; i < resourcesAllocated.size(); i++) {
			Resource resource = resources.get(i);
						
			if ((resource.quantity-resourcesAllocated.get(i)) > 0) {
				resourcesAvailable.add(resources.get(i));
			}
		}
		
		return resourcesAvailable;
	}
	
	public void releaseResource() {
		
		for(int i = 0 ; i < resourceConnections.size() ; i++){
			ResourceConnection rConnection = resourceConnections.get(i);
			rConnection.timeUsed++;
			
			if(rConnection.timeUsed%timeToReleaseResource == 0) {
				
				rConnection.resource.available.release();
				this.resourceConnections.remove(rConnection);
				System.out.println("O processo " + id + " liberou o recurso " + rConnection.resource.name);
			}
		}
		
	}

}

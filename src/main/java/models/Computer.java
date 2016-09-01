package models;
import java.util.ArrayList;


public class Computer implements Runnable{
	private OperationalSystem operationalSystem;//the operational system of the computer.
	private ArrayList<Resource> resources;//all the resources of the computer (printer, CD/DVD RW, etc).
	
	
	public OperationalSystem getOperationalSystem() {
		return operationalSystem;
	}
	
	public ArrayList<Resource> getResources() {
		return resources;
	}
	
	public int numberOfResources(){
		return resources.size();
	}
	
	public void addNewResource(Resource newOne){
		resources.add(newOne);
	}

	//this is just a simple initializer. We're using the basic things.
	public Computer(OperationalSystem operationalSystem, ArrayList<Resource> resources){
		this.operationalSystem = operationalSystem;
		this.resources = resources;
	}
    
    public createConflictMethod(){
        System.out.println("Git update 1: Flavio");
        System.out.println("Git update 2: Flavio");
        System.out.println("Git update 3: Flavio");
        System.out.println("Git update 4: Lucas");
    }

	

	public boolean isThereAResourceWithName(String name){
		for (Resource resource : resources) {
			if(resource.name.equalsIgnoreCase(name)) return true;
		}
		return false;
	}
	public void conflitctMethod(){
		while(true){
			System.out.println("PARA FAZER UM CONFLITO doido");
			System.out.println("PARA FAZER UM CONFLITO doido");
			System.out.println("PARA FAZER UM CONFLITO doido");
			System.out.println("PARA FAZER UM CONFLITO doido");
			System.out.println("PARA FAZER UM CONFLITO doido");
			System.out.println("PARA FAZER UM CONFLITO doido");
			System.out.println("PARA FAZER UM CONFLITO doido");
			System.out.println("PARA FAZER UM CONFLITO doido");
			System.out.println("PARA FAZER UM CONFLITO doido");
			System.out.println("PARA FAZER UM CONFLITO doido");
			System.out.println("PARA FAZER UM CONFLITO doido");
			System.out.println("PARA FAZER UM CONFLITO doido");
			System.out.println("PARA FAZER UM CONFLITO doido");
			System.out.println("PARA FAZER UM CONFLITO doido");
			System.out.println("PARA FAZER UM CONFLITO doido");
			System.out.println("PARA FAZER UM CONFLITO doido");
			System.out.println("PARA FAZER UM CONFLITO doido");
			System.out.println("PARA FAZER UM CONFLITO doido");
			System.out.println("PARA FAZER UM CONFLITO doido");
			System.out.println("PARA FAZER UM CONFLITO doido");
			System.out.println("PARA FAZER UM CONFLITO doido");
			System.out.println("PARA FAZER UM CONFLITO doido");
			System.out.println("PARA FAZER UM CONFLITO doido");
			System.out.println("PARA FAZER UM CONFLITO doido");
			System.out.println("PARA FAZER UM CONFLITO doido");
			System.out.println("PARA FAZER UM CONFLITO doido");
			System.out.println("PARA FAZER UM CONFLITO doido");
			System.out.println("PARA FAZER UM CONFLITO doido");
			System.out.println("PARA FAZER UM CONFLITO doido");

		}
	}

	@Override
	public void run() {
		
		//starting the os thread.
		Thread osThread = new Thread(operationalSystem);//in this thread we'll run the checking for the informations and will start the threads of the process too..
		osThread.start();
	}
	
	
}

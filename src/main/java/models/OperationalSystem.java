package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.Semaphore;

import javax.swing.JOptionPane;

import mainpack.Main;

//
public class OperationalSystem implements Runnable{
	
	
	
	private int checkingTimeInterval;//the time interval to check it there is any deadlock in the system.
	private ArrayList<Process> processes;//these are the processes in the system currently. 
	public boolean showedAlertOfDeadlock;
	
	public OperationalSystem(int checkingTimeInterval){
		this.checkingTimeInterval = checkingTimeInterval;
		processes = new ArrayList<Process>();
		showedAlertOfDeadlock = false;
	}
	
	public ArrayList<Process> getProcesses(){
		return processes;
	}
	
	public int numberOfProcesses(){
		if(processes.size() == 0){
			return 0;
		}
		return processes.size();
	}
	
	public int getNextID(){
		if(processes.size() == 0){
			return 0;
		}
		return processes.get(processes.size() - 1).getId() + 1;
	}
	
	public void addNewProcess(Process newOne){
		Thread newThread = new Thread(newOne);
		newThread.start();
		processes.add(newOne);
	}
	
	public boolean removeProcessWithID(int id){
	
		for(int i = 0 ; i < processes.size() ; i++){
			Process process = processes.get(i);
			if(process.id == id){
				process.kill();
				processes.remove(process);
				return true;
			}
		}
	
		return false;
	}

	private boolean checkForDeadLock(){
		System.out.println("checando por um deadlock");
		
		ArrayList<Resource> resources = Main.comp.getResources();
		
		ArrayList<Integer> existingResources = new ArrayList<Integer>(Collections.nCopies(resources.size(), 0));// instanciate it with resources.size() position populated with 0
		ArrayList<Integer> availableResources = new ArrayList<Integer>(Collections.nCopies(resources.size(), 0));// instanciate it with resources.size() position populated with 0
		
		ArrayList<ArrayList<Integer>> allocatedResources = new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> requestingResources = new ArrayList<ArrayList<Integer>>();
		
		populateStructures(existingResources, availableResources, allocatedResources, requestingResources);
		
		ArrayList<Integer> processesInDeadLock = checkDeadLock(existingResources, availableResources, allocatedResources, requestingResources);


		System.out.println(processesInDeadLock.size() > 0 ? "deadlock" : "sem deadLock");
		return processesInDeadLock.size() > 0;
	}
	
	private void presentDeadLockAlert(String message){
		JOptionPane.showMessageDialog(null, message);
	}
	
	private void populateStructures(ArrayList<Integer> existingResources, ArrayList<Integer> availableResources, ArrayList<ArrayList<Integer>> allocatedResources, ArrayList<ArrayList<Integer>> requestingResources) {
		ArrayList<Resource> resources = Main.comp.getResources();
		
		for (Resource resource : resources) {
			//1. Monte o vetor de recursos existentes E;
			existingResources.set(resource.id/*-1*/, resource.quantity);

			//4. Encontre o vetor de recursos disponiÌ�veis A;
			availableResources.set(resource.id/*-1*/, resource.available.availablePermits());

		}

		for (int i=0; i<processes.size(); i++) {
			Process process = processes.get(i);
			
			ArrayList<Integer> processResources = new ArrayList<Integer>(Collections.nCopies(resources.size(), 0));// instanciate it with resources.size() position populated with 0
			allocatedResources.add(processResources);
			
			//2. Monte a matriz de alocacÌ§aÌƒo corrente C;
			for (ResourceConnection connection : process.resourceConnections) {
				int valueAllocated = allocatedResources.get(i).get(connection.resource.id/*-1*/);
				allocatedResources.get(i).set(connection.resource.id/*-1*/, valueAllocated+1); 
			}
			 
			//3. Monte a matriz de requisicÌ§oÌƒes R;
			ArrayList<Integer> processRequest = new ArrayList<Integer>(Collections.nCopies(resources.size(), 0));
			requestingResources.add(processRequest);
			
			if (process.resourceTryingAcquire != null) {
				int idRequestedResource = process.resourceTryingAcquire.id/*-1*/;
				int quantityRequestedResource = requestingResources.get(i).get(idRequestedResource);
				requestingResources.get(i).set(idRequestedResource, quantityRequestedResource+1);
			}
		}
	}

	/**This method contains the algorithim that discover if there is or not deadlock
	 * */
	public ArrayList<Integer> checkDeadLock(ArrayList<Integer> existingResources, ArrayList<Integer> availableResources, ArrayList<ArrayList<Integer>> allocatedResources, ArrayList<ArrayList<Integer>> requestingResources) {
		
		ArrayList<Integer> processesInDeadLock = new ArrayList<Integer>();
		Boolean someProcessExecuted = true;
		
		while (someProcessExecuted) {
			someProcessExecuted = false;
			
			//5. Percorra as linhas da matriz R verificando se algum dos processos
			//podera ser executado com os recursos disponiveis A;
			for (int i = 0; i < requestingResources.size(); i++) {
				ArrayList<Integer> requestsOfOneProcess = requestingResources.get(i);
				
				//• Se sim, então
				//• Simule a entrega dos recursos para o processo;
				if (processCanExecute(requestsOfOneProcess, availableResources)) {
					//• Atualize C, R e A;
					//• Simule a conclusão do processo e a liberação dos seus
					//recursos alocados;
					//• Atualize o vetor A;
					//• Volte para o passo 5;
					
					for (int j = 0; j < requestsOfOneProcess.size(); j++) {
						int quantityRequisting = requestsOfOneProcess.get(j);
						int quantityAvailableResource = availableResources.get(j);
						int valueAllocated = allocatedResources.get(i).get(j);
						
						allocatedResources.get(i).set(j, 0); 
						requestingResources.get(i).set(j, quantityRequisting-quantityRequisting);
						availableResources.set(j, quantityAvailableResource+valueAllocated);
												
						if(valueAllocated>0) {
							someProcessExecuted = true;
						}
					}
					
				}
			}
			
		}
		
		
		for (int i = 0; i < requestingResources.size(); i++) {
			ArrayList<Integer> requestsOfOneProcess = requestingResources.get(i);
			
			for (int j = 0; j < requestsOfOneProcess.size(); j++) {
				int quantityRequisting = requestsOfOneProcess.get(j);

				if (quantityRequisting>0) {
					processesInDeadLock.add(i+1);
				}
			}
		}

		
		return processesInDeadLock;
	}
	
	public Boolean processCanExecute(ArrayList<Integer> requestsOfOneProcess, ArrayList<Integer> availableResources) {
		
		for (int j = 0; j < requestsOfOneProcess.size(); j++) {
			int quantityRequisting = requestsOfOneProcess.get(j);
			int quantityAvailableResource = availableResources.get(j);
	
			if (quantityAvailableResource-quantityRequisting<0) {
				return false;
			}
			
		}
		
		return true;
	}
	
	
	@Override
	public void run(){
		/*processes = new ArrayList<>();
		
		//starting some processes
		for (int i = 0; i < 3; i++) {
			Process newProcess = new Process(i+1, 4, 10);
			
			Thread newThread = new Thread(newProcess);
			newThread.start();
			
			processes.add(newProcess);
		}*/

		//we'll be forever in this loop, sleeping and looking for deadlocks.
		while(true){
			
			//try to sleep for the determined time.
			try {Thread.sleep(checkingTimeInterval);} 
			catch (InterruptedException e) {e.printStackTrace();}
			
			//when the sleeping has gone, we check if there is any deadlock.

			String messageDeadlock = "Com deadlock.";

			if (checkForDeadLock()) {
				if(!showedAlertOfDeadlock){//para evitar que fique mostrando varias vezes para um mesmo deadlock
					presentDeadLockAlert("DEADLOCK !!");
				}
				showedAlertOfDeadlock = true;
			}
			else{
				showedAlertOfDeadlock = false;
				messageDeadlock = "Sem deadlock.";
			}

			System.out.println(messageDeadlock);
			
		}
	}
}

package mainpack;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

import UIComponents.UIController;
import models.*;

public class Main {
	public static Computer comp;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		UIController controller = new UIController();
		//Scanner scanner = new Scanner(System.in);
		
		
		
		
	}
	
	public static void startSimulation(){
		
		Thread compThread = new Thread(comp);
		compThread.start();
	}
	
	/**
	 * Create an instance of OperationalSystem and of Computer
	 * */
	public static void InitializeSystem(ArrayList<Resource> listRes){
		OperationalSystem os = new OperationalSystem(3000);
		comp = new Computer(os, listRes);
	}

}

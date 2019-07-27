package main;

import financial.bank.ATM;
import financial.person.Customer;
import financial.bank.Bank;
import financial.Simulator;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main{

	public static void main(String[] args){
		if( args.length != 3 ){
			System.out.println("Hiba, nincs elég paraméter");
		}else{
			String bankName = args[0];
			
			String inputFile = args[1];
			String outputFile = args[2];
			try{
				Simulator simulator = new Simulator(bankName,1000000,outputFile);
				simulator.simulate(inputFile);
				simulator.close();
			}catch(FileNotFoundException ex){
				System.out.println("Az inputfile nem megfelelő! A File nincs meg!");
			}catch(IOException e){
				System.out.println("Az inputfile nem megfelelő!");
			}
		}
	}
}
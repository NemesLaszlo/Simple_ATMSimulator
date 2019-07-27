package financial;

import financial.bank.ATM;
import financial.person.Customer;
import financial.bank.Bank;
import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.util.Scanner;

public class Simulator{
	private ATM atm;
	private ArrayList<Customer> customers;
	private PrintWriter pwLog;
	
	public Simulator(String bankName,int initAmount,String outFileName)throws FileNotFoundException {
		this.atm = ATM.makeATM(bankName,initAmount);
		this.customers = new ArrayList<>();
		this.pwLog = new PrintWriter(outFileName);
	}
	
	private Customer getCustomerByName(String cName){
		for(int i = 0; i < customers.size(); ++i){
			if(cName.equals(customers.get(i).getName())){
				return customers.get(i);
			}
		}
		return null;
	}
	
	public void insertCustomer(String customerName,int birthYear,String bankName){
		if(getCustomerByName(customerName) == null){
			Customer newCustomer = Customer.makeCustomer(customerName,birthYear,bankName);
			if( newCustomer != null ){
				customers.add(newCustomer);
			}
		}
	}
	
	public void withdrawCash(String customerName,int amount){
		Customer wCustomer = getCustomerByName(customerName);
		if( wCustomer != null 
				&& wCustomer.getAmount() >= amount + atm.calculateFee(wCustomer.getBank(),amount) 
				&& atm.getAmount() >= amount && amount > 0){
			wCustomer.decreaseAmount(amount + atm.calculateFee(wCustomer.getBank(),amount));
			atm.decraseAmount(amount);
			pwLog.print(wCustomer.toString() + "\r\n");
			pwLog.flush();
		}
	}
	
	public void depositCash(String customerName,int amount){
		Customer depositCustomer = getCustomerByName(customerName);
		if( depositCustomer != null && amount > 0 ){
			depositCustomer.increaseAmount(amount);
			atm.increaseAmount(amount);
			pwLog.print(depositCustomer.toString() + "\r\n");
			pwLog.flush();
		}
	}
	
	public void simulate(String inputFile) throws FileNotFoundException,IOException{
		try(Scanner sc = new Scanner(new File(inputFile))){
			while(sc.hasNextLine()){
				String line = sc.nextLine();
				String[] str = line.split(":");
				if( line == str[0] || str.length != 2 ){continue;}
				String firstPart = str[0];
				String secoundPart = str[1];
				String[] str2 = secoundPart.split(",");
				if( str2.length < 2 || secoundPart == str2[0] 
						||  !str2[1].chars().allMatch(Character::isDigit) ) {continue;}
				switch(firstPart){
					case "REG": 
						if(str2.length == 3){
							insertCustomer(str2[0], Integer.parseInt(str2[1]), str2[2]);
						}
						break;
					case "GET":
						if(str2.length == 2){
							withdrawCash(str2[0], Integer.parseInt(str2[1]));
						}
						break;
					case "PUT":
						if(str2.length == 2){
							depositCash(str2[0], Integer.parseInt(str2[1]));
						}
						break;
				}
			}
			sc.close();
		}
	}
	
	public void close(){
		pwLog.close();
	}
}
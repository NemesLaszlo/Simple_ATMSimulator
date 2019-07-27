package financial.person;

import financial.bank.Bank;


public class Customer{
	private String name;
	private int birthYear;
	private Bank bank;
	private int amount;
	
	private Customer(String name,int birthYear,Bank bank){
		this.name = name;
		this.birthYear = birthYear;
		this.bank = bank;
		this.amount = 0;
	}
	
	public static Customer makeCustomer(String name,int birthYear, String bankName){
		Bank bank;
		boolean isIn = true;
		try{
			bank = Bank.valueOf(bankName);
		}catch(IllegalArgumentException e){return null;}
		String[] nameSplit = name.split(" ");
		if(!(nameSplit.length >= 2 
			&& nameSplit.length <= 4 && birthYear >= 1918 && birthYear <= 1998)){
				isIn = false;
		}
		else{
			for(String x : nameSplit){
				if(!(x.length() >= 3 && x.matches("\\b([A-Z][a-z]*)\\b"))){
					isIn = false;
				}
			}
		}
		if( isIn ){
			return new Customer(name,birthYear,bank);
		}
		else{
			return null;
		}
	}
	
	public String getName(){
		return this.name;
	}
	
	public Bank getBank(){
		return this.bank;
	}
	
	public int getAmount(){
		return this.amount;
	}
	
	public void decreaseAmount(int amount){
		this.amount -= amount;
	}
	
	public void increaseAmount(int amount){
		this.amount += amount;
	}
	
	public String toString(){
		return this.name + ":" + " " + this.amount;
	}
}
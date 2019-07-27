package financial.bank;

import java.lang.Math;

public class ATM{
	private Bank bank;
	private int amount;
	
	private ATM(Bank bank,int amount){
		this.bank = bank;
		this.amount = amount;
	}
	
	public static ATM makeATM(String bankName,int amount){
		Bank bank;
		try{
			bank = Bank.valueOf(bankName);
		}catch(IllegalArgumentException e){return null;}
		if(amount >= 0){
			ATM result = new ATM(bank,amount);
			return result;
		}else{
			return null;
		}
	}
	
	public int getAmount(){
		return this.amount;
	}
	
	public void decraseAmount(int amount){
		this.amount -= amount;
	}
	
	public void increaseAmount(int amount){
		this.amount += amount;
	}
	
	public int calculateFee(Bank bank,int value){
		double fee=0;
		if(bank == this.bank ){
			fee = Math.ceil(value * 0.01);
			if(fee < 200){
				fee = 200;
			}
		}else{
			fee = Math.ceil(value * 0.03);
			if(fee < 500){
				fee = 500;
			}
		}
		return (int)fee;
	}

}
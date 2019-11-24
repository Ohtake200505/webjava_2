package jp.co.systena.tigerscave.rpg.model;

public class Wizard implements Job {

	@Override
	public String attack(){
		return "まほうで攻撃した！ 10のダメージを与えた！";
	}

	@Override
	public String heal(){
		return "まほうで回復した！ HPが50回復した！";
	}
}



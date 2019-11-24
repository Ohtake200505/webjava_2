package jp.co.systena.tigerscave.rpg.model;

public class Warrior extends Physical implements Job {

	@Override
	public String attack(){
		return "剣で攻撃した！ 10のダメージを与えた！";
	}

}



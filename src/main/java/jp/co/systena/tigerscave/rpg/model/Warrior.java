package jp.co.systena.tigerscave.rpg.model;

public class Warrior extends Physical implements Job {

	private int jobId = 1;

	@Override
	public String attack(){
		return "剣で攻撃した！ 10のダメージを与えた！";
	}

}



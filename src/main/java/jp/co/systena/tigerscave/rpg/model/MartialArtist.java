package jp.co.systena.tigerscave.rpg.model;

public class MartialArtist extends Physical implements Job {

	@Override
	public String attack(){
		return "拳で攻撃した！ 10のダメージを与えた！";
	}
}



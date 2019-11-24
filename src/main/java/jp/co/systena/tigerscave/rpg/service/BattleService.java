package jp.co.systena.tigerscave.rpg.service;

import java.util.Map;

import jp.co.systena.tigerscave.rpg.model.Monster;
import jp.co.systena.tigerscave.rpg.model.Player;

public class BattleService {

  private int dmg;
  private int monDmg;
  private String monTargetName;

  public void battle(PartyService partyService, MonsterService monsterService) {
    Map<Integer, Player> partyListMap = partyService.getPartyListMap();

    //partyListMapのサイズを与え、ダメージを受けるメンバーをランダムで選択し、
    //monsterのmonTargetに格納させる
    monsterService.checkMonTarget(partyListMap.size());
    Monster monster = monsterService.getMonster();

    //dmg ：モンスターに与えるダメージ
    //partyService.checkDamage()で、たたかうを選択した人数x10の値を得る
    dmg = partyService.checkDamage();

    //monDmg ：monsterから受けるダメージ量
    monDmg = monster.getAtk();

    //monTarget ：ダメージを受けるメンバーのKEY番号
    int monTarget = monster.getMonTarget();
    //monTargetName ：ダメージを受けるメンバーの名前
    monTargetName = partyListMap.get(monTarget).getName();

    //monsterに与えるダメージとmonsterの残HPを比較し、
    //残HPのほうが高ければダメージ計算を行い、
    //さらにパーティが受けるダメージの計算を行う
    if(dmg < monster.getHp()) {
    	monster.setHp(monster.getHp() - dmg);
    	if(partyListMap.get(monTarget).getHp() > monDmg) {
        	partyListMap.get(monTarget).setHp(partyListMap.get(monTarget).getHp() - monDmg);
        }else {
        	partyListMap.get(monTarget).setHp(0);
        }
    }else {
    	monster.setHp(0);
    }
  }

  public int getDmg() {
    return this.dmg;
  }

  public void setDmg(int dmg) {
    this.dmg = dmg;
  }

  public int getMonDmg() {
    return this.monDmg;
  }

  public void setMonDmg(int monDmg) {
    this.monDmg = monDmg;
  }

  public String getMonTargetName() {
    return this.monTargetName;
  }

  public void setMonTargetName(String monTargetName) {
    this.monTargetName = monTargetName;
  }

}

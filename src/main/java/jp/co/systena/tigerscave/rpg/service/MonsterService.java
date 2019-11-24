package jp.co.systena.tigerscave.rpg.service;

import java.util.Random;

import org.springframework.stereotype.Service;

import jp.co.systena.tigerscave.rpg.model.Monster;

@Service
public class MonsterService {
  private Monster monster;

  public Monster getMonster() {
    return this.monster;
  }

  public Monster createMonster() {
    Random random = new Random();
    int randomValue = random.nextInt(6);

    switch(randomValue) {
      case 0:
      case 1:
      case 2:
          monster = new Monster("スライム", 10, "/images/slime.png");
          return monster;
      case 3:
      case 4:
          monster = new Monster("ドラゴン", 20, "/images/dragon.png");
          return monster;
      case 5:
          monster = new Monster("ゴジラ", 30, "/images/godzilla.png");
          return monster;
    }
    monster = new Monster("例外", 0, "例外");
    return monster;
  }

  public void checkMonTarget(int num) {

    if(num != 1) {
    	Random random = new Random();
    	int randomValue = random.nextInt(num) +1;
    	monster.setMonTarget(randomValue);
    }else {
    	monster.setMonTarget(1);
    }
  }

}

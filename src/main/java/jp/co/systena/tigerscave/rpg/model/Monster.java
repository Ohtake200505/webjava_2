package jp.co.systena.tigerscave.rpg.model;

public class Monster {
  private int hp;
  private int atk;
  private MonsterType monsterType;
  private String name;
  private String command;
  private int monTarget;
  private String imgPath;

  public Monster(String name, int atk, String imgPath) {
    switch(name) {
    case "スライム":
    	monsterType = new Slime();
    	break;
    case "ドラゴン":
    	monsterType = new Dragon();
    	break;
    case "ゴジラ":
    	monsterType = new Godzilla();
    	break;
    }

    setName(name);
    setHp(100);
    setAtk(atk);
    setCommand(monsterType.attack());
    setMonTarget(0);
    setImgPath(imgPath);
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getHp() {
    return this.hp;
  }

  public void setHp(int hp) {
    this.hp = hp;
  }

  public int getAtk() {
    return this.atk;
  }

  public void setAtk(int atk) {
    this.atk = atk;
  }

  public String getCommand() {
    return this.command;
  }

  public void setCommand(String command) {
    this.command = command;
  }

  public int getMonTarget() {
    return this.monTarget;
  }

  public void setMonTarget(int monTarget) {
    this.monTarget = monTarget;
  }

  public String getImgPath() {
    return this.imgPath;
  }

  public void setImgPath(String imgPath) {
    this.imgPath = imgPath;
  }



}

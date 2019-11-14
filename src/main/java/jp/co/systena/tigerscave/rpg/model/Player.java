package jp.co.systena.tigerscave.rpg.model;

public class Player {

	private int charId;
	private String jobName;
	private int hp;
	private Job job;
	private String name;
	private String command;

	public Player(int charId, String jobName, int hp, String name) {
		switch(jobName) {
		case "戦士":
			job = new Warrior();
			break;
		case "魔法使い":
			job = new Wizard();
			break;
		case "武闘家":
			job = new MartialArtist();
			break;
		}

		setCharId(charId);
		setJobName(jobName);
		setHp(hp);
		setName(name);
		setCommand("未選択");
	}

	public int getCharId() {
		return this.charId;
	}

	public void setCharId(int charId) {
		this.charId = charId;
	}

	public String getJobName() {
		return this.jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public int getHp() {
		return this.hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public Job getJob() {
		return this.job;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCommand() {
		return this.command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

}

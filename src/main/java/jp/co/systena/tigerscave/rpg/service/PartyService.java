package jp.co.systena.tigerscave.rpg.service;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import jp.co.systena.tigerscave.rpg.model.Player;

@Service
public class PartyService {

	Map<Integer, Player> partyListMap = new LinkedHashMap<Integer, Player>();

	public Map<Integer, Player> getPartyListMap() {
		return this.partyListMap;
	}

	public void addPartyListMap(int charId, Player player) {
		partyListMap.put(charId, player);
	}

	public int checkDamage() {
		int dmg = 0;
	    for(int i=1; i<partyListMap.size()+1; i++) {
	    	Player player = partyListMap.get(i);
	    	switch(player.getCommand()) {
	    	case "たたかう":
	    		player.setCommand(player.getJob().attack());
	    		dmg +=10;
	    		break;
	    	case "かいふく":
	    		player.setCommand(player.getJob().heal());
	    		player.setHp(player.getHp() +50);
	    		break;
	    	}
	    }
	    return dmg;
	}

	public void resetCommand() {
	    for(int i=1; i<partyListMap.size()+1; i++) {
	    	Player player = partyListMap.get(i);
	    	player.setCommand("未選択");
	    }
	}

}



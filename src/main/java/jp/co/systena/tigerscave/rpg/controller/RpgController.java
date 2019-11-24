package jp.co.systena.tigerscave.rpg.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import jp.co.systena.tigerscave.rpg.model.BattleForm;
import jp.co.systena.tigerscave.rpg.model.Player;
import jp.co.systena.tigerscave.rpg.model.PlayerForm;
import jp.co.systena.tigerscave.rpg.service.BattleService;
import jp.co.systena.tigerscave.rpg.service.MonsterService;
import jp.co.systena.tigerscave.rpg.service.PartyService;

@Controller
public class RpgController {

  @Autowired
  HttpSession session;

  @Autowired
  PartyService partyService;
  @Autowired
  MonsterService monsterService;

  @RequestMapping(value = "/Rpg", method = RequestMethod.GET)
  public ModelAndView initialSettings(ModelAndView mav) {

    //num ：パーティメンバー数(正確にはパーティ登録残数)
    //この値が99だと "InitialSettingView" で人数選択が現れる
    int num = 99;

    mav.addObject("num", num);
    mav.setViewName("InitialSettingView");

    return mav;
  }

  @RequestMapping(value = "/Rpg", method = RequestMethod.POST)
  public ModelAndView playerSettings(HttpSession session, ModelAndView mav, @Valid PlayerForm playerForm) {

    PartyService partyService = getSessionPartyService();

    //key ：partyListMapにplayer格納する際のKEY
    //最初のplayerのKEYが1で、以降インクリメント
   	int key = 1;

   	if(partyService.getPartyListMap() != null) {
   		key = partyService.getPartyListMap().size() + 1;
   	}

	Player player = new Player(key, playerForm.getJobName(), 100, playerForm.getName());
   	partyService.addPartyListMap(key, player);

   	session.setAttribute("partyService", partyService);

   	//パーティ登録残数が0になるまで初期設定を繰り返す
   	int num = playerForm.getNum() -1;

   	if(num != 0) {
   		mav.addObject("num", num);
   		mav.addObject("partyListMap", partyService.getPartyListMap());
       	mav.setViewName("InitialSettingView");

       	return mav;
    }

    return new ModelAndView("redirect:/Encounter");
  }


  @RequestMapping(value = "/Encounter", method = RequestMethod.GET)
  public ModelAndView encounter(HttpSession session, ModelAndView mav) {

    //モンスター生成
    MonsterService monsterService = getSessionMonsterService();
    monsterService.createMonster();

    session.setAttribute("monsterService", monsterService);
    mav.addObject("monster", monsterService.getMonster());
    mav.setViewName("EncounterView");

    return mav;
  }


  @RequestMapping(value = "/SelectCommand", method = RequestMethod.GET)
  public ModelAndView reselectCommand(HttpSession session, ModelAndView mav) {

    PartyService partyService = getSessionPartyService();
    MonsterService monsterService = getSessionMonsterService();

    //全パーティメンバーの選択コマンドを初期化(未選択に変更)
    partyService.resetCommand();
   	session.setAttribute("partyService", partyService);

    mav.addObject("partyListMap", partyService.getPartyListMap());
   	mav.addObject("key", 1);
   	mav.addObject("monster", monsterService.getMonster());
    mav.setViewName("SelectCommandView");

    return mav;
  }

  @RequestMapping(value = "/SelectCommand", method = RequestMethod.POST)
  public ModelAndView selsectCommand(HttpSession session, ModelAndView mav, @Valid BattleForm battleForm) {


    PartyService partyService = getSessionPartyService();
    MonsterService monsterService = getSessionMonsterService();

    //該当playerが選択したコマンドをbattleFormから抽出し、
	//partyListMapのplayerのcommandに書き込む
    partyService.getPartyListMap().get(battleForm.getCharId()).setCommand(battleForm.getCommand());

    //keyの値をインクリメントし、全パーティメンバーのコマンド入力を繰り返す
    if(partyService.getPartyListMap().size() != battleForm.getCharId()) {
    	int key = battleForm.getCharId() +1;

       	session.setAttribute("partyService", partyService);
    	mav.addObject("key", key);
    	mav.addObject("partyListMap", partyService.getPartyListMap());
    	mav.addObject("monster", monsterService.getMonster());
        mav.setViewName("SelectCommandView");

        return mav;
    }

    return new ModelAndView("redirect:/Battle");
  }



  @RequestMapping(value = "/Battle", method = RequestMethod.GET)
  public ModelAndView battle(HttpSession session, ModelAndView mav) {

    PartyService partyService = getSessionPartyService();
    MonsterService monsterService = getSessionMonsterService();

    //敵・味方のダメージ計算
//    BattleService battleService = new BattleService(partyService, monsterService);
    BattleService battleService = getSessionBattleService();
    battleService.battle(partyService, monsterService);

    session.setAttribute("battleService", battleService);
    session.setAttribute("partyService", partyService);
    session.setAttribute("monsterService", monsterService);

    mav.addObject("partyListMap", partyService.getPartyListMap());
    mav.addObject("monster", monsterService.getMonster());
    mav.addObject("battleService", battleService);
    mav.setViewName("BattleView");

    return mav;
  }

  public PartyService getSessionPartyService() {
	  PartyService partyService = (PartyService)session.getAttribute("partyService");
	  if(partyService == null) {
		  partyService = new PartyService();
		  session.setAttribute("partyService", partyService);
	  }

	  return partyService;
  }

  public MonsterService getSessionMonsterService() {
	  MonsterService monsterService = (MonsterService)session.getAttribute("monsterService");
	  if(monsterService == null) {
		  monsterService = new MonsterService();
		  session.setAttribute("monsterService", monsterService);
	  }

	  return monsterService;
  }

  public BattleService getSessionBattleService() {
	  BattleService battleService = (BattleService)session.getAttribute("battleService");
	  if(battleService == null) {
		  battleService = new BattleService();
		  session.setAttribute("battleService", battleService);
	  }

	  return battleService;
  }

}



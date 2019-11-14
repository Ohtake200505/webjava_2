package jp.co.systena.tigerscave.rpg.controller;

import java.util.Map;

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
import jp.co.systena.tigerscave.rpg.service.PartyService;

@Controller
public class RpgController {

  @Autowired
  HttpSession session;

  PartyService partyService = new PartyService();


  @RequestMapping(value = "/Rpg", method = RequestMethod.GET)
  public ModelAndView initialSettings(HttpSession session, ModelAndView mav) {

    int num = 99;

    mav.addObject("num", num);
    mav.setViewName("InitialSettingView");


    return mav;
  }

  @RequestMapping(value = "/Rpg", method = RequestMethod.POST)
  public ModelAndView playerSettings(HttpSession session, ModelAndView mav, @Valid PlayerForm playerForm) {

   	int key = 1;
   	if(session.getAttribute("partyListMap") != null) {
   		Map<Integer, Player> partyListMap = (Map<Integer, Player>)session.getAttribute("partyListMap");
   		key = partyListMap.size() + 1;
   	}

	Player player = new Player(key, playerForm.getJobName(), 100, playerForm.getName());
   	partyService.addPartyListMap(key, player);

   	session.setAttribute("partyListMap", partyService.getPartyListMap());

   	int num = playerForm.getNum() -1;

   	if(num != 0) {
   		mav.addObject("num", num);
   		mav.addObject("partyListMap", partyService.getPartyListMap());
       	mav.setViewName("InitialSettingView");

       	return mav;
    }

   	mav.addObject("key", 1);
    mav.addObject("partyListMap", session.getAttribute("partyListMap"));
    mav.setViewName("SelectCommandView");

    return mav;
  }

  @RequestMapping(value = "/SelectCommand", method = RequestMethod.GET)
  public ModelAndView reselectCommand(HttpSession session, ModelAndView mav) {

    partyService.resetCommand();

    session.setAttribute("partyListMap", partyService.getPartyListMap());
    mav.addObject("partyListMap", session.getAttribute("partyListMap"));
   	mav.addObject("key", 1);
    mav.setViewName("SelectCommandView");

    return mav;
  }

  @RequestMapping(value = "/SelectCommand", method = RequestMethod.POST)
  public ModelAndView selsectCommand(HttpSession session, ModelAndView mav, @Valid BattleForm battleForm) {

    Map<Integer, Player> partyListMap = (Map<Integer, Player>)session.getAttribute("partyListMap");
    partyListMap.get(battleForm.getCharId()).setCommand(battleForm.getCommand());

    if(partyListMap.size() != battleForm.getCharId()) {
    	int key = battleForm.getCharId() +1;

    	session.setAttribute("partyListMap", partyListMap);
    	mav.addObject("key", key);
    	mav.addObject("partyListMap", session.getAttribute("partyListMap"));
        mav.setViewName("SelectCommandView");

        return mav;
    }

    int dmg = partyService.changeCommand();
    partyListMap = partyService.getPartyListMap();

    session.setAttribute("partyListMap", partyListMap);
    mav.addObject("partyListMap", session.getAttribute("partyListMap"));
    mav.addObject("dmg", dmg);
    mav.setViewName("BattleView");

    return mav;
  }
}



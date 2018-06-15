package it.polimi.ingsw.sagrada.game.cards;
import it.polimi.ingsw.sagrada.game.base.utility.Colors;
import it.polimi.ingsw.sagrada.game.base.utility.Picker;
import it.polimi.ingsw.sagrada.game.rules.ObjectiveBuilder;

import it.polimi.ingsw.sagrada.game.rules.ObjectiveRule;
import it.polimi.ingsw.sagrada.game.rules.ToolBuilder;
import it.polimi.ingsw.sagrada.game.rules.ToolRule;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * This class is utilised to generate all sets of cards that are used in the game.
 * This class should be called only from GameManager to deal various cards to the respective class
 * that will manage and utilised the sets.
 */
public class CardManager {
	
	/** The Constant NUM_MAX_PLAYER. */
	private static final int NUM_MAX_PLAYER = 4;
	
	/** The Constant NUM_PUBLIC_OBJECTIVE. */
	private static final int NUM_PUBLIC_OBJECTIVE = 3;
	
	/** The Constant NUM_TOOLS. */
	private static final int NUM_TOOLS = 3;

	/** The Constant BASE_PATH_OBJECTIVE. */
	private static final String BASE_PATH_OBJECTIVE = "/json/objective/PublicObjective.json";
	
	/** The Constant BASE_PATH_TOOL. */
	private static final String BASE_PATH_TOOL = "/json/tool/ToolCard.json";

	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger.getLogger(Colors.class.getName());

	/**
	 * Deal tool.
	 *
	 * @return list of of already scrambled tools
	 */
	public List<ToolCard> dealTool() {
		List<ToolCard> toolCards = new ArrayList<>();

		JSONParser parser = new JSONParser();
		try{
			JSONArray tools = (JSONArray)parser.parse(new InputStreamReader(CardManager.class.getResourceAsStream(BASE_PATH_TOOL)));
			Iterator<JSONObject> picker = new Picker<JSONObject>(tools).pickerIterator();
			for(int i=0; i<NUM_TOOLS; i++) {
				if(picker.hasNext()) {
					JSONObject tool = picker.next();
					int id = ((Long)tool.get("id")).intValue();
					String name = (String)tool.get("name");
					JSONArray actions = (JSONArray)tool.get("action");

					toolCards.add(new ToolCard(id, name, getToolRule(actions)));
				}
			}
		}catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Something breaks in reading JSON file");
		} catch (ParseException e) {
			LOGGER.log(Level.SEVERE, "JSON parser founds something wrong, check JSON file");
		}

		return toolCards;
	}

	/**
	 * Gets the tool rule.
	 *
	 * @param actions the actions
	 * @return the tool rule
	 */
	private ToolRule getToolRule(JSONArray actions) {
		ToolBuilder toolBuilder = ToolRule.builder();
		for(int j=0; j<actions.size(); j++) {
			String action = (String)actions.get(j);
			switch (action) {
				case "setIncrementDiceFeature": toolBuilder.setIncrementDiceFeature(); break;
				case "setMoveIgnoringColorRuleFeature": toolBuilder.setMoveIgnoringColorRuleFeature(); break;
				case "setMoveIgnoringValueRuleFeature": toolBuilder.setMoveIgnoringValueRuleFeature(); break;
				default: LOGGER.log(Level.SEVERE, () -> "JSON is not correct. Check PublicObjective.json id");
			}
		}

		return toolBuilder.build();
	}

	/**
	 * Deal public objective.
	 *
	 * @return list of of already scrambled publicObjective
	 */
	public List<ObjectiveCard> dealPublicObjective() {
		List<ObjectiveCard> cards = new ArrayList<>();

		JSONParser parser = new JSONParser();
		try{
			JSONArray publicObjective = (JSONArray)parser.parse(new InputStreamReader(CardManager.class.getResourceAsStream(BASE_PATH_OBJECTIVE)));
			Iterator<JSONObject> picker = new Picker<JSONObject>(publicObjective).pickerIterator();
			for(int i=0; i<NUM_PUBLIC_OBJECTIVE; i++) {
				if(picker.hasNext()) {
					JSONObject card = picker.next();
					int id = ((Long)card.get("id")).intValue();
					int value = ((Long)card.get("value")).intValue();
					String name = (String)card.get("name");
					cards.add(new ObjectiveCard(id, name, findObjectiveRule(id, value)));
				}
			}
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, () -> "Something breaks in reading JSON file");
		} catch (ParseException e) {
			LOGGER.log(Level.SEVERE, () -> "JSON parser founds something wrong, check JSON file");
		}
		return cards;
	}

	/**
	 * Find objective rule.
	 *
	 * @param id the id
	 * @param value the value
	 * @return the objective rule
	 */
	private ObjectiveRule findObjectiveRule(int id, int value) {
		ObjectiveBuilder objectiveBuilder = ObjectiveRule.builder();
		switch (id) {
			case 0: objectiveBuilder.setDifferentDiceColorByRowsObjective(value); break;
			case 1: objectiveBuilder.setDifferentDiceColorByColsObjective(value); break;
			case 2: objectiveBuilder.setDifferentDiceValueByRowsObjective(value); break;
			case 3: objectiveBuilder.setDifferentDiceValueByColsObjective(value); break;
			case 4: objectiveBuilder.setValueCoupleObjective(value, 1, 2); break;
			case 5: objectiveBuilder.setValueCoupleObjective(value, 3, 4); break;
			case 6: objectiveBuilder.setValueCoupleObjective(value, 5, 6); break;
			case 7: objectiveBuilder.setEveryDiceValueRepeatingObjective(value); break;
			case 8: objectiveBuilder.setSameDiagonalColorObjective(); break;
			case 9: objectiveBuilder.setEveryColorRepeatingObjective(value); break;
			default: LOGGER.log(Level.SEVERE, () -> "JSON is not correct. Check PublicObjective.json id");
		}
		return objectiveBuilder.build();
	}

	/**
	 * Deal private objective.
	 *
	 * @param numPlayer the num player
	 * @return list of already scrambled privateObjective
	 */
	public List<ObjectiveCard> dealPrivateObjective(int numPlayer) {
		List<ObjectiveCard> cards = new ArrayList<>();
		List<Colors> colors = Colors.getColorList();
		Iterator<Colors> picker = new Picker<>(colors).pickerIterator();

		if(numPlayer>NUM_MAX_PLAYER) return cards;

		for(int i=0; i<numPlayer; i++) {
			if(picker.hasNext()) {
				ObjectiveBuilder objectiveBuilder = ObjectiveRule.builder();
				Colors color = picker.next();
				objectiveBuilder.setColorShadeColorObjective(color);
				cards.add(new ObjectiveCard(i, "Obiettivo "+color.toString(), objectiveBuilder.build()));
			}
		}
		return cards;
	}

}
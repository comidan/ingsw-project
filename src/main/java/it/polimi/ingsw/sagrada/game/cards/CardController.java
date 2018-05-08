package it.polimi.ingsw.sagrada.game.cards;
import it.polimi.ingsw.sagrada.game.base.Colors;
import it.polimi.ingsw.sagrada.game.base.Picker;
import it.polimi.ingsw.sagrada.game.rules.ObjectiveBuilder;

import it.polimi.ingsw.sagrada.game.rules.ObjectiveRule;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 */
public class CardController {

	private static final Logger logger = Logger.getAnonymousLogger();
	private static final int NUM_MAX_PLAYER = 4;
	private static final int NUM_PUBLIC_OBJECTIVE = 3;
	private static final String BASE_PATH_OBJECTIVE = "res\\json\\Objective\\";
	private final ObjectiveBuilder objectiveBuilder = new ObjectiveBuilder();


	/**
	 * default constructor
	 */
	public CardController() {
	}

	public List<ToolCard> dealTool() {
		return null;
	}

	/**
	 * @return list of publicObjective
	 */
	public List<ObjectiveCard> dealPublicObjective() {
		List<ObjectiveCard> cards = new ArrayList<>();

		JSONParser parser = new JSONParser();
		try{
			JSONArray publicObjective = (JSONArray)parser.parse(new FileReader(BASE_PATH_OBJECTIVE+"PublicObjective.json"));
			Iterator<JSONObject> picker = new Picker<JSONObject>(publicObjective).pickerIterator();
			for(int i=0; i<NUM_PUBLIC_OBJECTIVE; i++) {
				if(picker.hasNext()) {
					JSONObject card = picker.next();
					int id = ((Long)card.get("id")).intValue();
					int value = ((Long)card.get("value")).intValue();
					String name = (String)card.get("name");
					cards.add(new ObjectiveCard(id, name, findRule(id, value)));
				}
			}
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Something breaks in reading JSON file");
		} catch (ParseException e) {
			logger.log(Level.SEVERE, "JSON parser founds something wrong, check JSON file");
		}
		return cards;
	}

	private ObjectiveRule findRule(int id, int value) {
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
			default: throw new JSONErrorException("JSON is not correct. Check publicobjective.json id");
		}
		return objectiveBuilder.build();
	}

	/**
	 * @return list of privateObjective
	 */
	public List<ObjectiveCard> dealPrivateObjective(int numPlayer) {
		List<ObjectiveCard> cards = new ArrayList<>();
		List<Color> colors = Colors.getColorList();
		Iterator<Color> picker = new Picker<>(colors).pickerIterator();

		if(numPlayer>NUM_MAX_PLAYER) return cards;

		for(int i=0; i<numPlayer; i++) {
			if(picker.hasNext()) {
				Color color = picker.next();
				objectiveBuilder.setColorShadeColorObjective(color);
				cards.add(new ObjectiveCard(i, "Obiettivo "+color.toString(), objectiveBuilder.build()));
			}
		}
		return cards;
	}

}
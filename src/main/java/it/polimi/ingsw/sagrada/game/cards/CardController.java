package it.polimi.ingsw.sagrada.game.cards;
import it.polimi.ingsw.sagrada.game.base.Colors;
import it.polimi.ingsw.sagrada.game.base.Picker;
import it.polimi.ingsw.sagrada.game.rules.ObjectiveBuilder;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * 
 */
public class CardController {

	private static final int NUM_MAX_PLAYER = 4;
	private static final int NUM_PUBLIC_OBJECTIVE = 3;
	private static final String BASE_PATH_OBJECTIVE = "JSONResources\\Objective\\";
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
	 * @return
	 */
	public List<ObjectiveCard> dealPublicObjective() {
		JSONParser parser = new JSONParser();
		try{
			JSONArray publicObjective = (JSONArray)parser.parse(new FileReader(BASE_PATH_OBJECTIVE+"PublicObjective"));
			for(int i=0; i<NUM_PUBLIC_OBJECTIVE; i++) {

			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * @return
	 */
	public Map<Color, ObjectiveCard> dealPrivateObjective(int numPlayer) {
		Map<Color, ObjectiveCard> cards = new HashMap<>();
		List<Color> colors = Colors.getColorList();
		Iterator<Color> picker = new Picker<>(colors).pickerIterator();

		if(numPlayer>NUM_MAX_PLAYER) return cards;

		for(int i=0; i<numPlayer; i++) {
			if(picker.hasNext()) {
				Color color = picker.next();
				objectiveBuilder.setColorShadeColorObjective(color);
				cards.put(color, new ObjectiveCard(i, objectiveBuilder.build()));
			}
		}
		return cards;
	}

}
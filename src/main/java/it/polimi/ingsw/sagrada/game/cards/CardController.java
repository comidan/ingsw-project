package it.polimi.ingsw.sagrada.game.cards;
import it.polimi.ingsw.sagrada.game.rules.ObjectiveBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * 
 */
public class CardController {

	//private final ToolBuilder toolBuilder = new ToolBuilder();
	private final ObjectiveBuilder objectiveBuilder = new ObjectiveBuilder();

	public CardController() {
	}

	public List<ToolCard> dealTool() {
		return null;
	}

	/**
	 * @return create a tool cards
	 */
	public List<ToolCard> createObjectiveCards() throws IOException, ParseException {
		List<ToolCard> toolCards = new ArrayList<>();

		JSONParser parser = new JSONParser();
		try {
			JSONObject jsonObject = (JSONObject)parser.parse(new FileReader("JSONResources\\Objective\\TestObjective.json"));
			int id = ((Long)jsonObject.get("id")).intValue();
			System.out.println("ID:"+id);
			String type = (String) jsonObject.get("type");
			System.out.println("Type: "+type);
			int value = ((Long)jsonObject.get("value")).intValue();
			System.out.println("Value: "+value);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return toolCards;
	}

	/**
	 * @return
	 */
	public List<ObjectiveCard> dealPublicObjective() {
		// TODO implement here
		return null;
	}

	/**
	 * @return
	 */
	public ObjectiveCard dealPrivateObjective() {
		// TODO implement here
		return null;
	}

}
# Test cases :
 - # ObjectiveRule
    - colorCountRule ✓
    - consecutiveDiagonalColorRule ✓
    - consecutiveAntidiagonalColorRule ✓
    - dicePairRule ✓
    - everyDiceValueRule ✓
    - everyColorRule ✓
 - # CellRule
    - colorConstraintRule ✓
    - valueConstraintRule ✓ 
 - # ObjectiveRuleBuilder
    - building every rule type ✓
 - # CellRuleBuilder
    - building every rule type ✓
 - # RuleController
    - windowMainGameRuleTest ✓ 
    - dynamic and generic lambda called rules test ✓
    - generated errors for bad positioning test ✓
 - # MainGameRule
    - mainGameRuleTesting on various matrix enviroments ✓
    - colorGeneratedErrorTesting ✓
    - diceValueGeneratedErrorTesting ✓
- # DiceController
    - dice pick from draft and bag ✓
    - dice pick from empty draft ✓
    - pick of wrong number of dice ✓
- # Window    
    - dice on empty cell ✓
    - dice on occupied cell ✓
- # GameController    
   - correct player number ✓
   - correct rounds sequence ✓
- # Cell
    - cellRule creation ✓
    - setting of occupied status ✓
    - setting of dice ✓
- # Dice
    - value and color initialization ✓
- # RoundTrack
    - get dice from round track ✓
    - get list of color of dice on round track ✓
    - get wrong dice fail ✓
- # ScoreTrack
    - correct score calculation ✓
- # CardController
    - deal private objectives without repetitions and with correct type ✓
    - private objectives are well formed ✓
    - deal public objectives without repetitions and with correct type ✓
    - public objectives are well formed ✓
    - deal tools without repetitions and with correct type ✓
    - tools are well formed ✓
- # WindowParser
    - Window are read correctly from JSON ✓
    - JSON tested for correctness ✓
    - Window are created in the right way ✓
- # Picker
    - picks from a list of generic object an element without repetitions ✓
- # Database
    - connect to remote MySQL database on port 3306 and check query result ✓
- # Heartbeat protocol
    - test heartbeat protocol on localhost, wait for at least an UDP datagram ping ✓

# Maven example testing output - same results on Travis CI
    -------------------------------------------------------
     T E S T S
    -------------------------------------------------------
    Running it.polimi.ingsw.base.DiceManagerTest
    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.131 sec
    Running it.polimi.ingsw.base.GameManagerTest
    giu 13, 2018 12:40:59 AM it.polimi.ingsw.sagrada.game.intercomm.MessageDispatcher dispatch
    GRAVE: Handler not found for class it.polimi.ingsw.sagrada.game.intercomm.message.card.PrivateObjectiveResponse
    giu 13, 2018 12:40:59 AM it.polimi.ingsw.sagrada.game.intercomm.MessageDispatcher dispatch
    GRAVE: Handler not found for class it.polimi.ingsw.sagrada.game.intercomm.message.card.PrivateObjectiveResponse
    giu 13, 2018 12:40:59 AM it.polimi.ingsw.sagrada.game.intercomm.MessageDispatcher dispatch
    GRAVE: Handler not found for class it.polimi.ingsw.sagrada.game.intercomm.message.card.ToolCardResponse
    giu 13, 2018 12:40:59 AM it.polimi.ingsw.sagrada.game.intercomm.MessageDispatcher dispatch
    GRAVE: Handler not found for class it.polimi.ingsw.sagrada.game.intercomm.message.card.PublicObjectiveResponse
    giu 13, 2018 12:40:59 AM it.polimi.ingsw.sagrada.game.intercomm.MessageDispatcher dispatch
    GRAVE: Handler not found for class it.polimi.ingsw.sagrada.game.intercomm.message.window.WindowResponse
    giu 13, 2018 12:40:59 AM it.polimi.ingsw.sagrada.game.intercomm.MessageDispatcher dispatch
    GRAVE: Handler not found for class it.polimi.ingsw.sagrada.game.intercomm.message.window.WindowResponse
    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.228 sec
    Running it.polimi.ingsw.base.PickerTest
    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.001 sec
    Running it.polimi.ingsw.base.PlayerIteratorTest
    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.001 sec
    Running it.polimi.ingsw.base.RoundTest
    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0 sec
    Running it.polimi.ingsw.cards.PrivateObjectiveDealerTest
    Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0 sec
    Running it.polimi.ingsw.cards.PublicObjectiveDealerTest
    Colori diversi - Colonna
    Sfumature medie
    Colori diversi - Riga
    Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.001 sec
    Running it.polimi.ingsw.cards.ToolDealerTest
    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0 sec
    Running it.polimi.ingsw.database.DatabaseSQLRemoteConnectionTest
    Wed Jun 13 00:40:59 CEST 2018 WARN: Establishing SSL connection without server's identity verification is not recommended. According to MySQL 5.5.45+, 5.6.26+ and 5.7.6+ requirements SSL connection must be established by default if explicit option isn't set. For compliance with existing applications not using SSL the verifyServerCertificate property is set to 'false'. You need either to explicitly disable SSL by setting useSSL=false, or set useSSL=true and provide truststore for server certificate verification.
    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.659 sec
    giu 13, 2018 12:41:00 AM it.polimi.ingsw.sagrada.database.SQLDatabase connect
    GRAVE: Fatal DB error : Access denied for user 'root'@'localhost' (using password: YES)
    Running it.polimi.ingsw.gui.CellViewTest
    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.29 sec
    Running it.polimi.ingsw.gui.ConstraintGeneratorTest
    1|W|3|B|W|
    W|2|B|W|W|
    6|B|W|4|W|
    B|5|2|W|1|
    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.001 sec
    Running it.polimi.ingsw.gui.ConstraintTest
    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0 sec
    Running it.polimi.ingsw.gui.DiceViewTest
    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0 sec
    Running it.polimi.ingsw.gui.GUIManagerTest
    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.011 sec
    Running it.polimi.ingsw.gui.ResizerTest
    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0 sec
    Running it.polimi.ingsw.gui.WindowGameManagerTest
    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.001 sec
    Running it.polimi.ingsw.gui.WindowImageTest
    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.001 sec
    Running it.polimi.ingsw.intercomm.IntercommunicationActionResponseVisitorTest
    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0 sec
    Running it.polimi.ingsw.intercomm.IntercommunicationMessageVisitorTest
    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.001 sec
    giu 13, 2018 12:41:00 AM it.polimi.ingsw.sagrada.game.intercomm.MessageDispatcher dispatch
    Running it.polimi.ingsw.intercomm.RouterTest
    GRAVE: Handler not found for class it.polimi.ingsw.sagrada.game.intercomm.message.card.PrivateObjectiveResponse
    giu 13, 2018 12:41:00 AM it.polimi.ingsw.sagrada.game.intercomm.MessageDispatcher dispatch
    GRAVE: Handler not found for class it.polimi.ingsw.sagrada.game.intercomm.message.card.PrivateObjectiveResponse
    giu 13, 2018 12:41:00 AM it.polimi.ingsw.sagrada.game.intercomm.MessageDispatcher dispatch
    GRAVE: Handler not found for class it.polimi.ingsw.sagrada.game.intercomm.message.card.ToolCardResponse
    it.polimi.ingsw.sagrada.game.intercomm.message.window.WindowGameManagerEvent
    giu 13, 2018 12:41:00 AM it.polimi.ingsw.sagrada.game.intercomm.MessageDispatcher dispatch
    Window player Mottola : Aurorae Magnificus 5
    GRAVE: Handler not found for class it.polimi.ingsw.sagrada.game.intercomm.message.card.PublicObjectiveResponse
    it.polimi.ingsw.sagrada.game.intercomm.message.window.WindowGameManagerEvent
    giu 13, 2018 12:41:00 AM it.polimi.ingsw.sagrada.game.intercomm.MessageDispatcher dispatch
    Window player ingconti : Comitas 10
    Initializing opponents windows message
    GRAVE: Handler not found for class it.polimi.ingsw.sagrada.game.intercomm.message.window.OpponentWindowResponse
    1 turn started
    giu 13, 2018 12:41:00 AM it.polimi.ingsw.sagrada.game.intercomm.MessageDispatcher dispatch
    GRAVE: Handler not found for class it.polimi.ingsw.sagrada.game.intercomm.message.game.NewTurnResponse
    giu 13, 2018 12:41:00 AM it.polimi.ingsw.sagrada.game.intercomm.MessageDispatcher dispatch
    GRAVE: Handler not found for class it.polimi.ingsw.sagrada.game.intercomm.message.game.BeginTurnEvent
    giu 13, 2018 12:41:00 AM it.polimi.ingsw.sagrada.game.intercomm.MessageDispatcher dispatch
    Printing
    GRAVE: Handler not found for class it.polimi.ingsw.sagrada.game.intercomm.message.game.RuleResponse
    9
    giu 13, 2018 12:41:00 AM it.polimi.ingsw.sagrada.game.intercomm.MessageDispatcher dispatch
    42
    2
    GRAVE: Handler not found for class it.polimi.ingsw.sagrada.game.intercomm.message.game.BeginTurnEvent
    61
    giu 13, 2018 12:41:00 AM it.polimi.ingsw.sagrada.game.intercomm.MessageDispatcher dispatch
    71
    GRAVE: Handler not found for class it.polimi.ingsw.sagrada.game.intercomm.message.game.RuleResponse
    End print
    giu 13, 2018 12:41:00 AM it.polimi.ingsw.sagrada.game.intercomm.MessageDispatcher dispatch
    Begin turn sent to Mottola
    GRAVE: Handler not found for class it.polimi.ingsw.sagrada.game.intercomm.message.game.BeginTurnEvent
    it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceGameManagerEvent
    giu 13, 2018 12:41:00 AM it.polimi.ingsw.sagrada.game.intercomm.MessageDispatcher dispatch
    it.polimi.ingsw.sagrada.game.intercomm.message.game.EndTurnEvent
    Begin turn sent to ingconti
    GRAVE: Handler not found for class it.polimi.ingsw.sagrada.game.intercomm.message.dice.OpponentDiceMoveResponse
    it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceGameManagerEvent
    giu 13, 2018 12:41:00 AM it.polimi.ingsw.sagrada.game.intercomm.MessageDispatcher dispatch
    it.polimi.ingsw.sagrada.game.intercomm.message.game.EndTurnEvent
    GRAVE: Handler not found for class it.polimi.ingsw.sagrada.game.intercomm.message.game.RuleResponse
    giu 13, 2018 12:41:00 AM it.polimi.ingsw.sagrada.game.intercomm.MessageDispatcher dispatch
    GRAVE: Handler not found for class it.polimi.ingsw.sagrada.game.intercomm.message.game.BeginTurnEvent
    giu 13, 2018 12:41:00 AM it.polimi.ingsw.sagrada.game.intercomm.MessageDispatcher dispatch
    GRAVE: Handler not found for class it.polimi.ingsw.sagrada.game.intercomm.message.dice.OpponentDiceMoveResponse
    giu 13, 2018 12:41:00 AM it.polimi.ingsw.sagrada.game.intercomm.MessageDispatcher dispatch
    GRAVE: Handler not found for class it.polimi.ingsw.sagrada.game.intercomm.message.game.RuleResponse
    giu 13, 2018 12:41:00 AM it.polimi.ingsw.sagrada.game.intercomm.MessageDispatcher dispatch
    GRAVE: Handler not found for class it.polimi.ingsw.sagrada.game.intercomm.message.game.NewTurnResponse
    giu 13, 2018 12:41:00 AM it.polimi.ingsw.sagrada.game.intercomm.MessageDispatcher dispatch
    GRAVE: Handler not found for class it.polimi.ingsw.sagrada.game.intercomm.message.game.BeginTurnEvent
    Begin turn sent to ingconti
    it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceGameManagerEvent
    it.polimi.ingsw.sagrada.game.intercomm.message.game.EndTurnEvent
    Begin turn sent to Mottola
    it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceGameManagerEvent
    it.polimi.ingsw.sagrada.game.intercomm.message.game.EndTurnEvent
    Ending round...
    2 turn started
    Printing
    80
    82
    34
    32
    76
    End print
    Begin turn sent to ingconti
    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.001 sec
    Running it.polimi.ingsw.network.client.JSONMessageTest
    Type : login_heartbeat
    Type : login
    Type : error
    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.001 sec
    Running it.polimi.ingsw.network.CommandParserTest
    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0 sec
    Running it.polimi.ingsw.network.MatchLobbyPoolTest
    1
    2
    giu 13, 2018 12:41:03 AM it.polimi.ingsw.sagrada.network.server.tools.MatchLobbyPool releaseLobby
    GRAVE: Connection refused to host: 192.168.1.5; nested exception is: 
     java.net.ConnectException: Connection refused: connect
    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 2.232 sec
    Running it.polimi.ingsw.network.MatchLobbyTest
    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0 sec
    Running it.polimi.ingsw.network.protocols.DiscoverInternetTest
    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0 sec
    Running it.polimi.ingsw.network.protocols.DiscoverLanTest
    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.248 sec
    giu 13, 2018 12:41:03 AM it.polimi.ingsw.network.protocols.HeartbeatTest onAcquiredCommunication
    INFORMAZIONI: identifier connected at 1528843263442
    Running it.polimi.ingsw.network.protocols.HeartbeatTest
    3
    giu 13, 2018 12:41:04 AM it.polimi.ingsw.network.protocols.HeartbeatTest onHeartbeat
    INFORMAZIONI: Received heartbet from identifier in 1000 at 1528843264443
    4
    giu 13, 2018 12:41:05 AM it.polimi.ingsw.network.protocols.HeartbeatTest onHeartbeat
    INFORMAZIONI: Received heartbet from identifier in 1000 at 1528843265446
    5
    giu 13, 2018 12:41:06 AM it.polimi.ingsw.network.protocols.HeartbeatTest onHeartbeat
    INFORMAZIONI: Received heartbet from identifier in 1000 at 1528843266446
    6
    giu 13, 2018 12:41:07 AM it.polimi.ingsw.network.protocols.HeartbeatTest onHeartbeat
    INFORMAZIONI: Received heartbet from identifier in 1000 at 1528843267446
    7
    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 4.995 sec
    giu 13, 2018 12:41:08 AM it.polimi.ingsw.network.protocols.HeartbeatTest onHeartbeat
    Running it.polimi.ingsw.network.protocols.NetworkCommunicationProtocolTest
    INFORMAZIONI: Received heartbet from identifier in 1000 at 1528843268446
    Type : dice_list
    Type : window_list
    Type : begin_turn
    Type : opponent_window_list
    Dice pre message: 0
    Type : opponent_dice_response
    Type : newround
    Type : rule_response
    Type : public_objectives
    Type : private_objective
    Type : tool_cards
    Type : login_heartbeat
    Type : lobby_add_player
    Type : login
    Type : lobby_time
    Type : lobby_remove_player
    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0 sec
    Running it.polimi.ingsw.network.protocols.NetworkUtilsTest
    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.108 sec
    Running it.polimi.ingsw.network.security.SecurityTest
    8
    Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.604 sec
    Running it.polimi.ingsw.network.tools.PortDiscoveryTest
    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0 sec
    giu 13, 2018 12:41:09 AM it.polimi.ingsw.network.tools.PortDiscoveryTest portDiscoveryTest
    INFORMAZIONI: waiting port being discovered, you can do something else
    giu 13, 2018 12:41:09 AM it.polimi.ingsw.network.tools.PortDiscoveryTest portDiscoveryTest
    INFORMAZIONI: waiting port being discovered, you can do something else
    giu 13, 2018 12:41:09 AM it.polimi.ingsw.network.tools.PortDiscoveryTest portDiscoveryTest
    INFORMAZIONI: waiting port being discovered, you can do something else
    giu 13, 2018 12:41:09 AM it.polimi.ingsw.network.tools.PortDiscoveryTest portDiscoveryTest
    INFORMAZIONI: waiting port being discovered, you can do something else
    giu 13, 2018 12:41:09 AM it.polimi.ingsw.network.tools.PortDiscoveryTest portDiscoveryTest
    INFORMAZIONI: discover available TCP and UDP port : 49153
    giu 13, 2018 12:41:09 AM it.polimi.ingsw.network.tools.PortDiscoveryTest portDiscoveryTest
    INFORMAZIONI: waiting port being discovered, you can do something else
    giu 13, 2018 12:41:09 AM it.polimi.ingsw.network.tools.PortDiscoveryTest portDiscoveryTest
    INFORMAZIONI: discover available TCP port : 49153
    giu 13, 2018 12:41:09 AM it.polimi.ingsw.network.tools.PortDiscoveryTest portDiscoveryTest
    INFORMAZIONI: waiting port being discovered, you can do something else
    giu 13, 2018 12:41:09 AM it.polimi.ingsw.network.tools.PortDiscoveryTest portDiscoveryTest
    INFORMAZIONI: waiting port being discovered, you can do something else
    giu 13, 2018 12:41:09 AM it.polimi.ingsw.network.tools.PortDiscoveryTest portDiscoveryTest
    INFORMAZIONI: waiting port being discovered, you can do something else
    giu 13, 2018 12:41:09 AM it.polimi.ingsw.network.tools.PortDiscoveryTest portDiscoveryTest
    INFORMAZIONI: waiting port being discovered, you can do something else
    giu 13, 2018 12:41:09 AM it.polimi.ingsw.network.tools.PortDiscoveryTest portDiscoveryTest
    INFORMAZIONI: waiting port being discovered, you can do something else
    giu 13, 2018 12:41:09 AM it.polimi.ingsw.network.tools.PortDiscoveryTest portDiscoveryTest
    INFORMAZIONI: discover available UDP port : 49152
    Running it.polimi.ingsw.playables.CellTest
    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0 sec
    Running it.polimi.ingsw.playables.DiceTest
    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.001 sec
    Running it.polimi.ingsw.playables.RoundTrackTest
    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0 sec
    Running it.polimi.ingsw.playables.ScoreTrackTest
    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0 sec
    Running it.polimi.ingsw.playables.WindowTest
    Kaleidoscopic Dream

    Y|B|||1|
    G||5||4|
    3||R||G|
    2|||B|Y|

    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0 sec
    Running it.polimi.ingsw.rules.CellBuilderTest
    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0 sec
    Running it.polimi.ingsw.rules.CellRuleTest
    Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.001 sec
    Running it.polimi.ingsw.rules.MainGameRuleTest
    Tests run: 5, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.001 sec
    Running it.polimi.ingsw.rules.ObjectiveBuilderTest
    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0 sec
    Running it.polimi.ingsw.rules.ObjectiveRuleTest
    Tests run: 9, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.001 sec
    Running it.polimi.ingsw.rules.RuleManagerTest
    Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.001 sec
    Running it.polimi.ingsw.rules.ToolBuilderTest
    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0 sec
    Running it.polimi.ingsw.rules.ToolRuleTest
    Tests run: 5, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.001 sec

    Results :

    Tests run: 66, Failures: 0, Errors: 0, Skipped: 0

    [INFO] ------------------------------------------------------------------------
    [INFO] BUILD SUCCESS
    [INFO] ------------------------------------------------------------------------
    [INFO] Total time: 14.189 s
    [INFO] Finished at: 2018-06-13T00:41:09+02:00
    [INFO] Final Memory: 12M/309M
    [INFO] ------------------------------------------------------------------------
# Travis CI OS detils :
    - Distributor ID:	Ubuntu
    - Description:	Ubuntu 14.04.5 LTS
    - Release:	14.04
    
# SonarQube last run screen : 
[![N|SonarQube](http://upload.vstanced.com/images/2018/06/13/yyP.jpg)](http://upload.vstanced.com/images/2018/06/13/yyP.jpg)

# Testing coverage :
 - ### Detailed coverage [here](https://github.com/Daniele-Comi/ingsw-project/wiki/Coverage-report)
[![N|Coverage](https://preview.ibb.co/dJqJPS/coverage1.png)](https://preview.ibb.co/dJqJPS/coverage1.png)
[![N|Coverage](https://preview.ibb.co/mWudPS/coverage2.png)](https://preview.ibb.co/mWudPS/coverage2.png)
[![N|Coverage](https://preview.ibb.co/kjUOr7/coverage3.png)](https://preview.ibb.co/kjUOr7/coverage3.png)
[![N|Coverage](https://preview.ibb.co/gXNbB7/coverage4.png)](https://preview.ibb.co/gXNbB7/coverage4.png)
[![N|Coverage](https://preview.ibb.co/cBn9W7/coverage5.png)](https://preview.ibb.co/cBn9W7/coverage5.png)

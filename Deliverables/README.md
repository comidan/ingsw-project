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

# Maven example testing output - same results on Travis CI
    -------------------------------------------------------
     T E S T S
    -------------------------------------------------------
    Running it.polimi.ingsw.base.PickerTest
    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.125 sec
    Running it.polimi.ingsw.base.RoundTest
    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0 sec
    Running it.polimi.ingsw.cards.PrivateObjectiveDealerTest
    Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.047 sec
    Running it.polimi.ingsw.cards.PublicObjectiveDealerTest
    Colori diversi - Colonna
    Sfumature chiare
    Sfumature medie
    Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.016 sec
    Running it.polimi.ingsw.cards.ToolDealerTest
    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0 sec
    Running it.polimi.ingsw.rules.CellBuilderTest
    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0 sec
    Running it.polimi.ingsw.rules.CellRuleTest
    Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0 sec
    Running it.polimi.ingsw.playables.CellTest
    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0 sec
    Running it.polimi.ingsw.DiceControllerTest
    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0 sec
    Running it.polimi.ingsw.playables.DiceTest
    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0 sec
    Running it.polimi.ingsw.base.GameManagerTest
    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0 sec
    Running it.polimi.ingsw.rules.MainGameRuleTest
    Tests run: 4, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0 sec
    Running it.polimi.ingsw.network.protocols.HeartbeatTest
    mag 14, 2018 11:36:58 AM it.polimi.ingsw.network.protocols.HeartbeatTest onAcquiredCommunication
    INFORMAZIONI: 02-15-6C-C1-13-AF connected at 1526290618592
    mag 14, 2018 11:36:59 AM it.polimi.ingsw.network.protocols.HeartbeatTest onHeartbeat
    INFORMAZIONI: Received heartbet from 02-15-6C-C1-13-AF in 1000 at 1526290619601
    mag 14, 2018 11:37:01 AM it.polimi.ingsw.network.protocols.HeartbeatTest onHeartbeat
    INFORMAZIONI: Received heartbet from 02-15-6C-C1-13-AF in 2000 at 1526290621608
    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 4.966 sec
    Running it.polimi.ingsw.rules.ObjectiveBuilderTest
    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0 sec
    Running it.polimi.ingsw.rules.ObjectiveRuleTest
    Tests run: 9, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0 sec
    Running it.polimi.ingsw.base.PlayerIteratorTest
    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0 sec
    Running it.polimi.ingsw.playables.RoundTrackTest
    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0 sec
    Running it.polimi.ingsw.RuleControllerTest
    Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0 sec
    Running it.polimi.ingsw.playables.ScoreTrackTest
    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0 sec
    Running it.polimi.ingsw.rules.ToolBuilderTest
    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0 sec
    Running it.polimi.ingsw.rules.ToolRuleTest
    Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0 sec
    Running it.polimi.ingsw.playables.WindowTest
    Fractal Drops

     |4| |Y|6|
    R| |2| | |
     | |R|P|1|
    L|Y| | | |

    Ripple of Light

     | | |R|5|
     | |P|4|L|
     |L|3|Y|6|
    Y|2|G|1|R|

    Gravitas

    1| |3|L| |
     |2|L| | |
    6|L| |4| |
    L|5|2| |1|

    Water of Life

    6|L| | |1|
     |5|L| | |
    4|R|2|L| |
    G|6|Y|3|P|

    Via Lux

    Y| |6| | |
     |1|5| |2|
    3|Y|R|P| |
     | |4|3|R|

    Industria

    1|R|3| |6|
    5|4|R|2| |
     | |5|R|1|
     | | |3|R|

    Batllo

     | |6| | |
     |5|L|4| |
    3|G|Y|P|2|
    1|4|R|5|3|

    Bellesguard

    L|6| | |Y|
     |3|L| | |
     |5|6|2| |
     |4| |1|G|

    Chromatic Splendor

     | |G| | |
    2|Y|5|L|1|
     |R|3|P| |
    1| |6| |4|

    Comitas

    Y| |2| |6|
     |4| |5|Y|
     | | |Y|5|
    1|2|Y|3| |

    Sun Catcher

     |L|2| |Y|
     |4| |R| |
     | |5|Y| |
    G|3| | |P|

    Shadow Thief

    6|P| | |5|
    5| |P| | |
    R|6| |P| |
    Y|R|5|4|3|

    Lux Mundi

     | |1| | |
    1|G|3|L|2|
    L|5|4|6|G|
     |L|5|G| |

    Lux Astram

     |1|G|P|4|
    6|P|2|5|G|
    1|G|5|3|P|
     | | | | |

    Aurorae Magnificus

    5|G|L|P|2|
    P| | | |Y|
    Y| |6| |P|
    1| | |G|4|

    Aurora Sagradis

    R| |L| |Y|
    4|P|3|G|2|
     |1| |5| |
     | |6| | |

    Kaleidoscopic Dream

    Y|L| | |1|
    G| |5| |4|
    3| |R| |G|
    2| | |L|Y|

    Firmitas

    P|6| | |3|
    5|P|3| | |
     |2|P|1| |
     |1|5|P|4|

    Firelight

    3|4|1|5| |
     |6|2| |Y|
     | | |Y|R|
    5| |Y|R|6|

    Sun's Glory

    1|P|Y| |4|
    P|Y| | |6|
    Y| | |5|3|
     |5|4|2|1|

    Symphony of Light

    2| |5| |1|
    Y|6|P|2|R|
     |L|4|G| |
     |3| |5| |

    Virtus

    4| |2|5|G|
     | |6|G|2|
     |3|G|4| |
    5|G|1| | |

    Fulgor del Cielo

     |L|R| | |
     |4|5| |L|
    L|2| |R|5|
    6|R|3|1| |

    Luz Celestial

     | |R|5| |
    P|4| |G|3|
    6| | |L| |
     |Y|2| | |

    Kaleidoscopic Dream

    Y|L| | |1|
    G| |5| |4|
    3| |R| |G|
    2| | |L|Y|

    Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0 sec

    Results :

    Tests run: 42, Failures: 0, Errors: 0, Skipped: 0

    [INFO] ------------------------------------------------------------------------
    [INFO] BUILD SUCCESS
    [INFO] ------------------------------------------------------------------------
    [INFO] Total time: 10.223 s
    [INFO] Finished at: 2018-05-14T11:37:02+02:00
    [INFO] Final Memory: 17M/212M
    [INFO] ------------------------------------------------------------------------

# Travis CI OS detils :
    - Distributor ID:	Ubuntu
    - Description:	Ubuntu 14.04.5 LTS
    - Release:	14.04
    
# SonarQube last run screen : 
[![N|SonarQube](https://preview.ibb.co/dPiY1J/sonar_Qube_Test_Intro.png)](https://preview.ibb.co/dPiY1J/sonar_Qube_Test_Intro.png)

# Testing coverage :
 - ### Detailed coverage [here](https://github.com/Daniele-Comi/ingsw-project/wiki/Coverage-report)
[![N|Coverage](https://preview.ibb.co/dJqJPS/coverage1.png)](https://preview.ibb.co/dJqJPS/coverage1.png)
[![N|Coverage](https://preview.ibb.co/mWudPS/coverage2.png)](https://preview.ibb.co/mWudPS/coverage2.png)
[![N|Coverage](https://preview.ibb.co/kjUOr7/coverage3.png)](https://preview.ibb.co/kjUOr7/coverage3.png)
[![N|Coverage](https://preview.ibb.co/gXNbB7/coverage4.png)](https://preview.ibb.co/gXNbB7/coverage4.png)
[![N|Coverage](https://preview.ibb.co/cBn9W7/coverage5.png)](https://preview.ibb.co/cBn9W7/coverage5.png)

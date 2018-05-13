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
    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.538 sec
    Running it.polimi.ingsw.base.RoundTest
    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.001 sec
    Running it.polimi.ingsw.cards.PrivateObjectiveDealerTest
    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0 sec
    Running it.polimi.ingsw.cards.PublicObjectiveDealerTest
    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.017 sec
    Running it.polimi.ingsw.CellBuilderTest
    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0 sec
    Running it.polimi.ingsw.CellRuleTest
    Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.001 sec
    Running it.polimi.ingsw.CellTest
    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0 sec
    Running it.polimi.ingsw.DiceControllerTest
    Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.001 sec
    Running it.polimi.ingsw.DiceTest
    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.001 sec
    Running it.polimi.ingsw.GameControllerTest
    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0 sec
    Running it.polimi.ingsw.MainGameRuleTest
    Tests run: 4, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.001 sec
    Running it.polimi.ingsw.ObjectiveBuilderTest
    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.001 sec
    Running it.polimi.ingsw.ObjectiveRuleTest
    Tests run: 9, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.002 sec
    Running it.polimi.ingsw.RoundTrackTest
    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0 sec
    Running it.polimi.ingsw.RuleControllerTest
    Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0 sec
    Running it.polimi.ingsw.ScoreTrackTest
    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0 sec
    Running it.polimi.ingsw.WindowTest
    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.001 sec
    
    Results :
    
    Tests run: 33, Failures: 0, Errors: 0, Skipped: 0
    
    [INFO] ------------------------------------------------------------------------
    [INFO] BUILD SUCCESS
    [INFO] ------------------------------------------------------------------------
    [INFO] Total time: 12.369 s
    [INFO] Finished at: 2018-05-07T20:46:13+02:00
    [INFO] Final Memory: 18M/211M
    [INFO] ------------------------------------------------------------------------

# Travis CI OS detils :
    - Distributor ID:	Ubuntu
    - Description:	Ubuntu 14.04.5 LTS
    - Release:	14.04
    
# SonarQube last run screen : 
[![N|SonarQube](https://preview.ibb.co/eEGNon/sonar_Qube_Test_Intro.png)](https://preview.ibb.co/eEGNon/sonar_Qube_Test_Intro.png)

# Testing coverage :
[![N|Coverage](https://preview.ibb.co/dJqJPS/coverage1.png)](https://preview.ibb.co/dJqJPS/coverage1.png)
[![N|Coverage](https://preview.ibb.co/mWudPS/coverage2.png)](https://preview.ibb.co/mWudPS/coverage2.png)
[![N|Coverage](https://preview.ibb.co/kjUOr7/coverage3.png)](https://preview.ibb.co/kjUOr7/coverage3.png)
[![N|Coverage](https://preview.ibb.co/gXNbB7/coverage4.png)](https://preview.ibb.co/gXNbB7/coverage4.png)
[![N|Coverage](https://preview.ibb.co/cBn9W7/coverage5.png)](https://preview.ibb.co/cBn9W7/coverage5.png)

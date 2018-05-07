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
    - dice pick from empty draft✓
    - pick of wrong number of dice ✓
- # Window    
    - dice on empty cell ✓
    - dice on occupied cell ✓
- # GameController    
   - correct player number ✓
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

    
    

    
    



# Maven example testing output - same results on Travis CI
    -------------------------------------------------------
     T E S T S
    -------------------------------------------------------

    Running it.polimi.ingsw.base.PickerTest
    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0 sec
    Running it.polimi.ingsw.base.RoundTest
    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0 sec
    Running it.polimi.ingsw.cards.PrivateObjectiveDealerTest
    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0 sec
    Running it.polimi.ingsw.cards.PublicObjectiveDealerTest
    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.001 sec
    Running it.polimi.ingsw.CellBuilderTest
    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.001 sec
    Running it.polimi.ingsw.CellRuleTest
    Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0 sec
    Running it.polimi.ingsw.MainGameRuleTest
    Tests run: 4, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0 sec
    Running it.polimi.ingsw.ObjectiveBuilderTest
    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.001 sec
    Running it.polimi.ingsw.ObjectiveRuleTest
    Tests run: 9, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0 sec
    Running it.polimi.ingsw.RuleControllerTest
    Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0 sec
    
    Results :
    
    Tests run: 25, Failures: 0, Errors: 0, Skipped: 0
    
    [INFO] ------------------------------------------------------------------------
    [INFO] BUILD SUCCESS
    [INFO] ------------------------------------------------------------------------
    [INFO] Total time: 7.900 s
    [INFO] Finished at: 2018-05-07T17:11:10+02:00
    [INFO] Final Memory: 17M/211M
    [INFO] ------------------------------------------------------------------------

# Travis CI OS detils :
    - Distributor ID:	Ubuntu
    - Description:	Ubuntu 14.04.5 LTS
    - Release:	14.04
# SonarQube last run screen : 
[![N|Sagrada](https://preview.ibb.co/jgXRdn/sonar_Qube_Test_Intro.png)](https://preview.ibb.co/jgXRdn/sonar_Qube_Test_Intro.png)

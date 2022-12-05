package pairmatching.presentation

import camp.nextstep.edu.missionutils.Console
import pairmatching.domain.Crew
import pairmatching.util.validator.CrewValidator
import pairmatching.util.validator.InputValidator

class PairMatchingView(
    private val inputValidator: InputValidator = InputValidator(),
    private val crewValidator: CrewValidator = CrewValidator()
) : View() {

    fun selectOption(): String {
        print(SELECT_OPTION_MESSAGE)
        return try {
            val option = Console.readLine()
            printEnter()
            inputValidator.validateOption(option)
        } catch (error: IllegalArgumentException) {
            printError(error)
            selectOption()
        }
    }

    fun selectPairMatchingCondition(): List<String> {
        println(ENTER_PAIR_MATCHING_CONDITION_MESSAGE)
        return try {
            val condition = Console.readLine()
            printEnter()
            inputValidator.validatePairMatchingCondition(condition)
        } catch (error: IllegalArgumentException) {
            printError(error)
            selectPairMatchingCondition()
        }
    }

    fun enterRematch(): Boolean {
        println(REMATCH_MESSAGE)
        return try {
            val isRematch = Console.readLine()
            printEnter()
            inputValidator.validateRematch(isRematch)
        } catch (error: IllegalArgumentException) {
            printError(error)
            enterRematch()
        }
    }

    fun printMatchedCrews(matchedCrews: List<List<Crew>>) {
        runCatching {
            crewValidator.validateIsExistCrewPair(matchedCrews)
        }.onSuccess {
            matchedCrews.forEach { crews ->
                val pairMatchedCrewNames = crews.map { it.toString() }
                println(pairMatchedCrewNames.joinToString(" : "))
            }
        }.onFailure { printError(it) }
        println()
    }

    fun printInitializedMessage() {
        println(INITIALIZED_MESSAGE)
    }


    companion object {
        private const val SELECT_OPTION_MESSAGE =
            "기능을 선택하세요.\n" + "1. 페어 매칭\n" + "2. 페어 조회\n" + "3. 페어 초기화\n" + "Q. 종료\n"
        private const val ENTER_PAIR_MATCHING_CONDITION_MESSAGE =
            "#############################################\n" + "과정: 백엔드 | 프론트엔드\n" + "미션:\n" + "  - 레벨1: 자동차경주 | 로또 | 숫자야구게임\n" + "  - 레벨2: 장바구니 | 결제 | 지하철노선도\n" + "  - 레벨3: \n" + "  - 레벨4: 성능개선 | 배포\n" + "  - 레벨5: \n" + "############################################\n" + "과정, 레벨, 미션을 선택하세요.\n" + "ex) 백엔드, 레벨1, 자동차경주"
        private const val REMATCH_MESSAGE = "매칭 정보가 있습니다. 다시 매칭하시겠습니까?\n" + "네 | 아니오"
        private const val INITIALIZED_MESSAGE = "초기화 되었습니다.\n"
    }
}
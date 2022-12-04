package pairmatching.presentation

import camp.nextstep.edu.missionutils.Console
import pairmatching.util.validator.InputValidator

class PairMatchingView(
    private val inputValidator: InputValidator = InputValidator()
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


    companion object {
        private const val SELECT_OPTION_MESSAGE =
            "기능을 선택하세요.\n" + "1. 페어 매칭\n" + "2. 페어 조회\n" + "3. 페어 초기화\n" + "Q. 종료\n"
        private const val ENTER_PAIR_MATCHING_CONDITION_MESSAGE = "#############################################\n" +
                "과정: 백엔드 | 프론트엔드\n" +
                "미션:\n" +
                "  - 레벨1: 자동차경주 | 로또 | 숫자야구게임\n" +
                "  - 레벨2: 장바구니 | 결제 | 지하철노선도\n" +
                "  - 레벨3: \n" +
                "  - 레벨4: 성능개선 | 배포\n" +
                "  - 레벨5: \n" +
                "############################################\n" +
                "과정, 레벨, 미션을 선택하세요.\n" +
                "ex) 백엔드, 레벨1, 자동차경주"

    }
}
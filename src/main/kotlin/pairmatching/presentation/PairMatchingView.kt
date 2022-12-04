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


    companion object {
        private const val SELECT_OPTION_MESSAGE =
            "기능을 선택하세요.\n" + "1. 페어 매칭\n" + "2. 페어 조회\n" + "3. 페어 초기화\n" + "Q. 종료\n"
    }
}
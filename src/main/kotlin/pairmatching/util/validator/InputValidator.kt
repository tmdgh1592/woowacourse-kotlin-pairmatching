package pairmatching.util.validator

class InputValidator : Validator() {

    fun validateOption(option: String): String {
        val optionList = listOf("1", "2", "3", "Q")
        if (option !in optionList)  {
            throw IllegalArgumentException(INVALID_OPTION_EXCEPTION_MESSAGE)
        }
        return option
    }

    companion object {
        private const val INVALID_OPTION_EXCEPTION_MESSAGE = "$ERROR_PREFIX 올바른 옵션 값이 아닙니다."
    }
}
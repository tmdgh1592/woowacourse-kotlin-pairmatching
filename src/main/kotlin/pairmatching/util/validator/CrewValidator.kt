package pairmatching.util.validator

import pairmatching.domain.Crew

class CrewValidator : Validator() {
    fun validateIsExistCrewPair(crews: List<List<Crew>>) {
        if (crews.isEmpty()) {
            throw IllegalArgumentException(NO_MATCHED_EXCEPTION_MESSAGE)
        }
    }

    companion object {
        private const val NO_MATCHED_EXCEPTION_MESSAGE = "$ERROR_PREFIX 매칭 이력이 없습니다."
    }
}
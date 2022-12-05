package pairmatching.util.validator

import pairmatching.domain.Course
import pairmatching.domain.Level
import pairmatching.domain.Mission

class InputValidator : Validator() {

    fun validateOption(option: String): String {
        val optionList = listOf("1", "2", "3", "Q")
        if (option !in optionList) {
            throw IllegalArgumentException(INVALID_OPTION_EXCEPTION_MESSAGE)
        }
        return option
    }

    fun validatePairMatchingCondition(pairMatchingCondition: String): List<String> {
        validateSearchFormat(pairMatchingCondition)
        validateSearchValue(pairMatchingCondition)
        return pairMatchingCondition.split(", ")
    }

    private fun validateSearchFormat(pairMatchingCondition: String) {
        val pairMatchingConditions = pairMatchingCondition.split(", ")
        if (pairMatchingConditions.size != 3) {
            throw IllegalArgumentException(INVALID_SEARCH_FORMAT_EXCEPTION_MESSAGE)
        }
    }

    private fun validateSearchValue(pairMatchingCondition: String) {
        val (course, level, mission) = pairMatchingCondition.split(", ")
        validateCourse(course)
        validateLevel(level)
        validateMission(level, mission)
    }

    private fun validateCourse(course: String) {
        if (course !in Course.provideCourses()) {
            throw IllegalArgumentException(INVALID_COURSE_EXCEPTION_MESSAGE)
        }
    }

    private fun validateLevel(level: String) {
        val levels = Level.provideLevels()
        if (level !in levels) {
            throw IllegalArgumentException(INVALID_LEVEL_EXCEPTION_MESSAGE)
        }
    }

    private fun validateMission(level: String, mission: String) {
        val missionsByLevel = Mission.provideMissions(Level.convertLevel(level))
        if (mission !in missionsByLevel) {
            throw IllegalArgumentException(INVALID_MISSION_EXCEPTION_MESSAGE)
        }
    }

    fun validateRematch(rematch: String): Boolean {
        if (rematch == "네") {
            return true
        }
        if (rematch == "아니오") {
            return false
        }

        throw IllegalArgumentException(INVALID_REMATCH_EXCEPTION_MESSAGE)
    }

    companion object {
        private const val INVALID_OPTION_EXCEPTION_MESSAGE = "$ERROR_PREFIX 올바른 옵션 값이 아닙니다."
        private const val INVALID_SEARCH_FORMAT_EXCEPTION_MESSAGE = "$ERROR_PREFIX 올바른 매칭 검색 조건이 아닙니다."
        private const val INVALID_COURSE_EXCEPTION_MESSAGE = "$ERROR_PREFIX 올바른 코스명이 아닙니다."
        private const val INVALID_LEVEL_EXCEPTION_MESSAGE = "$ERROR_PREFIX 올바른 레벨이 아닙니다."
        private const val INVALID_MISSION_EXCEPTION_MESSAGE = "$ERROR_PREFIX 올바른 미션이 아닙니다."
        private const val INVALID_REMATCH_EXCEPTION_MESSAGE = "$ERROR_PREFIX 네 또는 아니오만 입력할 수 있습니다."
    }
}
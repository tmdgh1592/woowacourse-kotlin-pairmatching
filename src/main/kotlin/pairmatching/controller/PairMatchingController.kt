package pairmatching.controller

import pairmatching.domain.Course
import pairmatching.presentation.PairMatchingView
import pairmatching.service.PairMatchingService

class PairMatchingController(
    private val pairMatchingView: PairMatchingView,
    private val pairMatchingService: PairMatchingService
) {

    fun startPairMatching() {
        do {
            val option = selectOption()
            val quit = performOption(option)
        } while (quit)
    }

    private fun performOption(option: String): Boolean {
        when (option) {
            MATCH_PAIR_OPTION -> matchPair()
            SHOW_PAIR_OPTION -> inquirePair()
            INIT_OPTION -> initPair()
            QUIT_OPTION -> return QUIT
        }
        return CONTINUE
    }

    private fun matchPair() {
        val (course, level, mission) = pairMatchingView.selectPairMatchingCondition()
        if (pairMatchingService.isMatched(Course.convertCourse(course), mission)) { // 이미 매칭된 미션인 경우
            val isRematch = pairMatchingView.enterRematch()
            if (isRematch) {
                showMatchedCrews(pairMatchingService.matchCrew(course, level, mission))
            }
            return
        }

        showMatchedCrews(pairMatchingService.matchCrew(course, level, mission))
    }

    private fun inquirePair() {
        val (course, _, mission) = pairMatchingView.selectPairMatchingCondition()
        val matchedCrews = getMatchedCrews(Course.convertCourse(course), mission)
        showMatchedCrews(matchedCrews)
    }

    private fun showMatchedCrews(matchedCrews: List<List<String>>) {
        pairMatchingView.printMatchedCrews(matchedCrews)
    }

    private fun initPair() {
        pairMatchingService.clearPair()
        pairMatchingView.printInitializedMessage()
    }

    private fun getMatchedCrews(course: Course, mission: String): List<List<String>> =
        pairMatchingService.getMatchedCrews(course, mission)

    private fun selectOption(): String = pairMatchingView.selectOption()

    companion object {
        private const val MATCH_PAIR_OPTION = "1"
        private const val SHOW_PAIR_OPTION = "2"
        private const val INIT_OPTION = "3"
        private const val QUIT_OPTION = "Q"

        private const val QUIT = false
        private const val CONTINUE = true
    }
}
package pairmatching.controller

import pairmatching.domain.Crew
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
            pairMatchingOption -> pairMatching()
            quitOption -> return QUIT
        }
        return CONTINUE
    }

    private fun pairMatching() {
        val (course, level, mission) = pairMatchingView.selectPairMatchingCondition()
        if (pairMatchingService.isMatched(mission)) { // 이미 매칭된 미션인 경우
            val isRematch = pairMatchingView.enterRematch()
            if (isRematch) {
                showMatchedCrews(pairMatchingService.matchCrew(course, level, mission))
            }
        } else {
            showMatchedCrews(pairMatchingService.matchCrew(course, level, mission))
        }
    }

    private fun showMatchedCrews(matchedCrews: List<List<Crew>>) {
        pairMatchingView.printMatchedCrews(matchedCrews)
    }

    private fun selectOption(): String = pairMatchingView.selectOption()

    companion object {
        private const val pairMatchingOption = "1"
        private const val quitOption = "Q"

        private const val QUIT = false
        private const val CONTINUE = true
    }
}
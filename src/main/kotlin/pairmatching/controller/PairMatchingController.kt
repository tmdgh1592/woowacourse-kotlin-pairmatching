package pairmatching.controller

import pairmatching.presentation.PairMatchingView

class PairMatchingController(
    private val pairMatchingView: PairMatchingView,
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
        print(course + level + mission)
    }

    private fun selectOption(): String = pairMatchingView.selectOption()

    companion object {
        private const val pairMatchingOption = "1"
        private const val quitOption = "Q"

        private const val QUIT = false
        private const val CONTINUE = true
    }
}
package pairmatching.controller

import pairmatching.presentation.PairMatchingView

class PairMatchingController(
    private val pairMatchingView: PairMatchingView,
) {

    fun startPairMatching() {
        val option = selectOption()
        performOption(option)
    }

    private fun performOption(option: String) {
        when(option) {
            pairMatchingOption -> pairMatching()
        }
    }

    private fun pairMatching() {
        val (course, level, mission) = pairMatchingView.selectPairMatchingCondition()
        print(course + level + mission)
    }

    private fun selectOption(): String = pairMatchingView.selectOption()

    companion object {
        private const val pairMatchingOption = "1"
    }
}
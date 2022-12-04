package pairmatching.controller

import pairmatching.presentation.PairMatchingView

class PairMatchingController(
    private val pairMatchingView: PairMatchingView,
) {

    fun startPairMatching() {
        val option = selectOption()
    }

    private fun selectOption(): String = pairMatchingView.selectOption()
}
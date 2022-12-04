package pairmatching

import pairmatching.controller.PairMatchingController
import pairmatching.presentation.PairMatchingView

fun main() {
    // TODO: 프로그램 구현
    val pairMatchingController = PairMatchingController(PairMatchingView())
    pairMatchingController.startPairMatching()
}
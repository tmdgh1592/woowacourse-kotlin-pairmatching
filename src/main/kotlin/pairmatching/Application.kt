package pairmatching

import pairmatching.controller.PairMatchingController
import pairmatching.presentation.PairMatchingView
import pairmatching.service.PairMatchingService

fun main() {
    val pairMatchingController = PairMatchingController(PairMatchingView(), PairMatchingService())
    pairMatchingController.startPairMatching()
}
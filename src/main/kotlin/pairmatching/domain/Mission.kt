package pairmatching.domain

class Mission() {
//    CAR_RACE("자동차경주"),
//    LOTTO("로또"),
//    BASEBALL("숫자야구게임"),
//
//    BASKET("장바구니"),
//    PAYMENT("결제"),
//    SUBWAY("지하철노선도"),
//
//    IMPROVE("성능개선"),
//    DISTRIBUTE("배포");

    companion object {
        private val missionsByLevel = hashMapOf(
            Level.LEVEL1 to listOf("자동차경주", "로또", "숫자야구게임"),
            Level.LEVEL2 to listOf("장바구니", "결제", "지하철노선도"),
            Level.LEVEL3 to emptyList(),
            Level.LEVEL4 to listOf("성능개선", "배포"),
            Level.LEVEL5 to emptyList()
        )

        fun provideMissions(level: Level): List<String> = missionsByLevel[level]!!
    }
}
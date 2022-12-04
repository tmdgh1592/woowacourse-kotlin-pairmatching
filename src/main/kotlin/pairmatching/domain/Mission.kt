package pairmatching.domain

class Mission {

    companion object {
        private val missionsByLevel = hashMapOf(
            "레벨1" to listOf("자동차경주", "로또", "숫자야구게임"),
            "레벨2" to listOf("장바구니", "결제", "지하철노선도"),
            "레벨3" to emptyList(),
            "레벨4" to listOf("성능개선", "배포"),
            "레벨5" to emptyList()
        )

        fun provideMissions(level: String): List<String> = missionsByLevel[level]!!
    }
}
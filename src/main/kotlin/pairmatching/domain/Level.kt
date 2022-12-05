package pairmatching.domain

enum class Level(private val level: String) {
    LEVEL1("레벨1"),
    LEVEL2("레벨2"),
    LEVEL3("레벨3"),
    LEVEL4("레벨4"),
    LEVEL5("레벨5");

    companion object {
        fun provideLevels(): List<String> = Level.values().map {
            it.level
        }

        fun convertLevel(level: String) = when (level) {
            "레벨1" -> LEVEL1
            "레벨2" -> LEVEL2
            "레벨3" -> LEVEL3
            "레벨4" -> LEVEL4
            else -> LEVEL5
        }
    }
}
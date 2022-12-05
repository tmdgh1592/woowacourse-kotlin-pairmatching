package pairmatching.domain

data class Crew(
    private val course: Course,
    private val name: String
) {
    override fun toString(): String {
        return name
    }
}
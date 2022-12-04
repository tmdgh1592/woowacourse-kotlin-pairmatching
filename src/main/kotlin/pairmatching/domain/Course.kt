package pairmatching.domain

enum class Course(private val course: String) {
    BACKEND("백엔드"), FRONTEND("프론트엔드");

    companion object {
        fun provideCourses(): List<String> = Course.values().map {
            it.course
        }
    }
}
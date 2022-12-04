package pairmatching.util.reader

import pairmatching.domain.Course
import pairmatching.domain.Crew
import java.io.File

class CrewReader {

    fun readCrew(course: Course): List<Crew> {
        val crews = arrayListOf<Crew>()
        var crewName: String?
        val backendCrewFile = File(getPath(course))

        backendCrewFile.bufferedReader().use {
            while (true) {
                crewName = it.readLine()
                crewName?.let { name -> crews.add(Crew(course, name)) } ?: break
            }
        }
        return crews
    }

    private fun getPath(course: Course): String {
        if (course == Course.BACKEND) {
            return BACKEND_CREW_FILE_PATH
        }
        return FRONTEND_CREW_FILE_PATH
    }

    companion object {
        private const val BACKEND_CREW_FILE_PATH = "src/main/resources/backend-crew.md"
        private const val FRONTEND_CREW_FILE_PATH = "src/main/resources/frontend-crew.md"
    }
}
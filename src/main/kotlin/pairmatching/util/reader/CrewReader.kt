package pairmatching.util.reader

import pairmatching.domain.Course
import java.io.File

class CrewReader {

    fun readCrew(course: Course): List<String> {
        val crews = arrayListOf<String>()
        var crewName: String?
        val backendCrewFile = File(getPath(course))

        backendCrewFile.bufferedReader().use {
            while (true) {
                crewName = it.readLine()
                crewName?.let { name -> crews.add(name) } ?: break
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
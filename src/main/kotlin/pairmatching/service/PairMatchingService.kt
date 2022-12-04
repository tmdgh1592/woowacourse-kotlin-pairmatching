package pairmatching.service

import pairmatching.domain.Course
import pairmatching.domain.Crew
import pairmatching.util.reader.CrewReader

class PairMatchingService {
    private val crewReader = CrewReader()

    private fun getBackendCrews(): List<Crew> = crewReader.readCrew(Course.BACKEND)
    private fun getFrontendCrews(): List<Crew> = crewReader.readCrew(Course.FRONTEND)
}
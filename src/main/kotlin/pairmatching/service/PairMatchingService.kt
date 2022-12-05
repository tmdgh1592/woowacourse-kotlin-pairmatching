package pairmatching.service

import camp.nextstep.edu.missionutils.Randoms
import pairmatching.domain.Course
import pairmatching.domain.Course.Companion.convertCourse
import pairmatching.domain.Level
import pairmatching.util.reader.CrewReader

class PairMatchingService : Service() {
    private val crewReader = CrewReader()
    private val matchedCrewByLevel = hashMapOf<Course, HashMap<Level, HashMap<String, ArrayList<String>>>>()
    private val matchedCrewByMission = hashMapOf<Course, HashMap<String, List<List<String>>>>()

    fun getMatchedCrews(course: Course, mission: String): List<List<String>> {
        return matchedCrewByMission[course]?.get(mission) ?: emptyList()
    }

    fun matchCrew(course: String, level: String, mission: String): List<List<String>> {
        val shuffledCrews = getShuffledCrews(convertCourse(course))

        for (turn in 0 until 3) {
            val newMatchedCrews = match(shuffledCrews)
            val convertedLevel = Level.convertLevel(level)

            if (checkDuplication(newMatchedCrews, convertCourse(course), convertedLevel)) { // 중복된 경우 다시 매칭
                continue
            }
            addCrewsByLevel(newMatchedCrews, convertCourse(course), convertedLevel)
            setCrewsByMission(course, mission, newMatchedCrews)
            return newMatchedCrews
        }
        throw IllegalArgumentException(UNABLE_TO_MATCH_EXCEPTION_MESSAGE)
    }

    private fun addCrewsByLevel(
        newMatchedCrews: List<List<String>>, course: Course, level: Level
    ) {
        newMatchedCrews.forEach { pair ->
            pair.forEach { crew ->
                if (matchedCrewByLevel[course] == null) {
                    matchedCrewByLevel[course] = hashMapOf()
                }
                matchedCrewByLevel[course]!![level]?.getOrPut(crew) { arrayListOf() }?.addAll(pair)
            }
        }
    }

    private fun setCrewsByMission(
        course: String, mission: String, newMatchedCrews: List<List<String>>
    ) {
        matchedCrewByMission.getOrPut(convertCourse(course)) { hashMapOf(mission to newMatchedCrews) }
    }

    fun isMatched(course: Course, mission: String): Boolean {
        if (matchedCrewByMission[course]?.get(mission).isNullOrEmpty()) {
            return false
        }
        return true
    }

    private fun match(crews: List<String>): List<List<String>> {
        if (crews.size < MINIMUM_MATCHING_CREWS_SIZE) { // 최소 매칭 숫자
            throw IllegalArgumentException(INVALID_MATCHING_SIZE_EXCEPTION_MESSAGE)
        }

        val mutableCrews = ArrayList(crews)
        val matchedCrews = arrayListOf<List<String>>()
        while (mutableCrews.isNotEmpty()) {
            if (mutableCrews.size == MINIMUM_MATCHING_CREWS_ODD_SIZE) { // 3명 남은 경우
                matchedCrews.add(mutableCrews.slice(0 until MINIMUM_MATCHING_CREWS_ODD_SIZE))
                break
            }
            matchedCrews.add(mutableCrews.slice(0 until MINIMUM_MATCHING_CREWS_SIZE))
            mutableCrews.subList(0, MINIMUM_MATCHING_CREWS_SIZE).clear()
        }
        return matchedCrews
    }

    private fun checkDuplication(matchedCrews: List<List<String>>, course: Course, level: Level): Boolean {
        val matchedCrewsByLevel = matchedCrewByLevel[course]?.get(level) ?: return false
        matchedCrews.forEach { crews ->
            crews.forEach { crew ->
                val expectedSize = crews.size + (matchedCrewsByLevel[crew]?.size ?: 0)
                val nonDuplicatedSize = crews.union(matchedCrewsByLevel[crew] ?: emptyList()).size
                if (expectedSize != nonDuplicatedSize) {
                    return true
                }
            }
        }
        return false
    }

    fun clearPair() {
        matchedCrewByLevel.clear()
        matchedCrewByMission.clear()
    }

    private fun getShuffledCrews(course: Course): List<String> {
        return Randoms.shuffle(getCrews(course))
    }

    private fun getCrews(course: Course): List<String> = crewReader.readCrew(course)

    companion object {
        private const val MINIMUM_MATCHING_CREWS_SIZE = 2
        private const val MINIMUM_MATCHING_CREWS_ODD_SIZE = 3

        private const val INVALID_MATCHING_SIZE_EXCEPTION_MESSAGE = "$ERROR_PREFIX 크루원은 최소 2명 이상이어야 합니다."
        private const val UNABLE_TO_MATCH_EXCEPTION_MESSAGE = "$ERROR_PREFIX 3회 시도까지 매칭되지 않았습니다."
    }
}
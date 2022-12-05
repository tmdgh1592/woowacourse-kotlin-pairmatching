package pairmatching.service

import camp.nextstep.edu.missionutils.Randoms
import pairmatching.domain.Course
import pairmatching.domain.Crew
import pairmatching.domain.Level
import pairmatching.util.reader.CrewReader

class PairMatchingService : Service() {
    private val crewReader = CrewReader()
    private val matchedCrewByLevel = hashMapOf<Level, HashMap<Crew, ArrayList<Crew>>>()
    private val matchedCrewByMission = hashMapOf<String, List<List<Crew>>>()

    fun getMatchedCrews(mission: String): List<List<Crew>> {
        return matchedCrewByMission[mission] ?: emptyList()
    }

    fun matchCrew(course: String, level: String, mission: String): List<List<Crew>> {
        val shuffledCrews = getShuffledCrews(course)

        for (turn in 0 until 3) {
            val newMatchedCrews = match(shuffledCrews)
            val convertedLevel = Level.convertLevel(level)

            if (checkDuplication(newMatchedCrews, convertedLevel)) { // 중복된 경우 다시 매칭
                continue
            }
            addCrewByLevel(newMatchedCrews, convertedLevel)
            matchedCrewByMission[mission] = newMatchedCrews
            return newMatchedCrews
        }
        throw IllegalArgumentException(UNABLE_TO_MATCH_EXCEPTION_MESSAGE)
    }

    private fun addCrewByLevel(
        newMatchedCrews: List<List<Crew>>,
        convertedLevel: Level
    ) {
        newMatchedCrews.forEach { pair ->
            pair.forEach { crew ->
                matchedCrewByLevel[convertedLevel]?.get(crew)?.addAll(pair)
            }
        }
    }

    fun isMatched(mission: String): Boolean {
        if (matchedCrewByMission[mission].isNullOrEmpty()) {
            return false
        }
        return true
    }

    private fun match(crews: List<Crew>): List<List<Crew>> {
        if (crews.size < MINIMUM_MATCHING_CREWS_SIZE) { // 최소 매칭 숫자
            throw IllegalArgumentException(INVALID_MATCHING_SIZE_EXCEPTION_MESSAGE)
        }

        val mutableCrews =  ArrayList(crews)
        val matchedCrews = arrayListOf<List<Crew>>()
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

    private fun checkDuplication(matchedCrews: List<List<Crew>>, level: Level): Boolean {
        val matchedCrewsByLevel = matchedCrewByLevel[level] ?: return false
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

    private fun getShuffledCrews(course: String): List<Crew> {
        val crews = getCrews(Course.convertCourse(course))
        return Randoms.shuffle(crews)
    }

    private fun getCrews(course: Course): List<Crew> = crewReader.readCrew(course)

    companion object {
        private const val MINIMUM_MATCHING_CREWS_SIZE = 2
        private const val MINIMUM_MATCHING_CREWS_ODD_SIZE = 3

        private const val INVALID_MATCHING_SIZE_EXCEPTION_MESSAGE = "$ERROR_PREFIX 크루원은 최소 2명 이상이어야 합니다."
        private const val UNABLE_TO_MATCH_EXCEPTION_MESSAGE = "$ERROR_PREFIX 3회 시도까지 매칭되지 않았습니다."
    }
}
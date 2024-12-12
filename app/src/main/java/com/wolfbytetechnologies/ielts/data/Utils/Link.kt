package com.wolfbytetechnologies.ielts.data.Utils

object YouTubeLink {

    const val link = "https://www.youtube.com/results?search_query="
    const val ReadingLessonLink = link + "Reading+Lesson"
    const val ReadingTestLink = link + "Reading+Test"
    const val ListeningLessonLink = link + "Listening+Lesson"
    const val ListeningTestLink = link + "Listening+Test"
    const val WritingLessonLink = link + "Writing+Lesson"
    const val WritingTask1Link = link + "Writing+Task+1"
    const val WritingTask2Link = link + "Writing+Task+2"
    const val SpeakingLessonLink = link + "Speaking+Lesson"
    const val SpeakingTestLink = link + "Speaking+Test"


    fun getLink(query: String): String {
        return query
    }


}
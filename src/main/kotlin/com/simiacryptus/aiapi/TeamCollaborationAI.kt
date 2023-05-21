package com.simiacryptus.aiapi

import com.simiacryptus.openai.proxy.Description

interface TeamCollaborationAI {

    @Description("Assigns tasks to team members based on their skills and workload")
    fun assignTasks(taskList: List<Task>, teamMembers: List<TeamMember>): Map<TeamMember, List<Task>>

    @Description("Generates a meeting agenda based on the provided topics and priorities")
    fun generateMeetingAgenda(topics: List<Topic>, priorities: Map<Topic, Priority>): List<AgendaItem>

    @Description("Summarizes team discussions, highlighting key points and decisions")
    fun summarizeTeamDiscussions(discussions: List<String>, summaryLength: Int): String

    @Description("Suggests action items based on the team discussion")
    fun extractActionItems(discussions: List<String>): List<ActionItem>

    @Description("Analyzes team sentiment from communication data")
    fun analyzeTeamSentiment(communicationData: List<String>): Sentiment

    data class Task(val id: String, val description: String, val skillRequired: String, val estimatedEffort: Double)

    data class TeamMember(val id: String, val name: String, val skills: List<String>, val workload: Double)

    data class Topic(val id: String, val description: String)

    data class Priority(val id: String, val level: Int)

    data class AgendaItem(val topic: Topic, val priority: Priority)

    data class ActionItem(val id: String, val description: String, val responsibleMember: TeamMember, val dueDate: String)

    data class Sentiment(val category: String, val score: Double)
}

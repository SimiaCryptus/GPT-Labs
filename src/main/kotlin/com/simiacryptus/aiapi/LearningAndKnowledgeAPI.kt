package com.simiacryptus.aiapi

import com.simiacryptus.openai.proxy.Description

interface LearningAndKnowledgeAPI {

    @Description("Generates flashcards from the given text, extracting key concepts and their explanations")
    fun createFlashcards(text: String, maxFlashcards: Int): List<Flashcard>

    @Description("Summarizes key concepts from the given text")
    fun extractKeyConcepts(text: String, maxConcepts: Int): List<String>

    @Description("Creates a knowledge map based on the given text, identifying relationships between key concepts")
    fun createKnowledgeMap(text: String): KnowledgeMap

    @Description("Provides a spaced repetition schedule for learning the given set of concepts")
    fun generateSpacedRepetitionSchedule(concepts: List<String>, startDate: String, endDate: String): List<ScheduleItem>

    data class Flashcard(val concept: String, val explanation: String)

    data class KnowledgeMap(val nodes: List<Node>, val edges: List<Edge>)

    data class Node(val id: String, val label: String)

    data class Edge(val source: String, val target: String, val relationship: String)

    data class ScheduleItem(val concept: String, val date: String)
}
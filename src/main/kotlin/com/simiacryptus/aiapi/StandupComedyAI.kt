package com.simiacryptus.aiapi

import com.simiacryptus.openai.proxy.Description

interface StandupComedyAI {

    @Description("Generates a list of comedy routine models based on popular comedy structures")
    fun getRoutineModels(): List<RoutineModel>

    @Description("Generates a list of comedy artist themes/styles")
    fun getArtistStyles(): List<String>

    @Description("Generates a list of comedy topics/themes")
    fun getComedyTopics(): List<String>

    @Description("Creates a comedy routine based on the provided artist style, routine model, topic, and short description")
    fun generateRoutine(
        artistStyle: String,
        routineModel: RoutineModel,
        topic: String,
        shortDescription: String,
    ): ComedyRoutine

    @Description("Expands a comedy routine segment recursively by adding more humor content")
    fun expandSegment(segment: ComedySegment, depth: Int): ComedySegment

    @Description("Serializes the comedy routine into a final scripted format as plain text")
    fun serializeRoutinePlainText(comedyRoutine: ComedyRoutine): String

    @Description("Serializes the comedy routine into a final scripted format as HTML")
    fun serializeRoutineHTML(comedyRoutine: ComedyRoutine): String

    data class RoutineModel(val name: String? = null, val description: String? = null)

    data class ComedyRoutine(val title: String? = null, val segments: List<ComedySegment> = emptyList())

    data class ComedySegment(
        val id: String? = null,
        val type: String? = null, // e.g., "joke", "story", "one-liner", "callback"
        val content: String? = null,
        val structure: HumorStructure? = null,
        val subSegments: List<ComedySegment> = emptyList(),
    )

    data class HumorStructure(
        val setup: String? = null,
        val punchline: String? = null,
        val tags: List<String> = emptyList(),
        val callbacks: List<String> = emptyList(),
        val additionalDetails: Map<String, String> = emptyMap(),
    )
}
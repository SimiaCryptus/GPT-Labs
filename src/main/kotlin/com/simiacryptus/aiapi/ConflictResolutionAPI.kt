package com.simiacryptus.aiapi

import com.simiacryptus.openai.proxy.Description

interface ConflictResolutionAPI {

    @Description("Identifies conflicts in the given text and returns a list of Conflict data classes")
    fun detectConflicts(text: String): List<Conflict>

    @Description("Generates neutral responses to address conflicts identified in the given list of Conflict data classes")
    fun generateNeutralResponses(conflicts: List<Conflict>): List<Response>

    @Description("Suggests compromises or common ground between conflicting parties in the given list of Conflict data classes")
    fun suggestCompromises(conflicts: List<Conflict>): List<Compromise>

    @Description("Evaluates the tone of the given text and returns a ToneAnalysis data class")
    fun analyzeTone(text: String): ToneAnalysis

    @Description("Rewrites the given text to improve tone and reduce potential conflicts")
    fun rewriteForPositiveTone(text: String): String

    data class Conflict(
        @Description("Unique identifier for the conflict") val id: Int,
        @Description("Start index of the conflict in the text") val startIndex: Int,
        @Description("End index of the conflict in the text") val endIndex: Int,
        @Description("List of parties involved in the conflict") val parties: List<String>,
        @Description("Description of the issue causing the conflict") val issue: String
    )

    data class Response(
        @Description("Identifier of the conflict being addressed") val conflictId: Int,
        @Description("Neutral response text for the identified conflict") val responseText: String
    )

    data class Compromise(
        @Description("Identifier of the conflict being addressed") val conflictId: Int,
        @Description("Compromise or common ground text for the identified conflict") val compromiseText: String
    )

    data class ToneAnalysis(
        @Description("Tone category (e.g., positive, negative, neutral)") val category: String,
        @Description("Tone score (-1 to 1)") val score: Double
    )
}

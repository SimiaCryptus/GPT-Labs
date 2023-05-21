package com.simiacryptus.aiapi

import com.simiacryptus.openai.proxy.Description

interface CriticalReasoningAI {

    @Description("Identifies logical fallacies in the given text and returns a list of Fallacy data classes")
    fun detectLogicalFallacies(text: String): List<Fallacy>

    @Description("Generates a list of pros and cons for the given topic or idea")
    fun prosAndCons(topic: String): List<ProCon>

    @Description("Generates alternative solutions or ideas for the given problem statement")
    fun brainstorm(problemStatement: String, maxIdeas: Int): List<Idea>

    @Description("Analyzes the strengths, weaknesses, opportunities, and threats (SWOT) of the given subject")
    fun swotAnalysis(subject: String): SWOT

    @Description("Generates a list of questions to stimulate deeper thinking or inquiry about the given topic")
    fun socraticQuestions(topic: String, maxQuestions: Int): List<String>

    @Description("Evaluates the given argument for its soundness, validity, and coherence")
    fun evaluateArgument(argument: String): ArgumentAnalysis

    data class Fallacy(val name: String, val description: String)

    data class ProCon(val type: String, val description: String)

    data class Idea(val idea: String, val relevance: Double)

    data class SWOT(val strengths: List<String>, val weaknesses: List<String>, val opportunities: List<String>, val threats: List<String>)

    data class ArgumentAnalysis(val soundness: String, val validity: String, val coherence: String)
}
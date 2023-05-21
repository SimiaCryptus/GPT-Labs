package com.simiacryptus.aiapi

import com.simiacryptus.openai.proxy.Description

interface CreativeProblemSolvingAI {

    @Description("Generates analogies based on the given problem to provide alternative perspectives")
    fun generateAnalogies(problem: String, maxAnalogies: Int): List<String>

    @Description("Provides lateral thinking prompts to encourage unconventional problem-solving")
    fun lateralThinkingPrompts(problem: String, maxPrompts: Int): List<String>

    @Description("Suggests innovative solutions for the given problem")
    fun suggestInnovativeSolutions(problem: String, maxSolutions: Int): List<String>

    @Description("Generates questions to stimulate rethinking or reframing of the given problem")
    fun reframeProblemQuestions(problem: String, maxQuestions: Int): List<String>

    @Description("Combines different problem-solving techniques to generate a comprehensive solution")
    fun integratedSolution(problem: String, techniques: List<String>): String
}
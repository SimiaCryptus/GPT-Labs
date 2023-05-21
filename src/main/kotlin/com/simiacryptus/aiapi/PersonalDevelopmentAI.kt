package com.simiacryptus.aiapi

import com.simiacryptus.openai.proxy.Description

interface PersonalDevelopmentAI {

    @Description("Creates a list of personal goals based on the user's input")
    fun createGoals(userInput: String, maxGoals: Int): List<Goal>

    @Description("Suggests habits or activities to support the achievement of the specified goal")
    fun suggestHabits(goal: String, maxHabits: Int): List<Habit>

    @Description("Generates personalized advice for the user based on their input and specified topic")
    fun generateAdvice(userInput: String, topic: String): String

    @Description("Tracks the user's progress toward their goals and provides feedback")
    fun trackGoalProgress(goal: Goal, currentProgress: Double): GoalProgress

    @Description("Recommends resources (books, articles, videos, etc.) related to the user's goals or interests")
    fun recommendResources(userInput: String, maxResources: Int): List<Resource>

    data class Goal(val description: String, val category: String)

    data class Habit(val description: String, val relevance: Double)

    data class GoalProgress(val goal: Goal, val progress: Double, val feedback: String)

    data class Resource(val title: String, val type: String, val url: String)
}
package com.simiacryptus.aiapi

import com.simiacryptus.util.JsonUtil

/**
 * Inspired by AutoGPT-related internet discussions.
 */
class MoPedGPT {

    data class Thoughts(
        val text: String = "Description of the current thought",
        val reasoning: String = "Reasoning for the current thought",
        val plan: String = "Plan for the current thought",
        val criticism: String = "Criticism of the current plan",
        val speak: String = "Some text to output",
    )

    data class Command(
        val name: String = "search",
        val args: Map<String, String> = mapOf(
            "query" to "string"
        ),
    )

    data class State(
        val thought: Thoughts = Thoughts(),
        val command: Command = Command(),
    )

    data class Problem(
        val aiName: String = "Story-GPT",
        val aiDescription: String = "an AI designed to autonomously write stories",
        val goals: List<String> = listOf(
            "write a short story about flowers"
        ),
        val constraints: List<String> = listOf(
        ),
        val commands: List<Command> = listOf(
        ),
        val resources: List<String> = listOf(
        ),

        val performanceEvaluation: List<String> = listOf(
        ),
    ) {
        operator fun plus(other: Problem): Problem = Problem(
            aiName = aiName + other.aiName,
            aiDescription = aiDescription + other.aiDescription,
            goals = goals + other.goals,
            constraints = constraints + other.constraints,
            commands = commands + other.commands,
            resources = resources + other.resources,
            performanceEvaluation = performanceEvaluation + other.performanceEvaluation,
        )
    }

    val problemPredefs = Problem(
        aiName = "",
        aiDescription = "",
        goals = listOf(
        ),
        constraints = listOf(
            "4000 word limit for short term memory. Your short term memory is short, so immediately save important information to files.",
            "If you are unsure how you previously did something or want to recall past events, thinking about similar events will help you remember.",
            "No user assistance",
            "Exclusively use the commands listed in double quotes e.g. \"command name\"",
        ),
        commands = listOf(
            Command("search", mapOf("query" to "string")),
            Command("save", mapOf("file" to "string", "data" to "string")),
            Command("load", mapOf("file" to "string")),
            Command("output", mapOf("file" to "string", "data" to "string")),
        ),
        resources = listOf(
            "Internet access for searches and information gathering.",
            "Long Term memory management.",
            //"GPT-3.5 powered Agents for delegation of simple tasks.",
            "File output.",
        ),

        performanceEvaluation = listOf(
            "Continuously review and analyze your actions to ensure you are performing to the best of your abilities.",
            "Constructively self-criticize your big-picture behavior constantly.",
            "Reflect on past decisions and strategies to refine your approach.",
            "Every command has a cost, so be smart and efficient. Aim to complete tasks in the least number of steps.",
        ),
    )

    fun promptPrefix(problem: Problem) = """
        |You are ${problem.aiName}, ${problem.aiDescription}.
        |Your decisions must always be made independently without seeking user assistance. 
        |Play to your strengths as an LLM and pursue simple strategies with no legal complications.
        |
        |GOALS:
        |${problem.goals.joinToString("\n")}
        |
        |Constraints:
        |${problem.constraints.joinToString("\n")}
        |
        |Commands:
        |${problem.commands.joinToString("\n")}
        |
        |Resources:
        |${problem.resources.joinToString("\n")}
        |
        |Performance Evaluation:
        |${problem.performanceEvaluation.joinToString("\n")}
        |
        |You should only respond in JSON format as described below 
        |Response Format: 
        |${JsonUtil.toJson(State())}
    """.trimMargin()

}
package com.simiacryptus

import com.simiacryptus.openai.proxy.Description

interface JokeDemoTools {

    @Description("Generate some random jokes about a subject")
    fun writeJoke(
        subject: String,
        jokeCount: Int = 5,
    ): JokeResult

    data class JokeResult(
        val jokes: List<Joke> = listOf(),
    )

    data class Joke(
        @Description("The first part of the joke")
        val setup: String = "",
        @Description("The second and final of the joke")
        val punchline: String = "",
    )

    @Description("Summarize a text to a given number of tokens")
    fun summarize(text: String, maxTokens: Int = 100): String

    @Description("Given input text and a yes or no question, return the answer to the question")
    fun evaluateBool(text: String, question: String): Boolean

    @Description("Given input text and a question, return the answer to the question")
    fun evaluateText(text: String, question: String): String

    class Proxy(private val inner: JokeDemoTools) : JokeDemoTools {
        override fun writeJoke(subject: String, jokeCount: Int): JokeDemoTools.JokeResult {
            return inner.writeJoke(subject, jokeCount)
        }

        override fun summarize(text: String, maxTokens: Int): String {
            return inner.summarize(text, maxTokens)
        }

        override fun evaluateBool(text: String, question: String): Boolean {
            return inner.evaluateBool(text, question)
        }

        override fun evaluateText(text: String, question: String): String {
            return inner.evaluateText(text, question)
        }
    }
}
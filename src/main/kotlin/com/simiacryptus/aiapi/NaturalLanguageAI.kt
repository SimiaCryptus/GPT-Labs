package com.simiacryptus.aiapi

import com.simiacryptus.openai.proxy.Description

interface NaturalLanguageAI {

    @Description("Evaluates a concept based on a given question")
    fun test(concept: String, question: String): Boolean

    @Description("Returns sentiment category and score (-1 to 1) for the given text")
    fun sentimentAnalysis(text: String): Sentiment

    @Description("Identifies main topics and their relevance scores for the given text")
    fun topicModeling(text: String): List<Topic>

    @Description("Generates a summary of the given text with specified length")
    fun summarize(text: String, length: Int): String

    @Description("Extracts named entities and their types from the given text")
    fun extractEntities(text: String): List<Entity>

    @Description("Extracts relevant keywords and their importance scores from the given text")
    fun extractKeywords(text: String): List<Keyword>

    @Description("Answers a question based on the provided context and returns ranked answers with confidence scores")
    fun questionAnswering(question: String, context: String): List<Answer>

    @Description("Detects the language of the given text and returns ranked languages with confidence scores")
    fun languageDetection(text: String): List<Language>

    @Description("Translates the given text to the target language")
    fun translate(text: String, targetLanguage: String, sourceLanguage: String? = null): String

    @Description("Evaluates the given text for bias and returns a category and bias score (-1 to 1)")
    fun detectBias(text: String): Bias

    @Description("Measures semantic similarity between two given texts and returns a score between 0 and 1")
    fun evaluateSimilarity(text1: String, text2: String): Double

    @Description("Generates a coherent text based on the given prompt up to the specified maximum length")
    fun generateText(prompt: String, maxLength: Int): String

    data class Sentiment(val category: String, val score: Double)

    data class Topic(val name: String, val relevance: Double)

    data class Entity(val name: String, val type: String)

    data class Keyword(val keyword: String, val importance: Double)

    data class Answer(val answer: String, val confidence: Double)

    data class Language(val code: String, val confidence: Double)

    data class Bias(val category: String, val score: Double)
}


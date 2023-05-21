package com.simiacryptus.aiapi

import com.simiacryptus.openai.proxy.Description

interface EmotionalIntelligenceAI {

    @Description("Detects emotions in the given text and returns a list of Emotion data classes with scores")
    fun detectEmotions(text: String): List<Emotion>

    @Description("Performs emotion-based sentiment analysis and returns a Sentiment data class")
    fun emotionBasedSentimentAnalysis(text: String): Sentiment

    @Description("Generates an empathetic response to the given text")
    fun generateEmpatheticResponse(text: String, maxLength: Int): String

    @Description("Summarizes the given text while preserving emotional context")
    fun emotionAwareSummarization(text: String, length: Int): String

    data class Emotion(val name: String, val score: Double)

    data class Sentiment(val category: String, val score: Double)
}
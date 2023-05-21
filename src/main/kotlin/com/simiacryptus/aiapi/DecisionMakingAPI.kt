package com.simiacryptus.aiapi

import com.simiacryptus.openai.proxy.Description

interface DecisionMakingAPI {

    @Description("Performs multi-criteria decision analysis based on provided alternatives, criteria, and weights")
    fun multiCriteriaDecisionAnalysis(
        alternatives: List<String>,
        criteria: List<String>,
        weights: List<Double>,
    ): List<RankedAlternative>

    @Description("Assesses the risks associated with each option in the given list and returns a list of Risk data classes")
    fun riskAssessment(options: List<String>): List<Risk>

    @Description("Performs trade-off analysis between different options based on provided criteria and returns a list of TradeOff data classes")
    fun tradeOffAnalysis(options: List<String>, criteria: List<String>): List<TradeOff>

    @Description("Generates recommendations based on the user's preferences and the provided list of options")
    fun recommendationGeneration(userPreferences: Map<String, Any>, options: List<String>): List<RankedAlternative>

    data class RankedAlternative(val alternative: String? = null, val score: Double? = null)

    data class Risk(val option: String? = null, val riskLevel: String? = null, val riskScore: Double? = null)

    data class TradeOff(
        val option1: String? = null,
        val option2: String? = null,
        val criterion: String? = null,
        val winner: String? = null,
    )
}


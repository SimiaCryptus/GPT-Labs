package com.simiacryptus.aiapi

import com.simiacryptus.openai.proxy.Description

interface NarrativeGenerationAPI {

    @Description("Generates a character with specified traits, background, or role")
    fun createCharacter(traits: List<String>, background: String?, role: String?): Character

    @Description("Generates a plot outline based on user inputs or predefined templates")
    fun generatePlot(template: String?, input: Map<String, String>): Plot

    @Description("Creates a detailed description of a setting based on user inputs or predefined templates")
    fun describeSetting(template: String?, input: Map<String, String>): Setting

    @Description("Generates a dialogue between characters based on user inputs or predefined templates")
    fun generateDialogue(template: String?, characters: List<Character>, context: String): List<Dialogue>

    data class Character(
        val name: String? = null,
        val traits: List<String>? = null,
        val background: String? = null,
        val role: String? = null,
    )

    data class Plot(
        val events: List<String>? = null,
        val conflicts: List<String>? = null,
        val resolution: String? = null,
    )

    data class Setting(val name: String? = null, val description: String? = null, val atmosphere: String? = null)

    data class Dialogue(val character: Character? = null, val text: String? = null)
}


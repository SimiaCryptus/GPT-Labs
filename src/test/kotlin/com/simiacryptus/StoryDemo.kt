@file:Suppress("unused")

package com.simiacryptus

import com.simiacryptus.aiapi.NarrativeGenerationAPI
import com.simiacryptus.openai.OpenAIClient
import com.simiacryptus.openai.proxy.ChatProxy
import com.simiacryptus.skyenet.Heart
import com.simiacryptus.skyenet.body.SessionServerUtil.asJava
import com.simiacryptus.skyenet.heart.GroovyInterpreter
import com.simiacryptus.skyenet.util.AgentDemoBase
import org.junit.jupiter.api.Test
import java.util.Map

class StoryDemo : AgentDemoBase() {
    override fun heart(hands: Map<String, Object>): Heart = GroovyInterpreter(hands)

    override fun hands() = mapOf(
        "gpt" to ChatProxy(
            NarrativeGenerationAPI::class.java,
            api = OpenAIClient(apiKey),
        ).create() as Object,
    ).asJava

    @Test
    fun testCommand() {
        runCommand(
            """
                |
                |Tell me a story.
                |
                """.trimMargin().trim()
        )
    }

}


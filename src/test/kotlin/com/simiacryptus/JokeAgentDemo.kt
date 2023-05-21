@file:Suppress("unused")

package com.simiacryptus

import com.simiacryptus.openai.OpenAIClient
import com.simiacryptus.openai.proxy.ChatProxy
import com.simiacryptus.skyenet.body.SessionServerUtil.asJava
import com.simiacryptus.skyenet.util.AgentDemoBase
import com.simiacryptus.skyenet.util.SystemTools
import com.simiacryptus.skyenet.heart.GroovyInterpreter
import org.junit.jupiter.api.Test

class JokeAgentDemo : AgentDemoBase() {

    //    override fun heart(apiObjects: Map<String, Any>) = KotlinLocalInterpreter(apiObjects)
    override fun heart(apiObjects: java.util.Map<String, Object>) = GroovyInterpreter(apiObjects)
//    override fun heart(apiObjects: Map<String, Any>) = ScalaLocalInterpreter(apiObjects)

    override fun hands() = mapOf(
        "sys" to SystemTools() as Object,
        "gpt" to ChatProxy(
            JokeDemoTools::class.java,
            api = OpenAIClient(apiKey),
        ).create() as Object,
    ).asJava

    @Test
    fun testJokes1() {
        runCommand(
            """
                |
                |Make up 10 jokes about 5 different topics and tell them to me - but wait a few seconds before delivering the punchline.
                |
                """.trimMargin().trim()
        )
    }

    @Test
    fun testWebAgent() {
        launchWebAgent()
    }

    @Test
    fun testWikipedia() {
        runCommand(
            """
                |
                |Find an interesting article in Wikipedia and summarize it for me.
                |
                """.trimMargin().trim()
        )
    }

}


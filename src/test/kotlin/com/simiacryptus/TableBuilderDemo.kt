//@file:Suppress("unused")
//
//package com.simiacryptus
//
//import com.simiacryptus.openai.OpenAIClient
//import com.simiacryptus.openai.proxy.ChatProxy
//import com.simiacryptus.openai.proxy.Description
//import com.simiacryptus.skyenet.util.AgentDemoBase
//import com.simiacryptus.skyenet.Heart
//import com.simiacryptus.skyenet.body.SkyenetSessionServer
//import com.simiacryptus.skyenet.heart.GroovyInterpreter
//import com.simiacryptus.skyenet.webui.SkyenetSimpleSessionServer
//import org.junit.jupiter.api.Test
//import java.awt.Desktop
//import java.net.URI
//
//class TableBuilderDemo : AgentDemoBase() {
//    override fun heart(hands: java.util.Map<String, Object>): Heart = GroovyInterpreter(hands)
//
//    interface TableBuilderAI {
//        @Description("Use to obtain the contents of a cell in a table based on the table topic, row, and column.")
//        fun evaluateCell(tableTopic: String, row: String, col: String): String
//    }
//
//    class TableBuilderCallbacks(printBuffer: StringBuilder) : SkyenetSessionServer.MessageCallbacks(printBuffer) {
//        fun printTable(rows: List<List<String>>) {
//            // Print as HTML
//            print(
//                """
//                <table>
//                ${
//                    rows.joinToString("\n") { row ->
//                        "<tr>${
//                            row.joinToString("\n") { cell ->
//                                "<td>$cell</td>"
//                            }
//                        }</tr>"
//                    }
//                }
//                </table>
//                """.trimIndent()
//            )
//        }
//    }
//
//    override fun apiObjects() = mapOf(
//        "gpt" to ChatProxy(
//            TableBuilderAI::class.java,
//            api = OpenAIClient(apiKey),
//        ).create(),
//    )
//
//    @Test
//    fun testWebAgent() {
//        val port = 8080
//        val agentDemoBase = this
//        val server = object : SkyenetSimpleSessionServer() {
//            override fun apiObjects(): Map<String, Any> {
//                return agentDemoBase.apiObjects()
//            }
//
//            override fun apiObjects(printBuffer: StringBuilder) = apiObjects() + mapOf(
//                "sys" to TableBuilderCallbacks(printBuffer),
//            )
//
//            override val model: String? = "gpt-4-0314"
//
//            override fun heart(apiObjects: Map<String, Any>): Heart {
//                return agentDemoBase.heart(apiObjects)
//            }
//        }.start(port)
//        Desktop.getDesktop().browse(URI("http://localhost:$port/"))
//        server.join()
//    }
//
//}
//

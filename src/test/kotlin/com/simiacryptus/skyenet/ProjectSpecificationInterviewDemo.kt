package com.simiacryptus.skyenet

import com.simiacryptus.openai.proxy.Description
import com.simiacryptus.skyenet.body.SessionInterface
import com.simiacryptus.skyenet.body.SkyenetInterviewer
import com.simiacryptus.skyenet.body.WebSocketServer
import java.awt.Desktop
import java.net.URI

object ProjectSpecificationInterviewDemo {

    @Description("A fully specified software project")
    data class SoftwareProjectRequirements(
        @Description("The name of the software project")
        val projectName: String? = null,
        @Description("The programming language used for the project")
        val programmingLanguage: String? = null,
        @Description("The project's target environment(s)")
        val targetEnvironments: List<String>? = null,
        @Description("Various technologies (frameworks, libraries, design patterns) used in the project")
        val implementationTechnologies: List<String>? = null,
        @Description("A list of use cases for the project")
        val useCases: List<UseCase>? = null,
    ) {
        @Description("A specific use case for the software project")
        data class UseCase(
            @Description("The name of the use case")
            val useCaseName: String? = null,
            @Description("The subject (who) of the use case")
            val subject: String? = null,
            @Description("The goal (what) of the use case")
            val goal: String? = null,
            @Description("The motivation (why) of the use case, if applicable")
            val motivation: String? = null,
            @Description("Implementation notes (how) for the use case")
            val implementationNotes: String? = null,
            @Description("Requirements for the use case")
            val requirements: List<String>? = null,
            @Description("Risks associated with the use case, if applicable")
            val risks: List<String>? = null,
        )

        fun isFullySpecified(): Boolean {
            if (null == useCases) return false
            if (null == projectName) return false
            if (projectName.isBlank()) return false
            if (null == programmingLanguage) return false
            if (null == targetEnvironments) return false
            if (null == implementationTechnologies) return false
            if (useCases.isEmpty()) return false
            if (targetEnvironments.isEmpty()) return false
            if (implementationTechnologies.isEmpty()) return false
            return true
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val port = 8081
        val baseURL = "http://localhost:$port"
        val visiblePrompt = """
            |Hello! I am here to assist you in specifying a software project! 
            |I will guide you through a series of questions to gather the necessary information. 
            |Don't worry if you're not sure about any technical details; I'm here to help!
            |What would you like to build today?
            """.trimMargin()
        val applicationName = "Project Specification Interviewer"
        val server = SkyenetInterviewer(
            applicationName = applicationName,
            baseURL = baseURL,
            dataClass = SoftwareProjectRequirements::class.java,
            visiblePrompt = visiblePrompt,
            getHandler = ::postInterview,
            isFinished = SoftwareProjectRequirements::isFullySpecified
        ).start(port)
        Desktop.getDesktop().browse(URI(baseURL))
        server.join()
    }

    fun postInterview(requirements: SoftwareProjectRequirements) = object : SessionInterface {
        override fun removeSocket(socket: WebSocketServer.MessageWebSocket) {
            TODO("Not yet implemented")
        }

        override fun addSocket(socket: WebSocketServer.MessageWebSocket) {
            TODO("Not yet implemented")
        }

        override fun getReplay(): List<String> {
            TODO("Not yet implemented")
        }

        override fun onWebSocketText(socket: WebSocketServer.MessageWebSocket, message: String) {
            TODO("Not yet implemented")
        }

    }
}
package com.simiacryptus

import com.simiacryptus.openai.OpenAIClient
import com.simiacryptus.openai.proxy.ChatProxy
import com.simiacryptus.util.JsonUtil.toJson
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Test
import java.io.File

class ScratchTests {

    interface WritingAnalysis {

        fun analyze(text: String): Metadata

        fun write(data: TextData, metadata: Metadata): RewriteResult

        data class RewriteResult(
            val text: String = "",
        )

        data class Metadata(
            val language: String? = null,
            val tense: String? = null,
            val readingLevel: Double? = null,
            val sentiment: String? = null,
            val speechAct: String? = null,
            val formality: String? = null,
            val concreteness: String? = null,
            val register: String? = null,
            val polarity: Double? = null,
            val subjectivity: Double? = null,

            @com.simiacryptus.openai.proxy.Description("A map of emotions to their percentages in the text")
            val emotion: Map<String, Double>? = null,

            @com.simiacryptus.openai.proxy.Description("A map of pronoun types to their usage percentages in the text")
            val pronounUsage: Map<String, Double>? = null,

            @com.simiacryptus.openai.proxy.Description("A map of sentence structures to their percentages in the text")
            val sentenceStructure: Map<String, Double>? = null,

            val lexicalDiversity: Double? = null,
            val nounPhraseComplexity: Double? = null,
            val verbPhraseComplexity: Double? = null,
            val discourseMarkers: Double? = null,
            val cohesion: Double? = null,
            val coherence: Double? = null,

//            @com.simiacryptus.openai.proxy.Description("A map of entity types to lists of recognized entities in the text")
//            val namedEntities: Map<String, List<String>>? = null,

//            val syntacticPatterns: List<String>? = null,

            @com.simiacryptus.openai.proxy.Description("A map of function word types to their usage percentages in the text")
            val functionWordsUsage: Map<String, Double>? = null,

//            val collocations: List<String>? = null,
//            val idioms: List<String>? = null,
//            val phrasalVerbs: List<String>? = null,

            @com.simiacryptus.openai.proxy.Description("A map of figurative language types to lists of recognized examples in the text")
            val figurativeLanguage: Map<String, List<String>>? = null,

            val textType: String? = null,
        )

        fun parse(text: String): TextData

        data class TextData(
            val statements: List<Statement>? = null,
//            val events: List<Event>? = null,
            val hypotheses: List<Hypothesis>? = null,
//            val requests: List<Request>? = null,
//            val comparisons: List<Comparison>? = null,
            val descriptions: List<Description>? = null,
            val recommendations: List<Recommendation>? = null
        )
        data class Statement(
            val subject: String? = null,
            val verb: String? = null,
            val `object`: String? = null,
            val qualifiers: Map<String, String>? = null,
            val sentiment: String? = null,
        )

        data class Event(
            val eventName: String? = null,
            val time: String? = null,
            val location: String? = null,
            val participants: List<String>? = null,
            val outcome: String? = null,
        )

        data class Hypothesis(
            val proposition: String? = null,
            val supportingEvidence: List<String>? = null,
            val contradictingEvidence: List<String>? = null,
            val confidence: Double? = null,
        )

        data class Request(
            val action: String? = null,
            val target: String? = null,
            val condition: String? = null,
            val urgency: String? = null,
            val politeness: String? = null,
        )


        data class Comparison(
            val entityA: String? = null,
            val entityB: String? = null,
            val criteria: Map<String, String>? = null,
            val similarities: List<String>? = null,
            val differences: List<String>? = null,
        )

        data class Description(
            val entity: String? = null,
            val attributes: Map<String, String>? = null,
            val context: String? = null,
            val sentiment: String? = null,
        )

        data class Recommendation(
            val action: String? = null,
            val rationale: String? = null,
            val benefits: List<String>? = null,
            val drawbacks: List<String>? = null,
            val alternatives: List<String>? = null,
            val confidence: Double? = null,
        )
    }

    private val keyFile = File("C:\\Users\\andre\\code\\all-projects\\openai.key")
    private val apiKey = keyFile.readText().trim()

    @Test
    fun testWritingAnalysis() {
        val chatProxy = ChatProxy(WritingAnalysis::class.java, api = OpenAIClient(apiKey))
        @Language("Markdown") val inputText = """
                    |The NLP landscape has recently been revolutionized by
language models (Peters et al., 2018; Devlin et al., 2019;
Brown et al., 2020, inter alia). Scaling up the size of language models has been shown to confer a range of benefits,
such as improved performance and sample efficiency (Kaplan et al., 2020; Brown et al., 2020, inter alia). However,
scaling up model size alone has not proved sufficient for
achieving high performance on challenging tasks such as
arithmetic, commonsense, and symbolic reasoning (Rae
et al., 2021).
This work explores how the reasoning ability of large
language models can be unlocked by a simple method
motivated by two ideas. First, techniques for arithmetic
reasoning can benefit from generating natural language
rationales that lead to the final answer. Prior work has
given models the ability to generate natural language intermediate steps by training from scratch (Ling et al., 2017)
or finetuning a pretrained model (Cobbe et al., 2021), in
addition to neuro-symbolic methods that use formal languages instead of natural language (Roy and Roth, 2015;
Chiang and Chen, 2019; Amini et al., 2019; Chen et al.,
2019). Second, large language models offer the exciting
prospect of in-context few-shot learning via prompting. That is, instead of finetuning a separate
language model checkpoint for each new task, one can simply “prompt” the model with a few
input–output exemplars demonstrating the task. Remarkably, this has been successful for a range of
simple question-answering tasks (Brown et al., 2020).
Both of the above ideas, however, have key limitations. For rationale-augmented training and
finetuning methods, it is costly to create a large set of high quality rationales, which is much more
complicated than simple input–output pairs used in normal machine learning. For the traditional fewshot prompting method used in Brown et al. (2020), it works poorly on tasks that require reasoning
abilities, and often does not improve substantially with increasing language model scale (Rae et al.,
2021). In this paper, we combine the strengths of these two ideas in a way that avoids their limitations.
Specifically, we explore the ability of language models to perform few-shot prompting for reasoning
tasks, given a prompt that consists of triples: hinput, chain of thought, outputi. A chain of thought is
a series of intermediate natural language reasoning steps that lead to the final output, and we refer to
this approach as chain-of-thought prompting. An example prompt is shown in Figure 1.
                    |
                    |""".trimMargin().trim()
        val writingAnalysis = chatProxy.create()
        val metadata = writingAnalysis.analyze(inputText)
        println(toJson(metadata))
        val textData = writingAnalysis.parse(inputText)
        println(toJson(textData))
        println(wordWrap(writingAnalysis.write(textData, metadata).text))
//        println(
//            wordWrap(
//                writingAnalysis.rewrite(
//                    inputText, WritingAnalysis.Metadata(
//                        readingLevel = 4.0,
//                        sentiment = "positive",
//                        textType = "narrative",
//                    )
//                ).text
//            )
//        )
        println(toJson(chatProxy.metrics))
    }

    companion object {
        fun wordWrap(text: String, maxLength: Int = 120): String {
            val wrappedText = StringBuilder()
            val words = text.replace("\\s+", " ").split(" ")
            var lineLength = 0

            for (word in words) {
                if (lineLength + word.length + 1 > maxLength) {
                    wrappedText.append("\n")
                    lineLength = 0
                } else if (lineLength > 0) {
                    wrappedText.append(" ")
                    lineLength++
                }
                wrappedText.append(word)
                lineLength += word.length
            }

            return wrappedText.toString()
        }
    }


}
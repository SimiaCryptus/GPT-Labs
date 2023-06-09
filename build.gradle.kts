import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.api.tasks.testing.logging.TestLogEvent
import java.net.URI

fun properties(key: String) = project.findProperty(key).toString()
group = properties("libraryGroup")
version = properties("libraryVersion")


plugins {
    java
    `java-library`
    id("org.jetbrains.kotlin.jvm") version "1.7.21"
    `maven-publish`
    id("signing")
    id("com.github.johnrengelman.shadow") version "7.1.2"
    `scala`
//    kotlin("jvm") version "1.8.20"
}

repositories {
    mavenCentral() {
        metadataSources {
            mavenPom()
            artifact()
        }
    }
}

kotlin {
    //jvmToolchain(17)
    jvmToolchain(11)
}

val jetty_version = "11.0.15"
val kotlin_version = "1.7.21"
val scala_version = "2.13.8"
val skyenet_version = "1.0.8"
dependencies {

    implementation("com.simiacryptus:joe-penai:1.0.10")

    implementation("com.simiacryptus.skyenet:util:$skyenet_version")
    implementation("com.simiacryptus.skyenet:core:$skyenet_version")
    implementation("com.simiacryptus.skyenet:groovy:$skyenet_version")
    implementation("com.simiacryptus.skyenet:webui:$skyenet_version")

    implementation("org.eclipse.jetty:jetty-server:$jetty_version")
    implementation("org.eclipse.jetty:jetty-servlet:$jetty_version")
    implementation("org.eclipse.jetty:jetty-annotations:$jetty_version")
    implementation("org.eclipse.jetty.websocket:websocket-jetty-server:$jetty_version")
    implementation("org.eclipse.jetty.websocket:websocket-jetty-client:$jetty_version")
    implementation("org.eclipse.jetty.websocket:websocket-servlet:$jetty_version")

    implementation("org.jetbrains.kotlin:kotlin-script-util:$kotlin_version")
    implementation("org.jetbrains.kotlin:kotlin-compiler-embeddable:$kotlin_version")
    implementation("org.jetbrains.kotlin:kotlin-scripting-compiler-embeddable:$kotlin_version")
    implementation("org.jetbrains.kotlin:kotlin-script-runtime:$kotlin_version")
    implementation("org.jetbrains.kotlin:kotlin-scripting-jvm-host:$kotlin_version")

    implementation("com.google.api-client:google-api-client:1.35.2")
    implementation("com.google.oauth-client:google-oauth-client-jetty:1.34.1")
    implementation("com.google.apis:google-api-services-oauth2:v2-rev157-1.25.0")

    implementation("com.amazonaws:aws-java-sdk:1.12.454")

    implementation("org.scala-lang:scala-library:$scala_version")
    implementation("org.scala-lang:scala-compiler:$scala_version")
    implementation("org.scala-lang:scala-reflect:$scala_version")

    // Spark dependencies
    implementation("org.apache.spark:spark-core_2.13:3.4.0")
    implementation("org.apache.spark:spark-sql_2.13:3.4.0")

    implementation("org.apache.httpcomponents:httpclient:4.5.14")
    implementation("org.apache.httpcomponents:httpmime:4.5.14")

    implementation("org.graalvm.js:js:22.3.1")
    implementation("org.graalvm.js:js-scriptengine:22.3.1")

    implementation("org.slf4j:slf4j-api:2.0.5")
    implementation("org.slf4j:slf4j-simple:2.0.5")
    implementation("commons-io:commons-io:2.11.0")

    implementation(kotlin("stdlib"))
    testImplementation(kotlin("script-runtime"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")
}


tasks {
    compileKotlin {
        kotlinOptions {
            javaParameters = true
        }
    }
    compileTestKotlin {
        kotlinOptions {
            javaParameters = true
        }
    }
    test {
        useJUnitPlatform()
        systemProperty("surefire.useManifestOnlyJar", "false")
        testLogging {
            events(TestLogEvent.PASSED, TestLogEvent.SKIPPED, TestLogEvent.FAILED)
            exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
        }
    }
    wrapper {
        gradleVersion = properties("gradleVersion")
    }

}

tasks.withType(ShadowJar::class.java).configureEach {
    archiveClassifier.set("")
    mergeServiceFiles()
    append("META-INF/kotlin_module")

    exclude("**/META-INF/*.SF")
    exclude("**/META-INF/*.DSA")
    exclude("**/META-INF/*.RSA")
    exclude("**/META-INF/*.MF")
    exclude("META-INF/versions/9/module-info.class")

    include("com/simiacryptus/**", "com/intellij/uiDesigner/core/**")
    from(zipTree("lib/ui.jar"))
}

val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
    from(tasks.javadoc)
}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = "gpt-demos"
            artifact(tasks.shadowJar.get()) {
                classifier = null
            }
            artifact(javadocJar)
            artifact(sourcesJar)
            versionMapping {
                usage("java-api") {
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult()
                }
            }
            pom {
                name.set("SkyeNet")
                description.set("A very helpful puppy")
                url.set("https://github.com/SimiaCryptus/SkyeNet")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("acharneski")
                        name.set("Andrew Charneski")
                        email.set("acharneski@gmail.com")
                    }
                }
                scm {
                    connection.set("scm:git:git://git@github.com/SimiaCryptus/SkyeNet.git")
                    developerConnection.set("scm:git:ssh://git@github.com/SimiaCryptus/SkyeNet.git")
                    url.set("https://github.com/SimiaCryptus/SkyeNet")
                }
            }
        }
    }
    repositories {
        maven {
            val releasesRepoUrl = "https://oss.sonatype.org/service/local/staging/deploy/maven2/"
            val snapshotsRepoUrl = "https://oss.sonatype.org/mask/repositories/snapshots"
            url = URI(if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl)
            credentials {
                username = System.getenv("OSSRH_USERNAME") ?: System.getProperty("ossrhUsername")
                        ?: properties("ossrhUsername")
                password = System.getenv("OSSRH_PASSWORD") ?: System.getProperty("ossrhPassword")
                        ?: properties("ossrhPassword")
            }
        }
    }
    if (System.getenv("GPG_PRIVATE_KEY") != null && System.getenv("GPG_PASSPHRASE") != null) afterEvaluate {
        signing {
            sign(publications["mavenJava"])
        }
    }
}

if (System.getenv("GPG_PRIVATE_KEY") != null && System.getenv("GPG_PASSPHRASE") != null) {
    apply<SigningPlugin>()
    configure<SigningExtension> {
        useInMemoryPgpKeys(System.getenv("GPG_PRIVATE_KEY"), System.getenv("GPG_PASSPHRASE"))
        sign(configurations.archives.get())
    }
}

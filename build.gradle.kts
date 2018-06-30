import org.jetbrains.dokka.gradle.DokkaPlugin
import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.load.kotlin.signatures

// loads maven credentials
file("local.properties").loadProps()

val mavenUser = ext.properties["mavenUser"] as String
val mavenPassword = ext.properties["mavenPassword"] as String

plugins {
    kotlin("jvm") version "1.2.50"
    id("org.jetbrains.dokka") version "0.9.16"
    id("maven-publish")
}

group = "kfoenix"
version = "0.1.2-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    compile(kotlin("stdlib-jdk8"))
    compile("com.jfoenix:jfoenix:8.0.4")
    compile("no.tornado:tornadofx:1.7.16")
}


with(tasks) {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }

    withType<DokkaTask> {
        outputFormat = "html"
        outputDirectory = "$buildDir/javadoc"
    }
}

val sourcesJar by tasks.creating(Jar::class) {
    classifier = "sources"
    from(java.sourceSets["main"].allSource)
}

publishing {
    (publications) {
        "mavenJava"(MavenPublication::class) {
            groupId = "com.github.bkenn"
            artifactId = "kfoenix"
            version = "${project.version}"

            from(components["java"])
            artifact(sourcesJar)

            pom {
                name.set("KFoenix")
                description.set("A TornadoFX dsl for JFoenix")

                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }

                developers {
                    developer {
                        id.set("bkenn")
                        name.set("Brian Kennedy")
                        email.set("briankennedy392@gmail.com")
                    }
                }

                scm {
                    connection.set("scm:git:git://github.com/bkenn/KFoenix.git")
                    developerConnection.set("scm:git:ssh://github.com/bkenn/KFoenix.git")
                }

                distributionManagement {
                    repositories {
                        maven {
                            credentials {
                                username = mavenUser
                                password = mavenPassword
                            }

                            val snapshotUrl = "https://oss.sonatype.org/content/repositories/snapshots"
                            val releaseUrl = "https://oss.sonatype.org/service/local/staging/deploy/maven2"

                            if ("${project.version}".endsWith("-SNAPSHOT")) {
                                setUrl(snapshotUrl)
                            } else {
                                setUrl(releaseUrl)
                            }
                        }
                    }
                }
            }
        }
    }
}


fun File.loadProps() {
    readLines().forEach { line ->
        val props = line.split("=")
        ext.set(props[0], props[1])
    }
}
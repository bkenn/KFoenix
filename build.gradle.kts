import org.jetbrains.dokka.gradle.DokkaPlugin
import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.load.kotlin.signatures

plugins {
    kotlin("jvm") version "1.2.70"
    id("org.jetbrains.dokka") version "0.9.17"
    id("maven-publish")
    id("signing")
}

group = "kfoenix"
version = "0.1.3"

repositories {
    mavenCentral()
}

dependencies {
    compile(kotlin("stdlib-jdk8"))
    compile("com.jfoenix:jfoenix:8.0.7")
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
    from(sourceSets["main"].java.srcDirs)
}

signing {
    sign(configurations.archives)
}

publishing {
    publications {
        register("mavenJava", MavenPublication::class) {
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
                                // place credentials in ~/.gradle/gradle.properties
                                username = ext.properties["mavenUser"] as String?
                                password = ext.properties["mavenPassword"] as String?
                            }

                            val snapshotUrl = "https://oss.sonatype.org/content/repositories/snapshots"
                            val releaseUrl = "https://oss.sonatype.org/service/local/staging/deploy/maven2"

                            if (version.endsWith("-SNAPSHOT")) {
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

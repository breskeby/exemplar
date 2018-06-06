plugins {
    `build-scan`
}

subprojects {
    apply(plugin = "java-library")
    apply(plugin = "maven-publish")

    group = "org.gradle"
    version = "0.1.0"

    repositories {
        maven {
            url = uri("https://repo.gradle.org/gradle/libs-releases")
        }
        jcenter()
    }

    configure<PublishingExtension> {
        publications.create<MavenPublication>("mavenJava") {
            artifactId = project.name
            from(components["java"])
        }

        repositories {
            maven(url = "https://repo.gradle.org/gradle/ext-releases-local") {
                authentication {
                    credentials {
                        fun stringProperty(name: String): String? = project.findProperty(name) as? String

                        username = stringProperty("artifactory_user") ?: "nouser"
                        password = stringProperty("artifactory_password") ?: "nopass"
                    }
                }
            }
        }
    }
}

buildScan {
    setTermsOfServiceUrl("https://gradle.com/terms-of-service")
    setTermsOfServiceAgree("yes")
    if (!System.getenv("CI").isNullOrEmpty()) {
        publishAlways()
        tag("CI")
    }
}

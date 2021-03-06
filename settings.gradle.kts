plugins {
    id("com.gradle.enterprise").version("3.1.1")
}

rootProject.name = "exemplar"

include("sample-discovery")
include("sample-check")

gradleEnterprise {
    buildScan {
        setTermsOfServiceUrl("https://gradle.com/terms-of-service")
        setTermsOfServiceAgree("yes")
        if (!System.getenv("CI").isNullOrEmpty()) {
            publishAlways()
            tag("CI")
        }
    }
}
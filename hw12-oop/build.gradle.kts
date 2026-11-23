plugins {
    id("java-ext")
}


dependencies {
    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly(libs.junit.launcher)

    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.junit)
}

tasks.test {
    useJUnitPlatform()
}

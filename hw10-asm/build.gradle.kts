plugins {
    id("java-ext")
}


dependencies {
    implementation(libs.asm.core)
    implementation(libs.asm.tree)
    implementation(libs.asm.util)
}
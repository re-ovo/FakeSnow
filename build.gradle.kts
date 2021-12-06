import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.apache.tools.ant.filters.ReplaceTokens
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.github.johnrengelman.shadow") version "6.1.0"
    kotlin("jvm") version "1.5.31"
}

group = "me.rerere"
version = "1.3"

repositories {
    mavenCentral()
    maven(url = "https://hub.spigotmc.org/nexus/content/groups/public/")
    maven(url = "https://repo.dmulloy2.net/repository/public/")
    maven(url = "https://repo.codemc.io/repository/nms/")
    maven(url = "https://repo.extendedclip.com/content/repositories/placeholderapi/")
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.18-R0.1-SNAPSHOT")
    compileOnly("org.projectlombok:lombok:1.18.22")
    annotationProcessor("org.projectlombok:lombok:1.18.22")
    compileOnly(group = "com.comphenix.protocol", name = "ProtocolLib", version = "4.7.0")
    compileOnly(fileTree(baseDir = "libs"))
}

tasks.apply {
    withType<JavaCompile> {
        options.encoding = "UTF-8"
    }
    withType<KotlinCompile>{
        kotlinOptions.jvmTarget = "1.8"
    }

    val fatJar by named("shadowJar", ShadowJar::class) {
        dependencies {
            exclude(dependency("org.slf4j:.*"))
        }
        minimize()
        relocate("kotlin", "me.rerere.fakesnow.thirdparty.kotlin")
        relocate("org.jetbrains", "me.rerere.fakesnow.thirdparty.org.jetbrains")
        relocate("org.intellij", "me.rerere.fakesnow.thirdparty.org.intellij")
        relocate("okhttp","me.rerere.fakesnow.thirdparty.okhttp")
        relocate("okio","me.rerere.fakesnow.thirdparty.okio")
    }

    artifacts {
        add("archives", fatJar)
    }

    processResources {
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
        from("src/main/resources") {
            include("**/*.yml")
            filter<ReplaceTokens>(
                "tokens" to mapOf(
                    "VERSION" to project.version
                )
            )
        }
        filesMatching("application.properties") {
            expand(project.properties)
        }
    }

    test {
        useJUnit()
    }

    build {
        dependsOn(shadowJar)
    }
}
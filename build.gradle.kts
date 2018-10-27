import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.liquibase.gradle.Activity

plugins {
    val kotlinVersion = "1.2.71"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion

    id ("nu.studer.jooq") version "3.0.2"
    id("org.liquibase.gradle") version "2.0.1"
    id("org.springframework.boot") version "2.0.6.RELEASE"
    id("io.spring.dependency-management") version "1.0.5.RELEASE"
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = listOf("-Xjsr305=strict")
    }
}

val test by tasks.getting(Test::class) {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

liquibase {
    activities(
            closureOf<NamedDomainObjectContainer<Activity>> {
                mapOf(
                        "main" to closureOf<Activity> {
                            mapOf(
                                    "changeLogFile" to "src/main/resources/db/changelog.xml",
                                    "url" to "jdbc:h2:mem"
                            )
                        }
                )
            }
    )
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("com.h2database:h2")
    testCompile("org.springframework.boot:spring-boot-starter-test") {
        exclude(module = "junit")
    }
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")

    jooqRuntime("com.h2database:h2")

    liquibaseRuntime("org.liquibase:liquibase-core:3.6.1")
    liquibaseRuntime("com.h2database:h2")
    liquibaseRuntime("org.liquibase:liquibase-groovy-dsl:2.0.1")
}

repositories {
    mavenCentral()
}
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

val spec = "$rootDir/docs/openapi/api.yml"
val generatedSourcesDir = "$buildDir/generated/openapi"

plugins {
	id("org.springframework.boot") version "2.4.5"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	id ("org.openapi.generator") version "5.0.0"
	kotlin("jvm") version "1.4.32"
	kotlin("plugin.spring") version "1.4.32"
	kotlin("plugin.jpa") version "1.4.32"
	kotlin("kapt") version "1.3.61"
}


group = "com.bsfdv"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	jcenter()
}

dependencies {
	implementation("org.mapstruct:mapstruct:1.4.2.Final")
	kapt("org.mapstruct:mapstruct-processor:1.4.2.Final")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation ("org.springframework.boot:spring-boot-starter-validation")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.flywaydb:flyway-core")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	api("io.springfox:springfox-swagger2:2.8.0")
	api("io.springfox:springfox-swagger-ui:2.8.0")
	api("org.openapitools:jackson-databind-nullable:0.1.0")
	compileOnly("org.projectlombok:lombok")
	runtimeOnly("com.h2database:h2")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}
openApiGenerate {
	generatorName.set("spring")

	inputSpec.set(spec)
	outputDir.set(generatedSourcesDir)

	apiPackage.set("com.bsfdv.bankdemo.api")
	invokerPackage.set("com.bsfdv.bankdemo.invoker")
	modelPackage.set("com.bsfdv.bankdemo.model")

	configOptions.set(mapOf(
		"dateLibrary" to "java8",
		"serializationLibrary" to "jackson",
		"library" to "spring-boot",
		"useBeanValidation" to "true",
		"interfaceOnly" to "true",
		"serializableModel" to "true",
		"useTags" to "true"
	))

}
sourceSets {
	getByName("main") {
		java {
			srcDir("$generatedSourcesDir/src/main/java")
		}
	}
}
tasks {
	val openApiGenerate by getting

	val compileJava by getting {
		dependsOn(openApiGenerate)
	}
}
tasks.withType<KotlinCompile> {
	dependsOn("openApiGenerate")
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}




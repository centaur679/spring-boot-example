plugins {
	com.livk.service
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation(project(":spring-boot-extension-starters:oss-spring-boot-starter"))
	implementation("io.minio:minio")

	testImplementation("org.testcontainers:minio")
	testImplementation(project(":spring-testcontainers-support"))
}

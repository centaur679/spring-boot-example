plugins {
	com.livk.service
}

dependencies {
	implementation(project(":spring-boot-example:spring-redis:redis-commons"))
	implementation("org.springframework.boot:spring-boot-starter-web")
}

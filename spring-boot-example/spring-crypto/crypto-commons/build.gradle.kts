plugins {
	com.livk.common
}

dependencies {
	api(project(":spring-extension-commons"))
	compileProcessor(project(":spring-auto-service"))
	api("org.springframework:spring-web")
	optional("org.springframework:spring-webmvc")
	optional("org.springframework:spring-webflux")
	api("com.fasterxml.jackson.core:jackson-databind")
	api("org.springframework.boot:spring-boot-autoconfigure")
	api("org.springframework.boot:spring-boot-starter-logging")
}

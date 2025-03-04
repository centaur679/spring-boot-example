plugins {
	com.livk.common
}

dependencies {
	api(project(":spring-extension-commons"))
	api("org.springframework.boot:spring-boot-autoconfigure")
	optional("org.springframework.boot:spring-boot-starter-security")
	optional("org.springframework.boot:spring-boot-starter-logging")
	optional("jakarta.servlet:jakarta.servlet-api")
	optional("org.springframework.security:spring-security-oauth2-authorization-server")
	compileProcessor(project(":spring-auto-service"))
}

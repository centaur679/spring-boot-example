plugins {
	com.livk.common
}

dependencies {
	api(project(":spring-extension-commons"))
	api("org.springframework.boot:spring-boot-starter-rsocket")
	api("org.springframework.boot:spring-boot-starter-web")
}

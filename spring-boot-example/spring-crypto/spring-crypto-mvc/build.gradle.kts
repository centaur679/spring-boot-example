plugins {
	com.livk.service
}

dependencies {
	implementation(project(":spring-boot-example:spring-crypto:crypto-commons"))
	implementation("org.springframework.boot:spring-boot-starter-web")
}

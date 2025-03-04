plugins {
	com.livk.service
}

dependencies {
	implementation(project(":spring-boot-extension-starters:redis-ops-boot-starter"))
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("de.codecentric:spring-boot-admin-starter-server")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
	implementation(platform(libs.spring.boot.admin.dependencies))
}

rootProject.name = "msa-architecture-skeleton"


val infraModules : Array<String> = arrayOf(
    "discovery-service",
    "config-service",
    "gateway-service"
)

val serviceModules : Array<String> = arrayOf(
    "core-service:api",
    "core-service:domain",
    "core-service:storage",
    "core-service:common"
)


include(
    *infraModules,
    *serviceModules
)
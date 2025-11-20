rootProject.name = "msa-architecture-skeleton"


val infraModules : Array<String> = arrayOf(
    "discovery-service",
    "config-service",
    "gateway-service"
)


include(
    *infraModules
)
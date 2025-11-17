rootProject.name = "msa-architecture-skeleton"


val infraModules : Array<String> = arrayOf(
    "discovery-service"
)


include(
    *infraModules
)
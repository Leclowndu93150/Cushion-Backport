plugins {
    id("dev.prism")
}

group = "com.leclowndu93150"
version = "1.0.1"

prism {
    curseMaven()

    metadata {
        modId = "cushionbackport"
        name = "Cushion Backport"
        description = "A Minecraft mod."
        license = "MIT"
    }

    publishing {
        type = STABLE
        changelog = "fixed recipes in 1.21.1 + added 26.2 support"

        curseforge {
            accessToken = providers.environmentVariable("CURSEFORGE_TOKEN")
            projectId = "1601898"
        }

        modrinth {
            accessToken = providers.environmentVariable("MODRINTH_TOKEN")
            projectId = "DBGOO7gw"
        }
    }

    version("1.20.1") {
        javaVersion = 17
        minecraftVersions("1.20.1")
        fabric {
            loaderVersion = "0.16.10"
            fabricApi("0.92.6+1.20.1")
            dependencies {
                modImplementation("curse.maven:jei-238222:8367519")
            }
        }
        forge {
            loaderVersion = "47.4.0"
            dependencies {
                modImplementation("curse.maven:jei-238222:8367520")
            }
        }
    }

    version("26.1.2") {
        javaVersion = 25
        minecraftVersions("26.1.2")
        fabric {
            loaderVersion = "0.19.3"
            fabricApi("0.154.2+26.1.2")
            dependencies {
                modImplementation("curse.maven:jei-238222:8387492")
            }
        }
        neoforge {
            loaderVersion = "26.1.2.78"
            loaderVersionRange = "[4,)"
            dependencies {
                modImplementation("curse.maven:jei-238222:8387494")
            }
        }
    }

    version("26.2") {
        javaVersion = 25
        minecraftVersions("26.2")
        fabric {
            loaderVersion = "0.19.3"
            fabricApi("0.154.2+26.2")
            dependencies {
                modImplementation("curse.maven:jei-238222:8392159")
            }
        }
        neoforge {
            loaderVersion = "26.2.0.8-beta"
            loaderVersionRange = "[4,)"
            dependencies {
                modImplementation("curse.maven:jei-238222:8392160")
            }
        }
    }

    version("1.21.1") {
        javaVersion = 21
        minecraftVersions("1.21.1")
        fabric {
            loaderVersion = "0.19.3"
            fabricApi("0.116.13+1.21.1")
            dependencies {
                modImplementation("curse.maven:jei-238222:8376020")
            }
        }
        neoforge {
            loaderVersion = "21.1.235"
            loaderVersionRange = "[4,)"
            dependencies {
                modImplementation("curse.maven:jei-238222:8376022")
            }
        }
    }

    version("1.19.2") {
        javaVersion = 17
        minecraftVersions("1.19.2")
        fabric {
            loaderVersion = "0.16.10"
            fabricApi("0.77.0+1.19.2")
            dependencies {
                modImplementation("curse.maven:jei-238222:5846857")
            }
        }
        forge {
            loaderVersion = "43.5.0"
            dependencies {
                modImplementation("curse.maven:jei-238222:5846858")
            }
        }
    }

    version("1.18.2") {
        javaVersion = 17
        minecraftVersions("1.18.2")
        fabric {
            loaderVersion = "0.16.10"
            fabricApi("0.77.0+1.18.2")
            dependencies {
                modImplementation("curse.maven:jei-238222:5846863")
            }
        }
        forge {
            loaderVersion = "40.3.0"
            dependencies {
                modImplementation("curse.maven:jei-238222:5846864")
            }
        }
    }

}

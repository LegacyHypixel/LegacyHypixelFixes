# Fabric Example Mod

- [Quick start guide](#quick-start-guide)
  - [Introduction to the folder structure](#introduction-to-the-folder-structure)
  - [Creating your mod](#creating-your-mod)
  - [Useful gradle commands](#useful-gradle-commands)
  - [More info](#more-info)
- [Useful gradle plugins](#useful-gradle-plugins)
  - [Automated Modrinth publication](#automated-modrinth-publication)
  - [Adding mod dependencies](#adding-mod-dependencies)
  - [Improved source decompilation](#improved-source-decompilation)
- [License](#license)


## Quick start guide

### Introduction to the folder structure

**Build files:**

| File                | Description                                              |
| ------------------- | -------------------------------------------------------- |
| `build.gradle`      | Configures the compilation process.                      |
| `gradle.properties` | Contains properties for Minecraft, fabric, and your mod. |
| `settings.gradle`   | Configures the plugin repositories.                      |

**Fabric files:**

These files are located at `src/main/resources`.

| File                    | Description                              | Additional information                                                                                                |
| ----------------------- | ---------------------------------------- | --------------------------------------------------------------------------------------------------------------------- |
| `fabric.mod.json`       | Contains metadata about your mod.        | [wiki:fabric_mod_json_spec](https://fabricmc.net/wiki/documentation:fabric_mod_json_spec)                             |
| `modid.mixins.json`     | Contains a list of all your mixin files. | [wiki:mixin_registration](https://fabricmc.net/wiki/tutorial:mixin_registration)                                      |
| `assets/modid/icon.png` | The icon of your mod.                    | [wiki:fabric_mod_json_spec#icon](https://fabricmc.net/wiki/documentation:fabric_mod_json_spec?s[]=icon#custom_fields) |


### Creating your mod

First of you must replace all occurrences of `modid` with the id of your mod.

If your mod doesn't use mixins you can safely remove the mixin entry in your `fabric.mod.json` as well as delete any `*.mixin.json` files.

This template has the legacy fabric api included in it's build script, more info about the api can be found at it's [github repo](https://github.com/Legacy-Fabric/fabric).
If you know what you are doing you can also safely remove the api from the build script as it isn't required.

### Useful gradle commands

```sh
# Compile your mod
./gradlew build

# Remove old build files
./gradlew clean

# Generate Minecraft sources
./gradlew genSources

# Launch a modded Minecraft client
./gradlew runClient

# Kill gradle if it's doing stupid things
./gradlew --stop
```

### More info

For more detailed setup instructions please see the [fabric wiki](https://fabricmc.net/wiki/tutorial:setup).

If you are new to fabric or Minecraft modding in general then [this wiki page](https://fabricmc.net/wiki/tutorial:primer) may help you.


## Useful gradle plugins

### Automated Modrinth publication

The [minotaur](https://github.com/modrinth/minotaur) gradle plugin is a tool for deploying build artifacts to Modrinth.

For more info see https://github.com/modrinth/minotaur#readme

<details>
    <summary>Usage example</summary>

```groovy
// build.gradle

plugins {
    id "com.modrinth.minotaur" version "2.+"
}

modrinth {
    token = System.getenv("MODRINTH_TOKEN")

    projectId = "my-mod"

    versionNumber = "${project.mod_version}"
    versionName = "my-mod v${project.mod_version}"
    versionType = "release"

    uploadFile = remapJar
    gameVersions = ["1.8.9"]
    loaders = ["fabric"]
    dependencies {
        required.project "legacy-fabric-api"
    }
}
```

To publish to Modrinth run:
```sh
./gradlew build modrinth
```

</details>

### Adding mod dependencies

In order to implement the api of a mod, for example to add your mod settings to [Mod Menu](https://modrinth.com/mod/legacy-mod-menu), it is required that you add that mod to your build script as a dependency with `modImplementation`. To simplify this setup Modrinth allows you to load mods directly from there maven.

More info about this can be found in the [Modrinth docs](https://docs.modrinth.com/docs/tutorials/maven/). \
For more info about loom dependencies see the [fabric wiki](https://fabricmc.net/wiki/documentation:fabric_loom?s[]=dependencies#options).

<details>
    <summary>Usage example</summary>

```groovy
// build.gradle

repositories {
    exclusiveContent {
        forRepository {
            maven {
                name = "Modrinth"
                url = "https://api.modrinth.com/maven"
            }
        }
        filter {
            includeGroup "maven.modrinth"
        }
    }
}

dependencies {
    modImplementation "maven.modrinth:legacy-mod-menu:1.1.0"
}
```

</details>

### Improved source decompilation

By default the `genSources` task uses the fabric [cfr](https://github.com/FabricMC/cfr) decompiler to generate Minecraft sources.
Vineflower is a decompiler which contains many enhancements and generally produces much better source code. \
With the [loom-vineflower](https://github.com/Juuxel/loom-vineflower) plugin it is possible to integrate it directly into your project.

For more info see https://github.com/Juuxel/loom-vineflower#readme

<details>
    <summary>Usage example</summary>

```groovy
// build.gradle

plugins {
    id 'io.github.juuxel.loom-vineflower' version "1.11.0"
}
```

Instead of `genSources`, you can now run:
```sh
./gradlew genSourcesWithVineflower
```

</details>


## License

This template is available under the CC0 license. Feel free to learn from it and incorporate it in your own projects.

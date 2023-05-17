# Ultron

cnc machine client

## Build

app builds are located at _{app folder}/build/compose/binaries/main_

### Windows

you could use [chocolatey](https://chocolatey.org/install) to ease the build process
- open command prompt as administrator
- install java jdk `choco install oraclejdk`
- install gradle `choco install gradle`
- open application folder in command prompt
- run `gradlew packageDistributionForCurrentOS`
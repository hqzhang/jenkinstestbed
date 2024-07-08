hello world
// Load the shared library
@Library('my-shared-library') _

// Define the Active Choice parameter
properties([
  parameters([
    activeChoiceParam(
      name: 'DYNAMIC_PARAM',
      choiceType: 'SINGLE_SELECT',
      groovyScript: [
        script: 'return sharedLibraryMethod()',
        fallbackScript: 'return ["Default"]'
      ]
    )
  ])
])

def sharedLibraryMethod() {
  // Logic to generate dynamic choices
  return ["Choice1", "Choice2", "Choice3"]
}

/** Set git config of a project to use "githooks" directory for Git hooks. */
class GithooksConfigPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        println("[GithooksConfigPlugin] Setting up git hooks path")
        Runtime.getRuntime().exec("git config core.hooksPath githooks")
    }
}

apply<GithooksConfigPlugin>()

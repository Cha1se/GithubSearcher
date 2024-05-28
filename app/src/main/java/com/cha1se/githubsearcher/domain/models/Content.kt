data class Content(
    val name: String = "",
    val path: String = "",
    val sha: String = "",
    val size: Int = 0,
    val url: String = "",
    val html_url: String = "",
    val git_url: String = "",
    val download_url: String = "",
    val type: String = "",
    val _links: Links = Links(),
    val commit: Commit = Commit()
)

data class Links(
    val self: String = "",
    val git: String = "",
    val html: String = "",
)

data class Commit(
    val author: Author = Author(),
    val committer: Committer = Committer(),
    val html_url: String = "",
    val message: String = "",
    val node_id: String = "",
    val parents: List<Parent> = emptyList(),
    val sha: String = "",
    val tree: Tree = Tree(),
    val url: String = "",
    val verification: Verification = Verification()
)

data class Author(
    val date: String = "",
    val email: String = "",
    val name: String = ""
)

data class Committer(
    val date: String = "",
    val email: String = "",
    val name: String = ""
)

data class Parent(
    val html_url: String = "",
    val sha: String = "",
    val url: String = ""
)

data class Tree(
    val sha: String = "",
    val url: String = ""
)

data class Verification(
    val payload: Any = Any(),
    val reason: String = "",
    val signature: Any = Any(),
    val verified: Boolean = false,
)
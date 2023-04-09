package `fun`.mochen.learn.english.system.exception

class ServiceException(
    override var message: String?,
    var code: Int?,
    var detailMessage: String?
) : RuntimeException() {

    constructor() : this(null, null, null)

    constructor(message: String?) : this(message, null, null)

    constructor(message: String?, code: Int?) : this(message, code, null)

}

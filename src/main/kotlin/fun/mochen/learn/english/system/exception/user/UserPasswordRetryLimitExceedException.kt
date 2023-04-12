package `fun`.mochen.learn.english.system.exception.user

class UserPasswordRetryLimitExceedException(retryLimitCount: Int, lockTime: Int)
    : UserException(412, "重试次数超过${retryLimitCount}次，请${lockTime}分钟后重试")

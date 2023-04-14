package `fun`.mochen.learn.english.system.handle

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler
import `fun`.mochen.learn.english.system.service.TokenService
import org.apache.ibatis.reflection.MetaObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.Date
import javax.servlet.http.HttpServletRequest

/**
 * MybatisPlus自动填充
 * @author MoChen
 */
@Component
class MybatisMetaObjectHandler : MetaObjectHandler {

    @Autowired
    private lateinit var httpServletRequest: HttpServletRequest

    @Autowired
    private lateinit var tokenService: TokenService

    override fun insertFill(metaObject: MetaObject?) {
        if (metaObject == null) {
            return
        }
        this.setFieldValByName("createTime", Date(), metaObject)
        val user = tokenService.getLoginUser(httpServletRequest) ?: return
        this.setFieldValByName("creatorId", user.userId, metaObject)
        this.setFieldValByName("creatorName", user.username, metaObject)
    }

    override fun updateFill(metaObject: MetaObject?) {
        if (metaObject == null) {
            return
        }
        this.setFieldValByName("updateTime", Date(), metaObject)
        val user = tokenService.getLoginUser(httpServletRequest) ?: return
        this.setFieldValByName("updaterId", user.userId, metaObject)
        this.setFieldValByName("updaterName", user.username, metaObject)
    }
}

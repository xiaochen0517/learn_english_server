package `fun`.mochen.learn.english.core.common.utils

import cn.hutool.core.bean.BeanUtil

class BeanUtil : BeanUtil() {

    companion object {
        fun <T> copyList(sourceList: List<Any>, targetClass: Class<T>): List<T> {
            val targetList = mutableListOf<T>()
            sourceList.forEach {
                val target = targetClass.newInstance()
                BeanUtil.copyProperties(it, target)
                targetList.add(target)
            }
            return targetList
        }
    }
}

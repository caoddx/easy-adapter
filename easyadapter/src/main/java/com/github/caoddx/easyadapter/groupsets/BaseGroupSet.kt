package com.github.caoddx.easyadapter.groupsets

import com.github.caoddx.easyadapter.EasyAdapter
import com.github.caoddx.easyadapter.MixGroup


abstract class BaseGroupSet : MixGroup {
    override var adapter: EasyAdapter? = null
}
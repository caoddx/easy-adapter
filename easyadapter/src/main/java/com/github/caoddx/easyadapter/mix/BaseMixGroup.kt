package com.github.caoddx.easyadapter.mix

import com.github.caoddx.easyadapter.EasyAdapter
import com.github.caoddx.easyadapter.MixGroup


abstract class BaseMixGroup : MixGroup {
    override var adapter: EasyAdapter? = null
}
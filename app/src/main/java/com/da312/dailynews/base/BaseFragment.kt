package com.da312.dailynews.base

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class BaseFragment: Fragment {

    constructor() : super()

    constructor(
        @LayoutRes layoutId: Int
    ) : super(layoutId)
}
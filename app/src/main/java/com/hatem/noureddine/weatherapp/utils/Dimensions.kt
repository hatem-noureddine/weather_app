/**************************************************************************
 * Copyright (C) 2020 - John Paul corporation - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 ***************************************************************************/
package com.hatem.noureddine.weatherapp.utils

import android.content.res.Resources
import android.util.TypedValue

/**
 * Convert [Float] to [TypedValue.COMPLEX_UNIT_DIP]
 */
val Float.dp: Float
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, this, Resources.getSystem().displayMetrics
    )

/**
 * Convert [Int] to [TypedValue.COMPLEX_UNIT_DIP]
 */
val Int.dp: Int
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    ).toInt()

/**
 * Convert [Float] to [TypedValue.COMPLEX_UNIT_SP]
 */
val Float.sp: Float
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP, this, Resources.getSystem().displayMetrics
    )

/**
 * Convert [Int] to [TypedValue.COMPLEX_UNIT_SP]
 */
val Int.sp: Int
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    ).toInt()

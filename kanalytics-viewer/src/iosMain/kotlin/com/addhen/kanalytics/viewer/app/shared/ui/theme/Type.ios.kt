package com.addhen.kanalytics.viewer.app.shared.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.platform.Typeface
import org.jetbrains.skia.FontMgr
import org.jetbrains.skia.FontStyle
import org.jetbrains.skia.Typeface

@Composable
internal actual fun jetbrainsMonoFontFamily(): FontFamily {
  val skTypeface = loadCustomFont("JetBrains Mono")

  return try {
    FontFamily(Typeface(skTypeface))
  } finally {
    skTypeface.close()
  }
}

private fun loadCustomFont(familyName: String): Typeface {
  val fontMgr = FontMgr.default

  return fontMgr.matchFamilyStyle(familyName, FontStyle.NORMAL)
    ?: fontMgr.matchFamilyStyle(null, FontStyle.NORMAL) // default system font.
    ?: Typeface.makeEmpty()
}

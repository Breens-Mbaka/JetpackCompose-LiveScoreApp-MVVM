package com.breens.mvvmlivescorestarter.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.breens.mvvmlivescorestarter.R

object Nunito {
    val Light = Font(R.font.nunito_light, FontWeight.W300)
    val Regular = Font(R.font.nunito_regular, FontWeight.W400)
    val Medium = Font(R.font.nunito_medium, FontWeight.W500)
    val Bold = Font(R.font.nunito_bold, FontWeight.W600)
}

private val NunitoFontFamily =
    FontFamily(listOf(Nunito.Light, Nunito.Regular, Nunito.Medium, Nunito.Bold))

val LiveScoreAppTypography = Typography(
    h1 = TextStyle(
        fontFamily = NunitoFontFamily,
        fontWeight = FontWeight.W500,
        fontSize = 30.sp
    ),
    h2 = TextStyle(
        fontFamily = NunitoFontFamily,
        fontWeight = FontWeight.W500,
        fontSize = 24.sp
    ),
    h3 = TextStyle(
        fontFamily = NunitoFontFamily,
        fontWeight = FontWeight.W500,
        fontSize = 20.sp
    ),
    h4 = TextStyle(
        fontFamily = NunitoFontFamily,
        fontWeight = FontWeight.W400,
        fontSize = 18.sp
    ),
    h5 = TextStyle(
        fontFamily = NunitoFontFamily,
        fontWeight = FontWeight.W400,
        fontSize = 16.sp
    ),
    h6 = TextStyle(
        fontFamily = NunitoFontFamily,
        fontWeight = FontWeight.W400,
        fontSize = 14.sp
    ),
    subtitle1 = TextStyle(
        fontFamily = NunitoFontFamily,
        fontWeight = FontWeight.W500,
        fontSize = 16.sp,
    ),
    subtitle2 = TextStyle(
        fontFamily = NunitoFontFamily,
        fontWeight = FontWeight.W400,
        fontSize = 14.sp,
    ),
    body1 = TextStyle(
        fontFamily = NunitoFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    body2 = TextStyle(
        fontFamily = NunitoFontFamily,
        fontSize = 14.sp
    ),
    button = TextStyle(
        fontFamily = NunitoFontFamily,
        fontWeight = FontWeight.W400,
        fontSize = 15.sp,
        color = Color.White
    ),
    caption = TextStyle(
        fontFamily = NunitoFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    overline = TextStyle(
        fontFamily = NunitoFontFamily,
        fontWeight = FontWeight.W400,
        fontSize = 12.sp
    )
)
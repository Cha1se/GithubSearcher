package com.cha1se.githubsearcher.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import com.cha1se.githubsearcher.R

// Set of Material typography styles to start with
val CollegiateFont = FontFamily(Font(R.font.collegiate_regular))
val MonaSansMedium = FontFamily(Font(R.font.mona_sans_medium))
val MonaSansRegular = FontFamily(Font(R.font.mona_sans_regular))
val MonaSansBold = FontFamily(Font(R.font.mona_sans_bold))
val MonaSansSemiBold = FontFamily(Font(R.font.mona_sans_semi_bold))

val Typography = Typography(
    titleMedium = TextStyle( // Title card
        fontFamily = MonaSansMedium,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        color = TitleColor,
        lineHeight = 20.sp,
        textDecoration = TextDecoration.None,
    ),
    bodySmall = TextStyle( // About
        fontFamily = MonaSansRegular,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        color = SecondTextColor,
        lineHeight = 20.sp,
        textDecoration = TextDecoration.None,
    ),
    bodyMedium = TextStyle( // Search
        fontFamily = MonaSansRegular,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        color = Color.White,
        lineHeight = 20.sp,
        textDecoration = TextDecoration.None,
    ),
    titleLarge = TextStyle( // Title app
        fontFamily = CollegiateFont,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        color = SecondTextColor,
        lineHeight = 20.sp,
        textDecoration = TextDecoration.None,
    ),
    labelSmall = TextStyle(
        fontFamily = MonaSansMedium,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        color = SecondTextColor,
        lineHeight = 20.sp,
        textDecoration = TextDecoration.None,
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)
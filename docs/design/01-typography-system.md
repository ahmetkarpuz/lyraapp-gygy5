# LyraApp - Tipografi Sistemi

> Bu dosya LyraApp isimli uygulamanin tipografi yapisi icin
> **tek dogruluk kaynagi** (single source of truth) olup
> dogrudan bir **Android Jetpack Compose** projesinde kullanilmak
> uzere duzenlenmistir.

---

## 1. Temel Kural

> Hicbir `@Composable` icinde ham `TextStyle(...)` tanimlanmaz.
> Yazi stilleri daima `MaterialTheme.typography.<slot>` uzerinden
> okunmak zorundadir.

Ham `TextStyle(..)` tanimi yalnizca `Type.kt` icinde, sabit degisken tanimlanirken kullanilir.

---

## 2. Font Ailesi

| Ozellik       | Deger                          |
|---------------|--------------------------------|
| Font Ailesi   | **Roboto**                     |
| Kaynak        | Google Fonts (bundled)         |
| Format        | `.ttf`                         |
| Konum         | `res/font/`                    |

### Font Agirlik Haritasi

| Agirlik          | Dosya Adi                | FontWeight        |
|------------------|--------------------------|-------------------|
| Thin (100)       | `roboto_thin.ttf`        | `FontWeight.Thin` |
| Light (300)      | `roboto_light.ttf`       | `FontWeight.Light`|
| Regular (400)    | `roboto_regular.ttf`     | `FontWeight.Normal`|
| Medium (500)     | `roboto_medium.ttf`      | `FontWeight.Medium`|
| Bold (700)       | `roboto_bold.ttf`        | `FontWeight.Bold` |
| Black (900)      | `roboto_black.ttf`       | `FontWeight.Black`|

---

## 3. Tipografi Olcek Tablosu (Material 3 Type Scale)

Asagidaki tablo `Type.kt` dosyasinda tanimlanacak olan `Typography` nesnesinin slot'larini gostermektedir.

### Display

| Slot              | FontWeight | FontSize | LineHeight | LetterSpacing |
|-------------------|------------|----------|------------|---------------|
| `displayLarge`    | Normal     | 57 sp    | 64 sp      | -0.25 sp      |
| `displayMedium`   | Normal     | 45 sp    | 52 sp      | 0 sp          |
| `displaySmall`    | Normal     | 36 sp    | 44 sp      | 0 sp          |

### Headline

| Slot              | FontWeight | FontSize | LineHeight | LetterSpacing |
|-------------------|------------|----------|------------|---------------|
| `headlineLarge`   | Normal     | 32 sp    | 40 sp      | 0 sp          |
| `headlineMedium`  | Normal     | 28 sp    | 36 sp      | 0 sp          |
| `headlineSmall`   | Normal     | 24 sp    | 32 sp      | 0 sp          |

### Title

| Slot              | FontWeight | FontSize | LineHeight | LetterSpacing |
|-------------------|------------|----------|------------|---------------|
| `titleLarge`      | Normal     | 22 sp    | 28 sp      | 0 sp          |
| `titleMedium`     | Medium     | 16 sp    | 24 sp      | 0.15 sp       |
| `titleSmall`      | Medium     | 14 sp    | 20 sp      | 0.1 sp        |

### Body

| Slot              | FontWeight | FontSize | LineHeight | LetterSpacing |
|-------------------|------------|----------|------------|---------------|
| `bodyLarge`       | Normal     | 16 sp    | 24 sp      | 0.5 sp        |
| `bodyMedium`      | Normal     | 14 sp    | 20 sp      | 0.25 sp       |
| `bodySmall`       | Normal     | 12 sp    | 16 sp      | 0.4 sp        |

### Label

| Slot              | FontWeight | FontSize | LineHeight | LetterSpacing |
|-------------------|------------|----------|------------|---------------|
| `labelLarge`      | Medium     | 14 sp    | 20 sp      | 0.1 sp        |
| `labelMedium`     | Medium     | 12 sp    | 16 sp      | 0.5 sp        |
| `labelSmall`      | Medium     | 11 sp    | 16 sp      | 0.5 sp        |

---

## 4. `Type.kt` - Kod Yapisi

```kotlin
package com.turkcell.lyraapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.turkcell.lyraapp.R

val Roboto = FontFamily(
    Font(R.font.roboto_thin,    FontWeight.Thin),
    Font(R.font.roboto_light,   FontWeight.Light),
    Font(R.font.roboto_regular, FontWeight.Normal),
    Font(R.font.roboto_medium,  FontWeight.Medium),
    Font(R.font.roboto_bold,    FontWeight.Bold),
    Font(R.font.roboto_black,   FontWeight.Black),
)

val LyraTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = (-0.25).sp,
    ),
    displayMedium = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 45.sp,
        lineHeight = 52.sp,
        letterSpacing = 0.sp,
    ),
    displaySmall = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp,
        lineHeight = 44.sp,
        letterSpacing = 0.sp,
    ),
    headlineLarge = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp,
    ),
    headlineSmall = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp,
    ),
    labelLarge = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
    ),
)
```

---

## 5. Kullanim Kurallari

1. **Tum yazi stilleri** `MaterialTheme.typography.<slot>` uzerinden okunur.
2. Roboto font dosyalari `res/font/` dizininde `.ttf` formatinda barindiriliyor.
3. `Type.kt` dosyasinda `FontFamily.Default` yerine acikca `Roboto` FontFamily kullanilmaktadir.
4. `Theme.kt` dosyasinda `typography = LyraTypography` olarak baglama yapilmaktadir.

---

## 6. Dosya Haritasi

| Dosya                                     | Aciklama                              |
|-------------------------------------------|---------------------------------------|
| `docs/design/01-typography-system.md`     | Tipografi dokumantasyonu (bu dosya)    |
| `app/.../ui/theme/Type.kt`               | Tipografi kod tanimi                   |
| `app/src/main/res/font/roboto_*.ttf`     | Roboto font dosyalari                 |

---

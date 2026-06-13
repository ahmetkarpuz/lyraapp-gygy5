package com.turkcell.lyraapp.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.turkcell.lyraapp.data.home.PlaylistForYou
import com.turkcell.lyraapp.data.home.QuickPick
import com.turkcell.lyraapp.data.home.RecentlyPlayed
import com.turkcell.lyraapp.ui.icons.LyraIcons
import com.turkcell.lyraapp.ui.theme.LyraAppTheme

/**
 * Home akisinin durumlu (stateful) giris noktasi.
 *
 * [HomeViewModel]'i Hilt'ten alir, durumu yasam dongusune duyarli sekilde toplar ve
 * tek seferlik [HomeEffect]'leri tuketir. Yukleme hatasinda snackbar uzerinden
 * "Tekrar dene" aksiyonu [HomeIntent.Retry] niyetine koprulenir.
 */
@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is HomeEffect.ShowError -> {
                    val result = snackbarHostState.showSnackbar(
                        message = effect.message,
                        actionLabel = "Tekrar dene",
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        viewModel.onIntent(HomeIntent.Retry)
                    }
                }
            }
        }
    }

    HomeScreen(
        state = uiState,
        onIntent = viewModel::onIntent,
        snackbarHostState = snackbarHostState,
        modifier = modifier,
    )
}

/**
 * Ana sayfa ("Ne dinlemek istersin?") ekrani.
 *
 * Tamamen durumsuzdur (stateless): durumu [state] uzerinden alir, kullanici etkilesimlerini
 * [onIntent] ile yukari yayimlar. Alt cubuk boslugu dis Scaffold'dan (LyraNavHost) gelir;
 * burada yalnizca durum cubugu (status bar) boslugu yonetilir.
 */
@Composable
fun HomeScreen(
    state: HomeUiState,
    onIntent: (HomeIntent) -> Unit,
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { innerPadding ->
        if (state.isLoading && state.quickPicks.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .statusBarsPadding(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                item { HomeHeader(greeting = state.greeting, userInitials = state.userInitials) }
                item { QuickPickGrid(quickPicks = state.quickPicks) }
                item { SectionHeader(title = "Son calınanlar", trailingText = "Tumu") }
                item { RecentlyPlayedRow(items = state.recentlyPlayed) }
                item { SectionHeader(title = "Senin icin calma listeleri") }
                item { PlaylistsForYouRow(items = state.playlistsForYou) }
            }
        }
    }
}

/** Selamlama + baslik ile tema ikonu ve kullanici avatarini iceren ust bolum. */
@Composable
private fun HomeHeader(
    greeting: String,
    userInitials: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = greeting,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "Ne dinlemek istersin?",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
        Icon(
            imageVector = LyraIcons.LightMode,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(24.dp),
        )
        Spacer(Modifier.width(16.dp))
        UserAvatar(initials = userInitials)
    }
}

@Composable
private fun UserAvatar(initials: String) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primaryContainer),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = initials,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
        )
    }
}

/** Hizli secimlerin 2 sutunlu sabit grid'i (6 oge; dikey scroll LazyColumn'a aittir). */
@Composable
private fun QuickPickGrid(quickPicks: List<QuickPick>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        quickPicks.chunked(2).forEach { rowItems ->
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                rowItems.forEach { item ->
                    QuickPickCard(item = item, modifier = Modifier.weight(1f))
                }
                if (rowItems.size == 1) {
                    Spacer(Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun QuickPickCard(
    item: QuickPick,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .height(56.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceContainerHigh),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Artwork(
            startColor = item.artworkStartColor,
            endColor = item.artworkEndColor,
            modifier = Modifier
                .width(56.dp)
                .fillMaxHeight(),
        )
        Text(
            text = item.title,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(horizontal = 10.dp),
        )
    }
}

/** Bolum basligi; [trailingText] verilirse sagda vurgu rengiyle gosterilir (orn. "Tumu"). */
@Composable
private fun SectionHeader(
    title: String,
    trailingText: String? = null,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f),
        )
        if (trailingText != null) {
            Text(
                text = trailingText,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary,
            )
        }
    }
}

/** "Son calınanlar" yatay scrollable kart listesi. */
@Composable
private fun RecentlyPlayedRow(items: List<RecentlyPlayed>) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        items(items, key = { it.id }) { item ->
            Column(modifier = Modifier.width(150.dp)) {
                Artwork(
                    startColor = item.artworkStartColor,
                    endColor = item.artworkEndColor,
                    modifier = Modifier
                        .size(150.dp)
                        .clip(RoundedCornerShape(16.dp)),
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = item.subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

/** "Senin icin calma listeleri" yatay scrollable buyuk kart listesi. */
@Composable
private fun PlaylistsForYouRow(items: List<PlaylistForYou>) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        items(items, key = { it.id }) { item ->
            Column(modifier = Modifier.width(170.dp)) {
                Artwork(
                    startColor = item.artworkStartColor,
                    endColor = item.artworkEndColor,
                    modifier = Modifier
                        .size(170.dp)
                        .clip(RoundedCornerShape(20.dp)),
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

/**
 * Kapak gorseli yer tutucusu: modeldeki ARGB renk ciftinden kosegen gradyan + hafif
 * radyal parlama cizer. Gercek API gorsel URL'si sagladiginda bu composable gorsel
 * yukleyiciyle degistirilir.
 */
@Composable
private fun Artwork(
    startColor: Long,
    endColor: Long,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .background(Brush.linearGradient(listOf(Color(startColor), Color(endColor))))
            .background(
                Brush.radialGradient(
                    listOf(Color.White.copy(alpha = 0.16f), Color.Transparent),
                ),
            ),
    )
}

private val previewState = HomeUiState(
    greeting = "Iyi aksamlar",
    userInitials = "ZK",
    quickPicks = listOf(
        QuickPick("qp-1", "Gece Surusu", 0xFF8B6FB8, 0xFF4A3D6B),
        QuickPick("qp-2", "Sabah Kahvesi", 0xFF7C83D9, 0xFF3E4486),
        QuickPick("qp-3", "Neon Sokaklar", 0xFFD98E4A, 0xFF8A5526),
        QuickPick("qp-4", "Odaklan", 0xFF4AC2A8, 0xFF1F6E5C),
        QuickPick("qp-5", "Derin Mavi", 0xFF6FBF5A, 0xFF356B2A),
        QuickPick("qp-6", "Yaz Anilari", 0xFF5AAFC9, 0xFF2A5F73),
    ),
    recentlyPlayed = listOf(
        RecentlyPlayed("rp-1", "Neon Sokaklar", "Sehir Isiklari", 0xFFD98E4A, 0xFF8A5526),
        RecentlyPlayed("rp-2", "Derin Mavi", "Okyanus", 0xFF6FBF5A, 0xFF356B2A),
        RecentlyPlayed("rp-3", "Yildiz Tozu", "Polaris", 0xFF3D5A80, 0xFF1B2A45),
    ),
    playlistsForYou = listOf(
        PlaylistForYou("pl-1", "Haftalik Kesif", 0xFF9B7FC4, 0xFF5A4480),
        PlaylistForYou("pl-2", "Sakin Aksamlar", 0xFF6B5FB8, 0xFF3A3270),
        PlaylistForYou("pl-3", "Enerji Ver", 0xFF3FAE9C, 0xFF1E5D52),
    ),
)

@Preview(name = "Home - Dark", showBackground = true, showSystemUi = true)
@Composable
private fun HomeScreenDarkPreview() {
    LyraAppTheme(darkTheme = true) {
        HomeScreen(state = previewState, onIntent = {})
    }
}

@Preview(name = "Home - Light", showBackground = true, showSystemUi = true)
@Composable
private fun HomeScreenLightPreview() {
    LyraAppTheme(darkTheme = false) {
        HomeScreen(state = previewState, onIntent = {})
    }
}

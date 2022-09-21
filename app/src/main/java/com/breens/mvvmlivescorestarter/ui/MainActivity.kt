package com.breens.mvvmlivescorestarter.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.breens.mvvmlivescorestarter.R
import com.breens.mvvmlivescorestarter.data.remote.models.Data
import com.breens.mvvmlivescorestarter.ui.theme.Green900
import com.breens.mvvmlivescorestarter.ui.theme.MVVMLiveScoreStarterTheme
import com.breens.mvvmlivescorestarter.ui.util.imageLoader
import com.breens.mvvmlivescorestarter.viewmodel.MatchesViewModel
import com.breens.mvvmlivescorestarter.viewmodel.state.MatchesState
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MVVMLiveScoreStarterTheme {
                Column(modifier = Modifier.padding(10.dp)) {
                    TopAppBar()
                    FetchData()
                }
            }
        }
    }
}

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun FetchData(
    matchesViewModel: MatchesViewModel = viewModel()
) {

    val upcomingMatchesState = matchesViewModel.upcomingMatchesState.collectAsStateWithLifecycle()

    val inplayMatchesState = matchesViewModel.inPlayMatchesState.collectAsStateWithLifecycle()

    Column {
        when (val state = inplayMatchesState.value) {
            is MatchesState.Empty -> Text(text = "No data available")
            is MatchesState.Loading -> Text(text = "Loading...")
            is MatchesState.Success -> LiveMatches(liveMatches = state.data)
            is MatchesState.Error -> Text(text = state.message)
        }
        when (val state = upcomingMatchesState.value) {

            is MatchesState.Empty -> Text(text = "No data available")
            is MatchesState.Loading -> Text(text = "Loading...")
            is MatchesState.Success -> UpcomingMatches(upcomingMatches = state.data)
            is MatchesState.Error -> Text(text = state.message)
        }
    }
}

@Composable
fun LiveMatches(liveMatches: List<Data>) {

    Column(modifier = Modifier.padding(15.dp)) {
        Text(
            text = "Live Matches",
            style = MaterialTheme.typography.h3,
            modifier = Modifier.padding(top = 12.dp)
        )

        if (liveMatches.isEmpty()) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "No Live Matches Currently"
                )
                Text(
                    text = "No Live Matches Currently",
                    style = MaterialTheme.typography.h6
                )
            }
        } else {

            LazyRow(
                modifier = Modifier.padding(top = 15.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(liveMatches.size) {
                    LiveMatchItem(match = liveMatches[it])
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LiveMatchItem(match: Data) {

    Card(
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier
            .width(300.dp)
            .height(150.dp),
        elevation = 0.dp
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(
                text = match.group.group_name,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.h5
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val homeScore = match.stats.home_score
                val awayScore = match.stats.away_score

                Text(
                    text = match.home_team.common_name,
                    style = MaterialTheme.typography.h6
                )
                Text(
                    text = "$homeScore:$awayScore",
                    style = MaterialTheme.typography.h5
                )
                Text(
                    text = match.away_team.common_name,
                    style = MaterialTheme.typography.h6
                )
            }
            Chip(
                onClick = { /*TODO*/ },
                colors = ChipDefaults.chipColors(
                    contentColor = Color.White,
                    backgroundColor = Green900
                ),
                modifier = Modifier
                    .align(
                        Alignment.CenterHorizontally
                    )
                    .padding(top = 20.dp)
            ) {
                Text(matchStatus(match))
            }
        }
    }
}

fun matchStatus(match: Data): String {
    return when (match.status_code) {
        1 -> "${match.minute} '"
        11 -> "half time"
        0 -> "not started"
        3 -> "finished"
        5 -> "cancelled"
        4 -> "postponed"
        17 -> "to be announced"
        12 -> "extra time"
        13 -> "penalties"
        else -> "-"
    }
}

@Composable
fun UpcomingMatches(upcomingMatches: List<Data>) {

    Column(modifier = Modifier.padding(15.dp)) {

        Text(
            text = "Scheduled Matches",
            style = MaterialTheme.typography.h3,
            modifier = Modifier.padding(top = 12.dp)
        )

        val notStartedUpcomingMatches =
            upcomingMatches.filter { it.status_code == 1 || it.status_code == 17 }
        if (notStartedUpcomingMatches.isEmpty()) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "No Upcoming Matches Currently"
                )
                Text(
                    text = "No Upcoming Matches Currently",
                    style = MaterialTheme.typography.h6
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(top = 15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(upcomingMatches.size) {
                    UpcomingMatchItem(match = notStartedUpcomingMatches[it])
                }
            }
        }
    }
}

@Composable
fun UpcomingMatchItem(match: Data) {
    Card(
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(bottom = 10.dp),
        elevation = 0.dp
    ) {
        val homeTeamLogo = imageLoader(url = match.home_team.logo).value
        val awayTeamLogo = imageLoader(url = match.away_team.logo).value
        val month = getMatchDayAndMonth(match.match_start)
        val time = getMatchTime(match.match_start)

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier.weight(0.5f),
                verticalArrangement = Arrangement.spacedBy(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                homeTeamLogo?.let {
                    Image(
                        modifier = Modifier.size(30.dp),
                        bitmap = homeTeamLogo.asImageBitmap(),
                        contentDescription = "Home Team Logo",
                        contentScale = ContentScale.Crop
                    )
                }
                Text(
                    text = match.home_team.name,
                    style = MaterialTheme.typography.h6,
                    textAlign = TextAlign.Center
                )
            }

            Text(
                modifier = Modifier.weight(0.5f),
                text = "$time\n$month",
                style = MaterialTheme.typography.h6,
                color = Green900,
                textAlign = TextAlign.Center
            )

            Column(
                modifier = Modifier.weight(0.5f),
                verticalArrangement = Arrangement.spacedBy(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                awayTeamLogo?.let {
                    Image(
                        modifier = Modifier.size(30.dp),
                        bitmap = awayTeamLogo.asImageBitmap(),
                        contentDescription = "Away Team Logo",
                        contentScale = ContentScale.Crop
                    )
                }
                Text(
                    text = match.away_team.name,
                    style = MaterialTheme.typography.h6,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun TopAppBar(matchesViewModel: MatchesViewModel = viewModel()) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {
            matchesViewModel.getAllInPlayMatches()
            matchesViewModel.getUpcomingMatches()
        }) {
            Icon(imageVector = Icons.Default.Refresh, contentDescription = "Refresh Icon")
        }

        Text(text = "LiveScores", style = MaterialTheme.typography.h4)

        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.modeicon),
                contentDescription = "Toggle Theme"
            )
        }
    }
}

fun getMatchDayAndMonth(date: String): String? {
    val parser = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
    val formatter = SimpleDateFormat("d MMM", Locale.ENGLISH)
    return date.let { it -> parser.parse(it)?.let { formatter.format(it) } }
}

fun getMatchTime(date: String): String? {
    val parser = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
    val formatter = SimpleDateFormat("hh:mm a", Locale.ENGLISH)//06:30 pm
    return date.let { it -> parser.parse(it)?.let { formatter.format(it) } }
}